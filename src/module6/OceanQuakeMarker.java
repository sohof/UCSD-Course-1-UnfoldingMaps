package module6;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Sohof Dastmard
 * Date feb 2018
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		//Drawing centered square for Ocean earthquakes
		// we don't set the fill color. That will be set in the EarthquakeMarker
		// class to indicate the depth of the earthquake.
		pg.rect(x-radius, y-radius, 2*radius, 2*radius);
	}

}
