package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@Entity
@Table(name = "sessions")
@NoArgsConstructor
public class Session {

    public Session(String token, User u) {
        this.token = token;
        this.userId = u.getId();
        this.createdAt = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private Date createdAt;

    @NotNull
    private String token;

    @NotNull
    private Long userId;;
}
