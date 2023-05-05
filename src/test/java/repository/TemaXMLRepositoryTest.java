package repository;

import domain.Tema;
import org.junit.Test;
import validation.TemaValidator;
import validation.Validator;

public class TemaXMLRepositoryTest {

    private TemaXMLRepository setUp() {
        Validator<Tema> temaValidator = new TemaValidator();
        return new TemaXMLRepository(temaValidator, "src/test/resources/repository/teme.xml");
    }

    @Test
    public void testAddAssignment() {
        TemaXMLRepository temaXMLRepository = setUp();
        Tema tema = new Tema("4", "tema test", 10, 8);
        temaXMLRepository.save(tema);

        Tema temaSalvata = temaXMLRepository.entities.get("4");
        assert (temaSalvata.getID().equals("4"));
        assert (temaSalvata.getDescriere().equals("tema test"));
        assert (temaSalvata.getDeadline() == 10);
        assert (temaSalvata.getStartline() == 8);
        temaXMLRepository.delete("4");
    }

    @Test //(tema.getDeadline() < 1 || tema.getDeadline() > 14 || tema.getDeadline() < tema.getStartline())
    public void testAddAssignmentInvalidDeadline() {
        TemaXMLRepository temaXMLRepository = setUp();
        int size = temaXMLRepository.entities.size();

        Tema tema1 = new Tema("4", "tema test", 0, 8);
        temaXMLRepository.save(tema1);
        assert (temaXMLRepository.entities.size() == size);

        Tema tema2 = new Tema("4", "tema test", 15, 8);
        temaXMLRepository.save(tema2);
        assert (temaXMLRepository.entities.size() == size);

        Tema tema3 = new Tema("4", "tema test", 10, 11);
        temaXMLRepository.save(tema3);
        assert (temaXMLRepository.entities.size() == size);
    }

    @Test
    public void testAddAssignmentInvalidID() {
        TemaXMLRepository temaXMLRepository = setUp();
        int size = temaXMLRepository.entities.size();

        Tema tema1 = new Tema("", "tema test", 10, 8);
        temaXMLRepository.save(tema1);
        assert(size == temaXMLRepository.entities.size());

        Tema tema2 = new Tema(null, "tema test", 10, 8);
        temaXMLRepository.save(tema2);
        assert(size == temaXMLRepository.entities.size());
    }

    @Test
    public void testAddAssignmentInvalidDesc() {
        TemaXMLRepository temaXMLRepository = setUp();
        int size = temaXMLRepository.entities.size();

        Tema tema1 = new Tema("4", "", 10, 8);
        temaXMLRepository.save(tema1);
        assert(size == temaXMLRepository.entities.size());

        Tema tema2 = new Tema("4", null, 10, 8);
        temaXMLRepository.save(tema2);
        assert(size == temaXMLRepository.entities.size());
    }

    @Test
    public void testAddAssignmentInvalidStartLine() {
        TemaXMLRepository temaXMLRepository = setUp();
        int size = temaXMLRepository.entities.size();

        Tema tema1 = new Tema("5", "Desc", 10, 0);
        temaXMLRepository.save(tema1);
        assert(size == temaXMLRepository.entities.size());

        Tema tema2 = new Tema("5", "Desc", 10, 15);
        temaXMLRepository.save(tema2);
        assert(size == temaXMLRepository.entities.size());

        Tema tema3 = new Tema("5", "Desc", 10, 11);
        temaXMLRepository.save(tema3);
        assert(size == temaXMLRepository.entities.size());
    }

    @Test
    public void testAddAssignmentAlreadyAdded() {
        TemaXMLRepository temaXMLRepository = setUp();
        Tema tema = new Tema("4", "tema test", 10, 8);
        assert (temaXMLRepository.save(tema) == null);
        assert (temaXMLRepository.save(tema) != null);
        temaXMLRepository.delete("4");
    }
}
