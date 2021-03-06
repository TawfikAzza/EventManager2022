import be.Events;
import be.Participant;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import bll.utils.DateUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dal.db.EventDAO;
import dal.db.ParticipantDAO;
import gui.Model.EventModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class DAOTest {




    public static void main(String[] args) throws Exception {
        //addEvent();
        //getAllEvents();
        //getEvent();
        //removeEvent();
       // updateEvent();
//addParticipant();
       // getAllParticipants();
       // getHashMap();

    }

    public static void getHashMap() throws Exception {
        EventDAO eventDAO = new EventDAO();
        HashMap<Integer,List<Participant>> test = eventDAO.getParticipantByEvent();
        for (Map.Entry entry: test.entrySet()) {
            Integer index = (Integer) entry.getKey();
            List<Participant> testList = (List<Participant>) entry.getValue();
            for (Participant participant:testList) {
                System.out.println("IdEvent : "+index+" name : "+participant.getFname());
            }
        }

    }
    public static void getAllParticipants() throws Exception {
        ParticipantDAO participantDAO = new ParticipantDAO();
        List<Participant> participantList = new ArrayList<>();
        participantList = participantDAO.getAllParticipants();
        for (Participant participant:participantList) {
            System.out.println("Name: "+participant.getFname());
        }
    }
    public static void addParticipant() throws Exception {
        Participant participant = new Participant(0, "Mani", "Danivalsson", "mani6969@easv365.dk", "63258741");
        ParticipantDAO partipantDAO = new ParticipantDAO();
        partipantDAO.addParticipant(participant);
    }
    public static void addEvent() throws Exception {
        EventDAO eventDAO = new EventDAO();
        Events eventTest = new Events(1,"Event to be deleted","D-18");
        eventTest.setDescription("Delete this sh**");

        /*LocalDateTime testDate = DateUtil.parseDateTime("2022-03-19 11:15:30");
        System.out.println("date: "+testDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));*/
        eventTest.setStartDate(DateUtil.parseDateTime("2022-03-19 11:15:30"));
        eventTest.setItinerary("No direction given just delete me damnit");
        eventDAO.addEvent(eventTest);
    }

    public static void getAllEvents() throws Exception {
        EventDAO eventDAO = new EventDAO();
        List<Events> allEvents = eventDAO.getAllEvents();
        for (Events events: allEvents) {
            System.out.println("Name "+events.getName());
        }
    }
    public static void getEvent() throws Exception{
        EventDAO eventDAO = new EventDAO();
        Events event = eventDAO.getEvent(7);
        System.out.println("Name : "+event.getName());
    }
    public static void removeEvent() throws Exception {
        Events eventTest = new Events(8,"Event to be deleted","D-18");
        EventDAO eventDAO = new EventDAO();
        eventDAO.removeEvent(eventTest);
    }
    public static void updateEvent() throws Exception {
        Events eventTest = new Events(7,"Drinking Competition","D-18");
        eventTest.setStartDate(DateUtil.parseDateTime("2022-03-20 11:15:30"));
        eventTest.setDescription("A test for true warriors");
        eventTest.setItinerary("You'll know the way, just follow the empty bottles...");
        eventTest.setEndDate(DateUtil.parseDateTime("2022-03-21 11:30:30"));
        EventDAO eventDAO = new EventDAO();
        eventDAO.updateEvent(eventTest);
    }

}
