package Services;

import dao.DAO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Alumno;
import net.coobird.thumbnailator.Thumbnails;

public class ImagesDBAula extends DAO {

    public static void main(String[] args) {
        try {
            ImagesDBAula dao = new ImagesDBAula();
            List<Alumno> lista = dao.listar();
            lista.forEach((alumno) -> {
                dao.copyImage(alumno);
            });
        } catch (Exception ex) {
            Logger.getLogger(ImagesDBAula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Alumno> listar() throws Exception {
        this.Conectar();
        List<Alumno> lista;
        ResultSet rs;
        try {
            String sql = "Select ALUMNO.COD_ALUM,INITCAP(ALUMNO.APE_ALUM) as nombre from alumno INNER JOIN GRUPO ON GRUPO.COD_ALUM = alumno.COD_ALUM WHERE GRUPO.COD_AULA = 1";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            rs = ps.executeQuery();
            lista = new ArrayList<>();
            Alumno model;
            while (rs.next()) {
                model = new Alumno();
                model.setDNI(rs.getString("COD_ALUM"));
                model.setNombres(rs.getString("nombre"));
                lista.add(model);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void copyImage(Alumno alumno) {
        try {
            File input = new File("C:/Users/JCondori/Pictures/Valle Grande/Sistemas V - copia/" + alumno.getNombres() + ".jpg");
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
                    if (r < 100 && g < 100 && b < 100) {
                        y = y - 150;
                        break;
                    }
                }

                if (y < 0) {
                    y = 0;
                }

                BufferedImage imgcortada = img.getSubimage(x, y, 1800, 1800);

                Thumbnails.of(imgcortada).forceSize(800, 800).toFile(new File("C:/Users/JCondori/Pictures/Valle Grande 2/Sistemas V/" + alumno.getDNI() + ".jpg"));
                input.delete();
            } else {
                System.out.println("No esta Foto -> " + alumno.getNombres());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
