import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.TreeMap;

public class CSVReader {

    private static CSVReader csvReader = new CSVReader();
    private static HashMap <LocalDate, TreeMap<LocalTime, Float>> tempData;

    private CSVReader() {
        tempData = new HashMap<>();
        readCSV();
    }

    public static CSVReader getInstance() {
        if (csvReader == null) {
            csvReader = new CSVReader();
        }
        return csvReader;
    }

    public TreeMap<LocalTime, Float> getTempValuesFromDate(LocalDate localDate) {
        return tempData.get(localDate);
    }

    private static void readCSV() {
        String csvFile = "/Users/behzad1993/Documents/HTW/Verteilte-Systeme/WeatherReader/csv/history_export_2018-12-17T21_51_40.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ";";

        try {

            TreeMap<LocalTime, Float> tempWithLocalTimeMap = new TreeMap<>();
            br = new BufferedReader(new FileReader(csvFile));
            LocalDate tmpLocalDate = null;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                LocalDate localDate = buildDate(data);
                LocalTime localTime = buildTime(data);
                float temp = Float.parseFloat(data[5]);

                if (localDate.equals(tmpLocalDate)) {
                    tempWithLocalTimeMap.put(localTime, temp);
                    if (tempWithLocalTimeMap.size() == 24) {
                        tempData.put(localDate, tempWithLocalTimeMap);
                    }
                } else {
                    tempWithLocalTimeMap = new TreeMap<>();
                    tempWithLocalTimeMap.put(localTime, temp);
                }
                tmpLocalDate = localDate;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static LocalTime buildTime(String[] data) {
        String time = new StringBuilder()
                .append(data[3]).append(":")
                .append(data[4]).toString();
        return LocalTime.parse(time);
    }

    private static LocalDate buildDate(String[] data) {
        String date = new StringBuilder()
                .append(data[0]).append("-")
                .append(data[1]).append("-")
                .append(data[2]).toString();
        return LocalDate.parse(date);
    }

}