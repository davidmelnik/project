package geometries;

import primitives.*;

public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    /**
     * constructs a tube
     * @param axisRay the ray which is the axis of the tube
     * @param radius the tube's radius
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
