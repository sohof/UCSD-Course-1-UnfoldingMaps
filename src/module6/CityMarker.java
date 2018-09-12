package module6;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Sohof Dastmard
 */
public class CityMarker extends CommonMarker {
	
	public static int TRI_SIZE = 5;  // The size of the triangle marker
	
	public CityMarker(Location location) {
		super(location);
	}
		
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		// Cities have properties: "name" (city name), "country" (country name)
		// and "population" (population, in millions)
	}
	
	// pg is the graphics object on which you call the graphics
	// methods.  e.g. pg.fill(255, 0, 0) will set the color to red
	// x and y are the center of the object to draw. 
	// They will be used to calculate the coordinates to pass
	// into any shape drawing methods.  
	// e.g. pg.rect(x, y, 10, 10) will draw a 10x10 square
	// whose upper left corner is at position x, y
	/**
	 * Implementation of method to draw marker on the map.
	 */
	public void drawMarker(PGraphics pg, float x, float y) {
		// Save previous drawing style
		pg.pushStyle();
		
		// IMPLEMENT: drawing triangle for each city
		pg.fill(160, 30, 60); // set color of triangle fill
		pg.triangle(x, y-TRI_SIZE, x-TRI_SIZE, y+TRI_SIZE, x+TRI_SIZE, y+TRI_SIZE);
		
		// Restore previous drawing style
		pg.popStyle();
	}
	
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{ // method will be called from commonmarker if marker is selected
	
		String city_country = getCity() + " " + getCountry() + " ";;
		String pop = "Population: " + getPopulation() + " Million";
		int rectHeight = 35; // just a height to allow for two lines of text
		pg.pushStyle();
		//pg.stroke(110);
		pg.fill(240, 240, 240);
		pg.textSize(12);
		// rect(x coord, y coord, width, height, radii of corners )
		pg.rect(x, y-TRI_SIZE-rectHeight, Math.max(pg.textWidth(city_country), pg.textWidth(pop))+6, rectHeight, 5);
		// rect width is set to max texwidth + 6 to allow space for some margin 
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(city_country, x+3, y-TRI_SIZE-rectHeight + 3); 
		pg.text(pop, x+3, y -TRI_SIZE-rectHeight +18); // tweak text so it lands in rectangle box
		pg.popStyle();
	}
	
	private String getCity()
	{
		return getStringProperty("name");
	}
	
	private String getCountry()
	{
		return getStringProperty("country");
	}
	
	private float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}
}
