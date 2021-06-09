package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

import java.util.LinkedList;
import java.util.List;

public class Cylinder extends Tube{
    private double height;

    /**
     * constructs a cylinder
     * @param axisRay the ray which is the axis of the cylinder
     * @param radius cylinder's radius
     * @param height cylinder's height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;

        //find the min and max x,y,z
        Point3D base1= axisRay.getP0();
        Point3D base2= axisRay.getPoint(height);
        minX = base1.getX()< base2.getX()? base1.getX()-radius: base2.getX()-radius;
        minY = base1.getY()< base2.getY()? base1.getY()-radius: base2.getY()-radius;
        minZ = base1.getZ()< base2.getZ()? base1.getZ()-radius: base2.getZ()-radius;
        maxX = base1.getX()> base2.getX()? base1.getX()+radius: base2.getX()+radius;
        maxY = base1.getY()> base2.getY()? base1.getY()+radius: base2.getY()+radius;
        maxZ = base1.getZ()> base2.getZ()? base1.getZ()+radius: base2.getZ()+radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        // tf the point is on the center of the ray
        if (point.equals(this.axisRay.getP0()))
            return this.axisRay.getDir().scale(-1).normalized();


        double rayLength= getAxisRay().getDir().dotProduct(point.subtract(getAxisRay().getP0()));

        //if the point is on the bottom of the cylinder
        if (rayLength==0)
            return this.axisRay.getDir().scale(-1).normalized();

        //if the point is on the top of the cylinder
        if (Util.isZero(rayLength-height))
           return this.axisRay.getDir().normalized();

        //if the point is on the tube
        Point3D point0 = getAxisRay().getP0().add(getAxisRay().getDir().scale(rayLength));
        return point.subtract(point0).normalized();


    }

    public double getHeight() {
        return height;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> tubeList= super.findGeoIntersections(ray);
        if (tubeList==null)
            return null;
        List<GeoPoint> cylinderList=new LinkedList<>();
        for (GeoPoint point:tubeList) {
            Vector vec1 = point.point.subtract(this.axisRay.getP0());
            Vector vec2 = point.point.subtract(this.axisRay.getPoint(this.height));
            if (this.axisRay.getDir().dotProduct(vec1)*this.axisRay.getDir().dotProduct(vec2)<0)
                cylinderList.add(point);

        }



        return cylinderList;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
