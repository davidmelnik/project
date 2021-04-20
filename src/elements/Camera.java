package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Camera {

    private Point3D p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    private double width;
    private double height;

    private double distance;


    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        if (!Util.isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException ("vectors aren't vertical");

        this.vRight=vTo.crossProduct(vUp).normalized();
    }

    public Camera setViewPlaneSize(double width, double height){
        this.width=width;
        this.height=height;

        return this;
    }

    public Camera setDistance(double distance){
        if(distance<=0)
            throw  new IllegalArgumentException("distance must be positive");
        this.distance=distance;

        return this;
    }


    public Point3D getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }


    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

        Point3D Pc =this.p0.add(this.vTo.scale(this.distance));
        double Ry =this.getHeight()/((double) nY );
        double Rx =this.getWidth()/((double) nX );
        double  Yi=-(i-(nY-1)/2d)*Ry;
        double Xj=(j-(nX-1)/2d)*Rx;
        Point3D Pi_j;
        if(Xj == 0 && Yi==0 )
            Pi_j = Pc;
        else if(Xj ==0)
            Pi_j = Pc.add(vUp.scale(Yi));
        else if(Yi ==0)
            Pi_j = Pc.add(vRight.scale(Xj));
        else
            Pi_j = Pc.add(vRight.scale(Xj).add(vUp.scale(Yi)));
        return new Ray(this.p0, Pi_j.subtract(this.p0));
    }

}
