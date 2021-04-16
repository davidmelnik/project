package primitives;

import java.util.Objects;

public class Ray {
    private Point3D p0;
    private Vector dir;

    /**
     * constructs a normalized ray
     * normalizes the given vector
     * @param p0 starting point
     * @param dir direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public Point3D getPoint(double t){
        if (Util.isZero(t))
            return this.p0;
        return this.p0.add(this.dir.scale(t));
    }
}
