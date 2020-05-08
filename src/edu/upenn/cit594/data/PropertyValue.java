package edu.upenn.cit594.data;

/**
 * A class for property values which includes their market value, the total livable area, and the zipCode in which
 * the property is located
 *
 * @author Chris Henry + Tim Chung
 */
public class PropertyValue {
    protected int id, zipCode;
    protected double marketValue, totalLivableArea;

    /**
     * @param id               unique identifier for property
     * @param marketValue      market value of property
     * @param totalLivableArea total livable area of property
     * @param zipCode          zip code of property
     */
    public PropertyValue(int id, double marketValue, double totalLivableArea, int zipCode) {
        this.id = id;
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipCode = zipCode;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @return marketValue
     */
    public double getMarketValue() {
        return marketValue;
    }

    /**
     * @return totalLivableArea
     */
    public double getTotalLivableArea() {
        return totalLivableArea;
    }

    /**
     * @return zipCode
     */
    public int getZipCode() {
        return zipCode;
    }
}
