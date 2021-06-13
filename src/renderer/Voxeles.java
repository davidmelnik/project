package renderer;

import geometries.Geometry;
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

    public void InitializeVoxel(){
        cell= new LinkedList[countVoxelX][countVoxelY][countVoxelZ];
        List<Geometry>  list= scene.geometries.FindAllGeometries();
        for (Geometry geometry:list)
        {
            int cellMinX= (int) ((geometry.getMinX()-begX)/cellSizeX);
            int cellMinY= (int) ((geometry.getMinY()-begY)/cellSizeY);
            int cellMinZ= (int) ((geometry.getMinZ()-begZ)/cellSizeZ);
            int cellMaxX= (int) ((geometry.getMaxX()-begX)/cellSizeX);
            int cellMaxY= (int) ((geometry.getMaxY()-begY)/cellSizeY);
            int cellMaxZ= (int) ((geometry.getMaxZ()-begZ)/cellSizeZ);


            for (int x = cellMinX; x <= cellMaxX; x++)
                for (int y = cellMinY; y <= cellMaxY; y++)
                    for (int z = cellMinZ; z <= cellMaxZ; z++) {
                        if(cell[x][y][z] ==null)
                            cell[x][y][z]= new LinkedList<>();
                        cell[x][y][z].add(geometry);
                    }
        }
    }

    private Point3D findStartPoint(Ray ray){
        Point3D point =ray.getP0();
        //if ray in voxel
        if(point.getX() >=begX && point.getX()< endX
        &&point.getY()>=begY && point.getY()< endY
        && point.getZ()>= begZ && point.getZ()<endZ)
            return point;

        double min_t=Double.POSITIVE_INFINITY,current;
        if(!isZero(ray.getDir().getHead().getX())) {
            current = (begX - point.getX()) / ray.getDir().getHead().getX();
            if (current > 0 && current < min_t)
                min_t = current;
            current = (endX - point.getX()) / ray.getDir().getHead().getX();
            if (current > 0 && current < min_t)
                min_t = current;
        }

        if(!isZero(ray.getDir().getHead().getY())) {
            current = (begY - point.getY()) / ray.getDir().getHead().getY();
            if (current > 0 && current < min_t)
                min_t = current;
            current = (endY - point.getY()) / ray.getDir().getHead().getY();
            if (current > 0 && current < min_t)
                min_t = current;
        }

        if(!isZero(ray.getDir().getHead().getZ())) {
            current = (begZ - point.getZ()) / ray.getDir().getHead().getZ();
            if (current > 0 && current < min_t)
                min_t = current;
            current = (endZ - point.getZ()) / ray.getDir().getHead().getZ();
            if (current > 0 && current < min_t)
                min_t = current;
        }
        return ray.getPoint(min_t);

    }

    public List<GeoPoint> findGeoIntersections(Ray ray){

        Point3D startPoint= findStartPoint(ray);
        int X= (int) ((startPoint.getX()-begX)/cellSizeX);
        int Y= (int) ((startPoint.getY()-begY)/cellSizeY);
        int Z= (int) ((startPoint.getZ()-begZ)/cellSizeZ);

        int stepX=0,stepY=0,stepZ=0;
        double tDeltaX=0, tDeltaY=0,tDeltaZ=0,tMaxX,tMaxY,tMaxZ;
        if(ray.getDir().getHead().getX() !=0){
            stepX= ray.getDir().getHead().getX()>0 ? 1:-1;
            tDeltaX=stepX* cellSizeX/ (ray.getDir().getHead().getX());
            tMaxX= stepX*(((X+(stepX==1? 1:0))* cellSizeX  + begX)-startPoint.getX())/tDeltaX;
        }
        else
            tMaxX=Double.POSITIVE_INFINITY;

        if(ray.getDir().getHead().getY() !=0){
            stepY= ray.getDir().getHead().getY()>0 ? 1:-1;
            tDeltaY=stepY* cellSizeY/ (ray.getDir().getHead().getY());
            tMaxY= stepY*(((Y+(stepY==1? 1:0))* cellSizeY  + begY)-startPoint.getY())/tDeltaY;
        }
        else
            tMaxY=Double.POSITIVE_INFINITY;

        if(ray.getDir().getHead().getZ() !=0) {
            stepZ= ray.getDir().getHead().getZ()>0 ? 1:-1;
            tDeltaZ=stepZ* cellSizeZ/ (ray.getDir().getHead().getZ());
            tMaxZ= stepZ*(((Z+(stepZ==1? 1:0))* cellSizeZ  + begZ)-startPoint.getZ())/tDeltaZ;
        }
        else
            tMaxZ=Double.POSITIVE_INFINITY;



        List<GeoPoint> retList=null;
        do {
                if (tMaxX < tMaxY) {
                    if (tMaxX < tMaxZ) {
                        X = X + stepX;
                        if (X< 0 || X>=countVoxelX)
                            return null; /* outside grid */
                        tMaxX = tMaxX + tDeltaX;
                    } else {
                        Z = Z + stepZ;
                        if (Z< 0 || Z>=countVoxelZ)
                            return null;
                        tMaxZ = tMaxZ + tDeltaZ;
                    }
                } else {
                    if (tMaxY < tMaxZ) {
                        Y = Y + stepY;
                        if (Y< 0 || Y>=countVoxelY)
                            return null;
                        tMaxY = tMaxY + tDeltaY;
                    } else {
                        Z = Z + stepZ;
                        if (Z< 0 || Z>=countVoxelZ)
                            return null;
                        tMaxZ = tMaxZ + tDeltaZ;
                    }
                }
                if(cell[X][Y][Z] !=null)
                    retList= findGeoInVoxel(ray,X,Y,Z);
            } while (retList == null);

        return retList;

    }



    /**
     *
     * @param ray
     * @param X
     * @param Y
     * @param Z
     * @return list contain all the geopoint in this voxel, if not geopoint return null
     * we assume that list in voxel isn't null
     */
    private List<GeoPoint> findGeoInVoxel(Ray ray, int X,int Y, int Z){
        List<GeoPoint> retList = new LinkedList();
        List<Geometry> list =cell[X][Y][Z];
        double startX= begX +X*cellSizeX;
        double startY= begY +Y*cellSizeY;
        double startZ= begZ +Z*cellSizeZ;
        for (Geometry geometry: list
             ) {
            List<GeoPoint> listGeom=geometry.findGeoIntersections(ray);
            if(listGeom !=null) {
                for (GeoPoint geoPoint : listGeom
                ) {
                    Point3D point= geoPoint.point;
                    if(point.getX()>=startX  &&point.getX()<= startX+cellSizeX
                    &&point.getY()>= startY && point.getY()<=startY+cellSizeY
                    &&point.getZ()>=startZ && point.getZ()<=startZ+cellSizeZ)
                        retList.add(geoPoint);
                }
            }

        }
        if (retList.isEmpty())
            return null;
        return retList;
    }


}
