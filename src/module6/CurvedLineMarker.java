package module6;

import java.util.HashMap;
import java.util.List;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import processing.core.PGraphics;

public class CurvedLineMarker extends SimpleLinesMarker {
	
	public CurvedLineMarker(Location startLocation, Location endLocation) {
		super();
		addLocations(startLocation, endLocation);
	}
	public CurvedLineMarker(List<Location> locations, HashMap<String, Object> properties) {
		super(locations, properties);
	}
	
	@Override
	public void draw(PGraphics pg, List<MapPosition> mapPositions) {
		
		if (mapPositions.isEmpty() || isHidden())
			return;

		pg.pushStyle();
		pg.noFill(); // just lines should be drawn.. no filling 
		pg.stroke(120);  // select color of drawn line 
		
		pg.strokeWeight(2);
		pg.smooth();
		MapPosition start = mapPositions.get(0);
		MapPosition end = mapPositions.get(1);
		
		float mid_x = 40 + (start.x + end.x)/2;
		float mid_y = 40 + (start.y + end.y)/2;
		
		pg.bezier(start.x, start.y, mid_x , mid_y, mid_x, mid_y, end.x, end.y);
		pg.popStyle();
	}
}
