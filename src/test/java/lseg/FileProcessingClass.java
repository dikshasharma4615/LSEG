package lseg;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileProcessingClass {

    private final DataClass dataClass = new DataClass();
    private final DetectOutlier detectOutlier = new DetectOutlier();

    public void processFiles(String inputDir, String outputDir, int numFiles) {
        try {
            Files.createDirectories(Paths.get(outputDir));
            List<Path> csvFiles = Files.list(Paths.get(inputDir))
                    .filter(path -> path.toString().endsWith(".csv"))
                    .limit(numFiles)
                    .collect(Collectors.toList());

            if (csvFiles.isEmpty()) {
                throw new Exception("No CSV files found in the input directory.");
            }

            for (Path file : csvFiles) {
                System.out.println("Processing file: " + file.getFileName());
                List<Map<String, String>> sampledData = dataClass.getFileData(file.toString());
                List<Map<String, Object>> outliers = detectOutlier.identifyOutliers(sampledData);

                if (!outliers.isEmpty()) {
                    writeOutliersToCsv(file.getFileName().toString(), outputDir, outliers);
                } else {
                    System.out.println("No outliers found in file: " + file.getFileName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeOutliersToCsv(String fileName, String outputDir, List<Map<String, Object>> outliers) throws IOException {
        String outputFilePath = Paths.get(outputDir, "outliers_" + fileName).toString();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("Outlier stock price","Mean Price", "Deviation", "% Deviation"))) {

            for (Map<String, Object> outlier : outliers) {
                csvPrinter.printRecord(
                        outlier.get("Outlier stock price"),
                        outlier.get("Mean Price"),
                        outlier.get("Deviation"),
                        outlier.get("% Deviation")
                );
            }
        }
    }
}
