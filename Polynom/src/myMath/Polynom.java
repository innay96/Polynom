package myMath;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.StringTokenizer;

import myMath.Monom;
/**
 * 
 * This class represents a polynomial with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Inna and Chen
 *
 */
public class Polynom implements Polynom_able {

	/**
	 * 
	 */
	public ArrayList<Monom> polynom;
	private final Monom_Comperator comp = new Monom_Comperator();

	/**
	 * Empty Constructor- Zero polynomial
	 */
	public Polynom() { 
		polynom = new ArrayList<Monom>();
	}

	/**
	 * Constructor creates a polynomial according to the String input:
	 * @param s
	 * @throw Exception
	 */
	public Polynom(String s) {
		//Throws Exceptions 
		s = s.replaceAll(" " , "");
		if (s == null) throw new RuntimeException("Not a polynomial, it's a null");
		if (s == "" || s == "0") throw new RuntimeException("Not a polynomial, it's empty");
		boolean conatainsLetter = true;
		for (char i =58; (i < 88) && conatainsLetter ; i++) {//Besides 88 = X
			if(s.contains(""+i))   conatainsLetter = false;}
		for (char i =89; (i < 94) && conatainsLetter ; i++) {//Besides 94 = ^
			if(s.contains(""+i))   conatainsLetter = false;}
		for (char i =95; (i < 120) && conatainsLetter ; i++) {//Besides 120 = x
			if(s.contains(""+i))   conatainsLetter = false;}
		for (char i =121; (i < 127) && conatainsLetter ; i++) {
			if(s.contains(""+i))   conatainsLetter = false;}
		for (char i =33; (i < 42) &&  conatainsLetter ; i++) {//Signs
			if(s.contains(""+i))    conatainsLetter = false;}
		if(!conatainsLetter) {throw new RuntimeException("Not a polynomial, please enter a valid polynomial");}


		polynom = new ArrayList<Monom>();
		Monom c;

		StringTokenizer p = new StringTokenizer(s, "+");
		while(p.hasMoreTokens()) {
			String monom = p.nextToken();
			int lastChar;
			if (monom.contains("-")) {
				if(monom.contains("--")){throw new RuntimeException("Incorrect syntax\n Please enter '+' instead of '--'" );}
				String [] negative = monom.split("-");
				//Besides the first String in the array- all the other with negative coefficient for sure
				if(monom.charAt(0) == '-') { //first coefficient is negative also
					lastChar = negative[1].length()-1;
					if(negative[1].charAt(lastChar) == '^') throw new RuntimeException("Invalid power - Can not use a negative power");
					c = new Monom("-" + negative[1]);
					addMonomInit(c);
					for (int i = 2; i < negative.length; i++) {
						lastChar = negative[i].length()-1;
						if(negative[i].charAt(lastChar) == '^') throw new RuntimeException("Invalid power - Can not use a negative power");
						c = new Monom("-" + negative[i]);
						addMonomInit(c);
					}
				}
				else{ //first coefficient is positive and the rest negative
					lastChar = negative[0].length()-1;
					if(negative[0].charAt(lastChar) == '^') throw new RuntimeException("Invalid power - Can not use a negative power");
					c = new Monom(negative[0]);
					addMonomInit(c);
					for (int i = 1; i < negative.length; i++) { 
						lastChar = negative[i].length()-1;
						if(negative[i].charAt(lastChar) == '^') throw new RuntimeException("Invalid power - Can not use a negative power");
						c = new Monom("-" + negative[i]);
						addMonomInit(c);
					}
				}
			}
			else {
				c = new Monom (monom);
				addMonomInit(c);
			}
		}
		polynom.sort(comp);
	}

	/**
	 * Deep copy constructor
	 */
	public Polynom (Polynom other) {
		this.polynom = new ArrayList<Monom>(other.polynom);
	}


	/*Private void function: Support the String constructor 
	 *To avoid sorting more than once during the construction process
	 *Checks for the same coefficient and adds the monom
	 * @param monom   
	 */
	private void addMonomInit(Monom c) {
		if( c.getCoefficient() != 0) {
			Iterator<Monom> i = polynom.iterator();
			Monom m =new Monom(0,0);
			boolean flag = true;
			while(i.hasNext() && flag) {
				m = i.next();
				if(m.getPower() == c.getPower()) {
					m.add(c);
					flag = false;
					if(m.getCoefficient() == 0) i.remove();
				}
			}
			if (flag) { 
				add(c);
			}
		}
	}


	/**
	 * Add m1 to this polynomial
	 * @param c Monom
	 */
	public void add(Monom c) {
		boolean flag = true;
		if( c.getCoefficient() == 0) flag = false;
		Iterator<Monom> i = polynom.iterator();
		Monom m =new Monom(0,0);
		while(i.hasNext() && flag) {
			m = i.next();
			if(m.getPower() == c.getPower()) {
				m.add(c);
				flag = false;
				if(m.getCoefficient() == 0) i.remove();
			}
		}
		if (flag) {
			polynom.add(c);
			polynom.sort(comp);
		}
	}

