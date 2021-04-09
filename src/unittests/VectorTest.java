package unittests;

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

   @Test
    public void testSubtract() {
    }

    @Test
    public void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);
        Vector v3 = new Vector(-3, 4, 2);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("add() result wrong Vector",v1.add(v3),new Vector(-2,6,5));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from adding opposite vectors
        try {
            v1.add(v2);
            fail("add() for opposite vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    public void testScale() {
    }

    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        assertEquals("dotProduct() wrong value",v1.dotProduct(v2),-28,0.00001);

        // =============== Boundary Values Tests ==================
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
 //assertThrows("crossProduct() for parallel vectors does not throw an exception",
   //       IllegalArgumentException.class, () -> v1.crossProduct(v3));
   try {
       v1.crossProduct(v2);
       fail("crossProduct() for parallel vectors does not throw an exception");
   } catch (Exception e) {}
 }



    @Test
    public void testLengthSquared() {
     Vector v1 = new Vector(1, 2, 3);

     /**
      * Test method for {@link primitives.Vector#lengthSquared()}.
      */
     // ============ Equivalence Partitions Tests ==============
     //we used assert True instead  of assertEquals, because we wanted to use isZero
     assertTrue("lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
    }

    @Test
    public void testLength() {
        Vector v1 =new Vector(0, 3, 4);
    assertEquals(v1.length(),5,0.00001);
    }

    @Test
    public void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals("normalize() function creates a new vector", vCopy, vCopyNormalize);

        assertEquals("normalize() result is not a unit vector",vCopyNormalize.length(), 1,0.00001);

    }

    @org.junit.Test
    public void testNormalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();
        assertFalse ("normalizated() function does not create a new vector",u == v);

    }
}