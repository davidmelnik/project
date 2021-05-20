package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;



    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Point3D point = geopoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0)
                return false;
        }
        return true;
    }

    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        //if (intersections == null)
        //    return scene.background;
        GeoPoint closestPoint = ray.getClosestGeoPoint(intersections);
        return closestPoint== null ? Color.BLACK: calcColor(closestPoint, ray);
    }


    /**
     * calculate the color of a point
     * @param intersection point
     * @param ray pixel ray
     * @return point's color
     */
    /*
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmmission())
                // add calculated light contribution from all light sources)
                .add(calcLocalEffects(intersection, ray));
    }

     */

    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, MIN_CALC_COLOR_K)
                .add(scene.ambientLight.getIntensity);
    }

    private Color calcColor(GeoPoint intersection,Ray ray, int level, double k) {
        Color color= intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray));
        return 1 == level ?color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }



    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
        Color color= Color.BLACK;
        Material material= geopoint.geometry.getMaterial();
        double kr= material.kR, kkr= k * kr;
        if (kkr> MIN_CALC_COLOR_K) {
            Ray reflectedRay= constructReflectedRay(geopoint.geometry.getNormal(geopoint.point), geopoint.point, ray);
            GeoPoint reflectedPoint= findClosestIntersection(reflectedRay);
            color= color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
        }
        double kt = material.kt, kkt= k * kt;
        if (kkt> MIN_CALC_COLOR_K) {
            Ray refractedRay= constructRefractedRay(n, geopoint.point, inRay);
            GeoPoint refractedPoint= findClosestIntersection(refractedRay);
            color= color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
        }
        return color;
    }

    private Ray constructReflectedRay(Vector normal, Point3D point, Ray ray) {

    }



    /**
     *
     * @param intersection point of intersection of ray and geometry
     * @param ray that goes through pixel
     * @return color after local effects at intersection
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0)
            { // sign(nl) == sign(nv)
                if (unshaded(lightSource,l,n, intersection)) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);

                // first add effects values and then scale the color in order to save scaling time
                color = color.add(lightIntensity.scale(
                        calcDiffusive(kd, nl) +
                                calcSpecular(ks, l, n, v, nShininess, nl)));
            }
            }
        }
        return color;
    }

    /**
     *
     * @param ks factor
     * @param l light ray
     * @param n normal
     * @param v camera ray
     * @param nShininess factor
     * @param nl dot product of n and l
     * @return specular value before scaling the light intensity
     */
    private double calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, double nl) {
        Vector r=l.subtract(n.scale(2*nl));
        return ks*Math.pow( Math.max(0,-v.dotProduct(r)),nShininess);

    }

    /**
     *
     * @param kd factor
     * @param nl dot product of n and l
     * @return diffuse value before scaling the light intensity
     */
    private double calcDiffusive(double kd, double nl) {
        return kd*Math.abs(nl);
    }








/*
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0)
            { // sign(nl) == sign(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.subtract(n.scale(2*l.dotProduct(n)));
        return lightIntensity.scale(ks*Math.pow( Math.max(0,-v.dotProduct(r)),nShininess));

    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
    }
*/

}
