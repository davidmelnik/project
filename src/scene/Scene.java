package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background =Color.BLACK;
    public AmbientLight ambientLight =new AmbientLight(Color.BLACK,0);
    public Geometries geometries;
    public List<LightSource> lights;

    public Scene(String name) {
        this.name = name;
        this.geometries =new Geometries();
        lights= new LinkedList<LightSource>();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
