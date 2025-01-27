package com.ktech.appktechv2.util;

import com.ktech.appktechv2.modelo.*;
import com.ktech.appktechv2.modelo.xml.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XMLGeneratorSRI {

    public String generarXMLFactura(Factura factura, Emisor emisor,
            List<DetalleFactura> detalles,
            List<FormaPago> formasPago) throws JAXBException {

        // 1. Crear el comprobante raíz
        ComprobanteXML comprobante = new ComprobanteXML();
        comprobante.setId("comprobante");
        comprobante.setVersion("2.1.0");

        // 2. Generar información tributaria
        InfoTributaria infoTributaria = new InfoTributaria();
        infoTributaria.setAmbiente(emisor.getTipoAmbiente());
        infoTributaria.setTipoEmision("1"); // Emisión normal
        infoTributaria.setRazonSocial(emisor.getRazonSocial());
        infoTributaria.setNombreComercial(emisor.getNombreComercial());
        infoTributaria.setRuc(emisor.getRuc());
        infoTributaria.setClaveAcceso(factura.getClaveAcceso());
        infoTributaria.setCodDoc("01"); // 01 para Factura
        infoTributaria.setEstab(emisor.getCodigoEstablecimiento());
        infoTributaria.setPtoEmi(emisor.getPuntoEmision());
        infoTributaria.setSecuencial(factura.getNumeroSecuencial());
        infoTributaria.setDirMatriz(emisor.getDireccion());

        comprobante.setInfoTributaria(infoTributaria);

        // 3. Generar información de factura
        InfoFactura infoFactura = new InfoFactura();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        infoFactura.setFechaEmision(dateFormat.format(factura.getFechaEmision()));
        infoFactura.setDirEstablecimiento(emisor.getDireccion());
        infoFactura.setObligadoContabilidad("SI"); // Ajustar según el caso
        infoFactura.setTipoIdentificacionComprador(obtenerTipoIdentificacion(factura.getRucComprador()));
        infoFactura.setRazonSocialComprador(factura.getRazonSocialComprador());
        infoFactura.setIdentificacionComprador(factura.getRucComprador());
        infoFactura.setDireccionComprador(factura.getDireccionComprador());

        // 4. Calcular totales
        double totalSinImpuestos = detalles.stream()
                .mapToDouble(DetalleFactura::getSubtotal)
                .sum();
        double totalIva = detalles.stream()
                .mapToDouble(d -> d.getSubtotal() * 0.12)
                .sum();
        double importeTotal = totalSinImpuestos + totalIva;

        infoFactura.setTotalSinImpuestos(totalSinImpuestos);
        infoFactura.setTotalDescuento(0.0); // Ajustar si hay descuentos

        // 5. Generar impuestos
        TotalConImpuestos totalConImpuestos = generarTotalConImpuestos(totalSinImpuestos, totalIva);
        infoFactura.setTotalConImpuestos(totalConImpuestos);

        infoFactura.setPropina(0.0);
        infoFactura.setImporteTotal(importeTotal);
        infoFactura.setMoneda("DOLAR");

        // 6. Generar pagos
        Pagos pagos = generarPagos(formasPago);
        infoFactura.setPagos(pagos);

        comprobante.setInfoFactura(infoFactura);

        // 7. Generar detalles
        Detalles detallesXml = generarDetalles(detalles);
        comprobante.setDetalles(detallesXml);

        // 8. Generar XML
        return marshallXML(comprobante);
    }

    private String marshallXML(ComprobanteXML comprobante) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ComprobanteXML.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(comprobante, writer);
        return writer.toString();
    }

    private InfoTributaria generarInfoTributaria(Factura factura, Emisor emisor) {
        InfoTributaria infoTrib = new InfoTributaria();
        infoTrib.setAmbiente(emisor.getTipoAmbiente());
        infoTrib.setTipoEmision("1"); // Emisión normal
        infoTrib.setRazonSocial(emisor.getRazonSocial());
        infoTrib.setNombreComercial(emisor.getNombreComercial());
        infoTrib.setRuc(emisor.getRuc());
        infoTrib.setClaveAcceso(factura.getClaveAcceso());
        infoTrib.setCodDoc("01"); // 01 para facturas
        infoTrib.setEstab(emisor.getCodigoEstablecimiento());
        infoTrib.setPtoEmi(emisor.getPuntoEmision());
        infoTrib.setSecuencial(factura.getNumeroSecuencial());
        infoTrib.setDirMatriz(emisor.getDireccion());
        return infoTrib;
    }

    private InfoFactura generarInfoFactura(Factura factura, Emisor emisor, List<DetalleFactura> detalles, List<FormaPago> formasPago) {
        InfoFactura infoFact = new InfoFactura();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        infoFact.setFechaEmision(dateFormat.format(factura.getFechaEmision()));
        infoFact.setDirEstablecimiento(emisor.getDireccion());
        infoFact.setObligadoContabilidad("NO"); // Ajustar según necesidad
        infoFact.setTipoIdentificacionComprador(obtenerTipoIdentificacion(factura.getRucComprador()));
        infoFact.setRazonSocialComprador(factura.getRazonSocialComprador());
        infoFact.setIdentificacionComprador(factura.getRucComprador());
        infoFact.setDireccionComprador(factura.getDireccionComprador());

        // Calcular totales
        double totalSinImpuestos = calcularTotalSinImpuestos(detalles);
        double totalIva = calcularTotalIva(detalles);

        infoFact.setTotalSinImpuestos(totalSinImpuestos);
        infoFact.setTotalDescuento(0.0); // Implementar si manejas descuentos

        // Generar total con impuestos
        TotalConImpuestos totalConImpuestos = generarTotalConImpuestos(totalSinImpuestos, totalIva);
        infoFact.setTotalConImpuestos(totalConImpuestos);

        infoFact.setPropina(0.0);
        infoFact.setImporteTotal(totalSinImpuestos + totalIva);
        infoFact.setMoneda("DOLAR");

        // Generar pagos
        Pagos pagos = generarPagos(formasPago);
        infoFact.setPagos(pagos);

        return infoFact;
    }

    private Detalles generarDetalles(List<DetalleFactura> detalles) {
        Detalles detallesXml = new Detalles();
        List<Detalle> detallesList = new ArrayList<>();

        for (DetalleFactura df : detalles) {
            Detalle detalle = new Detalle();
            detalle.setCodigoPrincipal(String.valueOf(df.getIdProducto()));
            detalle.setDescripcion(df.getDescripcion());
            detalle.setCantidad(df.getCantidad());
            detalle.setPrecioUnitario(df.getPrecioUnitario());
            detalle.setDescuento(0.0);
            detalle.setPrecioTotalSinImpuesto(df.getSubtotal());

            // Generar impuestos del detalle
            DetalleImpuestos impuestos = generarDetalleImpuestos(df);
            detalle.setImpuestos(impuestos);

            detallesList.add(detalle);
        }

        detallesXml.setDetalle(detallesList);
        return detallesXml;
    }

    private DetalleImpuestos generarDetalleImpuestos(DetalleFactura detalle) {
        DetalleImpuestos impuestos = new DetalleImpuestos();
        List<Impuesto> impuestoList = new ArrayList<>();

        Impuesto impuesto = new Impuesto();
        impuesto.setCodigo("2"); // 2 para IVA
        impuesto.setCodigoPorcentaje("2"); // 2 para IVA 12%
        impuesto.setTarifa(12.0);
        impuesto.setBaseImponible(detalle.getSubtotal());
        impuesto.setValor(detalle.getSubtotal() * 0.12);

        impuestoList.add(impuesto);
        impuestos.setImpuesto(impuestoList);

        return impuestos;
    }

    private TotalConImpuestos generarTotalConImpuestos(double totalSinImpuestos, double totalIva) {
        TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
        List<TotalImpuesto> totalImpuestoList = new ArrayList<>();

        TotalImpuesto totalImpuesto = new TotalImpuesto();
        totalImpuesto.setCodigo("2");
        totalImpuesto.setCodigoPorcentaje("2");
        totalImpuesto.setBaseImponible(totalSinImpuestos);
        totalImpuesto.setValor(totalIva);

        totalImpuestoList.add(totalImpuesto);
        totalConImpuestos.setTotalImpuesto(totalImpuestoList);

        return totalConImpuestos;
    }

    private Pagos generarPagos(List<FormaPago> formasPago) {
        Pagos pagos = new Pagos();
        List<Pago> pagosList = new ArrayList<>();

        for (FormaPago fp : formasPago) {
            Pago pago = new Pago();
            pago.setFormaPago(fp.getFormaPago().split(" - ")[0]); // Obtiene solo el código
            pago.setTotal(fp.getValorPago());
            pago.setPlazo(0.0);
            pago.setUnidadTiempo("dias");
            pagosList.add(pago);
        }

        pagos.setPago(pagosList);
        return pagos;
    }

    private String obtenerTipoIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.isEmpty()) {
            return "07"; // Consumidor Final
        }
        switch (identificacion.length()) {
            case 13:
                return "04"; // RUC
            case 10:
                return "05"; // Cédula
            default:
                return "06"; // Pasaporte
        }
    }

    private double calcularTotalSinImpuestos(List<DetalleFactura> detalles) {
        return detalles.stream()
                .mapToDouble(DetalleFactura::getSubtotal)
                .sum();
    }

    private double calcularTotalIva(List<DetalleFactura> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getSubtotal() * 0.12)
                .sum();
    }
}
