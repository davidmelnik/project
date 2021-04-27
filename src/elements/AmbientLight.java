package elements;

import primitives.Color;

public class AmbientLight {
    private Color intensity;

    public AmbientLight(Color iA, double kA) {
        if(kA<0)
            throw new IllegalArgumentException("kA can not be negative");
        this.intensity=iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
