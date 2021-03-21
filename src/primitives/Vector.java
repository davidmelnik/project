package primitives;

import java.util.Objects;

public class Vector {
    private Point3D head;

    /**
     * constructs a vector by using the given head point
     * @param head head point
     */
    public Vector(Point3D head) {
        if(head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector 0 is illegal");
        this.head = head;
    }

    /**
     * constructs a vector by using the given head coordinates
     * @param x coordinate value
     * @param y coordinate value
     * @param z coordinate value
     */
    public Vector(Coordinate x, Coordinate y,Coordinate z) {
        Point3D tempHead= new Point3D(x,y,z);
        if(tempHead.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector 0 is illegal");
        this.head = tempHead;
    }

    /**
     *constructs a vector by using the given head coordinates double value
     * @param x coordinate double value
     * @param y coordinate double value
     * @param z coordinate double value
     */
    public Vector(double x,double y,double z) {

        Point3D tempHead= new Point3D(x,y,z);
        if(tempHead.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector 0 is illegal");
        this.head = tempHead;
    }


    public Point3D getHead() {
        return head;
    }

    /**
     * subtracts the other vector from this vector
     * @param other vector
     * @return new vector
     */
    public Vector subtract(Vector other){
        return head.subtract(other.getHead());

    }

    /**
     * adds the other vector from this vector
     * @param other vector
     * @return new vector
     */
    public Vector add(Vector other){
        return new Vector(head.add(other));

    }

    /**
     * multiply the vector by a scalar
     * @param scalar value which the vector is multiplied by
     * @return new vector
     */
    public Vector scale(double scalar){
        return new Vector(this.head.x.coord*scalar,this.head.y.coord*scalar,this.head.z.coord*scalar);
    }

    /**
     * multiply 2 vectors by using dot product
     * @param other vector
     * @return multiplication result
     */
    public double dotProduct(Vector other){
        return this.head.x.coord*other.getHead().x.coord
                +this.head.y.coord*other.getHead().y.coord
                +this.head.z.coord*other.getHead().z.coord;
    }

    /**
     * multiply 2 vectors by using cross product
     * @param other vector
     * @return multiplication result
     */
    public Vector crossProduct(Vector other){
        return new Vector(this.head.y.coord*other.head.z.coord-this.head.z.coord*other.head.y.coord,
                            this.head.z.coord*other.head.x.coord-this.head.x.coord*other.head.z.coord,
                            this.head.x.coord*other.head.y.coord-this.head.y.coord*other.head.x.coord
                        );
    }

    /**
     * @return squared length of the vector
     */
    public double lengthSquared(){
        return this.head.distanceSquared(Point3D.ZERO);
    }

    /**
     * @return vector's length
     */
    public double length(){
        return this.head.distance(Point3D.ZERO);
    }

    /**
     * normalize the vector and return the normalized vector
     * @return normalized vector
     */
    public Vector normalize(){
        double vectorLength=this.length();
        this.head=new Point3D(this.head.x.coord/vectorLength,
                                this.head.y.coord/vectorLength,
                                this.head.z.coord/vectorLength);
        return this;
    }

    /**
     * @return normalized vector
     * doesn't change this vector
     */
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

    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }
}
