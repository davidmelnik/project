package unittests.primitives;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RayTest {
    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)} .
     */

    @Test
    public void findClosestPoint() {
        Ray ray =new Ray(new Point3D(0,1,0),new Vector(1,0,0));
        Point3D p1 =new Point3D(1,1,0);
        Point3D p2 =new Point3D(2,1,0);
        Point3D p3 =new Point3D(3,1,0);
        // ============ Equivalence Partitions Tests =============

        // TC01: point in the middle of the list
        assertEquals("point in the middle of the list!!",p1,ray.findClosestPoint( List.of(p2,p1,p3) ));


        // =============== Boundary Values Tests ==================
        // TC11: empty list
        assertEquals("the list is empty!!", null, ray.findClosestPoint(new ArrayList())) ;

        // TC12: point in the beginning of the list
        assertEquals("point in the beginning of the list!!",p1,ray.findClosestPoint( List.of(p1,p2,p3) ));

        // TC13: point in the end of the list
        assertEquals("point in the end of the list!!",p1,ray.findClosestPoint( List.of(p3,p2,p1) ));



    }
}