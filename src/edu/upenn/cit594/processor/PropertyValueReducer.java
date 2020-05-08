package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

/**
 * Reducer interface for PropertyValue objects. Any Implementation should an attribute
 * in an Iterable of PropertyValue objects
 */
public interface PropertyValueReducer {

    /**
     * @param properties list of properties to reduce an attribute
     * @return reduced value as implemented by the method
     */
    double reduce(Iterable<PropertyValue> properties);
}
