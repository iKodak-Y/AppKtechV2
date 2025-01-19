package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class DetalleImpuestos {
    @XmlElement(name = "impuesto")
    private List<Impuesto> impuesto;

    public List<Impuesto> getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(List<Impuesto> impuesto) {
        this.impuesto = impuesto;
    }
}
