//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v2.3.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2025.01.20 a las 06:23:43 PM COT 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene la informacion del destinatario
 * 
 * &lt;p&gt;Clase Java para destino complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="destino"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="motivoTraslado" type="{}motivoTraslado"/&amp;gt;
 *         &amp;lt;element name="docAduaneroUnico" type="{}docAduaneroUnico" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="codEstabDestino" type="{}establecimiento" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ruta" type="{}ruta" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "destino", propOrder = {
    "motivoTraslado",
    "docAduaneroUnico",
    "codEstabDestino",
    "ruta"
})
public class Destino {

    @XmlElement(required = true)
    protected String motivoTraslado;
    protected String docAduaneroUnico;
    protected String codEstabDestino;
    protected String ruta;

    /**
     * Obtiene el valor de la propiedad motivoTraslado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    /**
     * Define el valor de la propiedad motivoTraslado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoTraslado(String value) {
        this.motivoTraslado = value;
    }

    /**
     * Obtiene el valor de la propiedad docAduaneroUnico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocAduaneroUnico() {
        return docAduaneroUnico;
    }

    /**
     * Define el valor de la propiedad docAduaneroUnico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocAduaneroUnico(String value) {
        this.docAduaneroUnico = value;
    }

    /**
     * Obtiene el valor de la propiedad codEstabDestino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEstabDestino() {
        return codEstabDestino;
    }

    /**
     * Define el valor de la propiedad codEstabDestino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEstabDestino(String value) {
        this.codEstabDestino = value;
    }

    /**
     * Obtiene el valor de la propiedad ruta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * Define el valor de la propiedad ruta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuta(String value) {
        this.ruta = value;
    }

}
