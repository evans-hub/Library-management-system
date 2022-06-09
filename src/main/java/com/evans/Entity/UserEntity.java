package com.evans.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @Column(nullable = false,unique = true)
    @Email(message = "Provide a valid Email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles=new ArrayList<>();
    @ManyToMany
    private Collection<BookEntity> books=new ArrayList<>();
    private String resetToken;

}