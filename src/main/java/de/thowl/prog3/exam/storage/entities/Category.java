package de.thowl.prog3.exam.storage.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "categoryName")
    private String categoryName;

    @Column(name="categoryColour")
    private String categoryColour;

    @Nullable
    @OneToMany(mappedBy = "category")
    private List<Note> notes;
}
