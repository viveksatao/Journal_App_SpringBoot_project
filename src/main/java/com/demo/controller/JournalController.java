package com.demo.controller;

import com.demo.entity.JournalEntry;
import com.demo.service.JournalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(entries);
    }


@PostMapping
    public boolean addEntry(@Valid @RequestBody JournalEntry myEntry)
{
    JournalEntry savedEntry = journalService.saveJournalEntry(myEntry);
    return true;
}

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String id) {
        String cleanId = id.trim();
        JournalEntry entry = journalService.findById(cleanId); // throws exception if not found

        return ResponseEntity.ok(entry);
    }


@DeleteMapping("/id/{id}")
    public boolean deleteJournalEntryById(@PathVariable String id)
{
    String cleanId = id.trim();
   journalService.deleteById(cleanId);
   return true;

}

@PutMapping("/id/{id}")
    public JournalEntry updateJournalById(@PathVariable String id, @Valid @RequestBody JournalEntry myEntry)
{
  JournalEntry entryfromdb  =  journalService.findById(id);
  if(entryfromdb!=null)
  {
      entryfromdb.setContent(myEntry.getContent());
      entryfromdb.setContent(myEntry.getTitle());
      return journalService.saveJournalEntry(entryfromdb);
  }
  return null;


}







}
