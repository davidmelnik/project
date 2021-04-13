package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{

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

        return point.subtract(center);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector u= this.center.subtract(ray.getP0());
        double t_m=ray.getDir().dotProduct(u);
        double  d=Math.sqrt(u.lengthSquared()-t_m*t_m);
        if(d>=this.radius)
            return null;//
        double t_h=Math.sqrt(this.radius*this.radius -d*d);

    }
}
