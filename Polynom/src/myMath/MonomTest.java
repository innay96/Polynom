package myMath;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonomTest {
	Monom mn = new Monom(2, 2);
	Monom mF = new Monom("2x^3");
	Monom mMul = new Monom("3");
	Monom mAdd = new Monom(4, 2);

	@Test
	public void testF() {
		String s = "" + mF.f(2);
		assertEquals("16.0", s);
	}

	@Test
	public void testMultiply() {
		mn.multiply(mMul);
		String s = mn.toString();
		assertEquals("6.0x^2 + ", s);
	}

	@Test
	public void testDerivative(){
		String s = mn.derivative().toString();
		assertEquals("4.0x^1 + ", s);
	}

	@Test
	public void testAdd(){
		mn.add(mAdd);
		String s = mn.toString();
		assertEquals("6.0x^2 + ", s);
	}

}
