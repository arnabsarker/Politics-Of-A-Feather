import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	
	public static void main(String[] args) {
		TwitterCriteria criteria;
		Map<String, Integer> numTweets = new HashMap<String, Integer>();
		Set<String> allUsers = new TreeSet<String>();
		String currLine;
		
		try {
			
			States unitedStates = new States();
			Reader d = new FileReader("dates.txt");
			BufferedReader dateReader = new BufferedReader(d);
			currLine = dateReader.readLine();
			
			String state;
			String date;
			while(currLine != null){
				String[] allVals = currLine.split(" ");
				if(allVals.length == 3){
					allVals[0] = allVals[0] + " " + allVals[1];
					allVals[1] = allVals[2];
				}

				state = allVals[0];
				date = allVals[1];
				System.out.println("Getting tweets from " + state);
				System.out.println("GeoCode: " + unitedStates.getStateData(state) + " Date: " + date);
				criteria = TwitterCriteria.create()
						.setQuerySearch(" ")
						.setSince("2016-01-01")
						.setUntil(date)
						.setMaxTweets(10000)
						.setGeoCode(unitedStates.getStateData(state));
				List<Tweet> allTweets = TweetManager.getTweets(criteria);
				numTweets.put(state, allTweets.size());
				
				System.out.println("Writing tweets to file from " + state);
				File file = new File(state.replace(" ", "_") + ".txt");

				// if file exists, overwrite it
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				for(Tweet n: allTweets){
					if(!allUsers.contains(n.getUsername())){
						allUsers.add(n.getUsername());
						bw.write(n.getText());
						bw.newLine();
					}
				}
				bw.close();
				System.out.println(state + " is done!");
				currLine = dateReader.readLine();
			}
			dateReader.close();
			for(String stateName: numTweets.keySet()){
				System.out.println("State: " + " number of Tweets: " + numTweets.get(stateName));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}