package renderer;

import geometries.Geometry;
import geometries.Intersectable;
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
            int gridResolution, countVoxel;

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
    public List<Intersectable.GeoPoint> findGeoIntersections(Ray ray){

        Point3D startPoint= ray.getP0();
        int X= (int) ((startPoint.getX()-begX)/cellSizeX);
        int Y= (int) ((startPoint.getY()-begY)/cellSizeY);
        int Z= (int) ((startPoint.getZ()-begZ)/cellSizeZ);

        int stepX= ray.getDir().getHead().getX()>0 ? 1:-1;
        int stepY= ray.getDir().getHead().getY()>0 ? 1:-1;
        int stepZ= ray.getDir().getHead().getZ()>0 ? 1:-1;


        double tDeltaX=0;//???????????????
        if(ray.getDir().getHead().getX() !=0)
            tDeltaX=cellSizeX/ (ray.getDir().getHead().getX());
        double tDeltaY=cellSizeY/ (ray.getDir().getHead().getY());
        double tDeltaZ=cellSizeZ/ (ray.getDir().getHead().getZ());

        double tMaxX= (X* cellSizeX + begX)-startPoint.getX();

        list= null;
        do {
            if(tMaxX < tMaxY) {
                if(tMaxX < tMaxZ) {
                    X= X + stepX;
                    if(X == justOutX)
                        return(null); /* outside grid */
                    tMaxX= tMaxX + tDeltaX;
                } else {
                    Z= Z + stepZ;
                    if(Z == justOutZ)
                        return(null);
                    tMaxZ= tMaxZ + tDeltaZ;
                }
            } else {
                if(tMaxY < tMaxZ) {
                    Y= Y + stepY;
                    if(Y == justOutY)
                        return(null);
                    tMaxY= tMaxY + tDeltaY;
                } else {
                    Z= Z + stepZ;
                    if(Z == justOutZ)
                        return(null);
                    tMaxZ= tMaxZ + tDeltaZ;
                }
            }
            list= ObjectList[X][Y][Z];
        } while(list == null);
        return(list);

    }

}
