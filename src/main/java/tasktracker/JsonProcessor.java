package tasktracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

	List<Task> listAll() {
		return null;

	}

	List<Task> listDone() {
		return null;
	}

	List<Task> listTodo() {
		return null;
	}

	List<Task> listInProgress() {
		return null;
	}

	long getLengthOfRecord() {
		JsonNode rootArray;
		try {
			rootArray = mapper.readTree(jsonFile);
			return rootArray.size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
