package com.image.process;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {

    public static void main(String[] args) throws Exception {

        // read image from folder
        File input = new File("C:/Users/JCondori/Pictures/Valle Grande/Producción I/Escobar Rondinel.jpg");
//        File input = new File("C:/Users/JCondori/Pictures/SEM1/Benavente Casas.jpg");

        if (input.exists()) {

            BufferedImage img = Thumbnails.of(input).scale(1).asBufferedImage();

            int xs = (img.getWidth() / 2);
            int x = (img.getWidth() / 2) - 900;
            int y;

            for (y = 1; y < img.getHeight(); y++) {
                int rgba = img.getRGB(xs, y);
                Color col = new Color(rgba, true);
                int r = col.getRed();
                int g = col.getGreen();
                int b = col.getBlue();
                int a = col.getAlpha();
                if (r < 100 && g < 100 && b < 100) {
                    y = y - 150;
                    break;
                }
            }

            if (y < 0) {
                y = 0;
            }

            System.out.println(input.getName().substring(0, input.getName().length() - 4).toUpperCase());
            System.out.println(input.getParent());

            BufferedImage imgcortada = img.getSubimage(x, y, 1800, 1800);

            Thumbnails.of(imgcortada).forceSize(800, 800).toFile(new File("C:/Users/JCondori/Pictures/@/Demo.jpg"));

        } else {
            System.out.println("No esta");
        }

    }

}
