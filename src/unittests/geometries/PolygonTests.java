/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.*;
import org.junit.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link geometries.Polygon# Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Polygon polygon  = new Polygon(new Point3D(-1,0,0),new Point3D(0,1,0),new Point3D(1,1,0),new Point3D(1,0,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's Inside polygon (1 points)
        List<Point3D> result = polygon.findIntersections(new Ray(new Point3D(0,0.5,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses polygon", List.of(new Point3D(0,0.5,0)), result);
        // TC02: Ray's Outside against edge polygon (0 points)
        result = polygon.findIntersections(new Ray(new Point3D(-1, 1,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", null, result);
        // TC03: Ray's Outside against vertex polygon (0 points)
        result = polygon.findIntersections(new Ray(new Point3D(-8, -1,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", null, result);

        // =============== Boundary Values Tests ==================
        // **** Group: the ray begins "before" the plane
        // TC10: Ray's On edge (0 points)
        result = polygon.findIntersections(new Ray(new Point3D(0.5, 0,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points, Ray's On edge", null, result);
        // TC11: Ray's In vertex (0 points)
        result = polygon.findIntersections(new Ray(new Point3D(1, 0,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points, Ray's In vertex", null, result);
        // TC12: Ray's  On edge's continuation (0 points)
        result = polygon.findIntersections(new Ray(new Point3D(-8, 0,1),
                new Vector(0, 0, -1)));
        assertEquals("Wrong number of points, Ray's  On edge's continuation", null, result);

    }
}
