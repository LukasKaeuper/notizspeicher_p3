package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "notes")
public class Note {

    @Id
    @GeneratedValue
    private Long Id;

    private String title;

    private String content;

    private long userId;

    //private List<Note> subNotes;

//    @ElementCollection
//    private List<String> tags;
}
