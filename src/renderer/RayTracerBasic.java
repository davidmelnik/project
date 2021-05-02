package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return calcColor(ray.findClosestPoint(scene.geometries.findIntersections(ray)));
    }

    /**
     * calculate the color of a point
     * @param point
     * @return point's color
     */
    private Color calcColor(Point3D point){
        //no intersection
        if(point == null)
            return scene.background;

        return scene.ambientLight.getIntensity();
    }



}
