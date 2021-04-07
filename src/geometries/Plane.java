package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{
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
    }

    /**
     * constructs a plane
     * not implemented yet !!!
     * @param q0 first point
     * @param q1 second point
     * @param q2 third point
     */
    public Plane(Point3D q0, Point3D q1, Point3D q2) {
        this.q0 = q0;
        this.normal = null;
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
}