package de.thowl.prog3.exam.storage.entities;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "notes")
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private Long userId;

    @ElementCollection(fetch = FetchType.EAGER) // Sammlung soll immer geladen werden
    @CollectionTable(name = "my_tags", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tags")
    private List<String> tags;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;

//    @ElementCollection
//    private List<Note> subNotes;
}
