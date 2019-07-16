package fr.equiwatch;

import org.junit.Assert;
import org.junit.Test;

import fr.equiwatch.model.EquidesClass;

public class EquidesClassTest {
    @Test
    public void EquidesClassConstructorTest() {
        String id = "Azyz54JUH7Iujj77515fehg";
        String idEnclos = "Azyz54JUH7Iujj77";
        String nom = "Manege";
        String idCapteur = "Azyz54JUH7esffseIujj77";

        EquidesClass equides = new EquidesClass(id, nom, idEnclos, idCapteur);
        Assert.assertEquals(nom, equides.getNom());
        Assert.assertEquals(idCapteur, equides.getIdCapteur());
        Assert.assertEquals(idEnclos, equides.getIdEnclos());
        Assert.assertEquals(id, equides.getId());

        String newId = "Azyz54Jd1UH7Iufrfjj77";
        String newIdEnclos = "Azyz54JUH7Iufrfjj77";
        String newNom = "Maneges";
        String newIdCapteur = "Azyz54JUH7esffseIu5846f1r45fj77";

        equides.setIdCapteur(newIdCapteur);
        equides.setIdEnclos(newIdEnclos);
        equides.setNom(newNom);
        equides.setId(newId);

        Assert.assertNotEquals(nom, equides.getNom());
        Assert.assertNotEquals(idCapteur, equides.getIdCapteur());
        Assert.assertNotEquals(idEnclos, equides.getIdEnclos());
        Assert.assertNotEquals(id, equides.getId());
    }

    @Test
    public void EnclosClassConstructorWithoutId() {
        String idEnclos = "Azyz54JUH7Iujj77";
        String nom = "Manege";
        String idCapteur = "Azyz54JUH7esffseIujj77";

        EquidesClass equides = new EquidesClass(nom, idEnclos, idCapteur);
        Assert.assertEquals(nom, equides.getNom());
        Assert.assertEquals(idCapteur, equides.getIdCapteur());
        Assert.assertEquals(idEnclos, equides.getIdEnclos());

        String newIdEnclos = "Azyz54JUH7Iufrfjj77";
        String newNom = "Maneges";
        String newIdCapteur = "Azyz54JUH7esffseIu5846f1r45fj77";

        equides.setIdCapteur(newIdCapteur);
        equides.setIdEnclos(newIdEnclos);
        equides.setNom(newNom);

        Assert.assertNotEquals(nom, equides.getNom());
        Assert.assertNotEquals(idCapteur, equides.getIdCapteur());
        Assert.assertNotEquals(idEnclos, equides.getIdEnclos());
    }

    @Test
    public void EquidesClassConstructorEmpty() {
        EquidesClass equides = new EquidesClass();
    }
}
