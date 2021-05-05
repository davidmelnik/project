package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

public class PointLight  extends Light implements LightSource{
    private Point3D position;
    private double kC;
    private double kL;
    private double kQ;

    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 0;
        this.kL = 0;
        this.kQ = 0;
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distance = this.position.distance(p);
        double dominator =kC +kL*distance+kQ*distance*distance;
        if(isZero(dominator)){
            throw new IllegalArgumentException("the dominator =0");
        }
        return super.getIntensity().scale(1/dominator);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(this.position).normalized();
    }
}
