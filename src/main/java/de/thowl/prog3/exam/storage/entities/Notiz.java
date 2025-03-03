package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notiz {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;
}
