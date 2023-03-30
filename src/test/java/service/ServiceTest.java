package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class ServiceTest {

    private Service setUp(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src/main/resources/repository/studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src/main/resources/repository/teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src/main/resources/repository/note.xml");

        return new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void testAddStudent(){
        Service service = setUp();
        service.saveStudent("5", "Andrei", 936);
        List<Student> studenti = new ArrayList<>();
        service.findAllStudents().forEach(studenti::add);
        assert (studenti.size() == 4);
        service.deleteStudent("5");
    }

    @Test
    public void testAddStudentExistingId(){
        Service service = setUp();
        service.saveStudent("1", "Andrei", 936);
        List<Student> studenti = new ArrayList<>();
        service.findAllStudents().forEach(studenti::add);
        assert (studenti.size() == 3);
    }
}
