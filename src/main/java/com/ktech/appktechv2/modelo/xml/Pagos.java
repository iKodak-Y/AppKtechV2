package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Pagos {

    @XmlElement(name = "pago")
    private List<Pago> pago;

    public List<Pago> getPago() {
        return pago;
    }

    public void setPago(List<Pago> pago) {
        this.pago = pago;
    }
}
