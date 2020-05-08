package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.ParkingViolationProcessor;
import edu.upenn.cit594.processor.PopulationProcessor;
import edu.upenn.cit594.processor.PropertyValueProcessor;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * A command line interface class for outputting text via the console to the user
 *
 * @author Chris Henry + Tim Chung
 */
public class CommandLineUserInterface {
    protected ParkingViolationProcessor parkingViolationProcessor;
    protected PopulationProcessor populationProcessor;
    protected PropertyValueProcessor propertyValueProcessor;
    protected Logger logger;
    protected Scanner in;

    /**
     * Constructs a CommandLineUserInterface class for interaction with the user.
     * Uses processor classes to deliver the desired information to the user.
     *
     * @param parkingViolationProcessor a parking violation processor
     * @param populationProcessor       a population processor
     * @param propertyValueProcessor    a property value processor
     */
    public CommandLineUserInterface(ParkingViolationProcessor parkingViolationProcessor,
                                    PopulationProcessor populationProcessor,
                                    PropertyValueProcessor propertyValueProcessor) {
        this.parkingViolationProcessor = parkingViolationProcessor;
        this.populationProcessor = populationProcessor;
        this.propertyValueProcessor = propertyValueProcessor;
        in = new Scanner(System.in);
        logger = Logger.getInstance();
    }

    /**
     * Starts the UI sequence
     */
    public void start() {
        int choice = -1;

        do {
            try {
                while (choice != 0) {
                    printInstructions();
                    choice = in.nextInt();
                    logUserEntry(choice);
                    performAction(choice);
                }
                break;
            } catch (InputMismatchException e) {
                printError();
            }
        } while (true);

        in.close();
    }

    /**
     * Makes calls to print functions based on user input
     *
     * @param choice integer value entered by user
     */
    private void performAction(int choice) {
        switch (choice) {
            case 0:
                break;
            case 1:
                printTotalPopulation();
                break;
            case 2:
                printTotalFinesPerCapita();
                break;
            case 3:
                printAverageResidentialMarketValue(getZipCode());
                break;
            case 4:
                printAverageResidentialTotalLivableArea(getZipCode());
                break;
            case 5:
                printTotalResidentialMarketValuePerCapita(getZipCode());
                break;
            case 6:
                printMarketValuePerCapitaOverAverageFine();
                break;
            default:
                printError();
                System.exit(5);
                break;
        }
    }

    /**
     * Prompt user for zip code
     *
     * @return zip code as string
     */
    private int getZipCode() {
        int zipCode;

        do {
            try {
                System.out.print("Enter zip code: ");
                zipCode = in.nextInt();
                logUserEntry(zipCode);
                break;
            } catch (InputMismatchException e) {
                printError();
            }
        } while (true);

        return zipCode;
    }

    /**
     * Prints choices to command line
     */
    private void printInstructions() {
        System.out.println("Choose from the following options.");
        System.out.println("Enter:");
        System.out.println("\t1 for the total population for all ZIP Codes");
        System.out.println("\t2 for the total fines per capita");
        System.out.println("\t3 for the average residential market value");
        System.out.println("\t4 for the average residential total livable area");
        System.out.println("\t5 for the total residential market value per capita");
        System.out.println("\t6 for market value per cap over average fine");
    }

    /**
     * Prints total population for all zip codes
     */
    private void printTotalPopulation() {
        System.out.println(populationProcessor.getTotalPopulation());
    }

    /**
     * Prints total fines per capita for each zip code
     */
    private void printTotalFinesPerCapita() {
        for (Map.Entry<Integer, Integer> entry : populationProcessor.getPopulationsMap().entrySet()) {
            int zipCode = entry.getKey();
            int populationCount = entry.getValue();

            double totalFinePerCapita = parkingViolationProcessor.getTotalFinesPerCapita(zipCode, populationCount);

            System.out.printf("%d %04.4f\n", zipCode, totalFinePerCapita);
        }
    }

    /**
     * Prints average residential market value for provided zip code
     */
    private void printAverageResidentialMarketValue(int zipCode) {
        System.out.printf("%d\n", (int) propertyValueProcessor.getAverageMarketValue(zipCode));
    }

    /**
     * Prints average residential total livable area for provided zip code
     */
    private void printAverageResidentialTotalLivableArea(int zipCode) {
        System.out.printf("%d\n", (int) propertyValueProcessor.getAverageLivableArea(zipCode));
    }

    /**
     * Prints average residential market value per capita for provided zip code
     */
    private void printTotalResidentialMarketValuePerCapita(int zipCode) {
        int populationCount = populationProcessor.getPopulationsByZip(zipCode);
        int total = (int) propertyValueProcessor.getTotalMarketValuePerCapita(zipCode, populationCount);

        System.out.printf("%d\n", total);
    }

    /**
     * CUSTOM FUNCTION: Prints market value per capita over average fine amount for each zip code
     */
    private void printMarketValuePerCapitaOverAverageFine() {
        for (Map.Entry<Integer, Integer> entry : populationProcessor.getPopulationsMap().entrySet()) {
            int zipCode = entry.getKey();
            int populationCount = entry.getValue();

            double averageFine = parkingViolationProcessor.getAverageFine(zipCode);
            double marketValuePerCapitaOverAverageFine = propertyValueProcessor.getMarketValuePerCapitaOverAverageFine(
                    zipCode, populationCount, averageFine);

            System.out.printf("%d %04.4f\n", zipCode, marketValuePerCapitaOverAverageFine);
        }
    }

    /**
     * Prints an error message to the user via the command line
     */
    private void printError() {
        System.out.println("Error: Invalid Input");
    }

    /**
     * Takes an input from a user and logs it with the current system time in milliseconds
     *
     * @param input integer values input by user
     */
    private void logUserEntry(int input) {
        logger.log(String.format("%d %d\n", System.currentTimeMillis(), input));
    }
}
