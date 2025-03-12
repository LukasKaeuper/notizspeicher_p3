package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;

@Data
public class NoteForm {
    private long noteId;
    private String title;
    private String content;
    private long userId;
}
