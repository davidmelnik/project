package project;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class project1 {

    private Scene scene = new Scene("Test scene");


    /**
     * a mirror  with a tube and two spheres
     */
    @Test
    public void projectTest1() {
        Camera camera = new Camera(new Point3D(1000, 1000, 500), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setP0(new Point3D(1000, 1000, 500))
                .turnAngle(new Point3D(0,0,0),-0.75*Math.PI)//
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.15));

        scene.geometries.add( //
                new Plane(new Point3D(0,0,0),new Vector(0,1,0)) // wall
                .setEmission(new Color(207,212,255))
                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0,0,0),new Vector(1,0,0)) // wall
                        .setEmission(new Color(207,212,255))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0,0,0),new Vector(0,0,1)) // flor
                        .setEmission(new Color(94,74,24)),
                new Polygon(new Point3D(1,50,50),new Point3D(1,150,50),
                        new Point3D(1,150,300),new Point3D(1,50,300))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7)),
                new Cylinder(new Ray(new Point3D(100,130,0),new Vector(0,0,1)),5,100)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Cylinder(new Ray(new Point3D(200,130,0),new Vector(0,0,1)),5,100)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Cylinder(new Ray(new Point3D(200,230,0),new Vector(0,0,1)),5,100)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Cylinder(new Ray(new Point3D(100,230,0),new Vector(0,0,1)),5,100)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Polygon (new Point3D(90,120,100),new Point3D(210,120,100),new Point3D(210,240,100),new Point3D(90,240,100))
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30))/*,
                new Sphere(new Point3D(50,20,-50),30)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Polygon(new Point3D(-20,30,500),new Point3D(20,30,500),
                        new Point3D(20,15,500),new Point3D(-20,15,500))
                        .setEmission(new Color(0,30,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90).setKt(2)),
                new Tube(new Ray(new Point3D(50,20,-50),new Vector(0,0,1)),10)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.9).setKs(0.7).setShininess(30).setKt(0.3)),
                new Sphere(new Point3D(-24,-30,10),15)
                        .setEmission(new Color(100, 100, 150)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.1))*/

        );

        //new Polygon (new Point3D())

        scene.lights.add(new SpotLight(new Color(java.awt.Color.white), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                .setKl(0.00004).setKq(0.0000006));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.orange),new Vector(0,-1,-1)));

        ImageWriter imageWriter = new ImageWriter("project1", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene))
                .setMultithreading(3);

        render.renderImage();
        render.writeToImage();


    }
}
