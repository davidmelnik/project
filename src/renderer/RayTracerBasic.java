package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase{

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    Voxeles voxeles;
    boolean voxelOn;



    public RayTracerBasic(Scene scene) {
        super(scene);
        voxelOn=false;
    }

    public Voxeles getVoxeles() {
        return voxeles;
    }

    public RayTracerBasic setVoxeles(Voxeles voxeles) {
        this.voxeles = voxeles;
        return this;
    }

    public boolean isVoxelOn() {
        return voxelOn;
    }

    public RayTracerBase setVoxelOn(boolean voxelOn) {
        if (voxelOn && voxeles == null) {
            throw new IllegalArgumentException("the voxel is null");
        }
        this.voxelOn = voxelOn;
        return this;
    }

    /**
     *
     * @param light
     * @param l vector from light to point
     * @param n geometry normal
     * @param geopoint
     * @return level of light passing through
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection= l.scale(-1); // from point to light source
        Ray lightRay= new Ray(geopoint.point, lightDirection, n);
        double lightDistance= light.getDistance(geopoint.point);
        var intersections = voxeles.findGeoIntersections(lightRay,true);
        if (intersections == null) return 1.0;
        double ktr= 1.0;
        for (GeoPoint gp: intersections) {
            if (alignZero(gp.point.distance(geopoint.point) -lightDistance) <= 0) {
                ktr*= gp.geometry.getMaterial().kT;
                if (ktr< MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections;
        if(isVoxelOn()) {
            intersections = voxeles.findGeoIntersections(ray,false);
        }
        else
            intersections = scene.geometries.findGeoIntersections(ray);
        GeoPoint closestPoint = ray.getClosestGeoPoint(intersections);
        return closestPoint== null ? scene.background: calcColor(closestPoint, ray);
    }




    /**
     *
     * @param geopoint
     * @param ray
     * @return color at this pixel
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * recursive calculate color include global effects
      * @param intersection
     * @param ray
     * @param level recursion level
     * @param k recursive light level
     * @return color at point
     */
    private Color calcColor(GeoPoint intersection,Ray ray, int level, double k) {
        Color color= intersection.geometry.getEmmission();
        color = color.add(calcLocalEffects(intersection, ray,k));
        return 1 == level ?color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }


    /**
     *
     * @param geopoint
     * @param ray
     * @param level recursion level
     * @param k recursive light level
     * @return color from the refactored and reflected rays
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
        Color color= Color.BLACK;
        Material material= geopoint.geometry.getMaterial();

        Vector normal=geopoint.getNormal();

        double b =normal.dotProduct(ray.getDir());
        if (normal.dotProduct(ray.getDir())>0)
            normal=normal.scale(-1);
        Vector oppositeNormal=normal.scale(-1);


        double kr= material.kR, kkr= k * kr;
        if (kkr> MIN_CALC_COLOR_K) {

            Ray reflectedRay= constructReflectedRay(geopoint.getNormal(), geopoint.point, ray);

            //if the material isn't glossy construct a single ray
            if (material.kG==1){
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);

                if (reflectedPoint!=null)
                    color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
            //else construct a beam of rays and calculate every single ray
            else {
                List<Ray> rayList = reflectedRay.getBeamOfRays(material.kG, normal);
                Color beamColor = new Color(Color.BLACK);
                for (Ray glossyRay : rayList) {

                    GeoPoint reflectedPoint = findClosestIntersection(glossyRay);

                    if (reflectedPoint != null)
                        beamColor = beamColor.add(calcColor(reflectedPoint, glossyRay, level - 1, kkr));

                }
                color = color.add((beamColor).scale(kr / Ray.getNumberRays()));
            }
        }
        double kt = material.kT, kkt= k * kt;
        if (kkt> MIN_CALC_COLOR_K) {
            Ray refractedRay= constructRefractedRay(geopoint.getNormal(), geopoint.point, ray);
            //if the material isn't glossy construct a single ray
            if (material.kB==1){
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);

                if (refractedPoint!=null)
                   color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
            //else construct a beam of rays and calculate every single ray
            else {
                List<Ray> rayList = refractedRay.getBeamOfRays(material.kB, oppositeNormal);
                Color beamColor=new Color(Color.BLACK);
                for (Ray blurryRay : rayList) {

                    GeoPoint refractedPoint = findClosestIntersection(blurryRay);

                    if (refractedPoint != null)
                        beamColor=beamColor.add(calcColor(refractedPoint, blurryRay, level - 1, kkt));

                }
                color = color.add((beamColor).scale(kt/Ray.getNumberRays()));
            }

        }
        return color;
    }

    /**
     * construct a Refracted Ray
     * @param normal
     * @param point
     * @param ray
     * @return Refracted Ray
     */
    private Ray constructRefractedRay(Vector normal, Point3D point, Ray ray) {
        return new Ray(point, ray.getDir(),normal);
    }

    /**
     * construct Reflected Ray
     * @param normal
     * @param point
     * @param ray
     * @return Reflected Ray
     */
    private Ray constructReflectedRay(Vector normal, Point3D point, Ray ray) {
        return new Ray(point, ray.getDir().subtract(normal.scale(2*normal.dotProduct(ray.getDir()))),normal);
    }

    /**
     * find closest intersection with the ray
     * @param ray
     * @return Closest Intersection
     */
    private GeoPoint findClosestIntersection(Ray ray){
        if (isVoxelOn())
            return ray.getClosestGeoPoint(voxeles.findGeoIntersections(ray,false));

        return ray.getClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     *
     * @param intersection point of intersection of ray and geometry
     * @param ray that goes through pixel
     * @return color after local effects at intersection
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0)
            { // sign(nl) == sign(nv)
                double ktr= transparency(lightSource, l, n, intersection);
                if ( ktr* k > MIN_CALC_COLOR_K){
                //if (unshaded(lightSource,l,n, intersection)) {
                Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);

                // first add effects values and then scale the color in order to save scaling time
                color = color.add(lightIntensity.scale(
                        calcDiffusive(kd, nl) +
                                calcSpecular(ks, l, n, v, nShininess, nl)));
            }
            }
        }
        return color;
    }

    /**
     *
     * @param ks factor
     * @param l light ray
     * @param n normal
     * @param v camera ray
     * @param nShininess factor
     * @param nl dot product of n and l
     * @return specular value before scaling the light intensity
     */
    private double calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, double nl) {
        Vector r=l.subtract(n.scale(2*nl));
        return ks*Math.pow( Math.max(0,-v.dotProduct(r)),nShininess);

    }

    /**
     *
     * @param kd factor
     * @param nl dot product of n and l
     * @return diffuse value before scaling the light intensity
     */
    private double calcDiffusive(double kd, double nl) {
        return kd*Math.abs(nl);
    }










}
