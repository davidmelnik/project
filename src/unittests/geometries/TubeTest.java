package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {

    @Test
    public void getNormal() {
        Tube tb = new Tube(new Ray(new Point3D(0,0,0),new Vector(0,0,1)),1);
        assertEquals("",new Vector(1,0,0),tb.getNormal(new Point3D(1,0,1)));
        assertEquals("",new Vector(1,0,0),tb.getNormal(new Point3D(1,0,0)));
    }
}