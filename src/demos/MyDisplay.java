package demos;

import processing.core.PApplet;
import processing.core.PImage;

/** 
 * A class to illustrate some use of the PApplet class
 * Used in module 3 of the UC San Diego MOOC Object Oriented Programming in Java
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * 
 *
 */
public class MyDisplay extends PApplet {
	
	String url = "../data/palmTrees.jpg";
	PImage webImg;
	public void setup()
	{
		size(400, 400);
		background(240);
		stroke(0);
	
		webImg = loadImage(url,"jpg");
		
	}
	
	public void draw()
	{
		webImg.resize(0,height); 
		image(webImg,0,0); // display image
		
		int [] color = sunColorSec(second());
		fill(color[0],color[1],color[2]);
		ellipse(width/4, height/5, width/5, height/5);
		fill(55, 0, 100);
		ellipse(90, 70, 10, 15);
		ellipse(115, 70, 10, 15);
		
		noFill();
		arc(99, 100, 45, 20, 0, PI);
	}
	public int[] sunColorSec(float seconds) {
	
		int [] rgb = new int[3];
		
		float diffFrom30 = Math.abs(30-seconds);
		float ratio = diffFrom30 / 30;
		
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int) (255*ratio);
		rgb[2] = 0;
		
		return rgb;
	}
	
}
	
	
