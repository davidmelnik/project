package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Tube
 *
 *
 */
public class TubeTest {

    /**
     * Test method for
     * {@link geometries.Tube#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Tube tb = new Tube(new Ray(new Point3D(0,0,0),new Vector(0,0,1)),1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals("Wrong normal to tube",new Vector(1,0,0),tb.getNormal(new Point3D(1,0,1)));

        // =============== Boundary Values Tests ==================
        // TC10: point is in the same line as the tube base
        assertEquals("Wrong vector when the point is in the same line as the tube base",new Vector(1,0,0),tb.getNormal(new Point3D(1,0,0)));
    }
}