package io.github.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.todo.Service.TodoService;
import io.github.todo.dto.TodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @RequestMapping("/hrllo")
    public String hello() {
        return "hi";
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST, consumes =MediaType.APPLICATION_JSON_VALUE )
    public List<TodoDTO> addTodo(@RequestBody TodoDTO todoDTO) throws JsonProcessingException {
          return todoService.addTodo(todoDTO);
    }

    @RequestMapping(value="/fetch",method = RequestMethod.GET)
    public List<TodoDTO> addTodo() {
        List<TodoDTO> todos = todoService.fetchTodo();
        System.out.println("Inside Controller : "+todos.toString());
        return todos;
    }

    @RequestMapping(value = "/update/{id}",method =RequestMethod.PUT )
    public String updateTodo(@PathVariable("id") String id)
    {
        return todoService.updateTodo(id);
    }

}
