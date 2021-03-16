package primitives;

public class Ray {
    private Point3D p0;
    private Vector dir;

    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir;
    }
}
