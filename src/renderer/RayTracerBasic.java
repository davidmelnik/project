package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return scene.background;
        GeoPoint closestPoint = ray.getClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }


    /**
     * calculate the color of a point
     * @param intersection point
     * @param ray pixel ray
     * @return point's color
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmmission())
                // add calculated light contribution from all light sources)
                .add(calcLocalEffects(intersection, ray));
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
                Color lightIntensity = lightSource.getIntensity(intersection.point);

                // first add effects values and then scale the color in order to save scaling time
                color = color.add(lightIntensity.scale(
                        calcDiffusive(kd, nl)+
                        calcSpecular(ks, l, n, v, nShininess, nl)));
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
