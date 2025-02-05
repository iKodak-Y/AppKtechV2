package com.ktech.appktechv2.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.ktech.appktechv2.modelo.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.BorderCollapsePropertyValue;
import com.ktech.appktechv2.SqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketPDFGenerator {

    private static final SqlConnection sqlConnection = new SqlConnection();

    public static String generateTicket(Emisor emisor, Venta venta, List<Producto> productos, String outputPath) throws SQLException {
        try {
            // Obtener datos adicionales del emisor y cliente
            EmisorData emisorData = obtenerDatosEmisor(emisor.getIdEmisor());
            ClienteData clienteData = obtenerDatosCliente(venta.getIdCliente());

            PdfWriter writer = new PdfWriter(outputPath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.setMargins(20, 20, 20, 20);

            // Tabla de encabezado
            Table headerTable = new Table(new float[]{49, 2, 49})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setFixedLayout();

            // Columna izquierda (fila 1: LOGO)
            Cell leftHeaderCell1 = new Cell()
                    .setBorder(Border.NO_BORDER)
                    .setPadding(10);

            // Usar el logo desde la base de datos
            if (emisorData.getLogo() != null) {
                ImageData imageData = ImageDataFactory.create(emisorData.getLogo());
                Image logoImage = new Image(imageData).setAutoScale(true);
                leftHeaderCell1.add(logoImage);
            }
            leftHeaderCell1.setHeight(80);

            // Columna izquierda (fila 2)
            Cell leftHeaderCell2 = new Cell()
                    .setBorder(new SolidBorder(1))
                    .setPadding(10)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT);
            leftHeaderCell2.add(new Paragraph("TICKET DE VENTA").setBold().setFontSize(12));
            leftHeaderCell2.add(new Paragraph("No. " + venta.getNumeroSecuencial()));
            leftHeaderCell2.add(new Paragraph("Fecha y Hora de Emisión: "
                    + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));

            Cell spacerCell = new Cell(2, 1)
                    .setBorder(Border.NO_BORDER);

            // Columna derecha usando el nombre comercial de la base de datos
            Cell rightHeaderCell = new Cell(2, 1)
                    .setBorder(new SolidBorder(1))
                    .setPadding(10)
                    .setTextAlignment(TextAlignment.LEFT);
            rightHeaderCell.add(new Paragraph(emisorData.getNombreComercial()).setBold().setFontSize(16).setPaddingTop(10));
            rightHeaderCell.add(new Paragraph("Especialistas en tecnología").setFontSize(10).setPaddingTop(10));
            rightHeaderCell.add(new Paragraph("Direccion Matriz: " + emisor.getDireccion()).setFontSize(10).setPaddingTop(10));

            headerTable.addCell(leftHeaderCell1);
            headerTable.addCell(spacerCell);
            headerTable.addCell(rightHeaderCell);
            headerTable.addCell(leftHeaderCell2);

            document.add(headerTable);

            // Información del cliente -----------------------------------
            Table clientTable = new Table(1)
                    .setWidth(UnitValue.createPercentValue(100))
                    .setBorder(new SolidBorder(1))
                    .setFontSize(10)
                    .setMarginTop(10);

            Cell clientCell = new Cell()
                    .setBorder(Border.NO_BORDER)
                    .setPadding(10);

            clientCell.add(new Paragraph("DATOS DEL CLIENTE").setBold());
            clientCell.add(new Paragraph("Razón Social / Nombres y Apellidos:               " + venta.getRazonSocialComprador()));
            clientCell.add(new Paragraph("Identificación:               " + venta.getRucComprador()));
            clientCell.add(new Paragraph("Fecha:                          " + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            clientCell.add(new Paragraph("Dirección:                     " + venta.getDireccionComprador()));

            clientTable.addCell(clientCell);
            document.add(clientTable);

            // Tabla de productos
            Table productTable = new Table(new float[]{1, 3, 1, 2, 2})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10)
                    .setFontSize(10)
                    .setBorder(new SolidBorder(1));

            // Encabezados
            productTable.addHeaderCell(createHeaderCell("Código"));
            productTable.addHeaderCell(createHeaderCell("Descripción"));
            productTable.addHeaderCell(createHeaderCell("Cant."));
            productTable.addHeaderCell(createHeaderCell("P.Unit"));
            productTable.addHeaderCell(createHeaderCell("Total"));

            // Productos
            double subtotal15 = 0;
            double subtotal0 = 0;
            double iva15 = 0;

            for (Producto producto : productos) {
                productTable.addCell(createProductCell(producto.getCodigo()));
                productTable.addCell(createProductCell(producto.getNombre()));
                productTable.addCell(createProductCell(String.valueOf(producto.getCantidad())));
                productTable.addCell(createProductCell(String.format("$%.2f", producto.getPvp())));
                productTable.addCell(createProductCell(String.format("$%.2f", producto.getTotal())));

                if (producto.getIva() > 0) {
                    subtotal15 += producto.getSubtotal();
                    iva15 += producto.getTotal() - producto.getSubtotal();
                } else {
                    subtotal0 += producto.getSubtotal();
                }
            }

            document.add(productTable);

            // Ajustar diseño para colocar las tablas "Información Adicional" y "Forma de Pago" junto a "Totales"
            Table layoutTable = new Table(new float[]{60, 40}) // Proporción de ancho entre columnas (izquierda:derecha)
                    .setWidth(UnitValue.createPercentValue(100)) // Ancho total del contenedor
                    .setFixedLayout() // Fijar el diseño para respetar las proporciones
                    .setBorder(Border.NO_BORDER); // Sin bordes para el contenedor principal

            // Columna izquierda: Dos filas (Información Adicional y Forma de Pago)
            Table leftColumnTable = new Table(1) // Una columna para las dos tablas
                    .setWidth(UnitValue.createPercentValue(100)) // Ancho al 100% dentro de la columna izquierda
                    .setBorder(Border.NO_BORDER); // Sin bordes para esta tabla

            // Fila 1: Tabla Información Adicional
            Table additionalInfoTable = createAdditionalInfoTable(clienteData);
            leftColumnTable.addCell(new Cell()
                    .add(additionalInfoTable)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingTop(5));

            // Fila 2: Tabla Forma de Pago
            Table paymentTable = createPaymentTable(venta.getTotal(), venta.getMetodoPago()); // Pasamos el total y el método de pago
            leftColumnTable.addCell(new Cell()
                    .add(paymentTable)
                    .setBorder(Border.NO_BORDER)
                    .setPaddingTop(5));

            // Columna derecha: Una sola celda con la tabla Totales
            Table totalsTable = createTotalsTable(subtotal15, subtotal0, iva15, venta.getTotal());
            Cell rightColumnCell = new Cell(2, 1) // Combinar dos filas en una columna
                    .add(totalsTable)
                    .setVerticalAlignment(VerticalAlignment.TOP) // Alinear la tabla hacia arriba
                    .setBorder(Border.NO_BORDER)
                    .setPaddingTop(5);

            // Agregar las columnas al contenedor principal
            layoutTable.addCell(new Cell()
                    .add(leftColumnTable)
                    .setBorder(Border.NO_BORDER));
            layoutTable.addCell(rightColumnCell);

            // Agregar el contenedor principal al documento
            document.add(layoutTable);

            // Información adicional y pie de página
            Table footerTable = new Table(1)
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginTop(10)
                    .setFontSize(10)
                    .setBorder(new SolidBorder(1));

            Cell footerCell = new Cell()
                    .setBorder(Border.NO_BORDER)
                    .setPadding(10);

            footerCell.add(new Paragraph("¡Gracias por su compra!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

            footerCell.add(new Paragraph("K-TECH - Su socio tecnológico")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10));

            footerTable.addCell(footerCell);
            document.add(footerTable);

            document.close();
            return outputPath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Cell createHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
    }

    private static Cell createProductCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
    }

    private static void addTotalRow(Table table, String label, String value) {
        table.addCell(new Cell()
                .add(new Paragraph(label))
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(new Cell()
                .add(new Paragraph(value))
                .setBorder(new SolidBorder(1))
                .setTextAlignment(TextAlignment.RIGHT)
                .setWidth(UnitValue.createPercentValue(30)));
    }

    private static Table createAdditionalInfoTable(ClienteData clienteData) {
        Table additionalInfoTable = new Table(2)
                .setWidth(UnitValue.createPercentValue(100))
                .setFontSize(8)
                .setBorder(new SolidBorder(1));

        Cell additionalInfoTitleCell = new Cell(1, 2)
                .add(new Paragraph("Información Adicional"))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8)
                .setBorder(new SolidBorder(1));
        additionalInfoTable.addCell(additionalInfoTitleCell);

        Cell phoneCell = new Cell().add(new Paragraph("Teléfono:")).setBorder(Border.NO_BORDER).setPadding(5);
        Cell phoneValueCell = new Cell().add(new Paragraph(clienteData.getTelefono())).setBorder(Border.NO_BORDER).setPadding(5);
        additionalInfoTable.addCell(phoneCell);
        additionalInfoTable.addCell(phoneValueCell);

        Cell emailCell = new Cell().add(new Paragraph("Email:")).setBorder(Border.NO_BORDER).setPadding(5);
        Cell emailValueCell = new Cell().add(new Paragraph(clienteData.getEmail())).setBorder(Border.NO_BORDER).setPadding(5);
        additionalInfoTable.addCell(emailCell);
        additionalInfoTable.addCell(emailValueCell);

        Cell saleCell = new Cell().add(new Paragraph("Venta:")).setBorder(Border.NO_BORDER).setPadding(5);
        Cell saleValueCell = new Cell().add(new Paragraph("Una vez salida la mercadería no se aceptan cambios ni devoluciones."))
                .setBorder(Border.NO_BORDER).setPadding(5);
        additionalInfoTable.addCell(saleCell);
        additionalInfoTable.addCell(saleValueCell);

        return additionalInfoTable;
    }

    // Clases auxiliares para manejar los datos
    private static class EmisorData {

        private final String nombreComercial;
        private final byte[] logo;

        public EmisorData(String nombreComercial, byte[] logo) {
            this.nombreComercial = nombreComercial;
            this.logo = logo;
        }

        public String getNombreComercial() {
            return nombreComercial;
        }

        public byte[] getLogo() {
            return logo;
        }
    }

    private static class ClienteData {

        private final String telefono;
        private final String email;

        public ClienteData(String telefono, String email) {
            this.telefono = telefono;
            this.email = email;
        }

        public String getTelefono() {
            return telefono;
        }

        public String getEmail() {
            return email;
        }
    }
// Métodos para obtener datos de la base de datos

    private static EmisorData obtenerDatosEmisor(int idEmisor) throws SQLException {
        try (Connection conn = sqlConnection.getConexion()) {
            String sql = "SELECT nombre_comercial, Logo FROM Emisor WHERE id_emisor = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEmisor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new EmisorData(
                        rs.getString("nombre_comercial"),
                        rs.getBytes("Logo")
                );
            }
            return new EmisorData("", null);
        }
    }

    private static ClienteData obtenerDatosCliente(int idCliente) throws SQLException {
        try (Connection conn = sqlConnection.getConexion()) {
            String sql = "SELECT Telefono, Email FROM Clientes WHERE IDCliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ClienteData(
                        rs.getString("Telefono"),
                        rs.getString("Email")
                );
            }
            return new ClienteData("", "");
        }
    }

    private static Table createPaymentTable(double total, String metodoPago) { // Recibe el total y el método de pago
        Table paymentTable = new Table(2)
                .setWidth(UnitValue.createPercentValue(80))
                .setFontSize(8)
                .setBorder(new SolidBorder(1));

        Cell paymentTitleCell = new Cell()
                .add(new Paragraph("Forma de pago"))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8)
                .setBorder(new SolidBorder(1));
        paymentTable.addCell(paymentTitleCell);
        Cell paymentTitleCellValue = new Cell()
                .add(new Paragraph("Valor"))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8)
                .setBorder(new SolidBorder(1));
        paymentTable.addCell(paymentTitleCellValue);
        Cell paymentMethodCell = new Cell()
                .add(new Paragraph(metodoPago)) // Usa el método de pago recuperado
                .setBorder(Border.NO_BORDER)
                .setPadding(5);

        Cell paymentValueCell = new Cell()
                .add(new Paragraph(String.format("$%.2f", total))) // Usa el total dinámico
                .setBorder(new SolidBorder(1))
                .setPadding(5)
                .setTextAlignment(TextAlignment.RIGHT);

        paymentTable.addCell(paymentMethodCell);
        paymentTable.addCell(paymentValueCell);

        return paymentTable;
    }

    private static Table createTotalsTable(double subtotal15, double subtotal0, double iva15, double total) {
        Table totalsTable = new Table(2)
                .setWidth(UnitValue.createPercentValue(100))
                .setFontSize(10)
                .setMarginTop(0);

        if (subtotal15 > 0) {
            addTotalRow(totalsTable, "Subtotal 15%:", String.format("$%.2f", subtotal15));
        }
        if (subtotal0 > 0) {
            addTotalRow(totalsTable, "Subtotal 0%:", String.format("$%.2f", subtotal0));
        }
        if (iva15 > 0) {
            addTotalRow(totalsTable, "IVA 15%:", String.format("$%.2f", iva15));
        }
        addTotalRow(totalsTable, "TOTAL:", String.format("$%.2f", total));

        return totalsTable;
    }
}
