package dal.interfaces;

import be.Events;

import java.util.List;

public interface IEventDAO {
    public Events addEvent(Events event) throws Exception;
    public void removeEvent(Events event) throws Exception;
    public void updateEvent(Events event) throws Exception;
    public List<Events> getAllEvents() throws Exception;
    public Events getEvent(int id) throws Exception;
}
