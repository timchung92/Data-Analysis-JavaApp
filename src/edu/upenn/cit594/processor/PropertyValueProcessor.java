package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.datamanagement.MappableByInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores a map of PropertyValue objects by their zip codes and
 * contains methods for performing various operations on the map
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValueProcessor implements Runnable {
    protected MappableByInteger<List<PropertyValue>> propertyValueReader;
    protected Map<Integer, List<PropertyValue>> propertyValuesMap;
    protected Map<Integer, SumSizePair> totalLivableAreaByZip;
    protected Map<Integer, SumSizePair> totalMarketValueByZip;

    /**
     * Constructs a PropertyValueProcessor to store a set of PropertyValue objects created by the
     * PropertyValueReader class
     *
     * @param propertyValueReader reader for property value data
     */
    public PropertyValueProcessor(MappableByInteger<List<PropertyValue>> propertyValueReader) {
        this.propertyValueReader = propertyValueReader;
        this.totalMarketValueByZip = new HashMap<>();
        this.totalLivableAreaByZip = new HashMap<>();
    }

    @Override
    public void run() {
        this.propertyValuesMap = propertyValueReader.getIntegerMap();
    }

    /**
     * @return property values by zip code
     */
    public Map<Integer, List<PropertyValue>> getPropertyValuesMap() {
        return propertyValuesMap;
    }

    /**
     * @param zipCode         zip code in which to search
     * @param populationCount population count for provided zip code
     * @return Total Residential Market Value Per Capita
     */
    public double getTotalMarketValuePerCapita(int zipCode, int populationCount) {
        if (populationCount < 1) {
            return 0.0;
        }

        double total = getTotalMarketValueByZip(zipCode);
        return total / populationCount;
    }

    /**
     * @param zipCode         zip code in which to get average market value
     * @param populationCount population count for provided zip code
     * @param averageFine     average fine in the zip code provided
     * @return average market value over average fine per capita
     */
    public double getMarketValuePerCapitaOverAverageFine(int zipCode, int populationCount, double averageFine) {
        double marketValuePerCapita = getTotalMarketValuePerCapita(zipCode, populationCount);
        return averageFine > 0 ? (marketValuePerCapita / averageFine) : 0.0;
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Market Value
     */
    public double getAverageMarketValue(int zipCode) {
        SumSizePair pair = getSumSizePair(zipCode, new MarketValueReducer(), totalMarketValueByZip);
        return (pair == null) ? 0.0 : (pair.getSum() / pair.getSize());
    }

    /**
     * @param zipCode zip code in which to search
     * @return Average Residential Total Livable Area
     */
    public double getAverageLivableArea(int zipCode) {
        SumSizePair pair = getSumSizePair(zipCode, new TotalLivableAreaReducer(), totalLivableAreaByZip);
        return pair.getSum() / pair.getSize();
    }

    /**
     * @return total market value for all residences in zip code
     */
    public double getTotalMarketValueByZip(int zipCode) {
        return getSumSizePair(zipCode, new MarketValueReducer(), totalMarketValueByZip).getSum();
    }

    /**
     * @param reducer strategy pattern for acquiring totals by zip code and the number of objects to create said total
     * @return a SumSizePair object
     */
    private SumSizePair getSumSizePair(int zipCode, PropertyValueReducer reducer, Map<Integer, SumSizePair> memo) {
        if (memo.containsKey(zipCode)) {
            return memo.get(zipCode);
        }

        List<PropertyValue> properties = getPropertyValuesMap().get(zipCode);
        SumSizePair newPair;

        if (properties == null || properties.isEmpty()) {
            newPair = new SumSizePair();
            memo.put(zipCode, newPair);
            return newPair;
        }

        newPair = new SumSizePair(reducer.reduce(properties), properties.size());
        memo.put(zipCode, newPair);

        return newPair;
    }
}
