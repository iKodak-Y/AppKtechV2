package com.ktech.appktechv2.modelo;

import java.util.Date;
import java.util.List;

public class Factura {

    private int idFactura;
    private int idEmisor;
    private String claveAcceso;
    private String numeroSecuencial;
    private Date fechaEmision;
    private String rucComprador;
    private String razonSocialComprador;
    private String direccionComprador;
    private String estado;
    private Date fechaAutorizacion;
    private String xmlAutorizado;
    private List<DetalleFactura> detalles;

    // Constructor vacío
    public Factura() {
    }

    // Constructor completo
    public Factura(int idFactura, int idEmisor, String claveAcceso, String numeroSecuencial, Date fechaEmision,
            String rucComprador, String razonSocialComprador, String direccionComprador, String estado,
            Date fechaAutorizacion, String xmlAutorizado, List<DetalleFactura> detalles) {
        this.idFactura = idFactura;
        this.idEmisor = idEmisor;
        this.claveAcceso = claveAcceso;
        this.numeroSecuencial = numeroSecuencial;
        this.fechaEmision = fechaEmision;
        this.rucComprador = rucComprador;
        this.razonSocialComprador = razonSocialComprador;
        this.direccionComprador = direccionComprador;
        this.estado = estado;
        this.fechaAutorizacion = fechaAutorizacion;
        this.xmlAutorizado = xmlAutorizado;
        this.detalles = detalles;
    }

    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getNumeroSecuencial() {
        return numeroSecuencial;
    }

    public void setNumeroSecuencial(String numeroSecuencial) {
        this.numeroSecuencial = numeroSecuencial;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getRucComprador() {
        return rucComprador;
    }

    public void setRucComprador(String rucComprador) {
        this.rucComprador = rucComprador;
    }

    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador;
    }

    public String getDireccionComprador() {
        return direccionComprador;
    }

    public void setDireccionComprador(String direccionComprador) {
        this.direccionComprador = direccionComprador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getXmlAutorizado() {
        return xmlAutorizado;
    }

    public void setXmlAutorizado(String xmlAutorizado) {
        this.xmlAutorizado = xmlAutorizado;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Factura{"
                + "idFactura=" + idFactura
                + ", idEmisor=" + idEmisor
                + ", claveAcceso='" + claveAcceso + '\''
                + ", numeroSecuencial='" + numeroSecuencial + '\''
                + ", fechaEmision=" + fechaEmision
                + ", rucComprador='" + rucComprador + '\''
                + ", razonSocialComprador='" + razonSocialComprador + '\''
                + ", direccionComprador='" + direccionComprador + '\''
                + ", estado='" + estado + '\''
                + ", fechaAutorizacion=" + fechaAutorizacion
                + ", xmlAutorizado='" + xmlAutorizado + '\''
                + ", detalles=" + detalles
                + '}';
    }
}
