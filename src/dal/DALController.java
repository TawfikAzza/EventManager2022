package dal;

import be.Events;
import dal.db.EventDAO;



import java.util.List;

public class DALController {
    private EventDAO eventDAO;
    public DALController() throws Exception {
        eventDAO = new EventDAO();
    }

    public Events addEvent(Events event) throws Exception {
        return eventDAO.addEvent(event);
    }

    public void removeEvent(Events event) throws Exception {
        eventDAO.removeEvent(event);
    }

    public void updateEvent(Events event) throws Exception {
        eventDAO.updateEvent(event);
    }
    public List<Events> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }
    public Events getEvent(int id) throws Exception {
        return eventDAO.getEvent(id);
    }

}
