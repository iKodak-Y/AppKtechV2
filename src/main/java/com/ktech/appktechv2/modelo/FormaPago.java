package com.ktech.appktechv2.modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FormaPago {

    private StringProperty formaPago;
    private DoubleProperty valorPago;

    public FormaPago(String formaPago, double valorPago) {
        this.formaPago = new SimpleStringProperty(formaPago);
        this.valorPago = new SimpleDoubleProperty(valorPago);
    }

    public String getFormaPago() {
        return formaPago.get();
    }

    public void setFormaPago(String formaPago) {
        this.formaPago.set(formaPago);
    }

    public StringProperty formaPagoProperty() {
        return formaPago;
    }

    public double getValorPago() {
        return valorPago.get();
    }

    public void setValorPago(double valorPago) {
        this.valorPago.set(valorPago);
    }

    public DoubleProperty valorPagoProperty() {
        return valorPago;
    }
}
