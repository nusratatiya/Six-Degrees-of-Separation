
import java.util.ArrayList;

public class Person {
	
	private String name, predecessor; //most recent person in the chain to this person
	private boolean explored; //whether this Person has already been explored
	private ArrayList <String> friendsList = new ArrayList <String> ();
	
	
	public Person(String name) {
		//constructor
		this.name = name;
		explored = false;
		
	} 

	
	//setters and getters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPredecessor() {
		return predecessor;
	}
	public void setPredecessor(String predecessor) {
		this.predecessor = predecessor;
	}
	public boolean isExplored() {
		return explored;
	}
	public void setExplored(boolean explored) {
		this.explored = explored;
	}
	public ArrayList<String> getFriendsList() {
		return friendsList;
	}
	public void setFriendsList(ArrayList<String> FriendsList) {
		friendsList = FriendsList;
	}

}
