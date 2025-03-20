package de.thowl.prog3.exam.web.dto;

import java.util.List;

public record NoteDTO (long id, String title, String content, List<String> tags, String category, String categoryColour, String createdAt, String shareToken, String shareLink, String type, String image) {
}
