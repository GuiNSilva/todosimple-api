package com.lucasangelo.todosimple.models;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//import org.springframework.scheduling.config.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")

public class User {

    public interface CreateUSER{}
    public interface  UpdateUser {}

    public static final String TABLE_NAME ="user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private  Long id;

    @OneToMany(mappedBy = "user")
    private List<Task>tasks = new ArrayList<Task>();


    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUSER.class)
    @NotEmpty(groups = CreateUSER.class)
    @Size(groups = CreateUSER.class, min = 2 , max = 100)
    private  String username;




    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUSER.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUSER.class, UpdateUser.class})
    @Size(groups = {CreateUSER.class, UpdateUser.class}, min = 3, max = 60)
    private  String password;



    public User() {

    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (!(o instanceof User))
            return false;


        User other = (User) o;
        if (this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id)) {
                return false;

            }
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username)
                && Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 :this.id.hashCode());
        return result;
    }
}
