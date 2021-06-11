package renderer;

import geometries.Geometry;
import geometries.Intersectable.*;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import javax.naming.InitialContext;
import java.util.LinkedList;
import java.util.List;

public class Voxeles {
    private Scene scene;
    LinkedList<Geometry>[][][] cell;
    double begX=0, begY=0, begZ=0, cellSizeX,cellSizeY,cellSizeZ, gridSize;
            int gridResolution, countVoxelX,countVoxelY,countVoxelZ;

    public Voxeles(Scene scene) {
        this.scene = scene;
    }
    public void InitializeVoxel(){
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
    public List<GeoPoint> findGeoIntersections(Ray ray){

        Point3D startPoint= ray.getP0();
        int X= (int) ((startPoint.getX()-begX)/cellSizeX);
        int Y= (int) ((startPoint.getY()-begY)/cellSizeY);
        int Z= (int) ((startPoint.getZ()-begZ)/cellSizeZ);

        int stepX= ray.getDir().getHead().getX()>0 ? 1:-1;
        int stepY= ray.getDir().getHead().getY()>0 ? 1:-1;
        int stepZ= ray.getDir().getHead().getZ()>0 ? 1:-1;


        double tDeltaX=0;//???????????????
        if(ray.getDir().getHead().getX() !=0)
            tDeltaX=stepX* cellSizeX/ (ray.getDir().getHead().getX());
        double tDeltaY=stepY* cellSizeY/ (ray.getDir().getHead().getY());
        double tDeltaZ=stepZ* cellSizeZ/ (ray.getDir().getHead().getZ());

        double tMaxX= stepX*(((X+(stepX==1? 1:0))* cellSizeX  + begX)-startPoint.getX())/tDeltaX;
        double tMaxY= stepY*(((Y+(stepY==1? 1:0))* cellSizeY  + begY)-startPoint.getY())/tDeltaY;
        double tMaxZ= stepZ*(((Z+(stepZ==1? 1:0))* cellSizeZ  + begZ)-startPoint.getZ())/tDeltaZ;


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
        return retList;
    }


}
