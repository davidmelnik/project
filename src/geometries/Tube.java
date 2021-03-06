package geometries;

import primitives.*;


import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class Tube extends Geometry {
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

        minX=minY=minZ=Double.NEGATIVE_INFINITY;
        maxX=maxY=maxZ=Double.POSITIVE_INFINITY;

        if (isZero(axisRay.getDir().getHead().getX())) {
            minX = axisRay.getP0().getX() - radius;
            maxX = axisRay.getP0().getX() + radius;
        }
        if (isZero(axisRay.getDir().getHead().getY())) {
            minY = axisRay.getP0().getY() - radius;
            maxY = axisRay.getP0().getY() + radius;
        }
        if (isZero(axisRay.getDir().getHead().getZ())) {
            minZ = axisRay.getP0().getZ() - radius;
            maxZ = axisRay.getP0().getZ() + radius;
        }

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
        return point.subtract(pointO).normalized();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector rootA;
        double v_va=ray.getDir().dotProduct(this.axisRay.getDir());
        if (Util.isZero(v_va))
            rootA= ray.getDir();
        else
            rootA=ray.getDir().subtract(this.axisRay.getDir().scale(v_va));
        double A=rootA.dotProduct(rootA);

        double B,C;

        if (ray.getP0().equals(this.axisRay.getP0())) {
            B = 0;
            C = -this.radius * this.radius;
        }
        else {
            Vector dp = ray.getP0().subtract(this.axisRay.getP0());
            double dp_va = dp.dotProduct(this.axisRay.getDir());

            Vector rootPartC;
            if (Util.isZero(dp_va))
                rootPartC = dp;
            else
                rootPartC = dp.subtract(this.axisRay.getDir().scale(dp_va));

            B = 2 * (rootA.dotProduct(rootPartC));

            C = rootPartC.dotProduct(rootPartC) - this.radius * this.radius;
        }


        if(Util.isZero(A)){
            return null;
        }

        double discriminant= Util.alignZero(B*B-4*A*C);
        //1 or no solution
        if (discriminant<=0)
            return null;

        //2 solutions
        List<GeoPoint> list = new LinkedList<GeoPoint>();

        double t1=Util.alignZero((-B+Math.sqrt(discriminant)) /( 2 * A));
        double t2=Util.alignZero((-B-Math.sqrt(discriminant)) /( 2 * A));
        if (t1<=0 && t2<=0 )
            return null;
        if (t1>0)
            list.add(new GeoPoint(this, ray.getPoint(t1)));
        if (t2>0)
            list.add(new GeoPoint(this, ray.getPoint(t2)));


        return list;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector rootA;
        double v_va=ray.getDir().dotProduct(this.axisRay.getDir());
        if (Util.isZero(v_va))
            rootA= ray.getDir();
        else
            rootA=ray.getDir().subtract(this.axisRay.getDir().scale(v_va));
        double A=rootA.dotProduct(rootA);

        double B,C;

        if (ray.getP0().equals(this.axisRay.getP0())) {
            B = 0;
            C = -this.radius * this.radius;
        }
        else {
            Vector dp = ray.getP0().subtract(this.axisRay.getP0());
            double dp_va = dp.dotProduct(this.axisRay.getDir());

            Vector rootPartC;
            if (Util.isZero(dp_va))
                rootPartC = dp;
            else
                rootPartC = dp.subtract(this.axisRay.getDir().scale(dp_va));

           B = 2 * (rootA.dotProduct(rootPartC));

           C = rootPartC.dotProduct(rootPartC) - this.radius * this.radius;
        }


       if(Util.isZero(A)){
           return null;
       }

       double discriminant= Util.alignZero(B*B-4*A*C);
        //1 or no solution
       if (discriminant<=0)
            return null;

        //2 solutions
        List<Point3D> list = new LinkedList<Point3D>();

        double t1=Util.alignZero((-B+Math.sqrt(discriminant)) /( 2 * A));
        double t2=Util.alignZero((-B-Math.sqrt(discriminant)) /( 2 * A));
        if (t1<=0 && t2<=0 )
            return null;
        if (t1>0)
        list.add(ray.getPoint(t1));
        if (t2>0)
        list.add(ray.getPoint(t2));


        return list;








    }
}
