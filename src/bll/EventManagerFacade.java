package bll;

import be.Events;

import java.util.List;

public interface EventManagerFacade {
    public List<Events> getAllEvents() throws Exception;
}
