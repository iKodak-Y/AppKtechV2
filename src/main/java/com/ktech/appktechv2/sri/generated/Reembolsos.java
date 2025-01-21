//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v2.3.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2025.01.20 a las 06:23:43 PM COT 
//


package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para reembolsos complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="reembolsos"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="reembolsoDetalle" maxOccurs="unbounded"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;sequence&amp;gt;
 *                   &amp;lt;element name="tipoIdentificacionProveedorReembolso" type="{}tipoIdentificacionProveedorReembolso"/&amp;gt;
 *                   &amp;lt;element name="identificacionProveedorReembolso" type="{}identificacionProveedorReembolso"/&amp;gt;
 *                   &amp;lt;element name="codPaisPagoProveedorReembolso" type="{}codPaisPagoProveedorReembolso" minOccurs="0"/&amp;gt;
 *                   &amp;lt;element name="tipoProveedorReembolso" type="{}tipoProveedorReembolso"/&amp;gt;
 *                   &amp;lt;element name="codDocReembolso" type="{}codDocReembolso"/&amp;gt;
 *                   &amp;lt;element name="estabDocReembolso" type="{}estabDocReembolso"/&amp;gt;
 *                   &amp;lt;element name="ptoEmiDocReembolso" type="{}ptoEmiDocReembolso"/&amp;gt;
 *                   &amp;lt;element name="secuencialDocReembolso" type="{}secuencialDocReembolso"/&amp;gt;
 *                   &amp;lt;element name="fechaEmisionDocReembolso" type="{}fechaEmisionDocReembolso"/&amp;gt;
 *                   &amp;lt;element name="numeroautorizacionDocReemb" type="{}numeroautorizacionDocReemb"/&amp;gt;
 *                   &amp;lt;element name="detalleImpuestos" type="{}detalleImpuestos"/&amp;gt;
 *                   &amp;lt;element name="compensacionesReembolso" type="{}compensacionesReembolso" minOccurs="0"/&amp;gt;
 *                 &amp;lt;/sequence&amp;gt;
 *               &amp;lt;/restriction&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reembolsos", propOrder = {
    "reembolsoDetalle"
})
public class Reembolsos {

    @XmlElement(required = true)
    protected List<Reembolsos.ReembolsoDetalle> reembolsoDetalle;

    /**
     * Gets the value of the reembolsoDetalle property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the reembolsoDetalle property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getReembolsoDetalle().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link Reembolsos.ReembolsoDetalle }
     * 
     * 
     */
    public List<Reembolsos.ReembolsoDetalle> getReembolsoDetalle() {
        if (reembolsoDetalle == null) {
            reembolsoDetalle = new ArrayList<Reembolsos.ReembolsoDetalle>();
        }
        return this.reembolsoDetalle;
    }


