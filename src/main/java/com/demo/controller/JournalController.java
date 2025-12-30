package com.demo.controller;

import com.demo.entity.JournalEntry;
import com.demo.entity.User;
import com.demo.service.JournalService;
import com.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalController
{

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @GetMapping("/getall/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        String clearname = userName.trim();
        User userFromDB = userService.findByUserName(clearname);
        List<JournalEntry> entries = userFromDB.getJournalEntryList();
        return new ResponseEntity<>(entries,HttpStatus.OK);
    }


@PostMapping("{userName}")
    public ResponseEntity<JournalEntry> addEntry(@Valid @RequestBody JournalEntry myEntry, @PathVariable String userName)
{
    try {
        String clearname = userName.trim();
        User userFromDB = userService.findByUserName(clearname);
        myEntry.setSavedDate(LocalDateTime.now());
        JournalEntry savedEntry = journalService.saveJournalEntry(myEntry,userName);
        return new ResponseEntity<>(savedEntry,HttpStatus.CREATED);
    }
    catch(Exception e)
    {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<JournalEntry>> getJournalEntryById(@PathVariable String id) {
        String cleanId = id.trim();
        Optional<JournalEntry> journalEntry = journalService.findById(cleanId);
        if(journalEntry.isPresent())
        {
            return new ResponseEntity<>(journalEntry,HttpStatus.FOUND);
        }
         return ResponseEntity.notFound().build(); // throws exception if not found
    }


@DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<Boolean> deleteJournalEntryById(@PathVariable String userName,@PathVariable String id)
{
    String cleanName = userName.trim();
    String cleanId = id.trim();
    User user = userService.findByUserName(cleanName);
    Optional<JournalEntry> entryfromdb = journalService.findById(cleanId);
    if(entryfromdb.isPresent() && user!=null)
    {
        journalService.deleteById(cleanId, userName);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
    return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);


}

@PutMapping("/id/{id}/{username}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable String id, @PathVariable String useName, @RequestBody JournalEntry myEntry)
{
    String cleanId = id.trim();
    String cleanName = useName.trim();
    User user = userService.findByUserName(cleanName);
  JournalEntry entryfromdb  =  journalService.findById(cleanId).orElse(null);
  if(entryfromdb!=null && user!=null)
  {
      if(myEntry.getTitle()!=null && !myEntry.getTitle().isBlank())
  {
      entryfromdb.setTitle(myEntry.getTitle());
  }

     if(myEntry.getContent()!=null && !myEntry.getContent().isBlank())
     {
         entryfromdb.setContent(myEntry.getContent());
     }
      return ResponseEntity.ok(journalService.saveJournalEntry(entryfromdb));

  }
    return ResponseEntity.notFound().build();


}







}
