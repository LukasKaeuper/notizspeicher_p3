package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.dto.NoteDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component("notemapper")
public class NoteMapper {

    public NoteDTO map(Note in) {
        return new NoteDTO(in.getId(), in.getTitle(), in.getContent(), in.getTags(), in.getCategory() != null ? in.getCategory().getCategoryName() : null, in.getCategory() != null ? in.getCategory().getCategoryColour() : "#f9f9a9",
                in.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm")), in.getShareToken(), in.getShareLink(), in.getType());
    }
}
