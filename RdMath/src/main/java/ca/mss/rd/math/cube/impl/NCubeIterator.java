package ca.mss.rd.math.cube.impl;

import java.util.Iterator;

import ca.mss.rd.math.cube.NCube;

public interface NCubeIterator<Point extends NCube<Point,Region>.NPoint, Region extends NCube<Point,Region>.NRegion> extends Iterator<Point> {

	public boolean isBreakable();

	public Point getStep();
	public Point getSize();
	public Point getLeft();
	public Point getRight();

	public int getLength(); 
	public int getIndex(); 
	
	public void addSurf(double p);
	
	public void setSurfValue(double p); 
	public double getSurfValue(); 

	public double[] getSurf(); 
	
}
