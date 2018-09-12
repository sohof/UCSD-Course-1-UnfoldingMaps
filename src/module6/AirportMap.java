package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Google.GoogleMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	// Just to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;
	
	UnfoldingMap map;
	private List<Marker> airportList;
	private List<Marker> selectedAirportList;
	private static final String [] selectedCities = {"Paris","London","Stockholm", "Berlin", 
													"Rome", "Vienna", "Madrid", "Oslo", "Minsk"}; 
	List<Marker> routeList;
	
	// Vars needed for handling mouse events 
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {
				
		// setting up PAppler
		size(1200,900, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 950, 850, new OpenStreetMap.OpenStreetMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		selectedAirportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
			
			m.setRadius(5);
			m.setId(feature.getId());
			airportList.add(m);
			String tmp = m.getStringProperty("city");
			// Each string city has double quatation marks around it like "Paris", which we need to clear
			String city = tmp.substring(1, tmp.length()-1); 
			// Only add marker to selectedCitiesList if airport is in list of selected cities
			for (String selectedCity : selectedCities ) {
				if (selectedCity.equals(city)  && m!=null) {
					selectedAirportList.add(m);
					// put airport in hashmap with OpenFlights unique id for key
					airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
				}

			}		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			CurvedLineMarker sl = new CurvedLineMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
			hideRouteMarkers(); // Begin screen with routes hidden
		}
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		//UNCOMMENT IF YOU WANT TO SEE ALL Airports
		//map.addMarkers(airportList);
		map.addMarkers(selectedAirportList);

	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(selectedAirportList);
		//selectMarkerIfHover(airportList);

	}
	
	// If there is a marker under the cursor, and lastSelected is null 
	// set the lastSelected to be the first marker found under the cursor
	// Make sure you do not select two markers.
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			hideRouteMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportsForClick();
	
		}
	}
	
	// loop over and unhide all markers
	private void hideRouteMarkers() {
		for(Marker marker : routeList) {
			marker.setHidden(true);
		}
			
	}
	
	private void checkAirportsForClick()
	{
		if (lastClicked != null) return;
		// Loop over the airport markers to see if one of them is selected
		for (Marker marker : selectedAirportList) {
			if (marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker)marker;
				showRoutes();
				return;
			}
		}		
	}
	
	private void showRoutes() {
		AirportMarker lastCL = (AirportMarker) lastClicked;
		for (Marker marker : routeList) {
			
			if(lastCL.getSource().equals(marker.getStringProperty("source"))) {
				marker.setHidden(false);
			}
		}
		
	}
}
