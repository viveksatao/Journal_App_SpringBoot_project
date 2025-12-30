package com.demo.entity;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@ToString
@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {


    @Id
    private String id;
    @NotNull
    @Size(max = 10, message = "Size must be less than 10")
    private String title;

    @NotNull
    @Size(max = 100, message = "Must be less than 100")
    private String content;

    private LocalDateTime savedDate;
}



