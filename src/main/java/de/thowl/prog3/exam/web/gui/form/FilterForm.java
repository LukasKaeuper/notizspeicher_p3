package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;

import java.util.List;

@Data
public class FilterForm {
    private List<String> filterTags;
    private String filterCategory;
    private boolean mustContainAllTags;
    private String filterDateType;
    private String filterDate;
    private boolean filterNoteTypeText;
    private boolean filterNoteTypeLink;
    private boolean filterNoteTypeImage;
    private String sortBy;
}
