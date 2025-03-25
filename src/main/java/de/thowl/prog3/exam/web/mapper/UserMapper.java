package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.web.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * This class converts between JPA based objects and a lightweight DTO
 * records.
 * The DTO is used to isolate the web layer from database dependencies
 *
 * Der UserMapper ist eine Klasse, die für die Umwandlung von User-Entitäten in UserDTO-Objekte und umgekehrt verantwortlich ist.
 * Dies ist nuetzlich, um die Datenbank-Entitaeten von den Datenuebertragungsobjekten (DTOs) zu trennen,
 * die in der Web-Schicht verwendet werden. Dadurch wird die Web-Schicht von den Datenbank-Details isoliert.
 */
@Component("usermapper")
public class UserMapper {

    public UserDTO map(de.thowl.prog3.exam.storage.entities.User in) {
        return new UserDTO(in.getId(), in.getName(), in.getEmail());
    }

}
