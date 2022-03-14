package dal.interfaces;

import be.Events;

import java.util.List;

public interface IEventDAO {
    public Events addEvent(Events event);
    public void removeEvent(Events event);
    public void updateEvent(Events event);
    public List<Events> getAllEvents();
    public Events getEvent(int id);
}
