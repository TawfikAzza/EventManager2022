package dal;

import be.Events;
import dal.db.EventDAO;
import dal.interfaces.IDALFacade;
import dal.interfaces.IEventDAO;

import java.util.List;

public class DALController implements IDALFacade {
    private IEventDAO eventDAO;
    public DALController() throws Exception {
        eventDAO = new EventDAO();
    }
    @Override
    public Events addEvent(Events event) throws Exception {
        return null;
    }

    @Override
    public void removeEvent(Events event) throws Exception {

    }

    @Override
    public void updateEvent(Events event) throws Exception {

    }

    @Override
    public List<Events> getAllEvents() throws Exception {
        return eventDAO.getAllEvents();
    }

    @Override
    public Events getEvent(int id) throws Exception {
        return null;
    }
}
