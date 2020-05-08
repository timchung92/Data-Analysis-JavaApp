package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * An abstract class to be implemented by each reader. Contains functionality for creating
 * both the file and the scanner in its constructor as well as a method to create a map commonly used
 * throughout the program.
 */
public abstract class Reader<V> implements MappableByInteger<V> {
    protected static final String FILE_ERR_MSG = "property value file must exist and be readable";
    protected static final String ZIP_CODE_REGEX = "\\d{5}";
    protected static final Pattern ZIP_CODE_PATTERN = Pattern.compile(ZIP_CODE_REGEX);
    protected static final String COMMA_REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
    protected static final String SPACE_REGEX = " ";
    protected Scanner readIn;
    protected FileReader file;

    /**
     * @param filename the file name used to read the existing data file
     */
    public Reader(String filename) {
        try {
            file = new FileReader(filename);
            Logger.getInstance().log(String.format("%d %s\n", System.currentTimeMillis(), filename));
            readIn = new Scanner(file);
        } catch (IOException e) {
            System.out.println(FILE_ERR_MSG);
            System.exit(4);
        }
    }

    /**
     * @param map     the map to be used for adding elements
     * @param key     integer key to be used for map
     * @param element element to be added to the list
     * @param <E>     type of element in the list
     */
    public <E> void updateIntegerListMap(Map<Integer, List<E>> map, Integer key, E element) {
        if (map.containsKey(key)) {
            map.get(key).add(element);
        } else {
            List<E> objectList = new LinkedList<>();
            objectList.add(element);
            map.put(key, objectList);
        }
    }
}