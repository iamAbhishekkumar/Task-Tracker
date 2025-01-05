package tasktracker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import tasktracker.helpers.TaskStatus;
import tasktracker.model.Task;

public class JsonProcessor {

	private ObjectMapper mapper;
	private File jsonFile;

	public JsonProcessor() {
		super();
		this.mapper = new ObjectMapper();
		init();

	}

	void init() {
		try {
			this.jsonFile = new File("./task-tracker.json");
			if (!jsonFile.exists()) {

				jsonFile.createNewFile();
				mapper.writeValue(jsonFile, new ArrayNode(mapper.getNodeFactory()));

			}
		} catch (IOException e) {
			System.out.println("Unable to read the file");
		}

	}

	int addToJson(Task task) {
		try {
			JsonNode rootArray = mapper.readTree(jsonFile);
			if (rootArray.isArray()) {
				ArrayNode arrayNode = (ArrayNode) rootArray;
				arrayNode.add(mapper.valueToTree(task));
				mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, arrayNode);
				return 0;
			} else {
				System.out.println("The root element is not an array!!.");
				return -1;
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	int updateToJson(Long id, String desc, TaskStatus taskStatus) {
		try {
			List<Task> taskList = mapper.readValue(jsonFile,
					mapper.getTypeFactory().constructCollectionType(List.class, Task.class));
			for (Task task : taskList) {
				if (task.getId() == id) {
					if (null != desc)
						task.setDesc(desc);
					if (null != taskStatus)
						task.setStatus(taskStatus);
					task.setUpdatedAt(new Date());
					break;
				}
			}
			mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, taskList);
			return 0;

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	int deleteToJson(long id) {
		boolean found = false;
		try {
			JsonNode rootArray = mapper.readTree(jsonFile);
			if (rootArray.isArray()) {
				ArrayNode arrayNode = (ArrayNode) rootArray;
				for (int i = 0; i < arrayNode.size(); i++) {
					long currId = arrayNode.get(i).path("id").asLong();
					if (currId == id) {
						arrayNode.remove(i);
						found = true;
						break;
					}
				}
				if (found) {
					mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, arrayNode);
					return 0;
				}
				return 1;
			} else {
				System.out.println("The root element is not an array!!.");
				return -1;
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	List<Task> listAll() {
		List<Task> taskList = null;
		try {
			taskList = mapper.readValue(jsonFile,
					mapper.getTypeFactory().constructCollectionType(List.class, Task.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;

	}

	List<Task> listByStatus(TaskStatus status) {
		List<Task> doneTaskList = null;
		try {
			List<Task> taskList = mapper.readValue(jsonFile,
					mapper.getTypeFactory().constructCollectionType(List.class, Task.class));
			doneTaskList = taskList.stream().filter(task -> task.getStatus().equals(status))
					.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doneTaskList;
	}

	long getAutoIncId() {
		try {
			List<Task> taskList = mapper.readValue(jsonFile,
					mapper.getTypeFactory().constructCollectionType(List.class, Task.class));
			return taskList.size() != 0 ? (taskList.get(taskList.size() - 1).getId()) + 1 : 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
