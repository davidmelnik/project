package primitives;

import java.util.Objects;

public class Point3D {

    Coordinate x;
    Coordinate y;
    Coordinate z;
    public static Point3D ZERO =new Point3D(0,0,0);

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }


    public Vector subtract(Point3D vertex) {
        return new Vector(x.coord- vertex.x.coord,y.coord- vertex.y.coord,z.coord- vertex.z.coord);
    }

    /**
     * don't change this point
     * @param vector
     * @return
     */
    public Point3D add(Vector vector){
        return new Point3D(this.x.coord+vector.getHead().x.coord,
                this.y.coord+vector.getHead().y.coord,
                this.z.coord+vector.getHead().z.coord);
    }
    public double distanceSquared(Point3D vertex){
        return (this.x.coord-vertex.x.coord)*(this.x.coord-vertex.x.coord)+
                (this.y.coord-vertex.y.coord)*(this.y.coord-vertex.y.coord)+
                (this.z.coord-vertex.z.coord)*(this.z.coord-vertex.z.coord);
    }

    public double distance(Point3D vertex){
        return Math.sqrt(distanceSquared(vertex));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }


}
