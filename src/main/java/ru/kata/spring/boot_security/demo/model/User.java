package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
//    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;
    @Column(name = "age")
//    @Min(value = 0, message = "Возраст не может быть меньше ноля")
    private int age;

    @Column(name = "email")
//    @NotEmpty(message = "Адрес не должен быть пустым")
    private String email;

    @Column(name = "password")
//    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    public User() {
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age + "; ";
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public void setRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    public StringBuilder getStringRoles() {
        StringBuilder stringRoles = new StringBuilder();

        for (Role i : roles) {
            stringRoles.append(i.toString());
            stringRoles.append(" ");

        }
        return stringRoles;
    }
}
