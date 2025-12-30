package com.demo.controller;

import com.demo.entity.JournalEntry;
import com.demo.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalController
{

    @Autowired
    private JournalService journalService;

    @GetMapping("/getall")
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> entries = journalService.getAllJournalEntry();
        return new ResponseEntity<>(entries,HttpStatus.OK);
    }


@PostMapping
    public ResponseEntity<JournalEntry> addEntry(@Valid @RequestBody JournalEntry myEntry)
{
    try {
        myEntry.setSavedDate(LocalDateTime.now());
        JournalEntry savedEntry = journalService.saveJournalEntry(myEntry);
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


@DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteJournalEntryById(@PathVariable String id)
{
    String cleanId = id.trim();
    Optional<JournalEntry> entryfromdb = journalService.findById(cleanId);
    if(entryfromdb.isPresent())
    {
        journalService.deleteById(cleanId);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
    return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);


}

@PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable String id, @RequestBody JournalEntry myEntry)
{
    String cleanId = id.trim();
  JournalEntry entryfromdb  =  journalService.findById(cleanId).orElse(null);
  if(entryfromdb!=null)
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
