package edu.upenn.cit594.data;

/**
 * A class for population based on zip code
 *
 * @author Chris Henry + Tim Chung
 */
public class Population {
    protected int zipCode;
    protected int population;

    /**
     * @param zipCode    zip code of population
     * @param population population count
     */
    public Population(int zipCode, int population) {
        this.zipCode = zipCode;
        this.population = population;
    }

    /**
     * @return zipCode
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * @return population
     */
    public int getPopulation() {
        return population;
    }
}
