package primitives;

import java.util.Objects;

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

    public Vector subtract(Vector other){
        return head.subtract(other.getHead());

    }

    public Vector add(Vector other){
        return new Vector(head.add(other));

    }

    public Vector scale(double scalar){
        return new Vector(this.head.x.coord*scalar,this.head.y.coord*scalar,this.head.z.coord*scalar);
    }

    public double dotProduct(Vector other){
        return this.head.x.coord*other.getHead().x.coord
                +this.head.y.coord*other.getHead().y.coord
                +this.head.z.coord*other.getHead().z.coord;
    }
    public Vector crossProduct(Vector other){
        return new Vector(this.head.y.coord*other.head.z.coord-this.head.z.coord*other.head.y.coord,
                            this.head.z.coord*other.head.x.coord-this.head.x.coord*other.head.z.coord,
                            this.head.x.coord*other.head.y.coord-this.head.y.coord*other.head.x.coord
                        );
    }

    public double lengthSquared(){
        return this.head.distanceSquared(Point3D.ZERO);
    }

    public double length(){
        return this.head.distance(Point3D.ZERO);
    }

    public Vector normalize(){
        double vectorLength=this.length();
        this.head=new Point3D(this.head.x.coord/vectorLength,
                                this.head.y.coord/vectorLength,
                                this.head.z.coord/vectorLength);
        return this;
    }

    public Vector normalized(){
        return new Vector(getHead()).normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }


}
