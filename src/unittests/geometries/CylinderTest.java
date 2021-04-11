package unittests.geometries;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;


/**
 * Testing Cylinder
 *
 *
 */
public class CylinderTest {

    /**
     * Test method for
     * {@link geometries.Cylinder#getNormal(Point3D)} .
     */
    @Test
    public void getNormal() {
        Cylinder cyl= new Cylinder(new Ray(new Point3D(0,0,0),new Vector(0,0,1)),2,10);

        // =============== Boundary Values Tests ==================
        // TC01: correct normal at bottom base
        assertEquals("wrong normal at bottom base",new Vector(0,0,-1),cyl.getNormal(new Point3D(1.5,-1.5,0)));

        // TC02: correct normal at top base
        assertEquals("wrong normal at top base",new Vector(0,0,1),cyl.getNormal(new Point3D(1.5,-1.5,10)));

        // TC03: correct normal at tube
        assertEquals("wrong normal at tube",new Vector(0,1,0),cyl.getNormal(new Point3D(0,2,2)));

        // =============== Boundary Values Tests ==================
        // TC10: correct normal at center of bottom base
        assertEquals("wrong normal at center of bottom base",new Vector(0,0,-1),cyl.getNormal(new Point3D(0,0,0)));

        // TC11: correct normal at center of top base
        assertEquals("wrong normal at center of top base",new Vector(0,0,1),cyl.getNormal(new Point3D(0,0,10)));

    }
}