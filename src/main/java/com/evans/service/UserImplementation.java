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
    public void addRoleToUserr(String email, String roleName) {
UserEntity user=userRepo.findByEmail(email);
Role role=roleRepo.findByName(roleName);
user.getRoles().add(role);
    }

    @Override
    public UserEntity getUser(String email) {
        return userRepo.findByEmail(email);
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
    public void addBooksToUser(String email, String bookname) {
        BookEntity name=bookRepo.findByName(bookname);
        UserEntity user=userRepo.findByEmail(email);
        user.getBooks().add(name);

    }

    @Override
    public void returnBook(String email, String bookname) {
        BookEntity name=bookRepo.findByName(bookname);
        UserEntity user=userRepo.findByEmail(email);
        user.getBooks().remove(name);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user=userRepo.findByEmail(email);
        if (user==null){
            log.error("User not found in the database");
            System.out.println("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");

        }
        else
        {
            log.info("User found in the database:{}",email);
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);




        };
    }

