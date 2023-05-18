package repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtils {

    public static final String Students = "student_test.xml";
    public static final String Assignments = "homework_test.xml";
    public static final String Grades = "grade_test.xml";


    public static File createXMLFile(String fileName) throws IOException {
        File createdFile = new File(fileName);
        if (createdFile.createNewFile()) {
            try (FileWriter fileWriter = new FileWriter(createdFile)) {
                fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Entitati>\n</Entitati>");
            }
        } else {
            throw new RuntimeException("File " + fileName + " could not be created!");
        }
        return createdFile;
    }

}