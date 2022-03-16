package dal.interfaces;

import be.Events;

import java.util.List;

public interface IEventDAO {
    Events addEvent(Events event) throws Exception;

    void removeEvent(Events event) throws Exception;

    void updateEvent(Events event) throws Exception;

    List<Events> getAllEvents() throws Exception;

    Events getEvent(int id) throws Exception;
}
