package service;

import annotation.CSVField;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class CSVWriter {

    public CSVWriter() {
    }

    public <T> void writeToFile(List<T> objects, String filePath) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Список объектов пуст");
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            T object = objects.get(0);
            Field[] fields = object.getClass().getDeclaredFields();
            StringBuilder header = new StringBuilder();
            for (Field field : fields) {
                if (field.isAnnotationPresent(CSVField.class)) {
                    CSVField annotation = field.getAnnotation(CSVField.class);
                    String columnName = annotation.value().isEmpty() ? field.getName() : annotation.value();
                    header.append(columnName).append(",");
                }
            }
            header.deleteCharAt(header.length() - 1);
            header.append("\n");
            fileWriter.write(header.toString());

            for (T t : objects) {

                StringBuilder values = new StringBuilder();
                fields = t.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(CSVField.class)) {
                        field.setAccessible(true);
                        Object value = field.get(t);
                        values.append(value).append(",");
                    }
                }
                values.deleteCharAt(values.length() - 1);
                values.append("\n");
                fileWriter.write(values.toString());
            }
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException("Ошибка в записи CSV файла");
        }
    }
}
