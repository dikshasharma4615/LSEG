package lseg;

import java.util.Scanner;

public class LSEG_Main {
    public static void main(String[] args) {

        int noOfFile = 0;
        String stockType = null;
        FileProcessingClass processor = new FileProcessingClass();
        String resourcesPath = "src/test/resources/";
        String inputPath = resourcesPath + stockType + "/";
        String outputPath = "src/test/output";
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the stock exchange to be processed : NYSE/NASDAQ/LSE");
        try {
            stockType = scan.next().toUpperCase();
            if (stockType.equals("NYSE") || stockType.equals("NASDAQ") || stockType.equals("LSE")) {
                System.out.println("Processing stock exchange: " + stockType);
            } else {
                System.out.println("Stock not valid, please choose a valid option.");
            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred. Please try again.");
        }
        System.out.println("Please enter the number of files to be processed: 1 or 2");

        try {
            noOfFile = scan.nextInt();
            if (noOfFile == 2 || noOfFile == 1) {
                System.out.println("Processing files for outlier....");
            } else {
                System.out.println("Invalid input, please try again");
            }
        } catch (RuntimeException e) {
            System.out.println("An error occurred. Please try again.");
        }
        inputPath = resourcesPath + stockType + "/";
        processor.processFiles(inputPath, outputPath, noOfFile);

    }
}