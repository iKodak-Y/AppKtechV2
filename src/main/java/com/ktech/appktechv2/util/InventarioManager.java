package com.ktech.appktechv2.util;

import com.ktech.appktechv2.modelo.Producto;
import com.ktech.appktechv2.modelo.ProductoDAO;

public class InventarioManager {

    private ProductoDAO productoDAO;

    public InventarioManager() {
        this.productoDAO = new ProductoDAO();
    }

    /**
     * Actualiza el stock de un producto restando la cantidad vendida.
     *
     * @param idProducto El ID del producto a actualizar.
     * @param cantidad La cantidad vendida.
     * @return true si la actualización fue exitosa, false si no hay suficiente
     * stock.
     */
    public boolean actualizarStock(int idProducto, int cantidad) {
        // Obtener el producto por su ID
        Producto producto = productoDAO.obtenerPorId(idProducto);

        // Verificar si el producto existe y si hay suficiente stock
        if (producto == null) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe.");
        }

        if (producto.getStockActual() < cantidad) {
            return false; // No hay suficiente stock
        }

        // Calcular el nuevo stock
        int nuevoStock = producto.getStockActual() - cantidad;
        producto.setStockActual(nuevoStock);

        // Actualizar el producto en la base de datos
        return productoDAO.actualizar(producto);
    }

    /**
     * Verifica si hay suficiente stock disponible para un producto.
     *
     * @param idProducto El ID del producto a verificar.
     * @param cantidad La cantidad requerida.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    public boolean verificarStock(int idProducto, int cantidad) {
        Producto producto = productoDAO.obtenerPorId(idProducto);
        return producto != null && producto.getStockActual() >= cantidad;
    }

    /**
     * Incrementa el stock de un producto (útil para devoluciones o compras).
     *
     * @param idProducto El ID del producto a actualizar.
     * @param cantidad La cantidad a incrementar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean incrementarStock(int idProducto, int cantidad) {
        Producto producto = productoDAO.obtenerPorId(idProducto);

        if (producto == null) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe.");
        }

        int nuevoStock = producto.getStockActual() + cantidad;
        producto.setStockActual(nuevoStock);

        return productoDAO.actualizar(producto);
    }
}
