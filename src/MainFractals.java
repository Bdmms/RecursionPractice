import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Sean Rannie
 * Dec. 2014
 * 
 * This program was testing recursion and fractal algorithms. 
 * The pattern produced is displayed onto the JPanel. The fractal
 * used is a straight line but each cycle the middle is replaced with
 * a two line point, then each of those lines has a two line point in
 * the middle of their segment, so on...
 */

public class MainFractals extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static JFrame frame = new JFrame("TEST");	//JFrame
	private static double zoom = 1.0;					//Effects the rate of zoom

	//main method
	public static void main(String[] args)
	{
		new MainFractals();
	}
	
	//Constructor
	public MainFractals()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(new Dimension(800, 600));
        frame.setLocation(0, 0);
        frame.setBackground(Color.WHITE);
        frame.add(this);
        
        System.out.println(factorial(4));
        while(true)
        {
        	refresh();
        	zoom += 0.01;
        }
	}
	
	//Calculates a factorial of a number
	public int factorial(int n) 
	{
		if (n == 1) {
		    return 1;
		} else {
		    return n * factorial(n-1);
		}
	}
	
	//paint component
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1400, 800);
		g.setColor(Color.BLACK);
		//g.drawLine(10, 10, 20, 40);
		//drawCircle(g, 250, 50, 100);
		drawPattern(g, 50, 50, 1350, 50, 500);
		drawPattern(g, 50*zoom, 50*zoom, 1350*zoom, 50*zoom, 500*zoom);
	}
	
	//Drawing a dot with Java Swing
	public int dot(int x1, int y1, int x2, int y2)
	{
		return x1*x2 + y1*y2;
	}
	
	//Searches for perpendicular
	public double[] findPerpendicular(double x, double y, double h)
	{
		//System.out.println("\n" + x + ", " + y + " ["+h+"]");
		double[] p = {-y, x};
		//System.out.println(p[0] + ", " + p[1]);
		double l = determineLength(p[0], p[1]);
		p[0] /= l;
		p[1] /= l;
		p[0] *= h;
		p[1] *= h;
		System.out.println(p[0] + ", " + p[1]);
		return p;
	}
	
	//Draws the fractal pattern
	public void drawPattern(Graphics g, double x1, double y1, double x2, double y2, double h) 
	{
		double xlen = (x2 - x1);
		double ylen = (y2 - y1);
		int xthird = (int) (x1 + xlen/3);
		int ythird = (int) (y1 + ylen/3);
		g.drawLine((int) x1, (int) y1, xthird, ythird);
		g.drawLine((int) (x1 + 2*xlen/3), (int) (y1 + 2*ylen/3), (int) x2, (int) y2);
		if(Math.abs(h) > 1)
		{
			double[] p = findPerpendicular(xlen, ylen, h);
			drawPattern(g, xthird, ythird, p[0] + x1 + xlen/2, p[1] + y1 + ylen/2, h/2); 
			drawPattern(g, x1 + 2*xlen/3, y1 + 2*ylen/3, p[0] + x1 + xlen/2, p[1] + y1 + ylen/2, -h/2); 
		}
	}
	
	//Determines length between two points
	public double determineLength(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
	}
	
	//Determines length of a point to the origin
	public double determineLength(double x, double y)
	{
		return Math.sqrt(x*x + y*y);
	}
	
	//Draws a circle pattern
	public void drawCircle(Graphics g, int x, int y, int radius) {
		g.drawOval(x, y, radius, radius);
		if(radius > 2) 
		{
			radius *= 0.5;
			drawCircle(g, x + radius, y, radius);
			drawCircle(g, x - radius, y, radius);
		}
	}
	
	//A refresh cycle, additional code can be added
	public void refresh()
	{
    	this.repaint();
    	pause(10, 0);
	}
	
	//pause algorithm
	public void pause(int time, int add)
	{
		try{
			Thread.sleep(time, add);
		} catch (Exception exc){}
	}
}
