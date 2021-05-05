package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     *
     * @param ray
     * @return color of the pixel
     */
    public abstract Color traceRay(Ray ray);
}