    /**
     * &lt;p&gt;Clase Java para anonymous complex type.
     * 
     * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
     *       &amp;lt;sequence&amp;gt;
     *         &amp;lt;element name="tipoIdentificacionProveedorReembolso" type="{}tipoIdentificacionProveedorReembolso"/&amp;gt;
     *         &amp;lt;element name="identificacionProveedorReembolso" type="{}identificacionProveedorReembolso"/&amp;gt;
     *         &amp;lt;element name="codPaisPagoProveedorReembolso" type="{}codPaisPagoProveedorReembolso" minOccurs="0"/&amp;gt;
     *         &amp;lt;element name="tipoProveedorReembolso" type="{}tipoProveedorReembolso"/&amp;gt;
     *         &amp;lt;element name="codDocReembolso" type="{}codDocReembolso"/&amp;gt;
     *         &amp;lt;element name="estabDocReembolso" type="{}estabDocReembolso"/&amp;gt;
     *         &amp;lt;element name="ptoEmiDocReembolso" type="{}ptoEmiDocReembolso"/&amp;gt;
     *         &amp;lt;element name="secuencialDocReembolso" type="{}secuencialDocReembolso"/&amp;gt;
     *         &amp;lt;element name="fechaEmisionDocReembolso" type="{}fechaEmisionDocReembolso"/&amp;gt;
     *         &amp;lt;element name="numeroautorizacionDocReemb" type="{}numeroautorizacionDocReemb"/&amp;gt;
     *         &amp;lt;element name="detalleImpuestos" type="{}detalleImpuestos"/&amp;gt;
     *         &amp;lt;element name="compensacionesReembolso" type="{}compensacionesReembolso" minOccurs="0"/&amp;gt;
     *       &amp;lt;/sequence&amp;gt;
     *     &amp;lt;/restriction&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tipoIdentificacionProveedorReembolso",
        "identificacionProveedorReembolso",
        "codPaisPagoProveedorReembolso",
        "tipoProveedorReembolso",
        "codDocReembolso",
        "estabDocReembolso",
        "ptoEmiDocReembolso",
        "secuencialDocReembolso",
        "fechaEmisionDocReembolso",
        "numeroautorizacionDocReemb",
        "detalleImpuestos",
        "compensacionesReembolso"
    })
    public static class ReembolsoDetalle {

        @XmlElement(required = true)
        protected String tipoIdentificacionProveedorReembolso;
        @XmlElement(required = true)
        protected String identificacionProveedorReembolso;
        protected String codPaisPagoProveedorReembolso;
        @XmlElement(required = true)
        protected String tipoProveedorReembolso;
        @XmlElement(required = true)
        protected String codDocReembolso;
        @XmlElement(required = true)
        protected String estabDocReembolso;
        @XmlElement(required = true)
        protected String ptoEmiDocReembolso;
        @XmlElement(required = true)
        protected String secuencialDocReembolso;
        @XmlElement(required = true)
        protected String fechaEmisionDocReembolso;
        @XmlElement(required = true)
        protected String numeroautorizacionDocReemb;
        @XmlElement(required = true)
        protected DetalleImpuestos detalleImpuestos;
        protected CompensacionesReembolso compensacionesReembolso;

        /**
         * Obtiene el valor de la propiedad tipoIdentificacionProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoIdentificacionProveedorReembolso() {
            return tipoIdentificacionProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad tipoIdentificacionProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoIdentificacionProveedorReembolso(String value) {
            this.tipoIdentificacionProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad identificacionProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentificacionProveedorReembolso() {
            return identificacionProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad identificacionProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentificacionProveedorReembolso(String value) {
            this.identificacionProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad codPaisPagoProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodPaisPagoProveedorReembolso() {
            return codPaisPagoProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad codPaisPagoProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodPaisPagoProveedorReembolso(String value) {
            this.codPaisPagoProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad tipoProveedorReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoProveedorReembolso() {
            return tipoProveedorReembolso;
        }

        /**
         * Define el valor de la propiedad tipoProveedorReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoProveedorReembolso(String value) {
            this.tipoProveedorReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad codDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodDocReembolso() {
            return codDocReembolso;
        }

        /**
         * Define el valor de la propiedad codDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodDocReembolso(String value) {
            this.codDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad estabDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEstabDocReembolso() {
            return estabDocReembolso;
        }

        /**
         * Define el valor de la propiedad estabDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEstabDocReembolso(String value) {
            this.estabDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad ptoEmiDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPtoEmiDocReembolso() {
            return ptoEmiDocReembolso;
        }

        /**
         * Define el valor de la propiedad ptoEmiDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPtoEmiDocReembolso(String value) {
            this.ptoEmiDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad secuencialDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSecuencialDocReembolso() {
            return secuencialDocReembolso;
        }

        /**
         * Define el valor de la propiedad secuencialDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSecuencialDocReembolso(String value) {
            this.secuencialDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaEmisionDocReembolso.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechaEmisionDocReembolso() {
            return fechaEmisionDocReembolso;
        }

        /**
         * Define el valor de la propiedad fechaEmisionDocReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechaEmisionDocReembolso(String value) {
            this.fechaEmisionDocReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad numeroautorizacionDocReemb.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumeroautorizacionDocReemb() {
            return numeroautorizacionDocReemb;
        }

        /**
         * Define el valor de la propiedad numeroautorizacionDocReemb.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumeroautorizacionDocReemb(String value) {
            this.numeroautorizacionDocReemb = value;
        }

        /**
         * Obtiene el valor de la propiedad detalleImpuestos.
         * 
         * @return
         *     possible object is
         *     {@link DetalleImpuestos }
         *     
         */
        public DetalleImpuestos getDetalleImpuestos() {
            return detalleImpuestos;
        }

        /**
         * Define el valor de la propiedad detalleImpuestos.
         * 
         * @param value
         *     allowed object is
         *     {@link DetalleImpuestos }
         *     
         */
        public void setDetalleImpuestos(DetalleImpuestos value) {
            this.detalleImpuestos = value;
        }

        /**
         * Obtiene el valor de la propiedad compensacionesReembolso.
         * 
         * @return
         *     possible object is
         *     {@link CompensacionesReembolso }
         *     
         */
        public CompensacionesReembolso getCompensacionesReembolso() {
            return compensacionesReembolso;
        }

        /**
         * Define el valor de la propiedad compensacionesReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link CompensacionesReembolso }
         *     
         */
        public void setCompensacionesReembolso(CompensacionesReembolso value) {
            this.compensacionesReembolso = value;
        }

    }

}
