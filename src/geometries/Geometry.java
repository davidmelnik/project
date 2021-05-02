package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * returns a normalized vector to the geometry
 */
public abstract class Geometry implements Intersectable {
   protected Color emmission =Color.BLACK;

   /**
    * emission builder
    * @param emmission
    * @return Geometry
    */
   public Geometry setEmmission(Color emmission) {
      this.emmission = emmission;

      return this;
   }

   /**
    *
    * @return emission
    */
   public Color getEmmission() {
      return emmission;
   }

   abstract Vector getNormal(Point3D point);
}
