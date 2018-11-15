package myMath;

import java.util.StringTokenizer;

/**This class represents a simple "Monom" of shape a*x^b,
 * where a is a real number and b is an integer (summed a non negative).
 * The class implements function and support simple operations
 * as: construction, value at x, derivative, add and multiply.
 */

public class Monom implements function {

	private double coefficient;
	private int power;

	/**
	 * values constructor - gets 2 parameters and create new Monom
	 * @param a - coefficient
	 * @param b - power
	 * @throw Exception if the power's value is negative number
	 */
	public Monom(double a,int b) {
		if (b < 0) throw new RuntimeException("Invalid power - Can not use a negative power");
		this.setCoefficient(a);
		this.setPower(b);
	}

	/**
	 * Deep copy constructor
	 *  * @param other  Monom
	 */
	public Monom(Monom other) {
		this(other.coefficient, other.power);
	}

	
	/**
	 * Constructor creates a Monom according to the String input:
	 * @param monom - String input
	 */
	public Monom(String monom) {
		monom = monom.replaceAll("X","x");
		double coe = 1;
		int pow = 1;

		if(monom.equals("x")) {
			this.setCoefficient(coe);
			this.setPower(pow);
		}

		else if(monom.equals("-x")) {
			this.setCoefficient(-1);
			this.setPower(pow);
		}

		else{
			if(monom.charAt(0) == '-') {//the coefficient is negative
				coe = -1;
				monom = monom.substring(1);
			}		

			StringTokenizer m = new StringTokenizer(monom, "*xX^");
			if (monom.charAt(0) != 'x') {//the coefficient is not 1
				if(!(monom.contains("x"))) {
					coe = coe *Double.parseDouble(m.nextToken());
					pow = 0;
				}
				else {//The power> 0
					coe = coe* Double.parseDouble(m.nextToken());
					if(m.hasMoreTokens()) {
						pow = Integer.parseInt(m.nextToken());
					}		
				}
			}
			else { // The power> 0 && coefficient = 1
				String POW = m.nextToken();
				if ( POW.charAt(0) == '-') throw new RuntimeException("Invalid power - Can not use negative power");
				pow = Integer.parseInt(POW);
			}
			if (pow < 0) throw new RuntimeException("Invalid power - Can not use negative power");
			this.setCoefficient(coe);
			this.setPower(pow);
		}
	}


	/**Compute the value for f(x) 
	 * @param n the defined Monom
	 * @param x the value for computing
	 * @return a*x^b
	 */
	public double f(double x) {
		return ( coefficient * (Math.pow(x, power))); 
	}


	/**
	 * Compute the derivative of the Monom at x-
	 * 1: Take the constant out (a*f)' = a*f'
	 * 2: Apply the power rule: (x^b)' = b*x^(b-1)
	 */
	public Monom derivative() {
		Monom d = new Monom(0,0);
		double newCoefficient  =  ( this.power * this.coefficient );
		int newPower = this.power - 1;
		if (newPower < 0) return d;
		else {
			d.setCoefficient(newCoefficient);
			d.setPower(newPower);
		}
		return d;
	}

	/** 
	 * multiplies this monom by m
	 * @param m
	 */
	public void multiply(Monom m){
		this.setCoefficient(this.getCoefficient() * m.getCoefficient());
		this.setPower(this.getPower() + m.getPower());
	}

	/**
	 * Check if this monom equals to other monom
	 * @param m
	 * @return
	 */
	public boolean equals(Monom m) {
		boolean ans = true;
		if(this.coefficient != m.coefficient) ans = false;
		if(this.power != m.power) ans = false;
		return ans;
	}
	

	/**
	 * get a monom and add it to the exist one
	 * @param m2
	 */
	public void add(Monom m2) {
		if (m2.getPower() != this.getPower()) throw new RuntimeException("Not the same Monom's power - Can not add");
		else {
			double newCoefficient = m2.coefficient + this.coefficient;
			this.coefficient = newCoefficient;
		}
	}
	
	/**
	 * @return this Monom represent by a String 
	 */
	public String toString() {
		String m = "";
		if((this.power == 0) && (this.coefficient < 0)) m =  "" + "-" +(-1)* this.coefficient;	//-a
		else if((this.power == 0) && (this.coefficient > 0)) m =  "" + this.coefficient; //a
		else if ((this.power == 1) && (this.coefficient == -1)) m = "" + "-x"; //-x
		else if ((this.power == 1) && (this.coefficient < 0)) m = ""+ "-" +(-1)* this.coefficient + "x"; //-ax
		else if ((this.power == 1) && (this.coefficient == 1)) m = "" + "x"; //x
		else if ((this.power == 1) && (this.coefficient > 0)) m = "" + this.coefficient + "x"; //ax
		else if ((this.power > 1) && (this.coefficient == -1)) m = "" + "-x^"  + this.power; //-x^b
		else if ((this.power > 1) && (this.coefficient < 0)) m = "" + "-" +(-1)* this.coefficient + "x^"  + this.power; //-ax^b
		else if ((this.power > 1) && (this.coefficient == 1)) m = "" + "x^"  + this.power; //x^b
		else if ((this.power > 1) && (this.coefficient > 0)) m = "" + this.coefficient + "x^"  + this.power; //ax^b
		return m;
	}
	
	/**
	 * @return this Monom coefficient 
	 */
	public double getCoefficient() {
		return coefficient;
	}


	/**
	 * @return this Monom power 
	 */
	public int getPower() {
		return power;
	}
	
	//****************** Private Methods and Data *****************
		private void setCoefficient(double a){
			this.coefficient = a;
		}

		private void setPower(int p) {
			this.power = p;
		}

}
