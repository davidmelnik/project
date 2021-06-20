package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

public class Plane extends Geometry {
    private Point3D q0;
    private Vector normal;

    /**
     * constructs a plane
     * @param q0 point
     * @param normal vector
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;

        minX=minY=minZ=Double.NEGATIVE_INFINITY;
        maxX=maxY=maxZ=Double.POSITIVE_INFINITY;

        if (normal.getHead().getX()==1)
            minX=maxX=q0.getX();

        if (normal.getHead().getY()==1)
            minY=maxY=q0.getY();

        if (normal.getHead().getZ()==1)
            minZ=maxZ=q0.getZ();
    }

    /**
     * constructs a plane
     * @param q0 first point
     * @param q1 second point
     * @param q2 third point
     */
    public Plane(Point3D q0, Point3D q1, Point3D q2) {
        if(q0.equals(q1) || q0.equals(q2)|| q1.equals(q2))
            throw new IllegalArgumentException("all 3 point in the plane must be different");
        this.q0= q0;
        try {
            this.normal = q0.subtract(q1).crossProduct(q0.subtract(q2)).normalized();
        } catch (Exception e) {
            throw new IllegalArgumentException("all 3 points are on same line");
        }


    }


    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    public Point3D getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        double denominator,numerator;
        if(ray.getP0().equals(this.q0))// if the head is on the plane there aren't any intersections, or the ray is included in the plane
            return null;
        numerator=this.getNormal().dotProduct(this.q0.subtract(ray.getP0()));
        denominator= ray.getDir().dotProduct(this.normal);
        if(denominator ==0)
            return null;// ray inside the plane
        double t= numerator/denominator;
        if(t<=0)
            return null;// the ray start after the plane
        List<GeoPoint> lst = new ArrayList<>();
        lst.add(new GeoPoint(this,ray.getPoint(t)));
        return lst;
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        double denominator,numerator;
        if(ray.getP0().equals(this.q0))// if the head is on the plane there aren't any intersections, or the ray is included in the plane
            return null;
        numerator=this.getNormal().dotProduct(this.q0.subtract(ray.getP0()));
        denominator= ray.getDir().dotProduct(this.normal);
        if(denominator ==0)
            return null;// ray inside the plane
        double t= numerator/denominator;
        if(t<=0)
            return null;// the ray start after the plane
        List<Point3D> lst = new ArrayList<>();
        lst.add(ray.getPoint(t));
        return lst;
    }
}
