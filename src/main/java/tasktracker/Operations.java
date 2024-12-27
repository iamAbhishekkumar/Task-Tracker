package tasktracker;

import java.util.Map;

public class Operations {
	final static Map<String, Integer> coreOperations = Map.of("add", 1, "update", 2, "delete", 3, "mark-in-progress", 4,
			"mark-done", 5, "list", 6);
	final static Map<String, Integer> listOperations = Map.of("done", 1, "todo", 2, "in-progress", 3);
	private String[] args;

	public Operations(String[] args) {
		this.args = args;
	}

	void parser() {
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
			System.out.println("Invalid Operation!!!");
			break;
		}
	}

	private void add() {

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
			listAll();
		} else if (args.length == 2) {
			Integer op = listOperations.getOrDefault(args[1], -1);
			switch (op) {
			case 1:
				listDone();
				break;
			case 2:
				listTodo();
				break;
			case 3:
				listInProgress();
				break;
			default:
				System.out.println("Invalid Operation!!!");
				break;
			}
		} else
			System.out.println("list operation require maximum 2 command line operation !!!");

	}

	private void listAll() {

	}

	private void listDone() {

	}

	private void listTodo() {

	}

	private void listInProgress() {

	}
}
