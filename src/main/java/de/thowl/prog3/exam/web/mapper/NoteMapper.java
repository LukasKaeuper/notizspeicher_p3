package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.web.dto.NoteDTO;
import org.springframework.stereotype.Component;

@Component("notemapper")
public class NoteMapper {

    public NoteDTO map(de.thowl.prog3.exam.storage.entities.Note in) {
        return new NoteDTO(in.getId(), in.getTitle(), in.getContent(), in.getTags(), in.getCategory().getCategoryName());
    }
}
