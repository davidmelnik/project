package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * returns a normalized vector to the geometry
 */
public interface Geometry {
   Vector getNormal(Point3D point);
}
