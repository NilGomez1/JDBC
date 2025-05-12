package monlau.dao;

import monlau.model.Producto;

import java.sql.*;

public class ProductoDAOImpl implements ProductoDAO {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/producto?useSSL=false";
    static final String DB_USR = "root";
    static final String DB_PWD = "";

    private void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR: No se pudo cargar el driver JDBC de MySQL.");
            ex.printStackTrace();
        }
    }

    @Override
    public void insert(Producto producto) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            String sql = "INSERT INTO productos (id, nombre, precio) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, producto.getId());
                ps.setString(2, producto.getNombre());
                ps.setDouble(3, producto.getPrecio());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public Producto read(Integer id) {
        Connection conn = null;
        Producto prod = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            String sql = "SELECT * FROM productos WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        prod = new Producto(id, rs.getString("nombre"), rs.getDouble("precio"));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return prod;
    }

    @Override
    public void update(Producto producto) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            String sql = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, producto.getNombre());
                ps.setDouble(2, producto.getPrecio());
                ps.setInt(3, producto.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Producto producto) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            String sql = "DELETE FROM productos WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, producto.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
