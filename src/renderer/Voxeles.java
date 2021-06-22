package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Intersectable.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util.*;
import scene.Scene;

import javax.naming.InitialContext;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class Voxeles {
    private Scene scene;
    LinkedList<Geometry>[][][] cell;
    double begX=0, begY=0, begZ=0,endX,endY,endZ ,cellSizeX,cellSizeY,cellSizeZ;
            int  countVoxelX,countVoxelY,countVoxelZ;

    /**
     *
     * @param scene
     * @param begX
     * @param begY
     * @param begZ
     * @param endX end bigger than beg
     * @param endY end bigger than beg
     * @param endZ end bigger than beg
     * @param countVoxelX
     * @param countVoxelY
     * @param countVoxelZ
     */
    public Voxeles(Scene scene, double begX, double begY, double begZ, double endX, double endY, double endZ,

                   int countVoxelX, int countVoxelY, int countVoxelZ) {
        this.scene = scene;
        this.begX = begX;
        this.begY = begY;
        this.begZ = begZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.cellSizeX = (endX-begX)/countVoxelX;
        this.cellSizeY = (endY-begY)/countVoxelY;
        this.cellSizeZ = (endZ-begZ)/countVoxelZ;
        this.countVoxelX = countVoxelX;
        this.countVoxelY = countVoxelY;
        this.countVoxelZ = countVoxelZ;
        InitializeVoxel();
    }

    /**
     * initialize voxel with geometries in the scene by creating an AABB around the geometry
     */
    public void InitializeVoxel(){
        cell= new LinkedList[countVoxelX][countVoxelY][countVoxelZ];
        List<Geometry>  list= scene.geometries.FindAllGeometries();
        for (Geometry geometry:list)
        {
            int cellMinX= geometry.getMinX()<begX?0: (int) Math.floor((geometry.getMinX()-begX)/cellSizeX);
            int cellMinY= geometry.getMinY()<begY?0: (int) Math.floor((geometry.getMinY()-begY)/cellSizeY);
            int cellMinZ= geometry.getMinZ()<begZ?0:(int) Math.floor((geometry.getMinZ()-begZ)/cellSizeZ);
            int cellMaxX= geometry.getMaxX()>=endX ? countVoxelX-1 : (int) Math.floor((geometry.getMaxX()-begX)/cellSizeX);
            int cellMaxY= geometry.getMaxY()>=endY ? countVoxelY-1 :(int) Math.floor((geometry.getMaxY()-begY)/cellSizeY);
            int cellMaxZ= geometry.getMaxZ()>=endZ ? countVoxelZ-1 :(int) Math.floor((geometry.getMaxZ()-begZ)/cellSizeZ);


            for (int x = cellMinX; x <= cellMaxX; x++)
                for (int y = cellMinY; y <= cellMaxY; y++)
                    for (int z = cellMinZ; z <= cellMaxZ; z++) {
                        if(z==50)
                            System.out.println();
                        if(cell[x][y][z] ==null)
                            cell[x][y][z]= new LinkedList<>();
                        cell[x][y][z].add(geometry);
                    }
        }
    }

    /**
     * test if point is in the voxel
     * @param point
     * @return true if point is in the voxel
     */
    private boolean pointInVoxels(Point3D point){
        if(point.getX() >=begX && point.getX()< endX
                &&point.getY()>=begY && point.getY()< endY
                && point.getZ()>= begZ && point.getZ()<endZ)
            return true;
        return false;

    }

    /**
     *
     * @param ray
     * @return ray start point in voxels grid
     */
    private Point3D findStartPoint(Ray ray){
        Point3D point =ray.getP0();

        //if ray in voxel start from our point
        if(pointInVoxels( point))
            return point;

        //if the point is on the end border it will be out of the voxel,
        //and therefore we decreased the end border by DELTA
        final double  DELTA=0.0001;

        //test if the start point is on the X border
        if(!isZero(ray.getDir().getHead().getX())) {
            Point3D xPoint;
            double border = (ray.getDir().getHead().getX() > 0) ? begX : endX-DELTA;
            double currentT = (border - point.getX())/ray.getDir().getHead().getX();
            if (currentT > 0) {
                xPoint = ray.getPoint(currentT);
                if (pointInVoxels(xPoint))
                    return xPoint;
            }
        }

        //test if the start point is on the Y border
        if(!isZero(ray.getDir().getHead().getY())) {
            Point3D yPoint;
            double border = (ray.getDir().getHead().getY() > 0) ? begY : endY-DELTA;
            double currentT = (border - point.getY())/ray.getDir().getHead().getY();
            if (currentT > 0) {
                yPoint = ray.getPoint(currentT);
                if (pointInVoxels(yPoint))
                    return yPoint;
            }
        }

        //test if the start point is on the Z border
        if(!isZero(ray.getDir().getHead().getZ())) {
            Point3D zPoint;
            double border = (ray.getDir().getHead().getZ() > 0) ? begZ : endZ-DELTA;
            double currentT = (border - point.getZ())/ray.getDir().getHead().getZ();
            if (currentT > 0) {
                zPoint = ray.getPoint(currentT);
                if (pointInVoxels(zPoint))
                    return zPoint;
            }
        }

        //if there is no intersection return null
        return null;

    }

    /**
     * find intersection of ray and geometries using grid of voxels
     * @param ray
     * @param findAll if we want all intersections findAll should be True,
     *                but if we wnt only the closest intersection findAll should be False
     * @return ray intersections
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, boolean findAll){

        Point3D startPoint= findStartPoint(ray);
        if (startPoint==null)
            return null;

        //start point for each direction
        int X= (int) ((startPoint.getX()-begX)/cellSizeX);
        int Y= (int) ((startPoint.getY()-begY)/cellSizeY);
        int Z= (int) ((startPoint.getZ()-begZ)/cellSizeZ);

        //step determines whether the vector factors are positive or negative
        int stepX=0,stepY=0,stepZ=0;

        //Max is the distance by t values to the closest border of the voxel
        //Delta is the the distance by t values from the beginning of the voxel to the end
        double tDeltaX=0, tDeltaY=0,tDeltaZ=0,tMaxX,tMaxY,tMaxZ;


        if(ray.getDir().getHead().getX() !=0){
            stepX= ray.getDir().getHead().getX()>0 ? 1:-1;
            tDeltaX=stepX* cellSizeX/ (ray.getDir().getHead().getX());
            tMaxX= (((X+(stepX==1? 1:0))* cellSizeX  + begX)-startPoint.getX())/ray.getDir().getHead().getX();
        }
        //if there is no X value in vector we will never reach the X border
        else
            tMaxX=Double.POSITIVE_INFINITY;

        if(ray.getDir().getHead().getY() !=0){
            stepY= ray.getDir().getHead().getY()>0 ? 1:-1;
            tDeltaY=stepY* cellSizeY/ (ray.getDir().getHead().getY());
            tMaxY= (((Y+(stepY==1? 1:0))* cellSizeY  + begY)-startPoint.getY())/ray.getDir().getHead().getY();
        }
        //if there is no Y value in vector we will never reach the Y border
        else
            tMaxY=Double.POSITIVE_INFINITY;

        if(ray.getDir().getHead().getZ() !=0) {
            stepZ= ray.getDir().getHead().getZ()>0 ? 1:-1;
            tDeltaZ=stepZ* cellSizeZ/ (ray.getDir().getHead().getZ());
            tMaxZ= (((Z+(stepZ==1? 1:0))* cellSizeZ  + begZ)-startPoint.getZ())/ray.getDir().getHead().getZ();
        }
        //if there is no Z value in vector we will never reach the Z border
        else
            tMaxZ=Double.POSITIVE_INFINITY;


        //list of intersections
        List<GeoPoint> retList=null;

        //if there are geometries in the voxel add the intersections to retList
        if(cell[X][Y][Z] !=null) {
            List<GeoPoint> geoPointList=findGeoInVoxel(ray, X, Y, Z,findAll);
            if (geoPointList!=null) {
                if (retList == null)
                    retList = geoPointList;
                else
                    retList.addAll(geoPointList);
            }
        }

        //continue going over all the voxels until you find an intersection
        while (findAll || retList == null){
                if (tMaxX < tMaxY) {
                    if (tMaxX < tMaxZ) {
                        X = X + stepX;
                        if (X< 0 || X>=countVoxelX)
                           break; /* outside grid */
                        tMaxX = tMaxX + tDeltaX;
                    } else {
                        Z = Z + stepZ;
                        if (Z< 0 || Z>=countVoxelZ)
                            break;
                        tMaxZ = tMaxZ + tDeltaZ;
                    }
                } else {
                    if (tMaxY < tMaxZ) {
                        Y = Y + stepY;
                        if (Y< 0 || Y>=countVoxelY)
                            break;
                        tMaxY = tMaxY + tDeltaY;
                    } else {
                        Z = Z + stepZ;
                        if (Z< 0 || Z>=countVoxelZ)
                            break;
                        tMaxZ = tMaxZ + tDeltaZ;
                    }
                }
                if(cell[X][Y][Z] !=null) {
                    List<GeoPoint> geoPointList=findGeoInVoxel(ray, X, Y, Z,findAll);
                    if (geoPointList!=null) {
                        if (retList == null)
                            retList = geoPointList;
                        else
                            retList.addAll(geoPointList);
                    }
                }
            }

        return retList;

    }



    /**
     *
     * @param ray
     * @param X
     * @param Y
     * @param Z
     * @return list contain all the geoPoint in this voxel, if not geoPoint return null
     * we assume that list in voxel isn't null
     */
    private List<GeoPoint> findGeoInVoxel(Ray ray, int X,int Y, int Z, boolean findAll){
       List<GeoPoint> retList = new LinkedList();
        List<Geometry> list =cell[X][Y][Z];
        double startX= begX +X*cellSizeX;
        double startY= begY +Y*cellSizeY;
        double startZ= begZ +Z*cellSizeZ;
        for (Geometry geometry: list) {
            List<GeoPoint> listGeom=geometry.findGeoIntersections(ray);
            if(listGeom !=null &&!listGeom.isEmpty()) {
                for (GeoPoint geoPoint : listGeom) {
                    Point3D point= geoPoint.point;
                    if(point.getX()>=startX  &&point.getX()< startX+cellSizeX
                    &&point.getY()>= startY && point.getY()<startY+cellSizeY
                    &&point.getZ()>=startZ && point.getZ()<startZ+cellSizeZ)
                        retList.add(geoPoint);
                }
            }

        }
        if (retList.isEmpty())
            return null;
        return retList;


    }


}
