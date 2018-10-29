package myMath;

import static org.junit.Assert.*;


import org.junit.Test;

public class PolynomTest {
	Polynom pa = new Polynom("2x^2");
	Polynom paEqual = new Polynom("4x^3 + 5x^2");
	Polynom paAdd = new Polynom("2x^2");
	Polynom paMul = new Polynom("2x^1 + 2");
	Polynom paF = new Polynom("2x^3 + 4");
	Polynom paSub = new Polynom("1x^2 + 1");

	@Test
	public void testPolynomString(){
		Polynom pS = new Polynom("x");
		assertEquals("1.0x^1 + ", pS.toString());
	}
	
	@Test
	public void testEqualsPolynom_able() {
		assertEquals(false, pa.equals(paEqual));
	}

	@Test
	public void testAddPolynom_able() {
		pa.add(paAdd);
		String s = pa.toString();
		assertEquals("4.0x^2 + ", s);
	}

	@Test
	public void testMultiply() {
		pa.multiply(paMul);
		String s = pa.toString();
		assertEquals("4.0x^2 + 4.0x^3 + ", s);
	}

	@Test
	public void testF() {
		String s = "" + paF.f(2);
		assertEquals("20.0", s);
	}

	@Test
	public void testSubstractPolynom_able() {
		pa.substract(paSub);
		String s = pa.toString();
		assertEquals("-1.0 + 1.0x^2 + ", s);
	}

	/*@Test
	public void testRoot() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testDerivative() {
		String s = "" + pa.derivative();
		assertEquals("4.0x^1 + " , s);
	}

	@Test
	public void testArea() {
		String s = "" + pa.area(1, 3, 1);
		assertEquals("28.0" , s);
	}

}
