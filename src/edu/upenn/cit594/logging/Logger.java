package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Singleton Logger class for logging output to a file
 *
 * @author Chris Henry + Tim Chung
 */
public class Logger {
    private static final String LOG_FILE_ERR_MSG = "log file must be writeable";
    private PrintWriter out;
    private static Logger instance;

    /**
     * Creates or uses a file by the specified filename to be used for logging
     *
     * @param file the appendable FileWriter to use for logging
     */
    private Logger(FileWriter file) {
        try {
            out = new PrintWriter(file);
        } catch (Exception e) {
        }
    }

    /**
     * Initializes the logger singleton using filename provided
     *
     * @param logFilename the filename to use for logging
     */
    public static void init(String logFilename) {
        try {
            FileWriter logFile = new FileWriter(logFilename, true);
            instance = new Logger(logFile);
        } catch (IOException e) {
            System.out.println(LOG_FILE_ERR_MSG);
            System.exit(3);
        }
    }

    /**
     * Gets an instance of the Logger class
     *
     * @return a singleton instance of the logger class
     */
    public static Logger getInstance() {
        return instance;
    }

    /**
     * Prints and flushes messages to the initialized log file
     *
     * @param msg the message to be printed to the log file
     */
    public void log(String msg) {
        out.println(msg);
        out.flush();
    }
}

