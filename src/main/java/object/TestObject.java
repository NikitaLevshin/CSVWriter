package object;

import annotation.CSVField;

import java.util.List;

public class TestObject {
    @CSVField(value = "name")
    private String name;

    @CSVField(value = "number")
    private int number;

    @CSVField(value = "stringList")
    private List<String> stringList;

    private String notDeclared;

    public TestObject(String name, int number, List<String> stringList) {
        this.name = name;
        this.number = number;
        this.stringList = stringList;
        this.notDeclared = "notDeclared";
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public String getNotDeclared() {
        return notDeclared;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setNotDeclared(String notDeclared) {
        this.notDeclared = notDeclared;
    }
}
