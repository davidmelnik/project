package unittests;

import org.junit.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void writeToImage() {
        ImageWriter imageWriter= new ImageWriter("gridTest",800,500);
        for (int i=0; i<800; i++)
            for (int j=0;j<500;j++)
                imageWriter.writePixel(i,j, new Color(java.awt.Color.blue));

        for (int i=0; i<800; i+=800/16)
            for (int j=0;j<500;j++)
                imageWriter.writePixel(i,j, new Color(java.awt.Color.orange));


        for (int i=0; i<800; i++)
            for (int j=0;j<500;j+=500/10)
                imageWriter.writePixel(i,j, new Color(java.awt.Color.orange));

        imageWriter.writeToImage();
    }
}