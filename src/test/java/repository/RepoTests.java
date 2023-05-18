package repository;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepoTests {
    private StudentXMLRepository realStudentRepository;
    @Mock
    private StudentXMLRepository mockStudentRepository;
    private TemaXMLRepository realAssignmentRepository;
    @Mock
    private TemaXMLRepository mockAssignmentRepository;
    private NotaXMLRepository realGradeRepository;
    @Mock
    private NotaXMLRepository mockGradeRepository;
    private Service service;
    @After
    public void tearDown() {
        new File(TestUtils.Students).delete();
        new File(TestUtils.Assignments).delete();
        new File(TestUtils.Grades).delete();
    }

    @Before
    public void setup() throws IOException {
        mockStudentRepository = mock(StudentXMLRepository.class);
        mockAssignmentRepository = mock(TemaXMLRepository.class);
        mockGradeRepository = mock(NotaXMLRepository.class);

        File studentFile = TestUtils.createXMLFile(TestUtils.Students);
        File temeFile = TestUtils.createXMLFile(TestUtils.Assignments);
        File noteFile = TestUtils.createXMLFile(TestUtils.Grades);

        realStudentRepository = new StudentXMLRepository(new StudentValidator(), studentFile.getPath());
        realAssignmentRepository = new TemaXMLRepository(new TemaValidator(), temeFile.getPath());
        realGradeRepository = new NotaXMLRepository(new NotaValidator(), noteFile.getPath());
    }

    @Test
    public void addGradeTests() {
        service = new Service(this.mockStudentRepository, this.mockAssignmentRepository, this.realGradeRepository);

        Student student = new Student("1", "IONEL", 935);
        Tema assignment = new Tema("1", "DESCRIPTION", 12, 1);

        when(this.mockStudentRepository.findOne("1")).thenReturn(student);
        when(this.mockAssignmentRepository.findOne("1")).thenReturn(assignment);

        assertDoesNotThrow(() -> service.saveNota("1", "1", 6, 11, "SLAB"));

        NotaXMLRepository gradeRepo = new NotaXMLRepository(new NotaValidator(), TestUtils.Grades);
        Iterable<Nota> grades = gradeRepo.findAll();
        ArrayList<Nota> gradeList = new ArrayList<>();
        grades.forEach(gradeList::add);

        assertEquals(gradeList.size(), 1);
        assertEquals(gradeList.get(0).getID(), new Pair<>("1", "1"));
        assertEquals(gradeList.get(0).getNota(), 6.0, 0.01);
        assertEquals(gradeList.get(0).getSaptamanaPredare(), 11);
        assertEquals(gradeList.get(0).getFeedback(), "SLAB");
    }

    @Test
    public void addStudentTests() throws IOException {
        service = new Service(this.realStudentRepository, this.mockAssignmentRepository, this.mockGradeRepository);

        assertDoesNotThrow(() -> service.saveStudent("1", "IONEL", 935));

        StudentXMLRepository studentRepo = new StudentXMLRepository(new StudentValidator(), TestUtils.Students);
        Iterable<Student> students = studentRepo.findAll();
        ArrayList<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);

        assertEquals(studentList.size(), 1);
        assertEquals(studentList.get(0).getID(), "1");
        assertEquals(studentList.get(0).getNume(), "IONEL");
        assertEquals(studentList.get(0).getGrupa(), 935);
    }

    @Test
    public void addAssignmentTests() {
        service = new Service(this.realStudentRepository, this.realAssignmentRepository, this.mockGradeRepository);

        assertDoesNotThrow(() -> service.saveStudent("1", "IONEL", 935));
        assertDoesNotThrow(() -> service.saveTema("1", "DESCRIPTION", 12, 1));

        StudentXMLRepository studentRepo = new StudentXMLRepository(new StudentValidator(), TestUtils.Students);
        Iterable<Student> students = studentRepo.findAll();
        ArrayList<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);

        TemaXMLRepository assignmentRepo = new TemaXMLRepository(new TemaValidator(), TestUtils.Assignments);
        Iterable<Tema> assignments = assignmentRepo.findAll();
        ArrayList<Tema> assignmentList = new ArrayList<>();
        assignments.forEach(assignmentList::add);

        assertEquals(studentList.size(), 1);
        assertEquals(studentList.get(0).getID(), "1");
        assertEquals(studentList.get(0).getNume(), "IONEL");
        assertEquals(studentList.get(0).getGrupa(), 935);

        assertEquals(assignmentList.size(), 1);
        assertEquals(assignmentList.get(0).getID(), "1");
        assertEquals(assignmentList.get(0).getDescriere(), "DESCRIPTION");
        assertEquals(assignmentList.get(0).getDeadline(), 12);
        assertEquals(assignmentList.get(0).getStartline(), 1);
    }
}