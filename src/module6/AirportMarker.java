package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(0,255,0); // the color green
		pg.ellipse(x, y, 10, 10);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		// show rectangle with title
		// params x and y are at center of our marker 
		pg.pushStyle();

		String name = "Name: " + getAirportName() + " " + getSource();
		String city_Country = getAirportCity() + " , " + getAirportCountry() + " ";
		int FONT_SIZE = 12;
		int RECT_HEIGHT = 2*FONT_SIZE + 5;

		pg.rectMode(PConstants.CORNER);

		pg.stroke(110);
		pg.fill(255, 204, 128);
		pg.rect(x, y + 15, Math.max(pg.textWidth(name),pg.textWidth(city_Country)) +6, RECT_HEIGHT, 5);

		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.textSize(FONT_SIZE);
		pg.text(name, x + 3 , y +17);
		pg.text(city_Country, x + 3 , y + 17 + FONT_SIZE);
		pg.popStyle();
		
		// show routes
				
	}
	
	// GETTERS ALl of which need to clear the " " around the string.
	
	public String getAirportName() {		
		// implicit this.getStringProperty("name");
		String tmp  = getStringProperty("name");
		return tmp.substring(1, tmp.length()-1);
		
	}
	public String getAirportCity() {
		String tmp  = getStringProperty("city");
		return tmp.substring(1, tmp.length()-1);
	}
	public String getAirportCountry() {
		String tmp  = getStringProperty("country");
		return tmp.substring(1, tmp.length()-1);
	}
	
	public String getSource() {
		
		return getId();
	}
	
}
