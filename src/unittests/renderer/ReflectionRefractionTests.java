/**
 * 
 */
package unittests.renderer;

import geometries.*;
import org.junit.Test;

import elements.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( //
				new Sphere( new Point3D(0, 0, -50),50) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere( new Point3D(0, 0, -50),25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere( new Point3D(-950, -900, -1000),400) //
						.setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
				new Sphere( new Point3D(-950, -900, -1000),200) //
						.setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setKr(1)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setKr(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere( new Point3D(60, 50, -50),30) //
						.setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
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
	public void OurReflectionRefractionTest() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Polygon(new Point3D(-100,100,100),new Point3D(0,100,-100),
						new Point3D(0,-100,-100),new Point3D(-100,-100,100))//
						.setEmission(new Color(java.awt.Color.DARK_GRAY))//
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7)),
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
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.1))

		);

		scene.lights.add(new SpotLight(new Color(java.awt.Color.white), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
				.setKl(0.00004).setKq(0.0000006));
		scene.lights.add(new DirectionalLight(new Color(java.awt.Color.orange),new Vector(0,-1,-1)));

		ImageWriter imageWriter = new ImageWriter("OurReflectionRefraction", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();


	}


	/**
	 * move and turn the camera for the image of the mirror with a tube and two spheres
	 */
	@Test
	public void turnOurReflectionRefractionTest() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Polygon(new Point3D(-100,100,100),new Point3D(0,100,-100),
						new Point3D(0,-100,-100),new Point3D(-100,-100,100))//
						.setEmission(new Color(java.awt.Color.DARK_GRAY))//
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.7)),
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
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.1))

		);


		scene.lights.add(new SpotLight(new Color(java.awt.Color.white), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
				.setKl(0.00004).setKq(0.0000006));
		scene.lights.add(new DirectionalLight(new Color(java.awt.Color.orange),new Vector(0,-1,-1)));


		//first image
		camera.setP0(new Point3D(20, 30, 950)).turnAngle(new Point3D(50,20,-50),0.5*Math.PI);

		ImageWriter imageWriter = new ImageWriter("turnOurReflectionRefraction1", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();

		//second image
		camera.setP0(new Point3D(-40, 50, 950)).turnAngle(new Point3D(-24,-30,10),-0.5*Math.PI);

		imageWriter = new ImageWriter("turnOurReflectionRefraction2", 600, 600);
		render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();

		//third image
		camera.setP0(new Point3D(45, -30, 950)).turnAngle(new Point3D(-50,0,0),Math.PI);

		imageWriter = new ImageWriter("turnOurReflectionRefraction3", 600, 600);
		render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();


		//fourth image
		camera.setP0(new Point3D(-15, 60, 950)).turnAngle(new Point3D(50,20,-40),0.25*Math.PI);

		imageWriter = new ImageWriter("turnOurReflectionRefraction4", 600, 600);
		render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
}