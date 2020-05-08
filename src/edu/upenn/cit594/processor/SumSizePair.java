package edu.upenn.cit594.processor;

/**
 * A basic value pair class for storing a sum and the number of values used to create said sum
 */
public class SumSizePair {
    protected int size;
    protected double sum;

    /**
     * Constructs an empty SumSizePair
     */
    public SumSizePair() {
        this.sum = 0.0;
        this.size = -1;
    }

    /**
     * @param sum  a double sum value
     * @param size the number of values used to create sum
     */
    public SumSizePair(double sum, int size) {
        this.sum = sum;
        this.size = size;
    }

    /**
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return sum
     */
    public double getSum() {
        return sum;
    }
}
