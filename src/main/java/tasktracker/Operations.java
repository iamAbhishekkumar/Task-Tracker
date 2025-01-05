package tasktracker;

import java.util.Date;
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
		super();
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
			long id = jsonProcessor.getAutoIncId();
			task.setId(id);
			task.setDesc(args[1]);
			task.setStatus(TaskStatus.TODO);
			task.setUpdatedAt(new Date());
			task.setCreatedAt(new Date());

			if (0 == jsonProcessor.addToJson(task))
				System.out.printf("Sucessfully added the task with ID : %s\n", id);
			else
				System.out.println("Failed to add the task!!!");

		} else {
			invalidOperation();
		}
	}

	private void delete() {
		if (args.length == 2) {
			long id = Long.parseLong(args[1]);
			int response = jsonProcessor.deleteToJson(id);
			if (0 == response)
				System.out.printf("Sucessfully deleted the task with ID : %s\n", id);
			else if (1 == response)
				System.out.printf("No task found for ID : %s\n", id);
			else
				System.out.println("Failed to deleted the task!!!");

		} else {
			invalidOperation();
			// correct usage of update
		}
	}

	private void update() {
		if (args.length == 3) {
			long id = Long.parseLong(args[1]);
			String desc = args[2];

			if (0 == jsonProcessor.updateToJson(id, desc, null))
				System.out.printf("Sucessfully added the task with ID : %s\n", id);
			else
				System.out.println("Failed to update the task!!!");

		} else {
			invalidOperation();
			// correct usage of update
		}
	}

	private void markInProgress() {
		if (args.length == 2) {
			long id = Long.parseLong(args[1]);

			if (0 == jsonProcessor.updateToJson(id, null, TaskStatus.IN_PROGRESS))
				System.out.printf("Sucessfully marked the task in-progress with ID : %s\n", id);
			else
				System.out.println("Failed to update the task!!!");

		} else {
			invalidOperation();
			// correct usage of update
		}
	}

	private void markDone() {
		if (args.length == 2) {
			long id = Long.parseLong(args[1]);

			if (0 == jsonProcessor.updateToJson(id, null, TaskStatus.DONE))
				System.out.printf("Sucessfully marked the task as done with ID : %s\n", id);
			else
				System.out.println("Failed to update the task!!!");

		} else {
			invalidOperation();
			// correct usage of update
		}
	}

	private void list() {
		List<Task> taskList = null;
		if (args.length == 1) {
			// list all tasks
			taskList = jsonProcessor.listAll();
			printTaskList(taskList);
		} else if (args.length == 2) {
			Integer op = listOperations.getOrDefault(args[1], -1);
			switch (op) {
			case 1:
				taskList = jsonProcessor.listByStatus(TaskStatus.DONE);
				break;
			case 2:
				taskList = jsonProcessor.listByStatus(TaskStatus.TODO);
				break;
			case 3:
				taskList = jsonProcessor.listByStatus(TaskStatus.IN_PROGRESS);
				break;
			default:
				System.out.println("Invalid Operation!!!");
				break;
			}
			printTaskList(taskList);
		} else
			System.out.println("list operation require maximum 2 command line operation !!!");

	}

	private void menu() {
		System.out.println("Available Commands");
		System.out.println("add \"<description>\"");
		System.out.println("update <id> \"<description>\"");
		System.out.println("delete <id>");
		System.out.println("mark-in-progress <id>");
		System.out.println("mark-done <id>");
		System.out.println("list");
		System.out.println("list done");
		System.out.println("list done");
		System.out.println("list in-progress");
		System.out.println("\n");

	}

	private void invalidOperation() {
		System.out.println("Invalid Operation!!!");
	}

	private void printTaskList(List<Task> list) {
		if (list != null) {
			if (list.isEmpty())
				System.out.println("No task found !!!");
			else
				for (Task task : list) {
					System.out.printf("Task Id : %d\n", task.getId());
					System.out.printf("Description : %s\n", task.getDesc());
					System.out.printf("Task Status : %s\n", task.getStatus());
					System.out.printf("Created At : %s\n", task.getCreatedAt().toString());
					System.out.printf("Updated At : %s\n", task.getUpdatedAt().toString());
					System.out.println();
				}
		}
	}
}
