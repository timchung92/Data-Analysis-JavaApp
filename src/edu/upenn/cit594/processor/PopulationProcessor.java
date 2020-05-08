package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.MappableByInteger;

import java.util.Map;

/**
 * Stores a list of Population objects and contains methods for performing various operations on the list
 *
 * @author Chris Henry + Tim Chung
 */
public class PopulationProcessor implements Runnable {
    private static final int TOTAL_POPULATION_UNINITIALIZED = -1;
    protected MappableByInteger<Integer> populationReader;
    protected Map<Integer, Integer> populationsMap;
    protected int totalPopulation;

    /**
     * Constructs a Population to store a set of Population objects created by the PopulationReader class
     *
     * @param populationReader reader for population data
     */
    public PopulationProcessor(MappableByInteger<Integer> populationReader) {
        this.populationReader = populationReader;
        this.totalPopulation = TOTAL_POPULATION_UNINITIALIZED;
    }

    @Override
    public void run() {
        this.populationsMap = populationReader.getIntegerMap();
    }

    /**
     * @return total population
     */
    public int getTotalPopulation() {
        if (totalPopulation == TOTAL_POPULATION_UNINITIALIZED) {
            totalPopulation++;

            for (Integer localPopulation : populationsMap.values()) {
                totalPopulation += localPopulation;
            }
        }

        return totalPopulation;
    }


    /**
     * @return population for zip code, -1 if not found
     */
    public int getPopulationsByZip(int zipCode) {
        if (populationsMap.containsKey(zipCode)) {
            return populationsMap.get(zipCode);
        }

        return TOTAL_POPULATION_UNINITIALIZED;
    }

    public Map<Integer, Integer> getPopulationsMap() {
        return populationsMap;
    }
}
