package com.ktech.appktechv2.modelo.xml;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "factura")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComprobanteXML {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String version;

    @XmlElement(name = "infoTributaria")
    private InfoTributaria infoTributaria;

    @XmlElement(name = "infoFactura")
    private InfoFactura infoFactura;

    @XmlElement(name = "detalles")
    private Detalles detalles;  // Cambiado de List<Detalle> a Detalles

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoFactura getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFactura infoFactura) {
        this.infoFactura = infoFactura;
    }

    public Detalles getDetalles() {
        return detalles;
    }

    public void setDetalles(Detalles detalles) {
        this.detalles = detalles;
    }
}
