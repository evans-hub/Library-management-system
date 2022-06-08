package com.evans.Controller;

import com.evans.Entity.BookEntity;
import com.evans.Entity.Role;
import com.evans.Entity.UserEntity;
import com.evans.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Resource {
    private  final UserService userService;
    @GetMapping("/allusers")
    public ResponseEntity<List<UserEntity>> getusers(){
        return ResponseEntity.ok().body(userService.listUsers());

    }
    @GetMapping("/allbooks")
    public ResponseEntity<List<BookEntity>> getBooks(){
        return ResponseEntity.ok().body(userService.listBooks());

    }
    @PostMapping("/saveuser")
    public ResponseEntity<UserEntity> saveuser(@RequestBody UserEntity user){
        return ResponseEntity.ok().body(userService.saveUser(user));

    }
    @PostMapping("/saverole")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));

    }
    @PostMapping("/addroletouser")
    public ResponseEntity addRoleToUserr(@RequestBody RR rr){
        userService.addRoleToUserr(rr.getUsername(),rr.getRoleName());
        return ResponseEntity.ok().build();

    }
    @PostMapping("/savebook")
    public ResponseEntity<BookEntity> saveBook(@RequestBody BookEntity book){
        return ResponseEntity.ok().body(userService.saveBook(book));

    }
    @PostMapping("/addbooktouser")
    public ResponseEntity addBooksToUser(@RequestBody RR rr){
        userService.addBooksToUser(rr.getUsername(),rr.getBookname());
        return ResponseEntity.ok().build();

    }
    @PostMapping("/returnbook")
    public ResponseEntity returnBook(@RequestBody RR rr){
        userService.returnBook(rr.getUsername(),rr.getBookname());
        return ResponseEntity.ok().build();

    }
    @GetMapping("/user/{username}")
    public ResponseEntity<UserEntity> getUser(@PathVariable("username") String username){

        return ResponseEntity.ok().body(userService.getUser(username));
    }
}
@Data
class RR{
   private String username;
   private String roleName;
   private String bookname;
}
