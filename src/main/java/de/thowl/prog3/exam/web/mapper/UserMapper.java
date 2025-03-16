package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.web.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * This class converts between JPA based objects and a lightweight DTO
 * records.
 * The DTO is used to isolate the web layer from database dependencies
 *
 * Der UserDTO (Data Transfer Object) ist eine einfache Klasse, die nur die Daten enthält,
 * die zwischen der Web-Schicht und der Service-Schicht übertragen werden sollen.
 * Es enthält keine Geschäftslogik oder Datenbank-Operationen. Ein UserDTO kann z.B. Felder wie id, username, email usw. enthalten.
 *
 * Der UserMapper ist eine Klasse, die für die Umwandlung von User-Entitäten in UserDTO-Objekte und umgekehrt verantwortlich ist.
 * Dies ist nützlich, um die Datenbank-Entitäten von den Datenübertragungsobjekten (DTOs) zu trennen,
 * die in der Web-Schicht verwendet werden. Dadurch wird die Web-Schicht von den Datenbank-Details isoliert.
 *
 * Wenn auf Daten in den FormControllern zugegriffen wird, wird der UserMapper verwendet, um die Datenbank-Entitäten in DTOs umzuwandeln.
 * In den verschiedenen Service-Klassen wird das Repository und die User Entität verwendet.
 */
@Component("usermapper")
public class UserMapper {

    public UserDTO map(de.thowl.prog3.exam.storage.entities.User in) {
        return new UserDTO(in.getId(), in.getName(), in.getEmail());
    }

}
