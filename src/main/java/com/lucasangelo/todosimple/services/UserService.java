package com.lucasangelo.todosimple.services;


import com.lucasangelo.todosimple.models.User;
import com.lucasangelo.todosimple.repositories.TaskRepository;
import com.lucasangelo.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! Id:" + id +", Tipo:" + User.class.getName()
        ));
    }

    @Transactional
    public User create (User obj){
        obj.setId (null); // setar o id nulo vai evitar sobescrever algum usuario
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e){
            throw  new RuntimeException("Não e Possivel excluir pois há entidades relacionadas");
        }
    }


}
