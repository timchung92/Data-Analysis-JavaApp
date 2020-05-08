package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.PropertyValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Uses a scanner to parse a comma separated text file for property value data
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueReaderCSV extends Reader<List<PropertyValue>> {

    /**
     * Takes in a filename and stores it for use on a comma separated text file
     *
     * @param filename a CSV filename for a property value file
     */
    public PropertyValueReaderCSV(String filename) {
        super(filename);
    }

    @Override
    public Map<Integer, List<PropertyValue>> getIntegerMap() {
        Map<Integer, List<PropertyValue>> propertyValuesMap = new HashMap<>();
        String headers = readIn.nextLine();
        String[] headerArray = headers.trim().split(COMMA_REGEX);
        int marketValueIndex = -1, totalLivableAreaIndex = -1, zipCodeIndex = -1, id = -1;

        for (int i = 0; i < headerArray.length; i++) {
            if (headerArray[i].equals("market_value")) {
                marketValueIndex = i;
            } else if (headerArray[i].equals("total_livable_area")) {
                totalLivableAreaIndex = i;
            } else if (headerArray[i].equals("zip_code")) {
                zipCodeIndex = i;
            }
        }

        while (readIn.hasNextLine()) {
            String propertyValue = readIn.nextLine();
            String[] propertyValueArray = propertyValue.trim().split(COMMA_REGEX);

            if (propertyValueArray.length != headerArray.length) {
                continue;
            }

            String marketValue = propertyValueArray[marketValueIndex].trim();
            if (marketValue.length() == 0) {
                continue;
            }

            String totalLivableArea = propertyValueArray[totalLivableAreaIndex].trim();
            if (totalLivableArea.length() == 0) {
                continue;
            }

            String zipCode = propertyValueArray[zipCodeIndex].trim();
            Matcher m = ZIP_CODE_PATTERN.matcher(zipCode);
            if (m.find()) {
                zipCode = m.group();
            } else {
                continue;
            }

            //Only add if all fields are correct data type
            try {
                double marketValueDouble = Double.parseDouble(marketValue);
                double totalLivableAreaDouble = Double.parseDouble(totalLivableArea);
                int zipCodeInt = Integer.parseInt(zipCode);

                PropertyValue newPropertyValue = new PropertyValue(++id, marketValueDouble,
                        totalLivableAreaDouble, zipCodeInt);

                updateIntegerListMap(propertyValuesMap, newPropertyValue.getZipCode(), newPropertyValue);

            } catch (NumberFormatException ignored) {
            }
        }

        return propertyValuesMap;
    }
}
