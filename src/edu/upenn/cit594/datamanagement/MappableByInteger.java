package edu.upenn.cit594.datamanagement;

import java.util.Map;

/**
 * An interface used to specify the methods for getting a map by integer key from a file
 *
 * @author Chris Henry + Tim Chung
 */
public interface MappableByInteger<V> {
    /**
     * Maps data from a file by some integer parsable attribute
     *
     * @return a map of parking violation objects by their zip codes
     */
    Map<Integer, V> getIntegerMap();
}
