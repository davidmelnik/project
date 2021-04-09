package unittests.geometries;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {
    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        // Because it is a flat surface, the normal can be to opposite vectors
        Triangle trgl= new Triangle(new Point3D(1,1,0),new Point3D(0,1,0),new Point3D(1,0,0));
        assertTrue("Bad normal to triangle", (trgl.getNormal(new Point3D(0,0,0)).equals(new Vector(0,0,1)) ||
                trgl.getNormal(new Point3D(0,0,0)).equals(new Vector(0,0,-1)) ));
    }

}