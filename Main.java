import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	private static final String USERNAME = "Username: ";
	private static final String RETWEETS = "Retweets: ";
	private static final String TEXT = "Text: ";
	private static final String MENTIONS = "Mentions: ";
	private static final String HASHTAGS = "Hashtags: ";

	public static void main(String[] args) {
		TwitterCriteria criteria;
		Set<String> allUsers = new TreeSet<String>();
		try {
			criteria = TwitterCriteria.create()
					.setQuerySearch("")
					.setSince("2016-03-20")
					.setUntil("2016-04-04")
					.setGeoCode("43.0731,-89.4012,45mi");
			List<Tweet> allTweets = TweetManager.getTweets(criteria);
			
			System.out.println("### Example 3 - Get tweets by username and bound dates [barackobama, '2015-09-10', '2015-09-12']");
			
			System.out.println("Number of tweets: " + allTweets.size());
			File file = new File("wisconsin.txt");

			// if file doesnt exists, then create it
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

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}