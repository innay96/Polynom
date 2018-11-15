package myMath;

import java.awt.Color;
import java.util.Iterator;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

/**
 * class that reflects a polynomial as a graph using GRAL GUI
 * @author Chen and Inna
 *
 */
public class GraphP extends JFrame {
	private Polynom polynom;

	/**
	 * function that draws a graph according to the polynomial it gets
	 */
	public void drawGraph() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		Polynom p = getPoly();
		DataTable data = new DataTable(Double.class, Double.class);
		DataTable data2 = new DataTable(Double.class, Double.class);
		for (double x = -2.0; x <= 6.0; x+=0.0001) {
			double y = p.f(x);
			data.add(x, y);
			if((p.f(x-0.0001) < y) && (p.f(x+0.0001) < y) || (p.f(x-0.0001) > y) && (p.f(x+0.0001) > y)) 
				data2.add(x,y);
		}
		XYPlot plot = new XYPlot(data, data2);
		getContentPane().add(new InteractivePanel(plot));
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderers(data, lines);
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderers(data).get(0).setColor(color);
		plot.getLineRenderers(data).get(0).setColor(color);
		plot.getPointRenderers(data2).get(0).setColor(new Color(1.0f, 0.0f, 0.8f));
		double S = areaPlot();
		System.out.println("The area of the polynomial is: " + S);
	}

	/**
	 * Calculates the area enclosed between the X axis and the function located under the X axis
	 * @return the area (double)
	 */
	public double areaPlot() {
		Polynom neg = new Polynom("-1");
		polynom.multiply(neg);
		double x0 = 0;
		boolean flag = true;
		for (double x = -2.0; x <= 6.0 && flag; x+=0.0001) {
			double y = polynom.f(x);
			if(y <= 0.01 && y >= -0.01) {
				x0 = x; 
				flag = false;
			}
		}
		double x1 = 0;
		flag = true;
		for (double x = x0+0.01; x <= 6.0 && flag; x+=0.0001) {
			double y = polynom.f(x);
			if(y <= 0.01 && y >= -0.01) {
				x1 = x; 
				flag = false;
			}
		}
		double S = polynom.area(x0, x1, 0.01);
		return S;
	}
	
	

	public void setPoly(Polynom poly) {
		polynom = new Polynom(poly);
	}

	private Polynom getPoly() {
		return this.polynom;
	}
}