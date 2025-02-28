package com.ktech.appktechv2.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultadoCarga {
    private String nombre;
    private double precio;
    private double pvp;
    private int stock;
    private double iva;
    private String categoria;
    private final StringProperty producto;
    private final StringProperty estado;
    private final StringProperty error;
    private String icono;

    public ResultadoCarga() {
        this.producto = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
        this.error = new SimpleStringProperty();
    }

    // Getter y Setter para Producto
    public String getProducto() {
        return producto.get();
    }

    public void setProducto(String producto) {
        this.producto.set(producto);
    }

    public StringProperty productoProperty() {
        return producto;
    }

    // Getter y Setter para Estado
    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    // Getter y Setter para Error
    public String getError() {
        return error.get();
    }

    public void setError(String error) {
        this.error.set(error);
    }

    public StringProperty errorProperty() {
        return error;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getIcono() {
        return icono;
    }
}