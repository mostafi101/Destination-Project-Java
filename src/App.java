import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.lang.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DebugGraphics;

public class App {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args){
		String cdr = System.getProperty("user.dir");
		String csvFile = cdr + "/src/zipcode.csv";
		String line = "";
        String cvsSplitBy = ",";
        double lat = 0.0;
        double lot = 0.0;
        
        ArrayList<City> citis = new ArrayList<City>();
        HashMap<String, String> timeZoneByState = new HashMap<String, String>();
        
        timeZoneByState = loadTimeZone();
        
        /*for(String entry: timeZoneByState.keySet()){
        	System.out.println(timeZoneByState.get(entry));
        }*/

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] city = line.split(cvsSplitBy);
                //System.out.println(city[5]);
                //System.out.println(timeZoneByState.get(city[2].substring(1, city[2].length() - 1)));
      
                City c = new City(city[1].substring(1, city[1].length() - 1), city[2].substring(1, city[2].length() - 1), city[3].substring(1, city[3].length() - 1),city[4].substring(1, city[4].length() - 1));
                

                citis.add(c);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Enter Your Location: ");
        City myLocation = getCityState(citis);
        double[] myLocationPoint = {Double.parseDouble(myLocation.getLatitude()),Double.parseDouble(myLocation.getLongitude())};
        
        System.out.println("Enter Destination Location: ");
        City destLocation = getCityState(citis);
        double[] myDestinationPoint = {Double.parseDouble(destLocation.getLatitude()),Double.parseDouble(destLocation.getLongitude())};
       // System.out.println(myLocation[0]+" "+myLocation[1]);
       // System.out.println(destLocation[0]+ " " +destLocation[1]);
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  hh:mm a");
        
        ZoneId zoneId = ZoneId.of(timeZoneByState.get(myLocation.getState()));
        LocalDateTime localtDateAndTime = LocalDateTime.now();
        ZonedDateTime date  = ZonedDateTime.of(localtDateAndTime, zoneId );
        System.out.println("Time at Departure City: " + date.format(format));
        
        localtDateAndTime = LocalDateTime.now();
        ZoneId deszoneId = ZoneId.of(timeZoneByState.get(destLocation.getState()));
        ZonedDateTime desDate  = ZonedDateTime.of(localtDateAndTime, deszoneId );
        System.out.println("Time at Arrival City: " + desDate.format(format));
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        
        double distance = distance(myLocationPoint, myDestinationPoint);
        System.out.println("Total distance :"+ distance +" Mi");
        //with a continus speed of 65 mi/hr
        double hr= distance/65;
        System.out.println("Total time needed at speed 65 mi/hr : " + hr+" hr");
        
        long timeinsec = (long) (hr*60*60);
        date = date.plusSeconds(timeinsec);
        desDate = desDate.plusSeconds(timeinsec);
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        
        
        
        System.out.println("Will Arrive :");
        
        System.out.println("Time at Departure City: "+date.format(format));
        System.out.println("Time at Arrival City: " + desDate.format(format));
        
      
    
	}
	
	static City getCityState(List<City> citis){
		
		System.out.println("Enter City");
        String ccity = sc.nextLine();
        System.out.println("Enter State");
        String cstate = sc.nextLine();
        City city = new City("name", "state", "latitude", "longitude");
        for(City c : citis){
        	if(ccity.equals(c.getName()) && cstate.equals(c.getState())){
				city = c;
				break;
        	}
        }
        
        if (city.getName().equals("name") | city.getState().equals("state") ){
        	System.out.println("Sorry this address not found... Try again: ");
        	getCityState(citis);
        }
        
        return city;
	}
	
	static double distance(double[] myLoc, double[] desLoc){
		final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(desLoc[0] - myLoc[0]);
	    Double lonDistance = Math.toRadians(desLoc[1] - myLoc[1]);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(myLoc[0])) * Math.cos(Math.toRadians(desLoc[0]))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c ; // convert to meter

	    distance = Math.pow(distance, 2);

	    return Math.sqrt(distance);
	}
	
	static HashMap<String, String> loadTimeZone(){
		HashMap<String, String> map = new HashMap<String,String>();
		String line = "";
		String txtSplitBy = ",";
		String cdr = System.getProperty("user.dir");
		String txtFile = cdr + "/src/timezone.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] st = line.split(txtSplitBy);
                
      
                map.put(st[0], st[1]);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return map;
	}
}
