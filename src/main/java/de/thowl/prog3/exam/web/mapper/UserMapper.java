package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.web.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * This class converts between JPA based objects and a lightweight DTO
 * records.
 * The DTO is used to isolate the web layer from database dependencies
 */
@Component("usermapper")
public class UserMapper {

    public UserDTO map(de.thowl.prog3.exam.storage.entities.User in) {
        return new UserDTO(in.getId(), in.getName(), in.getEmail());
    }

}
