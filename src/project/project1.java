package project;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import renderer.Voxeles;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

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
                new Plane(new Point3D(0, 0, 0), new Vector(0, 1, 0)) // wall
                        .setEmission(new Color(207 / 2, 212 / 2, 255 / 2))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0, 0, 0), new Vector(1, 0, 0)) // wall
                        .setEmission(new Color(207 / 2, 212 / 2, 255 / 2))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1)) // flor
                        .setEmission(new Color(94, 74, 24))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Polygon(new Point3D(1,100,50),new Point3D(1,300,50),//clear mirror
                        new Point3D(1,300,300),new Point3D(1,100,300))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7)),

                new Polygon(new Point3D(100,1,100),new Point3D(150,1,100),//blurry mirror
                        new Point3D(150,1,250),new Point3D(100,1,250))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.5)),
                new Polygon(new Point3D(200,1,100),new Point3D(250,1,100),//blurry mirror
                        new Point3D(250,1,250),new Point3D(200,1,250))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.4)),
                new Polygon(new Point3D(300,1,100),new Point3D(350,1,100),//blurry mirror
                        new Point3D(350,1,250),new Point3D(300,1,250))//
                        .setEmission(new Color(java.awt.Color.DARK_GRAY))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.3))



        );


        int i=0,j=0;
        for(i=0; i<450;i+=200){
            for (j=0; j<450;j+=200) {
                int interval=20;
                //points for table surface
                Point3D p1 = new Point3D(50 + i, 50 + j, 50);
                Point3D p2 = new Point3D(50 +interval+ i, 50 + j, 50);
                Point3D p3 = new Point3D(50 +interval*3 +i, 50 + j, 50);
                Point3D p4 = new Point3D(50 +interval*4 + i, 50 + j, 50);
                Point3D p5 = new Point3D(50 +interval*4 + i, 50 +interval*4+ j, 50);
                Point3D p6 = new Point3D(50 +interval*3 + i, 50 +interval*4+ j, 50);
                Point3D p7 = new Point3D(50 +interval+ i, 50 +interval*4+ j, 50);
                Point3D p8 = new Point3D(50 + i, 50 +interval*4+ j, 50);
                Point3D p9 = new Point3D(50 +interval + i, 50 +interval+ j, 50);
                Point3D p10 = new Point3D(50 +interval + i, 50 +interval*3+ j, 50);
                Point3D p11 = new Point3D(50 +interval*3 + i, 50 +interval*3+ j, 50);
                Point3D p12 = new Point3D(50 +interval*3 + i, 50 +interval+ j, 50);

                //points for table legs
                Point3D p13 = new Point3D(50 +interval/2 + i, 50 +interval/2+ j, 0);
                Point3D p14 = new Point3D(50 +interval*3.5 + i, 50 +interval/2+ j, 0);
                Point3D p15 = new Point3D(50 +interval*3.5 + i, 50 +interval*3.5+ j, 0);
                Point3D p16 = new Point3D(50 +interval/2 + i, 50 +interval*3.5+ j, 0);





                scene.geometries.add(
                        new Cylinder(new Ray(p13,new Vector(0,0,1)),5,50)//table leg1
                                .setEmission(new Color(100, 20, 20)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                        new Cylinder(new Ray(p14,new Vector(0,0,1)),5,50)//table leg2
                                .setEmission(new Color(100, 20, 20)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                        new Cylinder(new Ray(p15,new Vector(0,0,1)),5,50)//table leg3
                                .setEmission(new Color(100, 20, 20)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                        new Cylinder(new Ray(p16,new Vector(0,0,1)),5,50)//table leg4
                                .setEmission(new Color(100, 20, 20)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),

                        new Polygon (p1,p8,p7,p2)//table surface
                                .setEmission(new Color(50, 10, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                        new Polygon (p2,p9,p12,p3)//table surface
                                .setEmission(new Color(50, 10, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                        new Polygon (p3,p6,p5,p4)//table surface
                                .setEmission(new Color(50, 10, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                        new Polygon (p11,p6,p7,p10)//table surface
                                .setEmission(new Color(50, 10, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                        new Polygon (p9,p10,p11,p12)//table surface
                                .setEmission(new Color(50, 10, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.7).setkB(0.3)));





            }
        }



        for(i=0; i<500;i+=20){
            for (j=0; j<1000;j+=20) {
                Point3D p1 = new Point3D(0 + i, -10 + j, 0.1);
                Point3D p2 = new Point3D(10 + i, 0 + j, 0.1);
                Point3D p3 = new Point3D(20 + i, -10 + j, 0.1);
                Point3D p4 = new Point3D(0 + i, 10 + j, 0.1);
                Point3D p5 = new Point3D(20 + i, 10 + j, 0.1);
                Point3D p6 = new Point3D(10 + i, 20 + j, 0.1);

                scene.geometries.add(
                        new Triangle(p1, p2, p4)
                                .setEmission(new Color(java.awt.Color.red))
                                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                        new Triangle(p3, p2, p5)
                                .setEmission(new Color(java.awt.Color.red))
                                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                        new Polygon(p2, p4, p6, p5)
                                .setEmission(new Color(java.awt.Color.green))
                                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)));


            }
        }


        scene.lights.add(new SpotLight(new Color(java.awt.Color.lightGray), new Point3D(100, 150, 5000), new Vector(0, 0, -1)) //
                .setKl(0.000004).setKq(0.00000006));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.DARK_GRAY),new Vector(0,-1,-1)));
        scene.lights.add(new PointLight(new Color (java.awt.Color.green), new Point3D(5,250,350))
                .setKl(0.04).setKq(0.006));

        Ray.setNumberRays(10);
        Voxeles voxeles= new Voxeles(scene,0,0,0,500,500,400,7,7,5);
        ImageWriter imageWriter = new ImageWriter("project1", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene).setVoxeles(voxeles).setVoxelOn(true))
                .setMultithreading(0).setDebugPrint();

        render.renderImage();
        render.writeToImage();


    }




        /**
         * a floor with tiles
         */
        @Test
        public void tables() {
            Camera camera = new Camera(new Point3D(1000, 1000, 500), new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setP0(new Point3D(2000, 2000, 1500))
                    .turnAngle(new Point3D(0, 0, 0), 0)//
                    .setViewPlaneSize(200, 200).setDistance(1000);

            scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.15));

            scene.geometries.add( //
                    new Plane(new Point3D(0, 0, 0), new Vector(0, 1, 0)) // wall
                            .setEmission(new Color(207 / 2, 212 / 2, 255 / 2))
                            .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                    new Plane(new Point3D(0, 0, 0), new Vector(1, 0, 0)) // wall
                            .setEmission(new Color(207 / 2, 212 / 2, 255 / 2))
                            .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                    new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1)) // flor
                            .setEmission(new Color(94, 74, 24))
                            .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                    new Polygon(new Point3D(1,100,50),new Point3D(1,300,50),//clear mirror
                            new Point3D(1,300,300),new Point3D(1,100,300))//
                            .setEmission(new Color(java.awt.Color.DARK_GRAY))
                            .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7)),

                    new Polygon(new Point3D(100,1,100),new Point3D(150,1,100),//blurry mirror
                            new Point3D(150,1,250),new Point3D(100,1,250))//
                            .setEmission(new Color(java.awt.Color.DARK_GRAY))
                            .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.5)),
                    new Polygon(new Point3D(200,1,100),new Point3D(250,1,100),//blurry mirror
                            new Point3D(250,1,250),new Point3D(200,1,250))//
                            .setEmission(new Color(java.awt.Color.DARK_GRAY))
                            .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.4)),
                    new Polygon(new Point3D(300,1,100),new Point3D(350,1,100),//blurry mirror
                            new Point3D(350,1,250),new Point3D(300,1,250))//
                            .setEmission(new Color(java.awt.Color.DARK_GRAY))
                            .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7).setKg(0.3))



                    );


            int i=0,j=0;
            for(i=0; i<450;i+=200){
                for (j=0; j<450;j+=200) {
                    int interval=20;
                    //points for table surface
                    Point3D p1 = new Point3D(50 + i, 50 + j, 50);
                    Point3D p2 = new Point3D(50 +interval+ i, 50 + j, 50);
                    Point3D p3 = new Point3D(50 +interval*3 +i, 50 + j, 50);
                    Point3D p4 = new Point3D(50 +interval*4 + i, 50 + j, 50);
                    Point3D p5 = new Point3D(50 +interval*4 + i, 50 +interval*4+ j, 50);
                    Point3D p6 = new Point3D(50 +interval*3 + i, 50 +interval*4+ j, 50);
                    Point3D p7 = new Point3D(50 +interval+ i, 50 +interval*4+ j, 50);
                    Point3D p8 = new Point3D(50 + i, 50 +interval*4+ j, 50);
                    Point3D p9 = new Point3D(50 +interval + i, 50 +interval+ j, 50);
                    Point3D p10 = new Point3D(50 +interval + i, 50 +interval*3+ j, 50);
                    Point3D p11 = new Point3D(50 +interval*3 + i, 50 +interval*3+ j, 50);
                    Point3D p12 = new Point3D(50 +interval*3 + i, 50 +interval+ j, 50);

                    //points for table legs
                    Point3D p13 = new Point3D(50 +interval/2 + i, 50 +interval/2+ j, 0);
                    Point3D p14 = new Point3D(50 +interval*3.5 + i, 50 +interval/2+ j, 0);
                    Point3D p15 = new Point3D(50 +interval*3.5 + i, 50 +interval*3.5+ j, 0);
                    Point3D p16 = new Point3D(50 +interval/2 + i, 50 +interval*3.5+ j, 0);





                    scene.geometries.add(
                            new Cylinder(new Ray(p13,new Vector(0,0,1)),5,50)//table leg1
                                    .setEmission(new Color(100, 20, 20)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                            new Cylinder(new Ray(p14,new Vector(0,0,1)),5,50)//table leg2
                                    .setEmission(new Color(100, 20, 20)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                            new Cylinder(new Ray(p15,new Vector(0,0,1)),5,50)//table leg3
                                    .setEmission(new Color(100, 20, 20)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                            new Cylinder(new Ray(p16,new Vector(0,0,1)),5,50)//table leg4
                                    .setEmission(new Color(100, 20, 20)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),

                            new Polygon (p1,p8,p7,p2)//table surface
                                    .setEmission(new Color(50, 10, 10)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                            new Polygon (p2,p9,p12,p3)//table surface
                                .setEmission(new Color(50, 10, 10)) //
                                .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                            new Polygon (p3,p6,p5,p4)//table surface
                                    .setEmission(new Color(50, 10, 10)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                            new Polygon (p11,p6,p7,p10)//table surface
                                    .setEmission(new Color(50, 10, 10)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0)),
                            new Polygon (p9,p10,p11,p12)//table surface
                                    .setEmission(new Color(50, 10, 10)) //
                                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.7).setkB(0.3)));





                }
            }


            //int i,j;
            for(i=0; i<500;i+=20){
                for (j=0; j<1000;j+=20) {
                     Point3D p1 = new Point3D(0 + i, -10 + j, 0.1);
                    Point3D p2 = new Point3D(10 + i, 0 + j, 0.1);
                    Point3D p3 = new Point3D(20 + i, -10 + j, 0.1);
                    Point3D p4 = new Point3D(0 + i, 10 + j, 0.1);
                    Point3D p5 = new Point3D(20 + i, 10 + j, 0.1);
                    Point3D p6 = new Point3D(10 + i, 20 + j, 0.1);

                    scene.geometries.add(
                            new Triangle(p1, p2, p4)
                                    .setEmission(new Color(java.awt.Color.red))
                                    .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                            new Triangle(p3, p2, p5)
                                    .setEmission(new Color(java.awt.Color.red))
                                    .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                            new Polygon(p2, p4, p6, p5)
                                    .setEmission(new Color(java.awt.Color.green))
                                    .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)));


                }
            }
            scene.lights.add(new SpotLight(new Color(java.awt.Color.lightGray), new Point3D(100, 150, 5000), new Vector(0, 0, -1)) //
                    .setKl(0.000004).setKq(0.00000006));
            scene.lights.add(new DirectionalLight(new Color(java.awt.Color.DARK_GRAY), new Vector(0, -1, -1)));


            Ray.setNumberRays(3);
            Voxeles voxeles = new Voxeles(scene, 0, 0, 0, 500, 500, 400, 100, 100, 5);
            ImageWriter imageWriter = new ImageWriter("tables", 600, 600);
            Render render = new Render() //
                    .setImageWriter(imageWriter) //
                    .setCamera(camera) //
                    .setRayTracer(new RayTracerBasic(scene).setVoxeles(voxeles).setVoxelOn(true))
                    .setMultithreading(0).setDebugPrint();

            render.renderImage();
            render.writeToImage();
        }


    /**
     * tables
     */
    @Test
    public void tiles() {
        Camera camera = new Camera(new Point3D(1000, 1000, 500), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setP0(new Point3D(2000, 2000, 1500))
                .turnAngle(new Point3D(0, 0, 0), 0)//
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.15));

        scene.geometries.add( //
                new Plane(new Point3D(0, 0, 0), new Vector(0, 1, 0)) // wall
                        .setEmission(new Color(207 / 2, 212 / 2, 255 / 2))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0, 0, 0), new Vector(1, 0, 0)) // wall
                        .setEmission(new Color(207 / 2, 212 / 2, 255 / 2))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1)) // flor
                        .setEmission(new Color(94, 74, 24))
                        .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90))



        );


        int i,j;
        for(i=0; i<500;i+=20){
            for (j=0; j<1000;j+=20) {
                Point3D p1 = new Point3D(0 + i, -10 + j, 0.1);
                Point3D p2 = new Point3D(10 + i, 0 + j, 0.1);
                Point3D p3 = new Point3D(20 + i, -10 + j, 0.1);
                Point3D p4 = new Point3D(0 + i, 10 + j, 0.1);
                Point3D p5 = new Point3D(20 + i, 10 + j, 0.1);
                Point3D p6 = new Point3D(10 + i, 20 + j, 0.1);

                scene.geometries.add(
                        new Triangle(p1, p2, p4)
                                .setEmission(new Color(java.awt.Color.red))
                                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                        new Triangle(p3, p2, p5)
                                .setEmission(new Color(java.awt.Color.red))
                                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)),
                        new Polygon(p2, p4, p6, p5)
                                .setEmission(new Color(java.awt.Color.green))
                                .setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(90)));


            }
        }


        scene.lights.add(new SpotLight(new Color(java.awt.Color.lightGray), new Point3D(100, 150, 5000), new Vector(0, 0, -1)) //
                .setKl(0.000004).setKq(0.00000006));
        scene.lights.add(new DirectionalLight(new Color(java.awt.Color.DARK_GRAY), new Vector(0, -1, -1)));


        Ray.setNumberRays(50);
        Voxeles voxeles = new Voxeles(scene, 0, 0, 0, 500, 500, 400, 100, 100, 5);
        ImageWriter imageWriter = new ImageWriter("tiles", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene).setVoxeles(voxeles).setVoxelOn(false))
                .setMultithreading(0).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }
    }
