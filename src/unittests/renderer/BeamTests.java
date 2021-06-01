package unittests.renderer;
import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
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
                        .setEmission(new Color(java.awt.Color.black))
                        .setMaterial(new Material().setKt(1).setkB(0.4)),
                new Polygon(new Point3D(-100,200,0),new Point3D(-5,200,0),new Point3D(-5,-15,0),new Point3D(-100,-15,0))
                         .setEmission(new Color(java.awt.Color.black))
                        .setMaterial(new Material().setKt(1).setkB(0.7))/*,
                new Plane(new Point3D(5,1,-400),new Vector(new Point3D(0,0,1)))
                        .setEmission(new Color(java.awt.Color.white))*/
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

}