package bll;

import dal.db.EventDAO;

import java.util.Arrays;

public class FileManager {

    EventDAO databaseEvent;

    public FileManager() throws Exception {
        databaseEvent = new EventDAO();
    }

    public void toShowResult () {
        String[][] arraytoPrint = databaseEvent.getParticipantsForEventById(7);
        System.out.println("Array: " + Arrays.deepToString(arraytoPrint));
    }

}
