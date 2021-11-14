// I have neither given nor received unauthorized aid on this assignment

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.*; 

public class PersonDriver {
	
	static ArrayList<Person> peeps; // global variable
	static ArrayList <String> chainList = new ArrayList <String> ();
	static Scanner scan  = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException{
		
		peeps = new ArrayList<Person>();
		boolean nowPlaying = true;
		BufferedWriter out = new BufferedWriter(new FileWriter (new File("output.txt")));
		
		String filename = "friends.txt";
		readFile(filename);
	
		
		System.out.println("** Six Degree of Separation **");
		System.out.println("File Reading Complete.");
		out.write("Queries: " + "\n");
		while (nowPlaying = true) {
			String A = getFirstName();
			String B = getSecondName();
			
		
			if (!peeps.contains(getPerson(A))) {
				System.out.println("Error: " + A + " is not in the list");
				A = getFirstName();
			}
			
			if (!peeps.contains(getPerson(B))) {
				System.out.println("Error: " + B + " is not in the list");
				B = getSecondName();
			}
			
			ArrayList<String> relations = Search(getPerson(A), getPerson(B));
			
			if (relations.size() == 1) {
				System.out.println("The two people entered are not connected.");
			}
			else {
				System.out.println("Relation: ");
				System.out.println(arrayListToString(relations));
				out.write(arrayListToString(relations) + "\n");
				System.out.println("Degrees of Separation: " + numOfDegrees());
			}
			
			String response = query();
			if (response.charAt(0) == 'n') {
				nowPlaying = false;
				break;
			}
			if(response.charAt(0) == 'y') {
				nowPlaying=true;
			}
			
			
		}
		System.out.println("Computing Average Degree of Separation...");
		System.out.println("Average Degree of Separation:");
		System.out.println(degreesOfSeparation());
		
		out.write("Average Degree of Separation of entire group: " + degreesOfSeparation());
		out.close();
		
		
	}
	
	// sets the acquaintances for each person
	public static ArrayList<String> Search(Person A,Person B) {
		//initial
		resetPeeps();
		ArrayList <String> exploreList = new ArrayList <String> ();
		A.setExplored(false);
		B.setExplored(false);
		A.setPredecessor(null);
		B.setPredecessor(null);
		boolean found = false; // person B not found yet
		exploreList.add(A.getName());
		A.setExplored(true);  
		 
		while (!exploreList.isEmpty() && !found) {  
			String X = exploreList.get(0);
			exploreList.remove(0);
			if (X.equals(B.getName())) {
				found = true;
			}
			else {
				ArrayList<String> followingFriends = getPerson(X).getFriendsList();
				for(String Y: followingFriends){ 
					Person current = getPerson(Y);
					if (current.isExplored() == false) {
						exploreList.add(Y);
						current.setExplored(true);
						current.setPredecessor(X);
				} 
				}
			}
		}	
		// finds the chain of acquaintances for each person
		String current = B.getName();
		while (current!= null) {
			//System.out.println("chainlist");
			chainList.add(current);
			current = getPerson(current).getPredecessor();
			//System.out.println(current);
		}
		
		Collections.reverse(chainList);
		return chainList;
		
	}
	
	public static String arrayListToString(ArrayList<String> relationship) {
		String newChain = "";
		for (String person: relationship) {
			newChain += person + " ";
		}
		return newChain;
	}
	
	// computes the average degree of separation of the entire group
	public static double degreesOfSeparation() {
		
		int[][] connectArray = new int[peeps.size()][peeps.size()];
		double sum = 0;
		
		for(int row=0; row<connectArray.length; row++) {
			for(int col=0; col < connectArray[row].length; col++) {
				ArrayList<String> pairs = Search(peeps.get(row), peeps.get(col));
				if (pairs.size() > 1) {
					connectArray[row][col] = pairs.size() -1;
					sum += pairs.size() -1;
				}
				else {
					if (peeps.get(row).getName().equals(peeps.get(col).getName())) {
						connectArray[row][col] = 0;
					}
					else {
						connectArray[row][col] = peeps.size() + 1;
						sum += peeps.size() +1;
					}
				}
			}
		}
		int numOfPairs = connectArray.length * (connectArray[0].length - 1);
		double average = sum / numOfPairs;
		
		return average;
		
	}
	
	//extra methods
	
	public static String query() {
		System.out.println("Want to try another query? (y/n)");
		String response = scan.nextLine();
		return response;
	}
	
	public static void resetPeeps() {
		for (Person x: peeps) {
			x.setExplored(false);
			x.setPredecessor(null);
			chainList = new ArrayList<String>();
		}
	}
	
	public static void readFile(String file) throws IOException{
		Scanner fr = new Scanner(new File(file));
		
		while (fr.hasNextLine()) {
			String line = fr.nextLine();
			String[] names = line.split("\t");
			//use the first name to create Person
			Person person = new Person(names[0]);
			
			//use rest of the names for the friendsList
			for (int i=1; i<names.length; i++) {
				person.getFriendsList().add(names[i]);
			}
			
			peeps.add(person); // adds each person object to peeps ArrayList
		}
		fr.close();
	}
	
	// obtains Person object associated with string name
	public static Person getPerson(String name) {
		for (int i = 0; i<peeps.size(); i++) {
			if (peeps.get(i).getName().equals(name))
				return peeps.get(i);
		}
		return null;
	}
	
	public static int numOfDegrees() {
		int distance = chainList.size() -1;
		return distance;
	}
	
	public static String getFirstName() {
		System.out.println("Enter the name of the first person: ");
		String A = scan.nextLine();
		return A;
	}
	
	public static String getSecondName() {
		System.out.println("Enter the name of the second person: ");
		String B = scan.nextLine();
		return B;
	}
}
