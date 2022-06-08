package com.evans.service;

import com.evans.Entity.BookEntity;
import com.evans.Entity.Role;
import com.evans.Entity.UserEntity;


import java.util.List;


public interface UserService {
    UserEntity saveUser(UserEntity user);
    Role saveRole(Role role);
    BookEntity saveBook(BookEntity book);
    void addRoleToUserr(String username,String roleName);
    UserEntity getUser(String username);
    List<UserEntity> listUsers();
    List<BookEntity> listBooks();
    void addBooksToUser(String username,String bookname);
    void returnBook(String username,String bookname);
}
