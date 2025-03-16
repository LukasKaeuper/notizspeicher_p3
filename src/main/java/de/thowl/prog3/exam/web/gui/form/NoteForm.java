package de.thowl.prog3.exam.web.gui.form;


import lombok.Data;

import java.util.List;

@Data
public class NoteForm {
    private String title;
    private String content;
    private List<String> tags;
}
