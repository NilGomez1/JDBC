package monlau;

import monlau.dao.ProductoDAO;
import monlau.dao.ProductoDAOImpl;
import monlau.model.Producto;

public class ProductoManager {
    public static void main(String[] args) {
        ProductoDAO productoDAO = new ProductoDAOImpl();

        // Insertar un nuevo producto
        Producto nuevoProducto = new Producto(200, "Pollo", 10.0);
        productoDAO.insert(nuevoProducto);
        System.out.println("Producto insertado: " + nuevoProducto);

        // Leer el producto con ID 200
        Producto productoLeido = productoDAO.read(200);
        if (productoLeido != null) {
            System.out.println("Producto leído: " + productoLeido);
        } else {
            System.out.println("Producto con ID 200 no encontrado.");
        }

        // Actualizar el producto
        if (productoLeido != null) {
            productoLeido.setNombre("Pollo Asado");
            productoLeido.setPrecio(12.5);
            productoDAO.update(productoLeido);
            Producto productoActualizado = productoDAO.read(200);
            System.out.println("Producto actualizado: " + productoActualizado);
        }

        // Eliminar el producto
        if (productoLeido != null) {
            productoDAO.delete(productoLeido);
            System.out.println("Producto eliminado.");
        }

        // Intentar leer de nuevo el producto
        Producto productoEliminado = productoDAO.read(200);
        if (productoEliminado == null) {
            System.out.println("Producto con ID 200 no se encuentra (eliminado).");
        } else {
            System.out.println("Error: el producto todavía existe: " + productoEliminado);
        }
    }
}
