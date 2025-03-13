package de.thowl.prog3.exam.security.entities;

import java.util.Date;

import lombok.Data;

@Data
public class AccessToken {
    private String USID; // unique session id
    private Date lastactive;
    private Long user_id;
}
