package fr.equiwatch;

import org.junit.Assert;
import org.junit.Test;

import fr.equiwatch.model.PointsGpsClass;

public class PointsGpsClassTest {

    @Test
    public void PointsGpsClassConstructor() {
        double latitude = 55.5564684;
        double longitude = 35.568687;
        int ordre = 0;

        PointsGpsClass point = new PointsGpsClass(latitude, longitude, ordre);

        Assert.assertEquals(latitude, point.getLatitude(), 0.000001);
        Assert.assertEquals(longitude, point.getLongitude(), 0.000001);
        Assert.assertEquals(ordre, point.getOrdre());

        double newLatitude = 55.5512164;
        double newLongitude = 35.568787;
        int newOrdre = 5;

        point.setLatitude(newLatitude);
        point.setLongitude(newLongitude);
        point.setOrdre(newOrdre);

        Assert.assertNotEquals(latitude, point.getLatitude());
        Assert.assertNotEquals(longitude, point.getLongitude());
        Assert.assertNotEquals(ordre, point.getOrdre());
    }

    @Test
    public void PointsGpsClassConstructorEmpty() {
        PointsGpsClass point = new PointsGpsClass();
    }
}
