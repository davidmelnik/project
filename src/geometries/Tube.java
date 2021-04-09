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
        double t= getAxisRay().getDir().dotProduct(point.subtract(getAxisRay().getP0()));
        Point3D pointO;
        if(t==0)// to don't do vector0 if scalar=0
            pointO = getAxisRay().getP0();
        else
            pointO = getAxisRay().getP0().add(getAxisRay().getDir().scale(t));
        return point.subtract(pointO);
    }
}
