package unittests.primitives;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 * @author David and Menachem
 */
public class VectorTest {

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}.
     */
   @Test
    public void testSubtract() {
       Vector v1 = new Vector(2,2,2);
       Vector v2 = new Vector(1,1,1);

       // ============ Equivalence Partitions Tests ==============
       // TC01: There is a simple single test here
       assertEquals("subtract() result wrong Vector",new Vector(1,1,1),v1.subtract(v2));

       // =============== Boundary Values Tests ==================
       // TC10: test zero vector from subtracting equal vectors
       try {
           v1.subtract(v1);
           fail("subtract for equal vectors does not throw an exception");
       } catch (Exception e) {}

   }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);
        Vector v3 = new Vector(-3, 4, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals("add() result wrong Vector",v1.add(v3),new Vector(-2,6,5));

        // =============== Boundary Values Tests ==================
        // TC10: test zero vector from adding opposite vectors
        try {
            v1.add(v2);
            fail("add() for opposite vectors does not throw an exception");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals("scale() wrong value",new Vector(2, 4, 6), v1.scale(2));

        // =============== Boundary Values Tests ==================
        // TC10: test zero vector from zero scalar
        try {
            v1.scale(0);
            fail("scale() for zero scalar does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals("dotProduct() wrong value",v1.dotProduct(v2),-28,0.00001);

        // =============== Boundary Values Tests ==================
        // TC10: orthogonal vectors
        assertTrue("dotProduct() for orthogonal vectors is not zero",isZero(v1.dotProduct(v3)));

    }



    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
     public void testCrossProduct() {
      Vector v1 = new Vector(1, 2, 3);

      // ============ Equivalence Partitions Tests ==============
      Vector v2 = new Vector(0, 3, -2);
      Vector vr = v1.crossProduct(v2);

      // TC01: Test that length of cross-product is proper (orthogonal vectors taken
      // for simplicity)
      assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

      // TC02: Test cross-product result orthogonality to its operands
      assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
      assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

      // =============== Boundary Values Tests ==================
      // TC11: test zero vector from cross-productof co-lined vectors
      Vector v3 = new Vector(-2, -4, -6);

       //       IllegalArgumentException.class, () -> v1.crossProduct(v3));
       try {
           v1.crossProduct(v2);
           fail("crossProduct() for parallel vectors does not throw an exception");
       } catch (Exception e) {}
     }


    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
     Vector v1 = new Vector(1, 2, 3);


     // ============ Equivalence Partitions Tests ==============
    // TC01: There is a simple single test here
    //we used assert True instead  of assertEquals, because we wanted to use isZero
     assertTrue("lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 =new Vector(0, 3, 4);
        assertEquals(v1.length(),5,0.00001);
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: does not create a new vector
        assertEquals("normalize() function creates a new vector", vCopy, vCopyNormalize);

        // TC02: is a a unit vector
        assertEquals("normalize() result is not a unit vector",vCopyNormalize.length(), 1,0.00001);

    }

    /**
     * Test method for {@link Vector#normalized()}.
     */
    @Test
    public void testNormalized() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: creates a new vector
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertFalse ("normalizated() function does not create a new vector",u == v);

    }
}