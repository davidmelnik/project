package primitives;

public class Material {
    public double kD;
    public double kS;
    public int nShininess;

    public Material() {
        this.kD=0;
        this.kS=0;
        this.nShininess=0;
    }

    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public double getKd() {
        return kD;
    }

    public double getKs() {
        return kS;
    }

    public int getnShininess() {
        return nShininess;
    }
}
