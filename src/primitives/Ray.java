package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

public class Ray {
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
        if (Util.isZero(t))
            return this.p0;
        return this.p0.add(this.dir.scale(t));
    }
}
