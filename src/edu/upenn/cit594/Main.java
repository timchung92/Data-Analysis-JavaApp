package edu.upenn.cit594;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

import java.util.List;

/**
 * Main class takes in arguments from the caller, creates files, initializes logger and activates the CLI
 * <p>
 * Usage: java Main [parking violations file format] [parking violations filename] [property values filename]
 * [population filename] [log filename]
 *
 * @author Chris Henry + Tim Chung
 */
public class Main {
    private static final String JSON = "json";
    private static final String CSV = "csv";

    private static final String FILE_FORMAT_ERR_MSG = "parking violations file format must be either 'json' or 'csv'";
    private static final String USAGE_ERR_MSG = "Usage: java Main [parking violations file format] " +
            "[parking violations filename] [property values filename] [population filename] [log filename]";

    protected static String parkingViolationsFileFormat;
    protected static String parkingViolationsFilename;
    protected static String propertyValuesFilename;
    protected static String populationFilename;
    protected static String logFilename;
    protected static MappableByInteger<List<ParkingViolation>> parkingViolationReader;
    protected static PropertyValueReaderCSV propertyValueReader;
    protected static PopulationReaderSSV populationReader;
    protected static ParkingViolationProcessor parkingViolationProcessor;
    protected static PropertyValueProcessor propertyValueProcessor;
    protected static PopulationProcessor populationProcessor;
    protected static CommandLineUserInterface ui;

    /**
     * Main program runner which controls which readers and processors to use based on file inputs determined by
     * the program runner. Starts the user interface.
     *
     * @param args [parking violations file format] [parking violations filename] [property values filename]
     *             [population filename] [log filename]
     */
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println(USAGE_ERR_MSG);
            System.exit(1);
        }

        parkingViolationsFileFormat = args[0];
        boolean isParkingViolationsJSON = parkingViolationsFileFormat.equals(JSON);
        boolean isParkingViolationsCSV = parkingViolationsFileFormat.equals(CSV);

        if (!(isParkingViolationsJSON || isParkingViolationsCSV)) {
            System.out.println(FILE_FORMAT_ERR_MSG);
            System.exit(2);
        }

        parkingViolationsFilename = args[1];
        propertyValuesFilename = args[2];
        populationFilename = args[3];
        logFilename = args[4];

        Logger.init(logFilename);
        Logger.getInstance().log(String.format("%d %s %s %s %s %s\n", System.currentTimeMillis(),
                parkingViolationsFileFormat, parkingViolationsFilename, propertyValuesFilename, populationFilename,
                logFilename));

        createReaders(isParkingViolationsJSON);
        createProcessors();
        createCLI();

        if (ui != null) {
            ui.start();
        }
    }

    /**
     * Creates all necessary readers for the program
     *
     * @param isParkingViolationsJSON boolean to determine which parking violation reader to create
     */
    public static void createReaders(boolean isParkingViolationsJSON) {
        parkingViolationReader = isParkingViolationsJSON ? new ParkingViolationReaderJSON(parkingViolationsFilename) :
                new ParkingViolationReaderCSV(parkingViolationsFilename);
        propertyValueReader = new PropertyValueReaderCSV(propertyValuesFilename);
        populationReader = new PopulationReaderSSV(populationFilename);
    }

    /**
     * Creates all necessary processors for the program
     */
    public static void createProcessors() {
        parkingViolationProcessor = new ParkingViolationProcessor(parkingViolationReader);
        Thread t1 = new Thread(parkingViolationProcessor);

        propertyValueProcessor = new PropertyValueProcessor(propertyValueReader);
        Thread t2 = new Thread(propertyValueProcessor);

        populationProcessor = new PopulationProcessor(populationReader);
        Thread t3 = new Thread(populationProcessor);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Error joining processor threads");
            System.exit(6);
        }
    }

    /**
     * Creates the Command Line Interface for the program
     */
    public static void createCLI() {
        ui = new CommandLineUserInterface(parkingViolationProcessor, populationProcessor, propertyValueProcessor);
    }
}
