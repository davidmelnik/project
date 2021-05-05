package elements;

import primitives.Color;

public class AmbientLight extends Light {

    /**
     *
     * @param iA
     * @param kA
     * build light iA*(kA)
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }


}