	/**
	 * Add p1 to this polynomial
	 * @param p1
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> i = p1.iteretor();
		Monom m = new Monom(0,0);
		while(i.hasNext()) {
			m = new Monom(i.next())	;
			add(m);
		}
	}

	/**
	 *  A simple function of type y=f(x)
	 **/
	@Override
	public double f(double x) {
		double ans = 0;
		Iterator<Monom> iter = polynom.iterator();
		while(iter.hasNext()) {
			Monom m = iter.next();
			ans = ans + m.f(x);
		}
		return ans;
	}

	/**
	 * Compute the approximate value of polynomial's roots by:
	 * compute a value x' (x0 smaller/equal to x'smaller/equal to x1) for with |f(x')| smaller than eps
	 * assuming f(x0)*f(x1) smaller/equal to 0, returns f(x2) such that:
	 * (i) x0 smaller/equal to x2 smaller/equal to  x1 and (ii) f(x2) smaller than eps
	 * reference source: https://en.wikipedia.org/wiki/Bisection_method
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value
	 * @return
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		double left = x0;
		double right = x1;
		if (left > right  ||  this.f(left) * this.f(right) > 0) {
			throw new RuntimeException("Invalid parameters");
		}
		while(right-left > eps) {
			double mid = (right + left)/2;
			if(this.f(left)*this.f(mid) <= 0) right = mid;
			else left = mid;
		}
		return (left + right)/2;
	}

	/**
	 * Deep copy
	 */
	@Override
	public Polynom_able copy() {
		Polynom copy = new Polynom();
		Iterator<Monom> i = this.polynom.iterator();
		while(i.hasNext()) {
			Monom n = new Monom(i.next());//for deep copy of all monoms use the save word new 
			copy.add(n);
		}
		return copy;
	}


	/**
	 * Compute a new polynomial which is the derivative of this polynomial
	 * @return
	 */
	@Override
	public Polynom_able derivative() {
		Iterator<Monom> i = this.polynom.iterator();
		Polynom d = new Polynom();
		while(i.hasNext()) {
			Monom n = (i.next().derivative());
			d.add(n);
		}
		return d;
	}

	/**
	 * calculating the area of a rectangle till we get the whole area of the integral 
	 * @return the approximated area above the x-axis below this polynomial and between the [x0,x1] range.
	 * @param x0 starting point
	 * @param x1 end point
	 * @param eps step (positive) value
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double S = 0;
		double pointX = x0;
		while(pointX <= x1){
			Iterator<Monom> iter = this.iteretor();
			while(iter.hasNext()) {
				Monom m = iter.next();
				S += m.f(pointX)*eps;
			}
			pointX += eps;
		}
		return S;
	}

	/**
	 * @return an Iterator (of Monoms) over this polynomial
	 * @return
	 */
	@Override
	public Iterator<Monom> iteretor() {
		return this.polynom.iterator();
	}


	/**
	 * Subtract p1 to this polynomial
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
				if(m.getPower() == m2.getPower()) { // same power
					Monom after_sub = new Monom(m2.getCoefficient() - m.getCoefficient(), m2.getPower());
					found_power = true;
					if(after_sub.getCoefficient()==0) { 
						iter2.remove();
					}
					else
						ans.add(after_sub);
				}
			}
			if(!found_power) {
				Monom minus = new Monom((-1)*m.getCoefficient(), m.getPower());
				ans.add(minus);
			}
		}
		this.polynom = ans.polynom;
	}

	/**
	 * Multiply this polynomial by p1
	 * @param p1
	 */
	public void multiply(Polynom_able p1) {
		Polynom ans = new Polynom();

		Polynom s = new Polynom(p1.toString());
		if(!this.isZero() && !p1.isZero()) {
			Iterator<Monom> iter = this.iteretor();
			Iterator<Monom> iterP1 = s.iteretor();
			if(polynom.size() <= s.polynom.size()) {
				while(iter.hasNext()) {
					Monom m = iter.next();
					while(iterP1.hasNext()){
						Monom mP1 = iterP1.next();
						mP1.multiply(m);
						ans.add(mP1);
					}
				}
			}
			else {
				while(iterP1.hasNext()) {
					Monom m = iterP1.next();
					while(iter.hasNext()){
						Monom mP1 = iter.next();
						mP1.multiply(m);
						ans.add(mP1);
					}
				}
			}
		}
		this.polynom = ans.polynom;
	}



	/**
	 * Check if this polynomial equals to other 
	 * @param p1
	 * @return
	 */
	public boolean equals (Polynom_able p1) {
		boolean ans = false;
		if(this.polynom.size() == ((Polynom)p1).polynom.size()) {
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
	 * Test if this is the Zero polynomial
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
	 * @return this polynomial represent by a String 
	 */
	public String toString() {
		if(polynom.size() == 0 )  return "Polynomial not initialized";
		Iterator<Monom> i = polynom.iterator();
		Monom m = i.next();
		String ans = m.toString();
		while(i.hasNext()) {
			m = i.next();	
			if(m.getCoefficient()<0) ans = ans + "" + m.toString();
			else ans = ans + "+" + m.toString();
		}
		return ans ;
	}

	/**
	 * the main func runs the graph class and sends to it the desirable polynomial
	 * @param args
	 */
	public static void main(String[]args) {
		Polynom n = new Polynom("0.2x^4-1.5x^3+3.0x^2 -x -5");
		GraphP g = new GraphP();
		g.setVisible(true);
		g.setPoly(n);
		g.drawGraph();
	}
}


