package pl.rsokolowski.coder;

import pl.rsokolowski.support.Support;

import java.util.*;

public class Histogram {

    private final String toBeCoded;
    private final List<Sign> histogram;
    private final int histogramTotalQuantity;


    public Histogram(String toBeCoded) {
        if (Support.nullOrEmpty(toBeCoded)) {
            Support.logger.error("Nothing to be coded, provide some data for coding... empty string is not what the algorithm would like to work on:)");
            this.toBeCoded = toBeCoded;
            this.histogram = new ArrayList<>();
            this.histogramTotalQuantity = 0;
        } else {
            this.toBeCoded = toBeCoded;
            this.histogram = generateHistogram();
            this.histogramTotalQuantity = calculateTotalQuantity();
        }
    }

    public List<Sign> generateHistogram() {
        Map<String, Integer> histogramUnSorted;
        Support.logger.info("text to be coded by HuffmanCode:  " + "\"" + toBeCoded + "\"");
        histogramUnSorted = Support.makeHistogram(toBeCoded);
        Support.logger.info("unsorted histogram was created:   " + histogramUnSorted);
        Map<String, Integer> histogramSorted = Support.sortHistogram(histogramUnSorted);
        Support.logger.info("histogram was sorted:             " + histogramSorted);
        List<Sign> histogramSigns = Support.createHistogramSigns(histogramSorted);
        Support.logger.info("list of signs was created:        " + histogramSigns);
        return histogramSigns;

    }

    public int calculateTotalQuantity() {
//        return histogram.stream().mapToInt(s->s.getQuantity()).sum();
        return histogram.stream().mapToInt(Sign::getQuantity).sum();
    }

    public List<Sign> getHistogram() {
        return new ArrayList<>(histogram);
    }

}
