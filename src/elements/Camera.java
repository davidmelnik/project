package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {

    /**
     * cameras location
     */
    private Point3D p0;

    /**
     * 3 orthogonal vectors which represent the cameras direction
     */
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    /**
     * view plane size
     */
    private double width;
    private double height;

    /**
     * distance of the camera from the view plan
     */
    private double distance;


    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException ("vectors aren't vertical");

        this.vRight=vTo.crossProduct(vUp).normalized();
    }
/*
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException ("vectors aren't vertical");

        this.vRight=vTo.crossProduct(vUp).normalized();
    }
*/

    /**
     * select new point to view and rotate camera
     * @param goal
     * @param angle
     * @return
     */
    public Camera turnAngle(Point3D goal, double angle) {
        this.vTo=goal.subtract(this.p0).normalize();

        //set default vUp
        if (vTo.equals(new Vector(new Point3D(0,0,1)))||vTo.equals(new Vector(new Point3D(0,0,-1))))
            this.vUp=new Vector(new Point3D(0,1,0));
        else
            this.vUp=this.vTo.crossProduct(new Vector(new Point3D(0,0,1))).crossProduct(this.vTo).normalize();

        double cosAngle=Math.cos(angle);
        double sinAngle=Math.sin(angle);
        if (isZero(cosAngle))
            this.vUp=vRight.scale(sinAngle);
        else if (isZero(sinAngle))
            this.vUp=vUp.scale(cosAngle);
        else
            this.vUp=this.vUp.scale(Math.cos(angle)).add(this.vRight.scale(Math.sin(angle)));

        this.vRight=vTo.crossProduct(vUp).normalized();

        return this;
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

    public Camera setP0(Point3D p0) {
        this.p0 = p0;

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


    /**
     * constructs a ray that passes through a specific pixel
     * @param nX number of columns
     * @param nY number of rows
     * @param j pixel row
     * @param i pixel column
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

        //VP center
        Point3D Pc =this.p0.add(this.vTo.scale(this.distance));

        //pixel dimensions
        double Ry =this.getHeight()/((double) nY );
        double Rx =this.getWidth()/((double) nX );

        //distance from the pixel to the center of the VP
        double  Yi=-(i-(nY-1)/2d)*Ry;
        double Xj=(j-(nX-1)/2d)*Rx;


        /**
         * cannot construct a vector zero, so in such a case the point doesn't change
         */
        Point3D Pi_j = Pc;
         if(!isZero(Xj))
            Pi_j = Pi_j.add(vRight.scale(Xj));
         if(!isZero(Yi))
            Pi_j = Pi_j.add(vUp.scale(Yi));

        return new Ray(this.p0, Pi_j.subtract(this.p0));
    }

}
