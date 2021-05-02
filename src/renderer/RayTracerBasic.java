package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return calcColor(ray.getClosestGeoPoint(scene.geometries.findGeoIntersections(ray)));
    }

    /**
     * calculate the color of a point
     * @param point
     * @return point's color
     */
    private Color calcColor(GeoPoint point){
        //no intersection
        if(point == null)
            return scene.background;

        return scene.ambientLight.getIntensity().add(point.geometry.getEmmission());

    }



}
