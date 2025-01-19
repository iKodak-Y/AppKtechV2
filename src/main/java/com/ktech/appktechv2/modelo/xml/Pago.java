package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Pago {

    @XmlElement(required = true)
    private String formaPago;

    @XmlElement(required = true)
    private double total;

    @XmlElement
    private double plazo;

    @XmlElement
    private String unidadTiempo;

    public Pago() {
    }

    // Getters y Setters (implementar todos)

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPlazo() {
        return plazo;
    }

    public void setPlazo(double plazo) {
        this.plazo = plazo;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }
    
}
