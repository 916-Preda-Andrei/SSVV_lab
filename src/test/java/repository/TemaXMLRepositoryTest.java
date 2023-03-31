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

    @Test
    public void testAddAssignmentInvalidDeadline() {
        TemaXMLRepository temaXMLRepository = setUp();
        Tema tema = new Tema("4", "tema test", 15, 8);
        temaXMLRepository.save(tema);

        Tema temaSalvata = temaXMLRepository.entities.get("4");
        assert (temaSalvata == null);
        assert (temaXMLRepository.entities.size() == 1);
    }
}
