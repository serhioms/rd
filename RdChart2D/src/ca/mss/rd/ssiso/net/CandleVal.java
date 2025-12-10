package ca.mss.rd.ssiso.net;

import java.awt.Color;
import java.util.Random;

public class CandleVal {
	public double min, max, in, out;
	static private Random r = new Random();
	
	public CandleVal(){
		generateRandomFirst();
	}
	
	public CandleVal(CandleVal cv){
		generateRandomNext(cv);
	}
	
	public Color color(){
		return out>in? Color.green: Color.red;
	}
	
	public void copy(CandleVal cv){
		min = cv.min;
		max = cv.max;
		in = cv.in;
		out = cv.out;
	}
	
	public double height(){
		return out>in? out-in: in-out;
	}
	
	public double top(){
		return out>in? out: in;
	}
	
	public double flameUp(){
		return max - top();
	}
	
	public double flameDown(){
		return bottom()-min;
	}
	
	public double bottom(){
		return out>in? in: out;
	}
	
	public void generateRandomFirst(){
		in = 0.0;
		out = in + 5.0*(r.nextDouble()-0.5);
		if( in < out ){
			max = out + 3.0*r.nextDouble();
			min = in - 3.0*r.nextDouble();
		} else {
			max = in + 3.0*r.nextDouble();
			min = out - 3.0*r.nextDouble();
		}
	}
	
	public void generateRandomNext(CandleVal cv){
		in = cv.out;
		out = cv.out + 5.0*(r.nextDouble()-0.5);
		if( in < out ){
			max = out + 3.0*r.nextDouble();
			min = in - 3.0*r.nextDouble();
		} else {
			max = in + 3.0*r.nextDouble();
			min = out - 3.0*r.nextDouble();
		}
	}
}