package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * Uses a scanner to parse a comma separated text file for parking violation data
 *
 * @author Chris Henry + Tim Chung
 */
public class ParkingViolationReaderCSV extends Reader<List<ParkingViolation>> {
    private static final int VIOLATION_PROPERTY_COUNT = 7;

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename a CSV filename for a parking violation file
     */
    public ParkingViolationReaderCSV(String filename) {
        super(filename);
    }

    @Override
    public Map<Integer, List<ParkingViolation>> getIntegerMap() {
        Map<Integer, List<ParkingViolation>> parkingViolationsMap = new HashMap<>();

        while (readIn.hasNextLine()) {
            String parkingViolation = readIn.nextLine();
            String[] parkingViolationArray = parkingViolation.trim().split(COMMA_REGEX);

            if (parkingViolationArray.length != VIOLATION_PROPERTY_COUNT) {
                continue;
            }

            try {
                String timeString = parkingViolationArray[0].trim();
                if (timeString.length() == 0) {
                    continue;
                }

                String fine = parkingViolationArray[1].trim();
                if (fine.length() == 0) {
                    continue;
                }

                String violation = parkingViolationArray[2].trim();
                if (violation.length() == 0) {
                    continue;
                }

                String plateId = parkingViolationArray[3].trim();
                if (plateId.length() == 0) {
                    continue;
                }

                String state = parkingViolationArray[4].trim();
                if (state.length() == 0) {
                    continue;
                }

                String ticketNumber = parkingViolationArray[5].trim();
                if (ticketNumber.length() == 0) {
                    continue;
                }

                String zipCode = parkingViolationArray[6].trim();
                Matcher m = ZIP_CODE_PATTERN.matcher(zipCode);
                if (m.find()) {
                    zipCode = m.group();
                } else {
                    continue;
                }

                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(timeString);

                ParkingViolation newParkingViolation = new ParkingViolation(timeStamp, Double.parseDouble(fine), violation,
                        plateId, state, Integer.parseInt(ticketNumber), Integer.parseInt(zipCode));

                updateIntegerListMap(parkingViolationsMap, newParkingViolation.getZipCode(), newParkingViolation);

            } catch (ArrayIndexOutOfBoundsException | NumberFormatException | ParseException ignored) {
            }
        }
        return parkingViolationsMap;
    }
}
