package core;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import au.com.bytecode.opencsv.CSVReader;

public class Selenium_csv {

    public static List<String[]> readCSVDataFile(String csvFileName) throws IOException {

        CSVReader reader = new CSVReader(new FileReader(csvFileName));
        return reader.readAll();
    }

    public static void main(String[] args) {
        String textCaseId;
        String url;
        String expectedTitle;
        String actualTitle;
        WebDriver driver = new HtmlUnitDriver();

        Path path = null;
        try {
            path = Paths.get(Selenium_csv.class.getProtectionDomain().
                    getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String csvFileName = null;
        if (path != null) {
            csvFileName = path.getParent().toString() + File.separator + "classes" + File.separator + "Test.csv";
        }

        List<String[]> testData;
        try {
            testData = readCSVDataFile(csvFileName);

            for(String[] row : testData) {
                textCaseId = String.valueOf(row[0]);
                url = String.valueOf(row[1]);
                expectedTitle = String.valueOf(row[2]);

                driver.get(url);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

                actualTitle = driver.getTitle();
                System.out.println("");
                System.out.println("Test Case ID: \t\t" + textCaseId);
                System.out.println("URL: \t\t\t" + url);
                System.out.println("Title should contain: \t" + expectedTitle);
                System.out.println("Title Actual: \t\t" + actualTitle);
                if (actualTitle.contains(expectedTitle)) {
                    System.out.println("Test Case Result: \t" + "PASSED");
                } else {
                    System.out.println("Test Case Result: \t" + "FAILED");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
