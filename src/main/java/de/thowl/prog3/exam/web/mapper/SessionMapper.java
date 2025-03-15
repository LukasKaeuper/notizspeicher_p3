package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.web.dto.SessionDTO;
import org.springframework.stereotype.Component;

@Component("sessionmapper")
public class SessionMapper {

    public SessionDTO map(de.thowl.prog3.exam.storage.entities.Session in) {
        return new SessionDTO(in.getId(), in.getToken(), in.getUserId());
    }
}
