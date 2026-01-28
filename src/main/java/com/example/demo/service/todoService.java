package com.example.demo.service; 

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public void save(String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        repository.save(todo);
    }

    public void update(Long id, String title) {
        Todo todo = repository.findById(id).orElseThrow();
        todo.setTitle(title);
        repository.save(todo);
    }

    public void toggle(Long id) {
        Todo todo = repository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        repository.save(todo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}