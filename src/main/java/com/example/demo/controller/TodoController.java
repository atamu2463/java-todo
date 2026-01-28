package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // RestControllerからControllerに変更
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    // 1. 一覧表示 (Read)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", repository.findAll());
        return "index"; // templates/index.html を探す
    }

    // 2. 新規作成 (Create)
    @PostMapping
    public String create(@RequestParam String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        repository.save(todo);
        return "redirect:/todos"; // 作成後は一覧へリダイレクト
    }

    // 3. 完了状態の切り替え (Updateの簡易版)
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