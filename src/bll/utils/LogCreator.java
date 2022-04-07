package bll.utils;

import be.Users;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LogCreator {

    private Logger logger;
    private FileHandler fileHandler;

    public LogCreator(String logName)
    {
        this.logger = Logger.getLogger(logName);
        try {
            this.fileHandler = new FileHandler("resources/Log/" + logName + ".log", true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
}
