package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> listFromDb = userService.getAllUserEntry();
        return new ResponseEntity<>(listFromDb, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)
    {
       try
       {
            User user1 = userService.saveUserEntry(user);
            return new ResponseEntity<>(user1,HttpStatus.CREATED);
       }
       catch(Exception e)
       {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @PutMapping("/reset")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User userEntry )
    {

       User userEntryFromDB = userService.findByUserName(userEntry.getUserName());

        if(userEntryFromDB!=null)
        {
            userEntryFromDB.setPassword(userEntry.getPassword());
            userService.saveUserEntry(userEntryFromDB);
            return new ResponseEntity<>(userEntryFromDB,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }





}
