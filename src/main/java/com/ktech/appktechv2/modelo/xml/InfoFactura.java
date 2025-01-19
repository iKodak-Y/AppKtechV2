package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class InfoFactura {

    @XmlElement(required = true)
    private String fechaEmision;

    @XmlElement
    private String dirEstablecimiento;

    @XmlElement
    private String contribuyenteEspecial;

    @XmlElement
    private String obligadoContabilidad;

    @XmlElement(required = true)
    private String tipoIdentificacionComprador;

    @XmlElement
    private String guiaRemision;

    @XmlElement(required = true)
    private String razonSocialComprador;

    @XmlElement(required = true)
    private String identificacionComprador;

    @XmlElement
    private String direccionComprador;

    @XmlElement(required = true)
    private double totalSinImpuestos;

    @XmlElement(required = true)
    private double totalDescuento;

    @XmlElement(name = "totalConImpuestos")
    private TotalConImpuestos totalConImpuestos;

    @XmlElement(required = true)
    private double propina;

    @XmlElement(required = true)
    private double importeTotal;

    @XmlElement(required = true)
    private String moneda;

    @XmlElement(name = "pagos")
    private Pagos pagos;

    // Getters y Setters (implementar todos)

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }

    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador;
    }

    public String getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(String guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador;
    }

    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        this.identificacionComprador = identificacionComprador;
    }

    public String getDireccionComprador() {
        return direccionComprador;
    }

    public void setDireccionComprador(String direccionComprador) {
        this.direccionComprador = direccionComprador;
    }

    public double getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(double totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public double getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(double totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public TotalConImpuestos getTotalConImpuestos() {
        return totalConImpuestos;
    }

    public void setTotalConImpuestos(TotalConImpuestos totalConImpuestos) {
        this.totalConImpuestos = totalConImpuestos;
    }

    public double getPropina() {
        return propina;
    }

    public void setPropina(double propina) {
        this.propina = propina;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Pagos getPagos() {
        return pagos;
    }

    public void setPagos(Pagos pagos) {
        this.pagos = pagos;
    }
    
}
