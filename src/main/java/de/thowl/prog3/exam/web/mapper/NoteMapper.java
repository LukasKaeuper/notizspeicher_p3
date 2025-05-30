package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.dto.NoteDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Base64;


@Component("notemapper")
public class NoteMapper {

    public NoteDTO map(Note in) {
        return new NoteDTO(in.getId(), in.getTitle(), in.getContent(), in.getTags(), in.getCategory() != null ? in.getCategory().getCategoryName() : null, in.getCategory() != null ? in.getCategory().getCategoryColour() : "#f9f9a9",
                in.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm")), in.getShareToken(), in.getShareLink(), in.getType(), in.getImage() != null ? "background-image: url(data:image/jpeg;base64," + Base64.getEncoder().encodeToString(in.getImage()) + ")" : null, in.getLink());
    } //Base64 Encoder Idee von StackOverflow
}
