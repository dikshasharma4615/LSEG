package lseg;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataClass {

    public List<Map<String, String>> getFileData(String filePath) throws Exception {
        List<Map<String, String>> records = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CSVParser parser = CSVFormat.DEFAULT.withHeader()
                    .withSkipHeaderRecord(false)
                    .parse(reader);

            List<CSVRecord> dataList = new ArrayList<>();
            for (CSVRecord record : parser) {
                dataList.add(record);
            }

            if (dataList.size() < 30) {
                throw new Exception("File has less than 30 rows: " + filePath);
            }

            int startIdx = new Random().nextInt(dataList.size() - 29);
            for (int i = startIdx; i < startIdx + 30; i++) {
                CSVRecord record = dataList.get(i);
                Map<String, String> map = new LinkedHashMap<>();
                map.put(String.valueOf(i),record.get(2));
                records.add(map);
            }
        } catch (IOException e) {
            throw new Exception("Error reading file: " + filePath, e);
        }

        return records;
    }
}
