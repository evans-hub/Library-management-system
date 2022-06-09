package com.evans.service;

import com.evans.Entity.BookEntity;
import com.evans.Entity.Role;
import com.evans.Entity.UserEntity;


import java.util.List;


public interface UserService {
    UserEntity saveUser(UserEntity user);
    Role saveRole(Role role);
    BookEntity saveBook(BookEntity book);
    void addRoleToUserr(String email,String roleName);
    UserEntity getUser(String email);
    List<UserEntity> listUsers();
    List<BookEntity> listBooks();
    void addBooksToUser(String email,String bookname);
    void returnBook(String email,String bookname);
}
