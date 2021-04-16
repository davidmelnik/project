package unittests.geometries;

import geometries.*;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}  }.
     */
    @Test
    public void findIntersections() {


        Ray ray = new Ray(new Point3D(1,0,-1), new Vector(0,0,1));

        Plane planeTrue= new Plane(new Point3D (1,2,0), new Vector(0,0,-2));
        Plane planeFalse= new Plane(new Point3D (4,2,0), new Vector(1,0,0));

        Sphere sphereTrue= new Sphere(new Point3D(1,0,3),1);
        Sphere sphereFalse= new Sphere(new Point3D(500,2,0),1);

        Polygon polygonTrue= new Polygon(new Point3D(10,10,4), new Point3D(10,-10,4)
                ,new Point3D(-10,-10,4), new Point3D(-10,10,4));


        // =============== Equivalence Partitions Tests ==================
        // TC01: normal case
        assertEquals("Wrong number of intersections normal case",4,
                new Geometries(planeTrue,planeFalse,sphereTrue,sphereFalse,polygonTrue).findIntersections(ray).size());

        // =============== Boundary Values Tests ==================
        // TC10:  empty list of geometries
        assertEquals("Wrong number of empty list of geometries",null,
                new Geometries().findIntersections(ray));
        // TC11:  doesn't cross any geometry
        assertEquals("Wrong number doesn't cross any geometry",null,
                new Geometries(planeFalse,sphereFalse).findIntersections(ray));
        // TC12:  crosses only 1 geometry
        assertEquals("Wrong number crosses only 1 geometry",1,
                new Geometries(planeTrue,planeFalse,sphereFalse).findIntersections(ray).size());
        // TC12:  crosses all geometries
        assertEquals("Wrong number crosses all geometries",4,
                new Geometries(planeTrue,sphereTrue,polygonTrue).findIntersections(ray).size());




    }
}