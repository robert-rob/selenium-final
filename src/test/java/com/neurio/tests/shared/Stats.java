package com.neurio.tests.shared;

/**
 * Created by jameslNeurio on 2016-12-16.
 */

/*
    Refactor move Stats as a static class used for further test cases evaluating information
*/

import java.util.ArrayList;

public class Stats {

    public Double min;
    public Double max;
    public Double average;
    public Double total;
    public Double minRaw;
    public Double maxRaw;
    public Double averageRaw;
    public Double totalRaw;

    /**
     * Returns object with all statistics from the array
     */
    public static Stats getMinMaxAverage(ArrayList<Double> array) {
        Stats answer = new Stats();
        double total = 0;
        double min = 100000000;
        double max = 0;
        if (array.size() > 0) {
            min = array.get(0);
            max = array.get(0);
        }

        for (Double item : array) {
            total += item;
            if (item < min) {
                min = item;
            }
            if (item > max) {
                max = item;
            }
        }
        answer.minRaw = min;
        answer.maxRaw = max;
        answer.totalRaw = total;
        answer.averageRaw = total / array.size();
        answer.min = min / 3600000;
        answer.max = max / 3600000;
        answer.total = total / 3600000;
        answer.average = answer.total / array.size();
        return answer;
    }

    /**
     * Returns object with the net statistics from the consumption and generation arrays
     */
    public static Stats getMinMaxNet(ArrayList<Double> conArray, ArrayList<Double> genArray) {
        Stats answer = new Stats();
        double min = 100000000;
        double max = 0;
        double total = 0;
        if (genArray.size() != conArray.size()) {
            return answer;
        }

        if (conArray.size() > 0 && genArray.size() > 0) {
            min = conArray.get(0) - genArray.get(0);
            max = conArray.get(0) - genArray.get(0);
        }

        for (int i = 0; i < conArray.size(); i++) {
            double difference = conArray.get(i) - genArray.get(i);
            total += difference;
            if (difference < min) {
                min = difference;
            }
            if (difference > max) {
                max = difference;
            }
        }
        answer.minRaw = min;
        answer.maxRaw = max;
        answer.totalRaw = total;
        answer.averageRaw = total / conArray.size();
        answer.min = min / 3600000;
        answer.max = max / 3600000;
        answer.total = total / 3600000;
        answer.average = answer.total / conArray.size();
        return answer;
    }
}
