package com.demo.service;

import com.demo.entity.JournalEntry;
import com.demo.entity.User;
import com.demo.repository.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService
{
@Autowired
    private JournalRepo journalRepo;
@Autowired
private UserService userService;


 public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName)
 {
     User user = userService.findByUserName(userName);
     JournalEntry savedEntry = journalRepo.save(journalEntry);
     user.getJournalEntryList().add(savedEntry);
     userService.saveUserEntry(user);
     return savedEntry;
 }
    public JournalEntry saveJournalEntry(JournalEntry journalEntry)
    {
        JournalEntry savedEntry = journalRepo.save(journalEntry);
        return savedEntry;
    }

 public List<JournalEntry> getAllJournalEntry()
 {
     return journalRepo.findAll();
 }

 public Optional<JournalEntry> findById(String id)
 {
     return journalRepo.findById(id);
 }

 public void deleteById(String id, String userName)
 {
     User user = userService.findByUserName(userName);
     if(journalRepo.existsById(id))
     {
         user.getJournalEntryList().removeIf(x->x.getId().equals(id));
         userService.saveUserEntry(user);
         journalRepo.deleteById(id);
         System.out.println("Successfully Deleted ");
     }
    else {
         System.out.println("Id not Exist");
     }

 }



}
