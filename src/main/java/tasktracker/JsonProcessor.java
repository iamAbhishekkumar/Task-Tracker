package tasktracker;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tasktracker.model.Task;

public class JsonProcessor {
	
	private ObjectMapper mapper;
	
	
	
	
	public JsonProcessor() {
		super();
		this.mapper = new ObjectMapper();
		
	}
	
	void init() {
		
	}

	int addToJson(Task task) {
		try {
			String json = mapper.writeValueAsString(task);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
		return 0;
	}
}
