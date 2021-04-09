package unittests.geometries;

import geometries.Plane;
import geometries.Triangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        // Because it is a flat surface, the normal can be to opposite vectors
        Plane plane= new Plane(new Point3D(1,1,0),new Point3D(0,1,0),new Point3D(1,0,0));
        assertTrue("Bad normal to plane", (plane.getNormal()== new Vector(0,0,1)||
                plane.getNormal()== new Vector(0,0,-1)));

    }
    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)} ()}.
     */
    @Test
    public void Plane() {
        Plane pl0= new Plane(new Point3D(1,1,0),new Point3D(0,1,0),new Point3D(1,0,0));
        assertTrue("Bad normal to plane", (pl0.getNormal(new Point3D(0,0,0)).equals(new Vector(0,0,1))||
                pl0.getNormal(new Point3D(0,0,0)).equals( new Vector(0,0,-1))));
        try {
            Plane pl = new Plane(new Point3D(0,0,1),new Point3D(0,0,1),new Point3D(0,0,2));
            fail("although 2 points are equal it didn't through an exception");
        } catch (Exception e) {}

        try {
            Plane pl = new Plane(new Point3D(0,0,0),new Point3D(0,0,1),new Point3D(0,0,2));
            fail("although all 3 points are in the same line it didn't through an exception");
        } catch (Exception e) {}
    }
}