package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.web.dto.CategoryDTO;
import org.springframework.stereotype.Component;

@Component("categorymapper")
public class CategoryMapper {

    public CategoryDTO map(de.thowl.prog3.exam.storage.entities.Category in){
        if (in == null) return null;
        return new CategoryDTO(in.getId(), in.getCategoryName(), in.getCategoryColour());
    }
}
