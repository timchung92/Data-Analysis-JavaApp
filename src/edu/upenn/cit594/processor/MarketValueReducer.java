package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

/**
 * Reduces the marketValue attribute of an Iterable of PropertyValue objects
 */
public class MarketValueReducer implements PropertyValueReducer {

    @Override
    public double reduce(Iterable<PropertyValue> properties) {
        double sum = 0.0;

        for (PropertyValue property : properties) {
            sum += property.getMarketValue();
        }

        return sum;
    }
}
