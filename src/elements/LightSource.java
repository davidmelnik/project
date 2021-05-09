package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface LightSource- build interface to all light except
 * ambient light
 */
public interface LightSource {
    /**
     *
     * @param p
     * @return Intensity
     */
    public Color getIntensity(Point3D p);

    /**
     *
     * @param p
     * @return vector of light direction
     */
    public Vector getL(Point3D p);

    /**
     *
     * @param point
     * @return distance from light source
     */
    double getDistance(Point3D point);

}
