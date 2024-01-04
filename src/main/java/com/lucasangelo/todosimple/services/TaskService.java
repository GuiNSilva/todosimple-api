package com.lucasangelo.todosimple.services;


import com.lucasangelo.todosimple.models.Task;
import com.lucasangelo.todosimple.models.User;
import com.lucasangelo.todosimple.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrada! id: "+ id +", Tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public Task create (Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId (null); // setar o id nulo vai evitar sobescrever algum usuario
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e){
            throw  new RuntimeException("Não e Possivel excluir pois há entidades relacionadas");
        }
    }

}
