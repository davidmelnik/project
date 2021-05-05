package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight{

    private Vector direction;

    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction;
    }

    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double maximum = Math.max(0,this.direction.dotProduct(super.getL(p)));
        return super.getIntensity(p).scale(maximum);
    }
}
