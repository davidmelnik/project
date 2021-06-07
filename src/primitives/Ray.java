package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static primitives.Util.isZero;

public class Ray {
    public static int getNumberRays() {
        return NUMBER_RAYS;
    }

    private static int NUMBER_RAYS=50;
    private static final double DELTA = 0.1;
    private Point3D p0;
    private Vector dir;

    /**
     * constructs a normalized ray
     * normalizes the given vector
     * @param p0 starting point
     * @param dir direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    public Ray(Point3D point,  Vector dir, Vector n) {
        this.dir = dir.normalized();
        Vector delta = n.scale(Util.alignZero(n.dotProduct(dir)) > 0 ? DELTA : - DELTA);
        this.p0 = point.add(delta);
    }

    public static void setNumberRays(int numberRays) {
        NUMBER_RAYS = numberRays;
    }

    /**
     *
     * @param list
     * @return the closet point to the head of ray
     * if list empty= return null
     */
    public Point3D findClosestPoint(List<Point3D> list){
        if(list ==null || list.isEmpty())
            return null;
        double min_distance = list.get(0).distanceSquared(this.p0);
        Point3D close_point = list.get(0);

        //every time I take the min point and  min distance
        for (Point3D item: list) {
            if(item.distanceSquared(this.p0) <min_distance){
                min_distance=item.distanceSquared(this.p0);
                close_point =item;
            }
        }
        return close_point;
    }

    /**
     *
     * @param list
     * @return the closet point to the head of ray
     * if list empty= return null
     */
    public GeoPoint getClosestGeoPoint (List<Intersectable.GeoPoint> list){
        if(list ==null || list.isEmpty())
            return null;
        double min_distance = list.get(0).point.distanceSquared(this.p0);
        GeoPoint close_point = list.get(0);

        //every time I take the min point and  min distance
        for (GeoPoint item: list) {
            if(item.point.distanceSquared(this.p0) <min_distance){
                min_distance=item.point.distanceSquared(this.p0);
                close_point =item;
            }
        }
        return close_point;
    }

    /**
     *
     * @param k distribution factor
     * @param normal the normal must be in the direction of the new rays
     * @return a list of rays in a circle in the area of the original ray
     */
    public LinkedList<Ray> getBeamOfRays (double k, Vector normal){


        Point3D center= getPoint(100*k);//center of the circle

        Vector xVector, yVector;//two orthogonal vectors which create the circle

        //create a vector which is orthogonal to the given ray
        if (this.getDir().equals(new Vector(new Point3D(0,1,0)))||this.getDir().equals(new Vector(new Point3D(0,-1,0))))
            yVector=new Vector(new Point3D(0,0,1));
        else
            yVector=this.getDir().crossProduct(new Vector(new Point3D(0,1,0))).crossProduct(this.getDir()).normalize();

        //create a vector which is orthogonal to the given ray and the new vector
        xVector=this.getDir().crossProduct(yVector).normalized();



        Random random = new Random();
        LinkedList<Ray> list = new LinkedList();
        list.add(this);
        Vector newVector;

        // create new rays and add them to the list
        for(int i=0; i<NUMBER_RAYS-1; i++){
            do {
                double x=random.nextDouble()*2-1;
                double y=(random.nextDouble()*2-1)*Math.sqrt(1-x*x);
                Point3D point =center;
                if (!isZero(x))
                    point=point.add(xVector.scale(x));
                if (!isZero(y))
                    point=point.add(yVector.scale(y));
                newVector=point.subtract(this.getP0()).normalize();
                double test= newVector.dotProduct(normal);

            } while(newVector.dotProduct(normal)<=0);


            list.add(new Ray(this.p0,newVector));
        }

        return list;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public Point3D getPoint(double t){
        //if (Util.isZero(t))
        //    return this.p0;
       try {
           return this.p0.add(this.dir.scale(t));
       } catch (Exception e) {
           return this.p0;
       }
    }
}
