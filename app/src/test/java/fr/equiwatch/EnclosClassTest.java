package fr.equiwatch;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;

public class EnclosClassTest {

    @Test
    public void EnclosClassConstructorTest() {
        String label = "Manege";
        ArrayList<PointsGpsClass > pointsGps = new ArrayList<>();
        ArrayList<String> listIdCapteur = new ArrayList<>();

        EnclosClass enclos = new EnclosClass(label, pointsGps, listIdCapteur);

        Assert.assertArrayEquals(pointsGps.toArray(), enclos.getPointsGps().toArray());
        Assert.assertEquals(label, enclos.getLabel());
        Assert.assertArrayEquals(listIdCapteur.toArray(), enclos.getListIdCapteur().toArray());

        String setLabelTest = "new Label";
        ArrayList<PointsGpsClass > newPointsGps = new ArrayList<>();
        newPointsGps.add(new PointsGpsClass(55.23515, 22.554, 1));
        ArrayList<String> newListIdCapteur = new ArrayList<>();
        newListIdCapteur.add("ExkjjRF44YGuzd7484jdszu78");

        enclos.setLabel(setLabelTest);
        enclos.setListIdCapteur(newListIdCapteur);
        enclos.setPointsGps(newPointsGps);
        Assert.assertEquals(enclos.toString(), enclos.getLabel());

        Assert.assertNotEquals(enclos.getLabel(), label);
        Assert.assertNotEquals(enclos.getListIdCapteur(), listIdCapteur);
        Assert.assertNotEquals(enclos.getPointsGps(), pointsGps);
    }

    @Test
    public void EnclosClassConstructorWithIdTest() {
        String id = "ExkjjRF44YGuzd7484jdszu78";
        String label = "new Label";
        ArrayList<PointsGpsClass > pointsGps = new ArrayList<>();
        ArrayList<String> listIdCapteur = new ArrayList<>();
        pointsGps.add(new PointsGpsClass(55.23515, 22.554, 1));

        EnclosClass enclos = new EnclosClass(id, label, pointsGps, listIdCapteur);
        Assert.assertEquals(id, enclos.getId());
        Assert.assertArrayEquals(pointsGps.toArray(), enclos.getPointsGps().toArray());
        Assert.assertEquals(label, enclos.getLabel());

        String newId = "hjhbhjbjnlnkjvf54645KHGGF";
        enclos.setId(newId);
        Assert.assertNotEquals(id, enclos.getId());
    }

    @Test
    public void EnclosClassConstructorEmpty() {
        EnclosClass enclos = new EnclosClass();
    }
}
