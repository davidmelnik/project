package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * returns a normalized vector to the geometry
 */
public abstract class Geometry implements Intersectable {
   protected Color emmission =Color.BLACK;
   private Material material =new Material();

   /**
    * emission builder
    * @param emmission
    * @return Geometry
    */
   public Geometry setEmission(Color emmission) {
      this.emmission = emmission;
      return this;
   }

   public Geometry setMaterial(Material material) {
      this.material = material;
      return this;
   }

   /**
    *
    * @return emission
    */
   public Color getEmmission() {
      return emmission;
   }

   public abstract Vector getNormal(Point3D point);

   public Material getMaterial() {
      return material;
   }

   public abstract double getMinX();

   public abstract double getMinY();
   public abstract double getMinZ();
   public abstract double getMaxX();
   public abstract double getMaxY();
   public abstract double getMaxZ();

}
