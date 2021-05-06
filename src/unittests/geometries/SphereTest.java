package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class SphereTest {
    /**
     * Test method for {@link geometries.Sphere #getNormal(primitives.Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp1=new Sphere(new Point3D(0,1,0),3);
        Point3D point= new Point3D(0,4,0);
        assertEquals("wrong normal vector to sphere",sp1.getNormal(point),new Vector(0,1,0));

    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point3D(0.5, 0.5, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of( p2), result);
        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(14, 5, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", null, result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(p1,
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p2), result);
        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(p2,
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", null, result);

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        Point3D  p3= new Point3D(0,0,0);
        Point3D  p4= new Point3D(2,0,0);
        assertEquals("Ray crosses sphere", List.of(p3, p4), result);
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(p3,
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p4), result);
        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(0.5,0,0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p4), result);
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1,0,0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p4), result);
        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(p4,
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", null, result);
        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point3D(4,0,0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", null, result);
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(-2,-1,0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", null, result);
        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(1,-1,0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", null, result);
        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point3D(4,-1,0),
                new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", null, result);

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point3D(-2,0,0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points", null, result);
    }

}