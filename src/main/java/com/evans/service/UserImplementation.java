package com.evans.service;

import com.evans.Entity.BookEntity;
import com.evans.Entity.Role;
import com.evans.repo.BookRepo;
import com.evans.repo.RoleRepo;
import com.evans.Entity.UserEntity;
import com.evans.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserImplementation implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BookRepo bookRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserEntity saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public BookEntity saveBook(BookEntity book) {
        return bookRepo.save(book);
    }

    @Override
    public void addRoleToUserr(String username, String roleName) {
UserEntity user=userRepo.findByUsername(username);
Role role=roleRepo.findByName(roleName);
user.getRoles().add(role);
    }

    @Override
    public UserEntity getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserEntity> listUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<BookEntity> listBooks() {
      return  bookRepo.findAll();
    }

    @Override
    public void addBooksToUser(String username, String bookname) {
        BookEntity name=bookRepo.findByName(bookname);
        UserEntity user=userRepo.findByUsername(username);
        user.getBooks().add(name);

    }

    @Override
    public void returnBook(String username, String bookname) {
        BookEntity name=bookRepo.findByName(bookname);
        UserEntity user=userRepo.findByUsername(username);
        user.getBooks().remove(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userRepo.findByUsername(username);
        if (user==null){
            log.error("User not found in the database");
            System.out.println("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");

        }
        else
        {
            log.info("User found in the database:{}",username);
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);




        };
    }

