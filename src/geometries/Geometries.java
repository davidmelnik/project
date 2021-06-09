package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{




    private List<Intersectable> intersectList;


    public Geometries() {
        // we used an array list instead of a linked list, because
        // this action repeats itself, so the speed is more important
        // than the memory
        this.intersectList=new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries){
        this.intersectList =  new ArrayList<Intersectable>(List.of(geometries));
    }

    public void add(Intersectable... geometries){
        this.intersectList.addAll(List.of(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> points =new LinkedList();
        for (Intersectable item:this.intersectList)
        {
            List<GeoPoint> lst= item.findGeoIntersections(ray);
            if(lst!=null && !lst.isEmpty())
                points.addAll(lst);
        }
        if (points.isEmpty())
            return null;
        return points;
    }

    /**
     *
     * @return list contain all Geometry in recursion
     */
    public List<Geometry> FindAllGeometries() {
       return FindAllGeometries(intersectList);
    }

    /**
     *
     * @param geometries
     * @return list contain all Geometry in recursion
     */
    private List<Geometry> FindAllGeometries(List<Intersectable> geometries){
        List<Geometry> list =new LinkedList();
        for (Intersectable item:geometries){
            if(item instanceof Geometry)
                list.add((Geometry) item);
            else
                list.addAll(FindAllGeometries(((Geometries)item).intersectList));
        }
        return list;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> points =new LinkedList();
        for (Intersectable item:this.intersectList)
        {
            List<Point3D> lst= item.findIntersections(ray);
            if(lst!=null && !lst.isEmpty())
               points.addAll(lst);
        }
        if (points.isEmpty())
            return null;
        return points;
    }
}
