package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Sometimes when you make too many request to Google it will give an error message like:
 * "http://mt1.google.com/vt/lyrs=m@116&hl=de&x=0&y=3&z=2&s=Galileo" is missing or inaccessible"
 * in this case either change the map provider to something else or wait some time so google again
 * allows for requests to be made.
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;
	
	// Different radius sizes used for our SimplePoint markers
	private static final float MINOR = 7.5f;
	private static final float MEDIUM = 11f;
	private static final float LARGE = 15.0f;


	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	  
	    //TODO: Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    for (PointFeature eq : earthquakes) {
    			markers.add(createMarker(eq));
    	
	    }    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake. Also dds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below. 
	     System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    // int yellow = color(255, 255, 0);
		
		//Style the marker's size and color according to the magnitude of the earthquake. 
	    // Use constants THRESHOLD_MODERATE and THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables.
	    if (mag < THRESHOLD_LIGHT) // minor earthquake get a small blue marker 
	    	{
	    		marker.setRadius(MINOR);
	    		marker.setColor(color(0, 0, 255)); // color blue in rgb
	    }
	    else if (mag > THRESHOLD_LIGHT  && mag < THRESHOLD_MODERATE ) // medium size earthquake get a medium size yellow marker 
		{
			marker.setRadius(MEDIUM);
    			marker.setColor(color(255, 255, 0)); // color yellow in rgb
		}
	    else // larg size earthquake get a large size red marker 
		{
			marker.setRadius(LARGE);
    			marker.setColor(color(255, 0, 0)); // color red in rgb
		}
	    
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		fill(240,240,240);
		rect(30,180,150,220);
		fill(255,0,0);
		ellipse(50,250,15,15);
		fill(255,255,0);
		ellipse(50,300,11f,11f);
		fill(0,0,255);
		ellipse(50,350,7.5f,7.5f);
		fill(0,0,0); // change color of text to be black
		textSize(12);
		text("Earthquake Key",60,220);
		text("5.0+ Magnitude",70,255);
		text("4.0+ Magnitude",70,305);
		text("Below 4.0",70,354);
 		// Remember you can use Processing's graphics methods here
	
	}
}
