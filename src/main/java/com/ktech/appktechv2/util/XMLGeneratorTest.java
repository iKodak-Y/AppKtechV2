package com.ktech.appktechv2.util;

import com.ktech.appktechv2.modelo.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class XMLGeneratorTest {

    /**public static void main(String[] args) {
        // Crear instancia del generador
        XMLGeneratorSRI xmlGenerator = new XMLGeneratorSRI();

        try {
            // Crear datos de prueba
            Emisor emisor = crearEmisorPrueba();
            Factura factura = crearFacturaPrueba();
            List<DetalleFactura> detalles = crearDetallesPrueba();
            List<FormaPago> formasPago = crearFormasPagoPrueba();

            // Generar XML
            String xmlGenerado = xmlGenerator.generarXMLFactura(factura, emisor, detalles, formasPago);

            // Imprimir el XML generado
            System.out.println("XML Generado:");
            System.out.println(xmlGenerado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Emisor crearEmisorPrueba() {
        Emisor emisor = new Emisor();
        emisor.setRuc("1234567890001");
        emisor.setRazonSocial("EMPRESA DE PRUEBA S.A.");
        emisor.setNombreComercial("EMPRESA DE PRUEBA");
        emisor.setDireccion("Dirección de Prueba 123");
        emisor.setCodigoEstablecimiento("001");
        emisor.setPuntoEmision("001");
        emisor.setTipoAmbiente("1"); // 1 para pruebas, 2 para producción
        return emisor;
    }

    private static Factura crearFacturaPrueba() {
        Factura factura = new Factura();
        factura.setIdFactura(1);
        factura.setClaveAcceso("1234567890123456789012345678901234567890123456789");
        factura.setNumeroSecuencial("000000001");
        factura.setFechaEmision(new Date());
        factura.setRucComprador("0987654321001");
        factura.setRazonSocialComprador("CONSUMIDOR FINAL");
        factura.setDireccionComprador("Dirección Cliente 456");
        factura.setEstado("PENDIENTE");
        return factura;
    }

    private static List<DetalleFactura> crearDetallesPrueba() {
        List<DetalleFactura> detalles = new ArrayList<>();

        DetalleFactura detalle1 = new DetalleFactura();
        detalle1.setIdProducto(1);
        detalle1.setDescripcion("Producto de Prueba 1");
        detalle1.setCantidad(2);
        detalle1.setPrecioUnitario(10.00);
        detalle1.setSubtotal(20.00);
        detalle1.setImpuestos("2"); // Código para IVA
        detalles.add(detalle1);

        DetalleFactura detalle2 = new DetalleFactura();
        detalle2.setIdProducto(2);
        detalle2.setDescripcion("Producto de Prueba 2");
        detalle2.setCantidad(1);
        detalle2.setPrecioUnitario(15.00);
        detalle2.setSubtotal(15.00);
        detalle2.setImpuestos("2");
        detalles.add(detalle2);

        return detalles;
    }

    private static List<FormaPago> crearFormasPagoPrueba() {
        List<FormaPago> formasPago = new ArrayList<>();
        formasPago.add(new FormaPago("01 - EFECTIVO", 35.00));
        return formasPago;
    }**/
}
