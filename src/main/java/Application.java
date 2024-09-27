import object.TestObject;
import service.CSVWriter;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        TestObject testObject = new TestObject("Test", 15, List.of("Hello", "World"));
        TestObject testObject2 = new TestObject("Test2", 23, List.of("Hello!", "CWorld", "Peace"));
        TestObject testObject3 = new TestObject(null, 11, null);
        CSVWriter csvWriter = new CSVWriter();

        String path = System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "csv" + System.getProperty("file.separator") + "test.csv";

        csvWriter.writeToFile(List.of(testObject, testObject2, testObject3), path);
    }
}
