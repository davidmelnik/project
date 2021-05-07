package elements;

import primitives.Color;

abstract class Light {

    private Color intensity;

    protected Light(Color intensity) {

        this.intensity = intensity;
    }

    /**
     *
     * @return color of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
