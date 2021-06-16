package unittests.renderer;
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
import renderer.Voxeles;
import scene.Scene;

import static org.junit.Assert.*;

public class BeamTests {


    private Scene scene = new Scene("Test scene");

    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);





    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void BlurryImage() {

        Geometry sphere = new Sphere(new Point3D(0, 0, -200), 50) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
        scene.geometries.add(sphere,
                new Polygon(new Point3D(100,20,0),new Point3D(5,20,0),new Point3D(5,-20,0),new Point3D(100,-20,0))
                        .setEmission(new Color(java.awt.Color.darkGray))
                        .setMaterial(new Material().setKt(0.99).setkB(0.4)),
                new Polygon(new Point3D(-100,200,0),new Point3D(-5,200,0),new Point3D(-5,-15,0),new Point3D(-100,-15,0))
                         .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKt(0.90).setkB(0.9))/*,
                new Plane(new Point3D(5,1,-400),new Vector(new Point3D(0,0,1)))
                        .setEmission(new Color(java.awt.Color.LIGHT_GRAY))*/
                );
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("BlurryImage", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * a mirror  with a tube and two spheres
     */
    @Test
    public void glossyTest() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Polygon(new Point3D(-100,50,100),new Point3D(0,50,-100),
                        new Point3D(0,-20,-100),new Point3D(-100,-20,100))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))//
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.1)),
                new Sphere(new Point3D(50,20,-50),30)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                /*new Polygon(new Point3D(-20,30,500),new Point3D(20,30,500),
                        new Point3D(20,15,500),new Point3D(-20,15,500))
                        .setEmission(new Color(0,30,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90).setKt(2)),*/
                new Tube(new Ray(new Point3D(50,20,-50),new Vector(0,0,1)),10)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.9).setKs(0.7).setShininess(30).setKt(0.3))/*,
                new Sphere(new Point3D(-24,-30,10),15)
                        .setEmission(new Color(100, 100, 150)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.6).setKg(0.1))*/

        );

        scene.lights.add(new SpotLight(new Color(java.awt.Color.white), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                .setKl(0.00004).setKq(0.0000006));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.orange),new Vector(0,-1,-1)));

        ImageWriter imageWriter = new ImageWriter("GlossyImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene))
                .setMultithreading(3);

        render.renderImage();
        render.writeToImage();


    }

    /**
     * a mirror  with a tube and two spheres
     */
    @Test
    public void notGlossyTest() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Polygon(new Point3D(-100,50,100),new Point3D(0,50,-100),
                        new Point3D(0,-20,-100),new Point3D(-100,-20,100))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))//
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(1)),
                new Sphere(new Point3D(50,20,-50),30)
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                /*new Polygon(new Point3D(-20,30,500),new Point3D(20,30,500),
                        new Point3D(20,15,500),new Point3D(-20,15,500))
                        .setEmission(new Color(0,30,0))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90).setKt(2)),*/
                new Tube(new Ray(new Point3D(50,20,-50),new Vector(0,0,1)),10)
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.9).setKs(0.7).setShininess(30).setKt(0.3))/*,
                new Sphere(new Point3D(-24,-30,10),15)
                        .setEmission(new Color(100, 100, 150)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.6).setKg(0.1))*/

        );

        scene.lights.add(new SpotLight(new Color(java.awt.Color.white), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                .setKl(0.00004).setKq(0.0000006));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.orange),new Vector(0,-1,-1)));

        Voxeles voxeles= new Voxeles(scene,-150,-150,-150,150,150,150,50,50,50);
        ImageWriter imageWriter = new ImageWriter("NotGlossyImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene).setVoxeles(voxeles).setVoxelOn(true))
                .setMultithreading(3);

        render.renderImage();
        render.writeToImage();


    }
}