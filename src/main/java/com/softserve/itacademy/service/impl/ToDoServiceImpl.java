package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository todoRepository;

    public ToDoServiceImpl(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        if (todo != null) {
            return todoRepository.save(todo);
        }
        throw new NullEntityReferenceException("ToDo cannot be 'null'");
    }

    @Override
    public ToDo readById(long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + id + " not found"));
    }

    @Override
    public ToDo update(ToDo todo) {
        if (todo != null) {
            readById(todo.getId());
            return todoRepository.save(todo);
        }
        throw new NullEntityReferenceException("ToDo cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        ToDo todo = readById(id);
        todoRepository.delete(todo);
    }

    @Override
    public List<ToDo> getAll() {
        return todoRepository.findAll();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        return todoRepository.getByUserId(userId);
    }
}
