package com.su.todomvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	@Autowired
	private TodoRepository todoRepository;

	@RequestMapping(value = "/getAll")
	public List<Todo> getAll() {
		return todoRepository.findAll();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Todo add(@RequestBody Map<String, Object> map) {
		String title = (String) map.get("title");
		Todo todo = new Todo();
		todo.setTitle(title);
		todo.setCompleted(false);
		return todoRepository.save(todo);
	}
	
	@RequestMapping(value = "/deleteById")
	public String deleteById(@RequestParam("id") String id) {
		todoRepository.deleteById(id);
		return id;
	}

	@DeleteMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		todoRepository.deleteById(id);
		return id;
	}
	
	@RequestMapping(value = "/update")
	public Todo update(@RequestBody Map<String, Object> map) {
		String id = (String) map.get("id");
		String title = (String) map.get("title");
		Boolean completed = (Boolean) map.get("completed");
		Todo todo = todoRepository.getOne(id);
		if (title!= null) {
			todo.setTitle(title);
		}
		if (completed != null) {
			todo.setCompleted(completed);
		}
		todoRepository.save(todo);
		return todo;
	}
	
	@RequestMapping(value = "/isCompletedAll")
	public List<Todo> isCompletedAll(@RequestParam("completed") Boolean completed) {
		List<Todo> todos = todoRepository.findAll();
		for(Todo todo: todos) {
			todo.setCompleted(completed);
		}
		todoRepository.saveAll(todos);
		return todos;
	}
	
	@RequestMapping(value = "/clearCompleted")
	public List<Todo> clearCompleted() {
		List<Todo> todos = todoRepository.findAll();
		List<Todo> delTodos = new ArrayList<Todo>();
		for(Todo todo: todos) {
			if (todo.getCompleted()) {
				delTodos.add(todo);
			}
		}
		todoRepository.deleteAll(delTodos);
		return todoRepository.findAll();
	}
}
