package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Sohof Dastmard
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		
		pg.rect(x-radius,y-radius,2*radius,2*radius);
		//rect(xpos on screen,ypos on screen, width,height)
		
		// Drawing a centered square for Ocean earthquakes
		// Notice the radius variable in the EarthquakeMarker class
		// and how it is set in the EarthquakeMarker constructor
	}

}