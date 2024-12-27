package tasktracker;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import tasktracker.helpers.TaskStatus;
import tasktracker.model.Task;

public class Operations {
	final static Map<String, Integer> coreOperations = Map.of("add", 1, "update", 2, "delete", 3, "mark-in-progress", 4,
			"mark-done", 5, "list", 6);
	final static Map<String, Integer> listOperations = Map.of("done", 1, "todo", 2, "in-progress", 3);
	private String[] args;
	private JsonProcessor jsonProcessor;

	public Operations(String[] args) {
		this.args = args;
		this.jsonProcessor = new JsonProcessor();
	}

	void parser() {
		if (args.length != 0) {
			Integer op = coreOperations.getOrDefault(args[0], -1);
			switch (op) {
			case 1:
				add();
				break;
			case 2:
				update();
				break;
			case 3:
				delete();
				break;
			case 4:
				markInProgress();
				break;
			case 5:
				markDone();
				break;
			case 6:
				list();
				break;
			default:
				invalidOperation();
				break;
			}
		} else {
			System.out.println("Welcome to Tasktracker!!!");
			menu();
		}
	}

	private void add() {
		if (args.length == 2) {
			Task task = new Task();
			long id = jsonProcessor.getLengthOfRecord() + 1;
			task.setId(id);
			task.setDesc(args[1]);
			task.setStatus(TaskStatus.TODO);
			task.setUpdatedAt(Instant.now());
			task.setCreatedAt(Instant.now());
			
			if(0 == jsonProcessor.addToJson(task)) 
				System.out.printf("Sucessfully added the task with ID : %s\n",id);
			else
				System.out.println("Failed to add the task!!!");
			
		} else {
			invalidOperation();
			menu();
		}
	}

	private void delete() {

	}

	private void update() {

	}

	private void markInProgress() {
	}

	private void markDone() {

	}

	private void list() {

		if (args.length == 1) {
			// list all tasks
			jsonProcessor.listAll();
		} else if (args.length == 2) {
			Integer op = listOperations.getOrDefault(args[1], -1);
			switch (op) {
			case 1:
				jsonProcessor.listDone();
				break;
			case 2:
				jsonProcessor.listTodo();
				break;
			case 3:
				jsonProcessor.listInProgress();
				break;
			default:
				System.out.println("Invalid Operation!!!");
				break;
			}
		} else
			System.out.println("list operation require maximum 2 command line operation !!!");

	}

	private void menu() {
		System.out.println("Possible Commands");
		System.out.println("\n");

	}

	private void invalidOperation() {
		System.out.println("Invalid Operation!!!");
	}
}
