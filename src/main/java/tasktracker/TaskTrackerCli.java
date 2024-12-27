package tasktracker;


public class TaskTrackerCli {
	
	public static void main(String[] args) {
		Operations operation = new Operations(args);
		operation.parser();
		
	}
}
