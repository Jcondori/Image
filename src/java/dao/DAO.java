package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class DAO {

    private Connection cn;

    public void Conectar() throws Exception {       //Metodo con los datos de acceso
        try {
            if (cn == null) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
//                cn = DriverManager.getConnection("jdbc:oracle:thin:@sistemas.vallegrande.edu.pe:1521:XE", "SJA", "sja2018");
                cn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SEMVG", "ADS2018");
            }
        } catch (ClassNotFoundException | SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay conexión a la base de datos"));
            throw e;
        }
    }

    public void Cerrar() throws SQLException {      //Cerrar la coneccion
        if (cn != null) {
            if (cn.isClosed() == false) {
                cn.close();
                cn = null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DAO dao = new DAO();
        dao.Conectar();
        if (dao.getCn() != null) {
            System.out.println("conectado");
        } else {
            System.out.println("error");
        }
    }

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
    
}
