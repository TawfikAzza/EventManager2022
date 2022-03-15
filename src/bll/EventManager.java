package bll;

import be.Events;
import dal.interfaces.IDALFacade;

import java.util.List;

public class EventManager implements EventManagerFacade{

    private IDALFacade idalFacade;
    @Override
    public List<Events> getAllEvents() throws Exception {
        return idalFacade.getAllEvents();
    }
}
