package renderer;

import elements.Camera;
import primitives.Color;
import scene.Scene;

import java.util.MissingResourceException;

public class Render
{
    private Scene scene;
    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase  rayTracerBase;

    public void renderImage(){
        if(this.scene==null || this.camera==null || this.imageWriter==null || this.rayTracerBase==null)
            throw new MissingResourceException("missing builder","Render","02");

        /**
         * traces all the rays for each pixel to create an image
         */
        for(int i=0; i<imageWriter.getNx();i++){
            for(int j=0; j<imageWriter.getNy();j++){
                imageWriter.writePixel(j,i,rayTracerBase.traceRay
                        (camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j,i)));
            }
        }




    }

    /**
     * prints a grid on the view plane
     * @param interval between rows and columns
     * @param color grid color
     */
    public void printGrid(int interval, Color color){
        if(this.imageWriter == null)
            throw new MissingResourceException("missing builder to image writer","Render","03");


        for (int i=0; i<this.imageWriter.getNy(); i+=interval)
            for (int j=0;j<this.imageWriter.getNx();j++)
                imageWriter.writePixel(i,j, color);

        for (int i=0; i<this.imageWriter.getNy(); i++)
            for (int j=0;j<this.imageWriter.getNx();j+=interval)
                imageWriter.writePixel(i,j, color);

    }
    public void writeToImage(){
        if(this.imageWriter == null)
            throw new MissingResourceException("missing builder to image writer","Render","03");
        imageWriter.writeToImage();
    }




    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }
}
