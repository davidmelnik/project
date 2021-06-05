package project;

import elements.*;
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
                .setP0(new Point3D(2000, 2000, 1500))
                .turnAngle(new Point3D(0,0,0),0)//
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.15));

        scene.geometries.add( //
                new Plane(new Point3D(0,0,0),new Vector(0,1,0)) // wall
                .setEmission(new Color(207/2,212/2,255/2))
                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0,0,0),new Vector(1,0,0)) // wall
                        .setEmission(new Color(207/2,212/2,255/2))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0,0,0),new Vector(0,0,1)) // flor
                        .setEmission(new Color(94,74,24))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Polygon(new Point3D(1,100,50),new Point3D(1,300,50),//clear mirror
                        new Point3D(1,300,300),new Point3D(1,100,300))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7)),
                new Polygon(new Point3D(100,1,50),new Point3D(300,1,50),//blurry mirror
                        new Point3D(300,1,300),new Point3D(100,1,300))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.3)),
                new Cylinder(new Ray(new Point3D(100,130,0),new Vector(0,0,1)),5,100)//table leg1
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Cylinder(new Ray(new Point3D(200,130,0),new Vector(0,0,1)),5,100)//table leg2
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Cylinder(new Ray(new Point3D(200,230,0),new Vector(0,0,1)),5,100)//table leg3
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Cylinder(new Ray(new Point3D(100,230,0),new Vector(0,0,1)),5,100)//table leg4
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Polygon (new Point3D(90,120,100),new Point3D(210,120,100),new Point3D(210,240,100),new Point3D(90,240,100))//table surface
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Sphere(new Point3D(140,160,120),30)
                        .setEmission(new Color(java.awt.Color.DARK_GRAY)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.9).setkB(0.2)),
                new Sphere(new Point3D(-9,250,350),18)//light bolb
                        .setEmission(new Color(java.awt.Color.DARK_GRAY)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.1).setkB(0.1))/*
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

        scene.lights.add(new SpotLight(new Color(java.awt.Color.lightGray), new Point3D(100, 150, 5000), new Vector(0, 0, -1)) //
                .setKl(0.000004).setKq(0.00000006));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.DARK_GRAY),new Vector(0,-1,-1)));
        scene.lights.add(new PointLight(new Color (java.awt.Color.green), new Point3D(5,250,350))
                .setKl(0.000004).setKq(0.00000006));

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
