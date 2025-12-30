package com.demo.service;

import com.demo.entity.JournalEntry;
import com.demo.repository.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService
{
@Autowired
    private JournalRepo journalRepo;

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

 public void deleteById(String id)
 {
     if(journalRepo.existsById(id))
     {
         journalRepo.deleteById(id);
         System.out.println("Successfully Deleted ");
     }
    else {
         System.out.println("Id not Exist");
     }

 }



}
