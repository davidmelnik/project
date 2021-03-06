package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Sphere extends Geometry {

    private Point3D center;
    private double radius;

    /**
     * constructs a a sphere
     * @param center center point
     * @param radius sphere's radius
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
        //the min and max x,y,z
        minX = center.getX()-radius;
        minY = center.getY()-radius;
        minZ = center.getZ()-radius;
        maxX = center.getX()+radius;
        maxY = center.getY()+radius;
        maxZ = center.getZ()+radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }


    @Override
    public Vector getNormal(Point3D point) {

        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if(this.center.equals(ray.getP0())){//if ray start at center of sphere
            List<GeoPoint> lst = new ArrayList<>();
            lst.add(new GeoPoint(this,
                    ray.getP0().add(ray.getDir().scale(this.radius))));
            return lst;
        }
        Vector u= this.center.subtract(ray.getP0());
        double t_m=ray.getDir().dotProduct(u);
        double  d=Math.sqrt(u.lengthSquared()-t_m*t_m);
        if(Util.alignZero(d-this.radius) >=0)
            return null;//
        double t_h=Math.sqrt(this.radius*this.radius -d*d);
        double t_1= Util.alignZero(t_m+t_h);
        double t_2= Util.alignZero(t_m-t_h);
        if(t_1 <=0 && t_2<=0)
            return null;
        List<GeoPoint> lst = new ArrayList<>();
        if(t_1>0)
            lst.add(new GeoPoint( this,(ray.getPoint(t_1))));
        if(t_2>0)
            lst.add(new GeoPoint(this, ray.getPoint(t_2)));
        return lst;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if(this.center.equals(ray.getP0())){//if ray start at center of sphere
            List<Point3D> lst = new ArrayList<>();
            lst.add(ray.getP0().add(ray.getDir().scale(this.radius)));
            return lst;
        }
        Vector u= this.center.subtract(ray.getP0());
        double t_m=ray.getDir().dotProduct(u);
        double  d=Math.sqrt(u.lengthSquared()-t_m*t_m);
        if(Util.alignZero(d-this.radius) >=0)
            return null;//
        double t_h=Math.sqrt(this.radius*this.radius -d*d);
        double t_1= Util.alignZero(t_m+t_h);
        double t_2= Util.alignZero(t_m-t_h);
        if(t_1 <=0 && t_2<=0)
            return null;
        List<Point3D> lst = new ArrayList<>();
        if(t_1>0)
            lst.add((ray.getPoint(t_1)));
        if(t_2>0)
            lst.add((ray.getPoint(t_2)));
        return lst;

    }
}
