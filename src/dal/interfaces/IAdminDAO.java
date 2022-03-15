package dal.interfaces;

import be.Coordinator;

import java.util.ArrayList;

public interface IAdminDAO {

    void addEventCoordinator(Coordinator coordinator);

    void deleteEventCoordinator(Coordinator coordinator);

    void updateEventCoordinator(Coordinator coordinator);

    ArrayList<Coordinator> getAllCoordinators();

}
