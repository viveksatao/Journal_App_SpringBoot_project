package com.demo.repository;

import com.demo.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface JournalRepo extends MongoRepository< JournalEntry, String> {
}
