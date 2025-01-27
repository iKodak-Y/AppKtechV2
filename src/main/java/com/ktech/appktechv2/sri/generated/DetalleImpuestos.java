package com.ktech.appktechv2.sri.generated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para detalleImpuestos complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="detalleImpuestos"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="detalleImpuesto" maxOccurs="unbounded"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;sequence&amp;gt;
 *                   &amp;lt;element name="codigo" type="{}codigoReembolso"/&amp;gt;
 *                   &amp;lt;element name="codigoPorcentaje" type="{}codigoPorcentajeReembolso"/&amp;gt;
 *                   &amp;lt;element name="tarifa" type="{}tarifa"/&amp;gt;
 *                   &amp;lt;element name="baseImponibleReembolso" type="{}baseImponibleReembolso"/&amp;gt;
 *                   &amp;lt;element name="impuestoReembolso" type="{}impuestoReembolso"/&amp;gt;
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
@XmlType(name = "detalleImpuestos", propOrder = {
    "detalleImpuesto"
})
public class DetalleImpuestos {

    @XmlElement(required = true)
    protected List<DetalleImpuestos.DetalleImpuesto> detalleImpuesto;

    /**
     * Gets the value of the detalleImpuesto property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the detalleImpuesto property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDetalleImpuesto().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DetalleImpuestos.DetalleImpuesto }
     * 
     * 
     */
    public List<DetalleImpuestos.DetalleImpuesto> getDetalleImpuesto() {
        if (detalleImpuesto == null) {
            detalleImpuesto = new ArrayList<DetalleImpuestos.DetalleImpuesto>();
        }
        return this.detalleImpuesto;
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
     *         &amp;lt;element name="codigo" type="{}codigoReembolso"/&amp;gt;
     *         &amp;lt;element name="codigoPorcentaje" type="{}codigoPorcentajeReembolso"/&amp;gt;
     *         &amp;lt;element name="tarifa" type="{}tarifa"/&amp;gt;
     *         &amp;lt;element name="baseImponibleReembolso" type="{}baseImponibleReembolso"/&amp;gt;
     *         &amp;lt;element name="impuestoReembolso" type="{}impuestoReembolso"/&amp;gt;
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
        "codigo",
        "codigoPorcentaje",
        "tarifa",
        "baseImponibleReembolso",
        "impuestoReembolso"
    })
    public static class DetalleImpuesto {

        @XmlElement(required = true)
        protected String codigo;
        @XmlElement(required = true)
        protected String codigoPorcentaje;
        @XmlElement(required = true)
        protected BigDecimal tarifa;
        @XmlElement(required = true)
        protected BigDecimal baseImponibleReembolso;
        @XmlElement(required = true)
        protected BigDecimal impuestoReembolso;

        /**
         * Obtiene el valor de la propiedad codigo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodigo() {
            return codigo;
        }

        /**
         * Define el valor de la propiedad codigo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodigo(String value) {
            this.codigo = value;
        }

        /**
         * Obtiene el valor de la propiedad codigoPorcentaje.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodigoPorcentaje() {
            return codigoPorcentaje;
        }

        /**
         * Define el valor de la propiedad codigoPorcentaje.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodigoPorcentaje(String value) {
            this.codigoPorcentaje = value;
        }

        /**
         * Obtiene el valor de la propiedad tarifa.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTarifa() {
            return tarifa;
        }

        /**
         * Define el valor de la propiedad tarifa.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTarifa(BigDecimal value) {
            this.tarifa = value;
        }

        /**
         * Obtiene el valor de la propiedad baseImponibleReembolso.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBaseImponibleReembolso() {
            return baseImponibleReembolso;
        }

        /**
         * Define el valor de la propiedad baseImponibleReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBaseImponibleReembolso(BigDecimal value) {
            this.baseImponibleReembolso = value;
        }

        /**
         * Obtiene el valor de la propiedad impuestoReembolso.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getImpuestoReembolso() {
            return impuestoReembolso;
        }

        /**
         * Define el valor de la propiedad impuestoReembolso.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setImpuestoReembolso(BigDecimal value) {
            this.impuestoReembolso = value;
        }

    }

}
