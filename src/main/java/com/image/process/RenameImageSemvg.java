package com.image.process;

import com.image.dao.DAO;
import com.image.model.Model;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RenameImageSemvg extends DAO {

    private String FolderOrigin = "D:\\Imágenes\\Asistencias";
    private String FolderTarget = "D:\\Imágenes\\Asistencias_v2";

    public static void main(String[] args) throws Exception {
        RenameImageSemvg renameImageSemvg = new RenameImageSemvg();
        for (Model model : renameImageSemvg.listar()) {
            renameImageSemvg.renameImage(model);
        }
    }

    public void renameImage(Model model) {
        try {
            File input = new File(FolderOrigin + "\\" + model.getDNI() + ".jpg");
            if (input.exists()) {
                File output = new File(FolderTarget + "\\" + model.getApellidos() + " " + model.getNombres() + " - " + model.getDNI() + ".jpg");
                if (!input.renameTo(output)) {
                    System.out.println(model);
                }
            } else {
                System.out.println("No encontrado -> " + model.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(model);
        }
    }

    public List<Model> listar() throws Exception {
        this.Conectar();
        ResultSet rs;
        List<Model> list = new ArrayList();
        try {
            String sql = "SELECT distinct A.COD_ALUM,\n" +
                    "       A.NOM_ALUM,\n" +
                    "       A.APE_ALUM\n" +
                    "FROM ALUMNO A\n" +
                    "         INNER JOIN GRUPO G on A.COD_ALUM = G.COD_ALUM\n" +
                    "         INNER JOIN AULA AU on G.COD_AULA = AU.COD_AULA\n" +
                    "WHERE 1 = 1 --G.ESTGRU = 'A'\n" +
                    "  AND AU.COD_CAR IN (1, 20, 19, 18, 3, 2)";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Model(rs.getString("COD_ALUM"),
                        rs.getString("NOM_ALUM"),
                        rs.getString("APE_ALUM")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

}
