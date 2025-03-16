package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
