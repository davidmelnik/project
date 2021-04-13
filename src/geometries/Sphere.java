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
        return null;
    }
}
