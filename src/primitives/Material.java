package primitives;

public class Material {
    public double kD; // defuse
    public double kS; // specular
    public double kT; // transparency
    public double kR; // reflection
    public double  kB; //blurry
    public double kG; // glossy

    public int nShininess;


    public Material() {
        this.kD=0;
        this.kS=0;
        this.kT=0;
        this.kR=0;
        this.kB=1;
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

    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setkB(double kB) {
        this.kB = kB;
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
