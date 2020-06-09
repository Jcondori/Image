package com.image.process;

import dao.DAO;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageFolder extends DAO {

    public static void main(String[] args) throws Exception {
        Services.ImageFolder dao = new Services.ImageFolder();
        File dir = new File("C:/Users/JCondori/Pictures/Valle Grande/Valle Grande 2 - copia");
        String[] ficheros = dir.list();

        for (String fichero : ficheros) {
            dao.FormatImage(fichero, dir.getAbsolutePath());
        }
    }

    public String listar(String Apellido) throws Exception {
        this.Conectar();
        ResultSet rs;
        try {
            String sql = "SELECT COD_ALUM FROM SEMVG.ALUMNO WHERE APE_ALUM like ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Apellido);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("COD_ALUM");
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void FormatImage(String NombreArchivo, String RutaArchivo) throws Exception {
        try {
            String ruta = RutaArchivo.replace("/", "/") + "/" + NombreArchivo;
            File input = new File(ruta);
            if (input.exists()) {

                String DNIAlumno = listar(input.getName().substring(0, input.getName().length() - 4).toUpperCase());

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
                    if (r < 100 && g < 100 && b < 100) {
                        y = y - 150;
                        break;
                    }
                }

                if (y < 0) {
                    y = 0;
                }

                if (DNIAlumno != null) {
                    BufferedImage imgcortada = img.getSubimage(x, y, 1800, 1800);
                    Thumbnails.of(imgcortada).forceSize(800, 800).toFile(new File("C:/Users/JCondori/Pictures/Valle Grande 2/Libres/" + DNIAlumno + ".jpg"));
                    input.delete();
                } else {
                    System.out.println("No se encuentra en la base de datos -> " + (input.getName().substring(0, input.getName().length() - 4)));
                }
            } else {
                System.out.println("No esta Foto -> " + NombreArchivo);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
