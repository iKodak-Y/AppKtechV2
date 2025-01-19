package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Detalles {

    @XmlElement(name = "detalle")
    private List<Detalle> detalle;

    public List<Detalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Detalle> detalle) {
        this.detalle = detalle;
    }
}
