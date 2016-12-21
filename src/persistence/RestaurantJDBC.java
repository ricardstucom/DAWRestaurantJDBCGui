/*
 * Clase que se encarga de la persistencia en la BBDD
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Cocinero;
import model.Plato;

/**
 *
 * @author mfontana
 */
public class RestaurantJDBC {

    private Connection conexion;

    public RestaurantJDBC()  {
    }

    // Método que recibe un cocinero y lo inserta en la BBDD
    public void insertarCocinero(Cocinero c) throws SQLException {
        conectar();
        String insert = "insert into cocinero values (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getTelefono());
        ps.setString(3, c.getSexo());
        ps.setInt(4, c.getEdad());
        ps.setInt(5, c.getExperiencia());
        ps.setString(6, c.getEspecialidad());
        ps.executeUpdate();
        ps.close();
        desconectar();
    }
    
    // Método que recibe un plato y lo inserta en la BBDD
    public void insertarPlato(Plato p) throws SQLException {
        String insert = "insert into plato values (?, ?, ?, ?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getTipo());
        ps.setDouble(3, p.getPrecio());
        // Importante, en el cocinero sólo insertamos el nombre en la BBDD (relacional)
        ps.setString(4, p.getCocinero().getNombre());
        ps.executeUpdate();
        ps.close();
    }

    private void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String usr = "root";
        String pass = "root";
        conexion = DriverManager.getConnection(url, usr, pass);
    }

    private void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

}
