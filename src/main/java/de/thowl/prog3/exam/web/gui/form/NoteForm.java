package de.thowl.prog3.exam.web.gui.form;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NoteForm {
    private String title;
    private String link;
    private String content;
    private List<String> tags;
    private String category;
    private MultipartFile image;
}
