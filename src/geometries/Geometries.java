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
