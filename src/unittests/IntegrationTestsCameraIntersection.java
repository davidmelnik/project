package unittests;

import static org.junit.Assert.*;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;

import elements.Camera;
import primitives.*;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTestsCameraIntersection {
    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */
    private int countIntersection(Camera camera, int nX, int nY, Geometry geometry){
        int counter=0;
        for(int i=0 ;i<nX;i++){
            for(int j=0; j<nY;j++){
                List<Point3D> list= geometry.findIntersections(camera.constructRayThroughPixel(nX,nY,j,i));
                if(list!= null)
                    counter+=list.size();
            }
        }
        return counter;

    }

    @Test
    public void testIntegretionFindIntersections() {
        Camera cameraCenter = new Camera(Point3D.ZERO,new Vector(0,0,-1),
                new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        Camera cameraOffCenter = new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),
                new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        // **** Group: sphere intersection
        // TC01:sphere after view plane(2 points)
        assertEquals("Wrong number points - sphere after view plane(2 points)",
                2,countIntersection(cameraCenter,3,3, new Sphere(new Point3D(0,0,-3),1)));
        // TC02:sphere include VP but not camera(18 points)
        assertEquals("Wrong number points - sphere include VP but not camera(18 points)",
                18,countIntersection(cameraOffCenter,3,3, new Sphere(new Point3D(0,0,-2.5),2.5)));
        // TC03:sphere crosses VP (10 points)
        assertEquals("Wrong number points - sphere crosses VP (10 points)",
                10,countIntersection(cameraOffCenter,3,3, new Sphere(new Point3D(0,0,-2),2)));
        // TC04:sphere include VP but and camera(9 points)
        assertEquals("Wrong number points - sphere include VP but and camera(9 points)",
                9,countIntersection(cameraOffCenter,3,3, new Sphere(new Point3D(0,0,0),4)));
        // TC05:sphere behind camera VP (0 points)
        assertEquals("Wrong number points - sphere behind camera VP (0 points)",
                0,countIntersection(cameraCenter,3,3, new Sphere(new Point3D(0,0,1),0.5)));


        // **** Group: plane intersection
        // TC11:plane parallel to view plane(9 points)
        assertEquals("Wrong number points - plane parallel to view plane(9 points)",
                9,countIntersection(cameraCenter,3,3, new Plane(new Point3D(0,0,-3),new Vector(0,0,1))));
        // TC12:plane angle with view plane <45(9 points)
        assertEquals("Wrong number points - plane angle with view plane <45(9 points)",
                9,countIntersection(cameraCenter,3,3, new Plane(new Point3D(0,0,-3),new Vector(0,-0.5,1))));
        // TC13:plane angle with view plane >=45(6 points)
        assertEquals("Wrong number points - plane angle with view plane >=45(6 points)",
                6,countIntersection(cameraCenter,3,3, new Plane(new Point3D(0,0,-6),new Vector(0,-1,1))));


        // **** Group: triangle intersection
        // TC11:mini triangle(1 points)
        assertEquals("Wrong number points - mini triangle(1 points)",
                1,countIntersection(cameraCenter,3,3,
                        new Triangle(new Point3D(0,1,-2),new Point3D(-1,-1,-2)
                                ,new Point3D(1,-1,-2))));
        // TC11:big triangle(2 points)
        assertEquals("Wrong number points - big triangle(2 points)",
                2,countIntersection(cameraCenter,3,3,
                        new Triangle(new Point3D(0,20,-2),new Point3D(-1,-1,-2)
                                ,new Point3D(1,-1,-2))));
    }


}






