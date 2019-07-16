package fr.equiwatch;

import org.junit.Assert;
import org.junit.Test;


import fr.equiwatch.model.CapteursClass;

public class CapteursClassTest {

    @Test
    public void CapteursClassConstructorTest() {
        String label = "testCpateurs";
        String type = "Vide";

        CapteursClass capteurs = new CapteursClass(label, type);

        Assert.assertEquals(label, capteurs.getLabel());
        Assert.assertEquals(type, capteurs.getType());
    }

    @Test
    public void CapteursClassConstructorWithIdTest() {
        String id = "ExkjjRF44YGuzd7484jdszu78";
        String label = "testCpateurs";
        String type = "Vide";

        CapteursClass capteurs = new CapteursClass(id, label, type);

        Assert.assertEquals(id, capteurs.getId());
        Assert.assertEquals(label, capteurs.getLabel());
        Assert.assertEquals(type, capteurs.getType());

    }

    @Test
    public void CapteursClassConstructorEmpty() {
        CapteursClass capteurs = new CapteursClass();
    }
}
