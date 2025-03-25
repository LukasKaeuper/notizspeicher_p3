package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.*;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void saveNote(String title, String content, Long userId, List<String> tags, String categoryName, MultipartFile image, String link) {
        Note note = new Note();
        note.setTitle(title);
        note.setUserId(userId);
        note.setTags(tags);
        note.setCreatedAt(LocalDateTime.now());
        String shareToken = generateToken();
        note.setShareToken(shareToken);
        note.setShareLink(generateLink(shareToken));
        note.setContent(content);

        if (!categoryName.isEmpty()) {
            note.setCategory(categoryService.getCategory(categoryName, userId));
        }
        else {
            note.setCategory(null);
        }

        if (!image.isEmpty()) {
            try {
                note.setImage(image.getBytes());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            URL url = new URL(link);
            note.setLink(url.toExternalForm());
        } catch (MalformedURLException ignored) {}

        if (!note.getContent().isEmpty() && note.getLink() == null && note.getImage() == null) {note.setType("text");}
        if (note.getContent().isEmpty() && note.getLink() != null && note.getImage() == null) {note.setType("link");}
        if (note.getContent().isEmpty() && note.getLink() == null && note.getImage() != null) {note.setType("image");}
        if (!note.getContent().isEmpty() && note.getLink() != null && note.getImage() == null) {note.setType("text_link");}
        if (!note.getContent().isEmpty() && note.getLink() == null && note.getImage() != null) {note.setType("text_image");}
        if (note.getContent().isEmpty() && note.getLink() != null && note.getImage() != null) {note.setType("link_image");}
        if (!note.getContent().isEmpty() && note.getLink() != null && note.getImage() != null) {note.setType("text_link_image");}
        if (note.getContent().isEmpty() && note.getLink() == null && note.getImage() == null) {note.setType("empty");}
        repository.save(note);
    }

    @Override
    public List<Note> getNotesByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Note> getFilteredNotes(Long userId, List<String> filterTags, String filterCategory, boolean mustContainAllTags, String filterDateType, String filterDate, boolean filterNoteTypeText, boolean filterNoteTypeLink, boolean filterNoteTypeImage, String sortBy) {
        List<Note> filteredNotes = new ArrayList<>();
        String filterNoteType = "disabled";
        if (filterNoteTypeText && !filterNoteTypeLink && !filterNoteTypeImage) {filterNoteType = "text";}
        if (!filterNoteTypeText && filterNoteTypeLink && !filterNoteTypeImage) {filterNoteType = "link";}
        if (!filterNoteTypeText && !filterNoteTypeLink && filterNoteTypeImage) {filterNoteType = "image";}
        if (filterNoteTypeText && filterNoteTypeLink && !filterNoteTypeImage) {filterNoteType = "text_link";}
        if (filterNoteTypeText && !filterNoteTypeLink && filterNoteTypeImage) {filterNoteType = "text_image";}
        if (!filterNoteTypeText && filterNoteTypeLink && filterNoteTypeImage) {filterNoteType = "link_image";}
        if (filterNoteTypeText && filterNoteTypeLink && filterNoteTypeImage) {filterNoteType = "text_link_image";}

        for (Note note : repository.findByUserId(userId)) {
            if (mustContainAllTags){
                if ((checkFilterNoteType(filterNoteType, note.getType())) && ((filterTags.isEmpty() && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())) ||
                        (!filterTags.isEmpty() && note.getTags().containsAll(filterTags) && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())))) {
                    filteredNotes.add(note);
                }
            }
            else{
                if ((checkFilterNoteType(filterNoteType, note.getType())) && ((filterTags.isEmpty() && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())) ||
                        (!filterTags.isEmpty() && !Collections.disjoint(note.getTags(), filterTags) && (filterCategory.equals("disabled") || note.getCategory() != null && filterCategory.equals(note.getCategory().getCategoryName()) || note.getCategory() == null && filterCategory.isEmpty())))) {
                    filteredNotes.add(note);
                }
            }
            if (!filterDateType.equals("disabled") && !filterDate.isEmpty()) {
                LocalDateTime filterDateObject = LocalDateTime.parse(filterDate);
                switch(filterDateType){
                    case "before":
                        if (filterDateObject.isBefore(note.getCreatedAt())) {
                            filteredNotes.remove(note);
                        }
                        break;
                    case "after":
                        if (filterDateObject.isAfter(note.getCreatedAt())) {
                            filteredNotes.remove(note);
                        }
                        break;
                }
            }
            switch (sortBy){
                case "createdAtAscending":
                    filteredNotes.sort((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()));
                    break;
                case "createdAtDescending":
                    filteredNotes.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
                    break;
                case "category":
                    filteredNotes.sort(new Comparator<Note>() {
                        @Override
                        public int compare(Note o1, Note o2) {
                            if (o1.getCategory() == null && o2.getCategory() != null) {return 1;}
                            if (o1.getCategory() != null && o2.getCategory() == null) {return -1;}
                            if (o1.getCategory() == null && o2.getCategory() == null) {return 0;}
                            return o1.getCategory().getCategoryName().compareTo(o2.getCategory().getCategoryName());
                        }
                    });
                    break;
            }
        }
        return filteredNotes;
    }

    private boolean checkFilterNoteType(String filterNoteType, String noteType){
        return switch (filterNoteType) {
            case "disabled" -> true;
            case "text" -> noteType.equals("text");
            case "link" -> noteType.equals("link");
            case "image" -> noteType.equals("image");
            case "text_link" -> noteType.equals("text_link") || noteType.equals("text") || noteType.equals("link");
            case "text_image" -> noteType.equals("text_image") || noteType.equals("text") || noteType.equals("image");
            case "link_image" -> noteType.equals("link_image") || noteType.equals("link") || noteType.equals("image");
            case "text_link_image" -> noteType.equals("text_link_image");
            default -> false;
        };
    }

    @Override
    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateLink(String token){
        return "http://localhost:8080/sharedNote?shareToken=" + token;
    }

    @Override
    public Note getNoteByToken(String token){
        return repository.findByShareToken(token).orElseThrow();
    }

    @Override
    public void deleteNote(Long userId, Long noteId) {
        repository.deleteById(noteId);
    }
}
