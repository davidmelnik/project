package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

public interface Intersectable {

    /**
     * a static class of a point with it's geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }


        /**
         * constructor
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        public Vector getNormal(){
            return this.geometry.getNormal(this.point);
        }


    }

    List<GeoPoint> findGeoIntersections(Ray ray);

    //TODO to delete
    List<Point3D> findIntersections(Ray ray);
}
