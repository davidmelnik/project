package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal() {
        Sphere sp1=new Sphere(new Point3D(0,1,0),3);
        Point3D point= new Point3D(0,4,0);

        assertEquals("wrong normal vector",sp1.getNormal(point),new Vector(0,3,0));

    }
}