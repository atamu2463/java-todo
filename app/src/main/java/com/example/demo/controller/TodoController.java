package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoService; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Controller 
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository repository;
    private final TodoService service;

    public TodoController(TodoRepository repository, TodoService service) {
        this.repository = repository;
        this.service = service;
    }

    // 1. 一覧表示 (Read)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", repository.findAll());
        //フォーム用の空のTodoオブジェクトを追加
        model.addAttribute("todo", new Todo());
        return "index";
    }

    // 2. 新規作成 (Create)
    @PostMapping
    public String create(@Validated @ModelAttribute Todo todo, Model model, BindingResult result) {
        //バリデーションチェック
        if (result.hasErrors()) {
            model.addAttribute("todos", repository.findAll());
            return "index";
        }
        service.save(todo.getTitle());

        return "redirect:/todos"; 
    }

    // 3. 完了状態の切り替え
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        Todo todo = repository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        repository.save(todo);
        return "redirect:/todos";
    }

    // 4. 削除 (Delete)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/todos";
    }
}