package unittests.geometries;

import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    /**
     * Test method for {@link geometries.Triangle #findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle  triangle= new Triangle(new Point3D(-1,0,0),new Point3D(0,1,0),new Point3D(1,0,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's Inside triangle (1 points)
        List<Point3D> result = triangle.findIntersections(new Ray(new Point3D(0,0.5,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses TRIANGLE", List.of(new Point3D(0,0.5,0)), result);
        // TC02: Ray's Outside against edge triangle (0 points)
        result = triangle.findIntersections(new Ray(new Point3D(-1, 1,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", null, result);
        // TC03: Ray's Outside against vertex triangle (0 points)
        result = triangle.findIntersections(new Ray(new Point3D(-8, -1,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", null, result);

        // =============== Boundary Values Tests ==================
        // **** Group: the ray begins "before" the plane
        // TC10: Ray's On edge (0 points)
        result = triangle.findIntersections(new Ray(new Point3D(0.5, 0,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points, Ray's On edge", null, result);
        // TC11: Ray's In vertex (0 points)
        result = triangle.findIntersections(new Ray(new Point3D(1, 0,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points, Ray's In vertex", null, result);
        // TC12: Ray's  On edge's continuation (0 points)
        result = triangle.findIntersections(new Ray(new Point3D(-8, 0,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points, Ray's  On edge's continuation", null, result);

    }

}