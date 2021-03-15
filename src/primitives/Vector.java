package primitives;

public class Vector {
    private Point3D head;

    public Vector(Point3D head) {
        if(head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector 0 is illegal");
        this.head = head;
    }

    public Vector(Coordinate x, Coordinate y,Coordinate z) {
        Point3D tempHead= new Point3D(x,y,z);
        if(tempHead.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector 0 is illegal");
        this.head = tempHead;
    }

    public Vector(double x,double y,double z) {

        Point3D tempHead= new Point3D(x,y,z);
        if(tempHead.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector 0 is illegal");
        this.head = tempHead;
    }

    public Point3D getHead() {
        return head;
    }
}
