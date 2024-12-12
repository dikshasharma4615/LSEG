package lseg;

import java.util.*;

public class DetectOutlier {

    public List<Map<String, Object>> identifyOutliers(List<Map<String, String>> sampledData) {
        List<Double> prices = new ArrayList<>();
        List<Integer> validIndices = new ArrayList<>(); // To track valid indices in sampledData

        for (int i = 0; i < sampledData.size(); i++) {
            Map<String, String> map = sampledData.get(i);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    try {
                        prices.add(Double.parseDouble(value));
                        validIndices.add(i); // Track the index of the valid price
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format: " + value);
                    }
                }
            }
        }

        System.out.println("Prices size: " + prices.size());
        System.out.println("SampledData size: " + sampledData.size());
        System.out.println("Prices: " + prices);
        System.out.println("Valid Indices: " + validIndices);
        // Calculate mean and standard deviation
        double mean = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double stdDev = Math.sqrt(prices.stream()
                .mapToDouble(p -> Math.pow(p - mean, 2))
                .average().orElse(0.0));
        double positiveThreshold = mean + 2 * stdDev;
        double negativeThreshold = mean - 2 * stdDev;
        System.out.println("Mean: " + mean);
        System.out.println("Standard Deviation: " + stdDev);
        System.out.println("Positive Threshold: " + positiveThreshold);
        System.out.println("Negative Threshold: " + negativeThreshold);
        List<Map<String, Object>> outliers = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            double price = prices.get(i);
            if (price > positiveThreshold || price < negativeThreshold) {
                System.out.println("Outlier detected: " + price);
                Map<String, String> originalData = sampledData.get(validIndices.get(i));
                Map<String, Object> outlier = new HashMap<>(originalData);
                outlier.put("Mean Price", mean);
                outlier.put("Outlier stock price", price);
                outlier.put("Deviation", price - mean);
                outlier.put("% Deviation", ((price - mean) / mean) * 100);
                outliers.add(outlier);
            }
        }


        return outliers;
    }


}
