package unittests.geometries;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        assertTrue("Bad normal to plane", (plane.getNormal().equals(new Vector(0,0,1))||
                plane.getNormal().equals(new Vector(0,0,-1))));

    }
    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)} ()}.
     */
    @Test
    public void Plane() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        // Because it is a flat surface, the normal can be to opposite vectors
        Plane pl0= new Plane(new Point3D(1,1,0),new Point3D(0,1,0),new Point3D(1,0,0));
        assertTrue("Bad normal to plane", (pl0.getNormal(new Point3D(0,0,0)).equals(new Vector(0,0,1))||
                pl0.getNormal(new Point3D(0,0,0)).equals( new Vector(0,0,-1))));

        // =============== Boundary Values Tests ==================
        // TC10: 2 equal points
        try {
            Plane pl = new Plane(new Point3D(0,0,1),new Point3D(0,0,1),new Point3D(0,0,2));
            fail("although 2 points are equal it didn't through an exception");
        } catch (Exception e) {}

        // TC11: V3 points are in the same line
        try {
            Plane pl = new Plane(new Point3D(0,0,0),new Point3D(0,0,1),new Point3D(0,0,2));
            fail("although all 3 points are in the same line it didn't through an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane= new Plane(new Point3D(2,2,0),new Vector(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        // **** Group: The Ray must be neither orthogonal nor parallel to the plane

        // TC01: Ray intersects the plane (1 points)
        List<Point3D> result=plane.findIntersections(
                new Ray(new Point3D(2,1,-1),new Vector(1,2,1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of( new Point3D(3,3,0)), result);
        // TC02:Ray does not intersect the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,-1),new Vector(-1,-2,-1)));
        assertEquals("ray does not cross plane", null, result);
        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC10:the ray included  in the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,0),new Vector(-1,-2,0)));
        assertEquals("ray included in the plane", null, result);
        // TC11:the ray not included in the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,-1),new Vector(-1,-2,0)));
        assertEquals(" ray is parallel to the plane and does not cross plane", null, result);

        // **** Group: Ray is orthogonal to the plane
        // TC12:the ray p0 start  before the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,-1),new Vector(0,0,1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of( new Point3D(2,1,0)), result);
        // TC13:the ray p0 start  in the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,0),new Vector(0,0,1)));
        assertEquals(" Ray is orthogonal to the plane and ray p0 start  in the plane", null, result);
        // TC14:the ray p0 start  after the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,2),new Vector(0,0,1)));
        assertEquals(" Ray is orthogonal to the plane and ray p0 start after the plane", null, result);
        // **** Group: Special cases
        // TC15: Ray is neither orthogonal nor parallel to and begins at the plane
        result=plane.findIntersections(
                new Ray(new Point3D(2,1,0),new Vector(1,7,1)));
        assertEquals(" Ray is neither orthogonal nor parallel to and begins at the plane", null, result);
        // TC16:Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane (Q)
        result=plane.findIntersections(
                new Ray(plane.getQ0(), new Vector(1,7,1)));
        assertEquals(" Ray is neither orthogonal nor parallel to and begins at the reference point", null, result);

    }
}