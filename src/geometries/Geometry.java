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
   protected double minX,minY,minZ,maxX,maxY,maxZ;

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

   public double getMinX() {
      return minX;
   }

   public double getMinY() {
      return minY;
   }

   public double getMinZ() {
      return minZ;
   }

   public double getMaxX() {
      return maxX;
   }

   public double getMaxY() {
      return maxY;
   }

   public double getMaxZ() {
      return maxZ;
   }
}
