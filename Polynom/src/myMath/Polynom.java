package myMath;

import java.util.ArrayList;
import java.util.Iterator;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	private ArrayList<Monom> polynom;
	private static final Monom_Comperator COMP = new Monom_Comperator();

	// ********** add your code below ***********

	/**
	 * constructor
	 */
	public Polynom() {
		polynom = new  ArrayList<Monom>();
	}

	/**
	 * init: creates a polynom according to the string we get 
	 * @param s
	 * @throws Exception
	 */
	public Polynom(String s) {
		this();
		Polynom th = init_from_string(s);
		Iterator<Monom> iter = th.iteretor();
		while(iter.hasNext()) {
			this.add(iter.next());
		}
	}

	/**
	 * checks if the string id a polynom then turns it to a numeric polynom
	 * @param s
	 * @return numeric polynom 
	 * @throws Exception
	 */
	private Polynom init_from_string(String s){
		if(s == null) throw new RuntimeException("not a polynom, its a null");
		String[] mon = s.split(" ");
		Polynom ans = new Polynom();
		for(int i=0;i<mon.length;i++) {
			String t = mon[i];
			if(!t.contains("+") || t.length()>1) {
				Monom m = new Monom(t);
				ans.add(m);
			}
		}
		return ans; 
	}

	/**
	 * deep copy
	 */
	@Override
	public Polynom_able copy() {
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			this.add(new Monom(m));
		}
		return this;
	}

	/**
	 * Test if this polynom is logically equals to p1.
	 * @param p1
	 * @return true if this polynom represents the same function as p1
	 */
	public boolean equals (Polynom_able p1) {
		boolean ans = false;
		if(this.size() == ((Polynom) p1).size()) {
			ans = true;
			Iterator<Monom> iter = this.iteretor();
			Iterator<Monom> iter1 = p1.iteretor();
			while(ans && iter.hasNext()) {
				Monom m = iter.next();
				Monom m1 = iter1.next();
				if(!m.equals(m1)) {
					ans = false;
				}
			}
		}
		return ans;
	}

	/**
	 * checks the size of the polynom
	 * @return size of polynom
	 */
	public int size() {
		return this.polynom.size();
	}

	/**
	 * Add p1 to this Polynom
	 * @param p1
	 */
	public void add(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			this.add(m);
		}
	}

	/**
	 * Add m1 to this Polynom
	 * @param m1 Monom
	 */
	public void add(Monom m1) {
		boolean found_power = false;
		Iterator<Monom> iter = this.iteretor();
		while(!found_power && iter.hasNext()) {
			Monom m = iter.next();
			if(m.get_power() == m1.get_power()) { // same power
				m.add(m1);
				found_power = true;
				if(m.get_coefficient()==0) { 
					iter.remove();
				}
			}
		}
		if(!found_power) {
			polynom.add(m1);
			this.polynom.sort(COMP);
		}
	}

	/**
	 * Multiply this Polynom by p1
	 * @param p1
	 */
	public void multiply(Polynom_able p1) {
		Polynom ans = new Polynom();
		if(!this.isZero() && !p1.isZero()) {
			Iterator<Monom> iter = this.iteretor();
			Iterator<Monom> iterP1 = p1.iteretor();
			while(iter.hasNext()) {
				Monom m = iter.next();
				while(iterP1.hasNext()){
					Monom mP1 = iterP1.next();
					mP1.multiply(m);
					ans.add(mP1);
				}
			}
		}
		this.polynom= ans.polynom;
	}

	/**
	 *  a simple function of type y=f(x)
	 **/
	@Override
	public double f(double x) {
		double ans = 0;
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			ans += m.f(x);
		}
		return ans;
	}

	/**
	 * substract p1 to this Polynom
	 * @param p1
	 */
	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> iter = p1.iteretor();
		Polynom ans = new Polynom();
		while(iter.hasNext()) {
			Monom m = iter.next();
			boolean found_power = false;
			//this.substract(m);
			Iterator<Monom> iter2 = this.iteretor();
			while(!found_power && iter2.hasNext()) {
				Monom m2 = iter2.next();
				if(m.get_power() == m2.get_power()) { // same power
					Monom after_sub = new Monom(m2.get_coefficient() - m.get_coefficient(), m2.get_power());
					found_power = true;
					if(after_sub.get_coefficient()==0) { 
						iter2.remove();
					}
					else
						ans.add(after_sub);
				}
			}
			if(!found_power) {
				Monom minus = new Monom("-1");
				m.multiply(minus);
				ans.add(m);
			}
		}
		this.polynom = ans.polynom;
	}

	/**
	 * Test if this is the Zero Polynom
	 * @return
	 */
	@Override
	public boolean isZero() {
		if(this.polynom.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Compute a value x' (x0<=x'<=x1) for with |f(x')| < eps
	 * assuming (f(x0)*f(x1)<=0, returns f(x2) such that:
	 * *	(i) x0<=x2<=x2 && (ii) f(x2)<eps
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value
	 * @return
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		return 0;
	}

	/**
	 * Compute a new Polynom which is the derivative of this Polynom
	 * @return
	 */
	@Override
	public Polynom_able derivative() {
		Polynom poly = new Polynom();
		//ArrayList<Monom> newPoly = new ArrayList<Monom>();
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			poly.add(m.derivative());
		}
		return poly;
	}

	/**
	 * calculating the area of a rectangle till we get the whole area of the integral 
	 * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range.
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double S = 0;
		double pointX = x0;
		while(pointX <= x1){
			Iterator<Monom> iter = this.iteretor();
			while(iter.hasNext()) {
				Monom m = iter.next();
				S += m.f(pointX);
			}
			pointX += eps;
		}
		return S;
	}

	@Override
	public Iterator<Monom> iteretor() {
		return this.polynom.iterator();
	}

	public String toString() {
		String ans = "";
		if(this.isZero()) {
			return "0";
		}
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			if(m.get_coefficient() == 0)
				ans += "0 + ";
			else
				if(m.get_power() == 0)
					ans += m.get_coefficient() + " + ";
				else
					ans += m.get_coefficient() + "x^" +  m.get_power() + " + ";
		}
		return ans;
	}
}
