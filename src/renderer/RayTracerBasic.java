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

    private Color calcColor(Point3D point){
        if(point == null)
            return scene.background;
        return new Color(java.awt.Color.BLUE);
    }



}
