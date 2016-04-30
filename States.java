import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class States {
    private Map<String, String> coordinates;
    private Map<String, String> radius;
	
    private Document wikiCoords;
    private Document wikiAreas;
    
    public States(){
    	String currLine;
		coordinates = new HashMap<String,String>();
		radius = new HashMap<String,String>();
		try {
			//Locations
			Reader c = new FileReader("locations.txt");
		    BufferedReader coordReader = new BufferedReader(c);
			currLine = coordReader.readLine();
			while(currLine != null){
				String[] allVals = currLine.split(" ");
				if(allVals.length == 3){
					allVals[0] = allVals[0] + " " + allVals[1];
					allVals[1] = allVals[2];
				}
				coordinates.put(allVals[0], allVals[1]);
				currLine = coordReader.readLine();
			}
			
			Reader a = new FileReader("areas.txt");
			BufferedReader areaReader = new BufferedReader(a);
			currLine = areaReader.readLine();
			while(currLine != null){
				String[] allVals = currLine.split(" ");
				if(allVals.length == 3){
					allVals[0] = allVals[0] + " " + allVals[1];
					allVals[1] = allVals[2];
				}
				double area = Double.parseDouble(allVals[1].replace(",", ""));
				int rad = (int) (Math.sqrt(area/ Math.PI));
				radius.put(allVals[0], Integer.toString(rad));
				currLine = areaReader.readLine();
			}
		    
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    public String getStateData(String state){
    	String ans = "";
    	ans += coordinates.get(state);
    	ans += ",";
    	ans += radius.get(state);
    	ans += "mi";
    	
        return ans;
    }
    
    public Set<String> allStates(){
    	return coordinates.keySet();
    }
}
