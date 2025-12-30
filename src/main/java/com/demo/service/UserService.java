package com.demo.service;

import com.demo.entity.JournalEntry;
import com.demo.entity.User;
import com.demo.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User saveUserEntry(User user)
    {
        User savedEntry = userRepo.save(user);
        return savedEntry;
    }

    public List<User> getAllUserEntry()
    {
        return userRepo.findAll();
    }

    public User findByUserName(String userName)
    {
        return userRepo.findUserByUserName(userName);
    }



    public void deleteUserById(ObjectId id)
    {
        if(userRepo.existsById(id))
        {
            userRepo.deleteById(id);
            System.out.println("Successfully Deleted ");
        }
        else {
            System.out.println("Id not Exist");
        }

    }
}
