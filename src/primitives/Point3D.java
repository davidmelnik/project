package primitives;

import java.util.Objects;

public class Point3D {

    Coordinate x;
    Coordinate y;
    Coordinate z;

    //the point (0,0,0)
    public static Point3D ZERO =new Point3D(0,0,0);

    /**
     * constructs a point by using 3 coordinates
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
     public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * constructs a point by using 3 double values
     * @param x coordinate double value
     * @param y coordinate double value
     * @param z coordinate double value
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * subtracts a point from this point
     * doesn't change this point
     * @param vertex other point
     * @return a vector between the points
     */
    public Vector subtract(Point3D vertex) {
        return new Vector(x.coord- vertex.x.coord,y.coord- vertex.y.coord,z.coord- vertex.z.coord);
    }

    /**
     * adds a vector to the point
     * doesn't change this point
     * @param vector vector to add to the point
     * @return a vector
     */
    public Point3D add(Vector vector){
        return new Point3D(this.x.coord+vector.getHead().x.coord,
                this.y.coord+vector.getHead().y.coord,
                this.z.coord+vector.getHead().z.coord);
    }

    /**
     * calculates the squared distance between the points
     * @param vertex other point
     * @return squared distance
     */
    public double distanceSquared(Point3D vertex){
        return (this.x.coord-vertex.x.coord)*(this.x.coord-vertex.x.coord)+
                (this.y.coord-vertex.y.coord)*(this.y.coord-vertex.y.coord)+
                (this.z.coord-vertex.z.coord)*(this.z.coord-vertex.z.coord);
    }

    /**
     * calculates the distance between the points
     * @param vertex other point
     * @return distance
     */
    public double distance(Point3D vertex){
        return Math.sqrt(distanceSquared(vertex));
    }

    public double getX() {
        return x.coord;
    }

    public double getY() {
        return y.coord;
    }

    public double getZ() {
        return z.coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
