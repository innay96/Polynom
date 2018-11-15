package myMath;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class PolynomTest {
	  private final TimeUnit periodTimeUnit = TimeUnit.SECONDS;
	@Test
	public void testPolynomConstructorEmpty(){
		Polynom a = new Polynom();
		assertEquals("Polynomial not initialized", a.toString());
	}
	
	@Test
	public void testPolynomConstructorCopy(){
		Polynom b = new Polynom("x + 2*x^2 - 3x^3");
		Polynom c = new Polynom(b);
		assertEquals(b.toString(), c.toString());
	}
	

	@Test
	public void testPolynomConstructorString1(){
		try {
			Polynom d = new Polynom("x");
			assertEquals("x", d.toString());
		}
		catch (RuntimeException re) {
			re.printStackTrace();
			System.out.println("Not correct input");		
		}
	}
	
	@Test(expected = RuntimeException.class )
	public void testPolynomConstructorString2() {
			Polynom b = new Polynom("x^-2");
			}

	@Test
	public void testPolynomConstructorString3(){
		try {
			Polynom c = new Polynom("x^2+ x^3");
			assertEquals("x^2+x^3", c.toString());
		}
		catch (RuntimeException re) {
			re.printStackTrace();
			System.out.println("Not correct input");		
		}
	}
	
	@Test
	public void testEqualsPolynom_able1(){
		Polynom d = new Polynom("5x+0x^0");
		Polynom f = new Polynom("5x");
		assertEquals(true, d.equals(f));
	}
	
	@Test
	public void testEqualsPolynom_able2(){
		Polynom h = new Polynom("-x-x");
		Polynom g = new Polynom("-x+x+0.2*x^0");
		assertEquals(false, h.equals(g));
	}

	@Test
	public void testAddPolynom_able() {
		Polynom i = new Polynom("-x^0- x^3");
		Polynom_able j = new Polynom("-x^1- x^3");
		i.add(j);
		String s = i.toString();
		System.out.println(i.toString());
		assertEquals("-1.0-x-2.0x^3", s);
	}
	
	@Test
	public void testAddMonom() {
		Polynom k = new Polynom ("-x + 2x^2 + 4");
		Monom l = new Monom(7,1);
		k.add(l);
		assertEquals("4.0+6.0x+2.0x^2", k.toString());
	}
	
	@Test
	public void testRoot() {
		Polynom m = new Polynom ("x^2+8x+15");
		assertEquals(true, m.root(-4, 1, 0.5)>=-3.5 && m.root(-4, 1, 0.5)<=-2.5 );
	}
	
	@Test
	public void testDerivative() {
		try {
			Polynom a = new Polynom("0.2x^4-1.5x^3+3.0x^2-x-5");
			Polynom derv = new Polynom((Polynom)a.derivative());
			Polynom derv2 = new Polynom("0.8x^3-4.5x^2+6x-1");
			assertEquals(derv.toString(),derv2.toString());
		}
		catch (RuntimeException re) {
			re.printStackTrace();
			System.out.println("Not correct input");		
		}
	}
	
	@Test
	public void testArea() {
		Polynom a = new Polynom("2x^2");
		String s = "" + a.area(1, 3, 1);
		assertEquals("28.0" , s);
	}
	

	@Test
	public void testMultiply() {
		Polynom m = new Polynom("2x^2");
		Polynom n = new Polynom("2x^2");
		m.multiply(n);
		String s = m.toString();
		assertEquals("4.0x^4", s);
	}
	
	@Test
	public void testSubstractPolynom_able() {
		Polynom o = new Polynom("-5.5x^3-2x");
		Polynom p = new Polynom("0.5x^3+1-x");
		o.substract(p);
		String s = o.toString();
		assertEquals("-1.0-x-6.0x^3", s);
	}


	@Test
	public void testF() {
		Polynom q = new Polynom("x^2");
		String s = "" + q.f(1);
		assertEquals("1.0", s);
	}


	@Test
	public void testIsZero() {
		Polynom r = new Polynom();
		assertTrue(r.isZero());
	}

	
	@Test
	public void testGraph() {
		try {
			Polynom n = new Polynom("0.2x^4-1.5x^3+3.0x^2 -x -5");
			GraphP g = new GraphP();
			g.setVisible(true);
			g.setPoly(n);
			g.drawGraph();
			periodTimeUnit.sleep(60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGrapeArea() {
		GraphP g = new GraphP();
		assertEquals("25.18361040240927", g.areaPlot());
	}
}
