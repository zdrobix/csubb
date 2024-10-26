package logs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    private static final String logFilename = "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\logs\\logs.txt";
    private static final String logModify = "Q:\\info\\csubb\\Semestru 3\\MAP\\lab\\lab3\\lab3\\src\\logs\\logs2.txt";

    public Logger () {};

    public void LogConnection (boolean connected) {
        try {
            FileWriter fw = new FileWriter(
                    logFilename,
                    true);
            if (connected)
                fw.append("Connection established. ").append(String.valueOf(LocalDateTime.now())).append(String.valueOf('\n'));
            else {
                fw.append("Connection failed. ").append(String.valueOf(LocalDateTime.now())).append(String.valueOf('\n'));
            }
            fw.close();
        } catch (Exception ie) {
            System.out.println(ie.getMessage());
        }
    }

    public void LogModify (String operation, String parameter) {
        try {
            FileWriter fw = new FileWriter(
                    logModify,
                    true);
            switch (operation) {
                case "save": {
                    fw.append("Saving: ").append(parameter).append(String.valueOf(LocalDateTime.now())).append(String.valueOf('\n'));
                    break;
                }
                case "findOne": {
                    fw.append("Looking for: ").append(parameter).append(String.valueOf(LocalDateTime.now())).append(String.valueOf('\n'));
                    break;
                }
                case "findAll": {
                    fw.append("Looking for all: ").append(String.valueOf(LocalDateTime.now())).append(String.valueOf('\n'));
                    break;
                }
                case "delete": {
                    fw.append("Deleting: ").append(parameter).append(String.valueOf(LocalDateTime.now())).append(String.valueOf('\n'));
                    break;
                }
                default: break;
            }
            fw.close();
        } catch (Exception ie) {
            System.out.println(ie.getMessage());
        }
    }
}
