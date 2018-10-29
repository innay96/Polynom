
package myMath;

import java.util.Iterator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{

	private double _coefficient;  
	private int _power; 
	
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}
	// ***************** add your code below **********************
	//constructor
	public Monom(String st){
		Monom mn = subSt(st);
		this.set_coefficient(mn.get_coefficient());
		this.set_power(mn.get_power());
	}

	//substring the monom we got to check if its valid and separate it to numbers
	private static Monom subSt(String s){
		if(s == null)
			throw new RuntimeException ("its not a monom, its null ! ");
		double a = 0.0;
		int b = 0;
		if(s.contains("x")){
			int indx = s.indexOf("x");
			if(s.substring(0, indx).equals("0")){
				Monom monom = new Monom("0.0");
				return monom;
			}
			else
				if(s.substring(0, indx).equals(""))
					a = 1.0;
			else
				a = Double.parseDouble(s.substring(0, indx));
			if(s.length()>indx+2) 
				b = Integer.parseInt(s.substring(indx+2));
			else
				b = 1;
		}
		else{ // just a number -> no power
			a = Double.parseDouble(s);
		}
		Monom monom = new Monom(a, b);
		return monom;
	}

	/**
	 * Compute a new monom which is the derivative of this monom
	 * @return
	 */
	public Monom derivative() {
		double a = this.get_coefficient()*this.get_power();
		int b = this.get_power() - 1;
		return new Monom(a,b);
	}

	/**
	 * adds m to this monom
	 * @param m
	 */
	public void add(Monom m){
		if(m.get_power() != this.get_power()) {
			throw new RuntimeException("cant add two monoms with different coefficients");
		}
		this.set_coefficient(m.get_coefficient() + this.get_coefficient());
	}

	/**
	 * multiplies this monom by m
	 * @param m
	 */
	public void multiply(Monom m){
		this.set_coefficient(this.get_coefficient() * m.get_coefficient());
		this.set_power(this.get_power() + m.get_power());
	}

	//returns value at x using the implemented function
	@Override
	public double f(double x){
		double a = get_coefficient();
		int b = get_power();
		double ans = a*Math.pow(x, b);
		return ans;
	}


	public String toString() {
		String ans = "";
		if(this.get_coefficient() == 0)
			ans += "0 + ";
		else
			if(this.get_power() == 0)
				ans += this.get_coefficient() + " + ";
			else
				ans += this.get_coefficient() + "x^" +  this.get_power() + " + ";
		return ans;
	}
	public int get_power() {
		return this._power;
	}
	public double get_coefficient() {
		return _coefficient;
	}
	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		this._power = p;
	}

}
