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
     * @param point
     * @return point's color

    private Color calcColor(GeoPoint point){
        //no intersection
        if(point == null)
            return scene.background;

        return scene.ambientLight.getIntensity().add(point.geometry.getEmmission()).add();

    }
    */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmmission())
                // add calculated light contribution from all light sources)
                .add(calcLocalEffects(intersection, ray));
    }

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
            { // sign(nl) == sing(nv)
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
        //return Color.BLACK;
    }


}
