package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Detalle {
    @XmlElement
    private String codigoPrincipal;
    
    @XmlElement
    private String codigoAuxiliar;
    
    @XmlElement(required = true)
    private String descripcion;
    
    @XmlElement(required = true)
    private double cantidad;
    
    @XmlElement(required = true)
    private double precioUnitario;
    
    @XmlElement(required = true)
    private double descuento;
    
    @XmlElement(required = true)
    private double precioTotalSinImpuesto;
    
    @XmlElement(required = true)
    private DetalleImpuestos impuestos;

    // Getters y Setters
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(double precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
    }

    public DetalleImpuestos getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(DetalleImpuestos impuestos) {
        this.impuestos = impuestos;
    }
}