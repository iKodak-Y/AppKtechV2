package com.ktech.appktechv2.modelo.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class TotalConImpuestos {
    @XmlElement(name = "totalImpuesto")
    private List<TotalImpuesto> totalImpuesto;

    public List<TotalImpuesto> getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(List<TotalImpuesto> totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }
}