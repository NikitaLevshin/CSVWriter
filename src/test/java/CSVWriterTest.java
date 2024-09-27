import annotation.CSVField;
import object.TestObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVWriterTest {

    TestObject testObject;
    TestObject testObject2;
    List<TestObject> testObjects;
    Field[] fields;
    File file;

    @BeforeEach
    public void setUp() {
        testObject = new TestObject("TestName", 15, List.of("Hello", "World"));
        testObject2 = new TestObject("TestName2", 30, List.of("Goodbye", "World!"));
        testObjects = List.of(testObject, testObject2);
        fields = testObject.getClass().getDeclaredFields();
    }

    @AfterEach
    public void tearDown() {
        if (file != null) file.delete();
    }

    @Test
    public void testCSVWriterShouldBeCompleted() throws IOException {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToFile(testObjects, "file.csv");

        StringBuilder declaredFields = new StringBuilder();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CSVField.class)) {
                declaredFields.append(field.getName()).append(",");
            }
        }
        declaredFields.deleteCharAt(declaredFields.length() - 1);

        file = new File("file.csv");
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());

        assertTrue(content.contains(declaredFields.toString()));

        assertTrue(content.contains(testObject.getName()));
        assertTrue(content.contains(testObject2.getName()));

        assertTrue(content.contains(String.valueOf(testObject.getNumber())));
        assertTrue(content.contains(String.valueOf(testObject2.getNumber())));

        assertTrue(content.contains(testObject.getStringList().toString()));
        assertTrue(content.contains(testObject2.getStringList().toString()));

    }

    @Test
    public void testCSVWriterShouldNotWriteUnannotatedFields() throws IOException {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToFile(testObjects, "file.csv");

        StringBuilder nonDeclaredFields = new StringBuilder();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(CSVField.class)) {
                nonDeclaredFields.append(field.getName()).append(",");
            }
        }
        nonDeclaredFields.deleteCharAt(nonDeclaredFields.length() - 1);

        file = new File("file.csv");
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());
        assertFalse(content.contains(nonDeclaredFields.toString()));
    }

    @Test
    public void testCSVWriterShouldThrowAnExceptionOnEmptyOrNullList() {
        CSVWriter csvWriter = new CSVWriter();

        assertThrows(IllegalArgumentException.class,
                () -> csvWriter.writeToFile(Collections.emptyList(), "file.csv"));

        assertThrows(IllegalArgumentException.class,
                () -> csvWriter.writeToFile(null, "file.csv"));


    }
}
