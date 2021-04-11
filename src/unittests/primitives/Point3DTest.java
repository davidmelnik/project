package unittests.primitives;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Point3D class
 *
 * @author David and Menachem
*/

public class Point3DTest {


    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)}.
     */
    @Test
    public void testSubtract() {

    // ============ Equivalence Partitions Tests ==============
    // TC01: There is a simple single test here

    assertEquals("Wrong point3d subtract result",new Vector(0,1,1),
            new Point3D(1,2,3).subtract(new Point3D(1,1,2)));

    // =============== Boundary Values Tests ==================
    // TC10: test zero vector from subtract points
    try {
        new Point3D(1,2,3).subtract(new Point3D(1,2,3));
        fail("subtract for equal points does not throw an exception");
    } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Point3D#add(Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals("Wrong point3d add result",new Point3D(2,3,5),
                new Point3D(1,2,3).add(new Vector(1,1,2)));
        
        
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D)}.
     */
    @Test
    public void testDistanceSquared() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        assertEquals("Wrong squared distance",4,new Point3D(0,0,0).distanceSquared(new Point3D(0,2,0)),0.00001);

        // =============== Boundary Values Tests ==================
        // TC10: zero squared distance
        assertEquals("Wrong zero squared distance",0,new Point3D(1,2,3).distanceSquared(new Point3D(1,2,3)),0.00001);
    }

    /**
     * Test method for {@link primitives.Point3D#distance(Point3D)}.
     */
    @Test
    public void testDistance() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        assertEquals("Wrong distance",2,new Point3D(0,0,0).distance(new Point3D(0,2,0)),0.00001);

        // =============== Boundary Values Tests ==================
        // TC10: zero squared distance
        assertEquals("Wrong zero  distance",0,new Point3D(1,2,3).distance(new Point3D(1,2,3)),0.00001);
    }

    /**
     * Test method for {@link primitives.Point3D#equals(Object)}.
     */
    @Test
    public void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: points are equal
        assertTrue("point are equal",new Point3D(1,1,1).equals(new Point3D( 1,1,1)));

        // TC02: points are not equal
        assertFalse("point aren't equal",new Point3D(1,1,1).equals(new Point3D( 1,2,1)));

    }
}