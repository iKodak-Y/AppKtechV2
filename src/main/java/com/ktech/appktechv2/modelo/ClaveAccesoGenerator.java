package com.ktech.appktechv2.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClaveAccesoGenerator {

    /**
     * Genera la clave de acceso según las especificaciones del SRI.
     *
     * @param fechaEmision Fecha de emisión en formato Date.
     * @param tipoComprobante Tipo de comprobante (Ej: "01" para factura).
     * @param ruc RUC del emisor.
     * @param tipoAmbiente Tipo de ambiente (1: Pruebas, 2: Producción).
     * @param serie Serie del establecimiento y punto de emisión (Ej: "001001").
     * @param secuencial Número secuencial de la factura (Ej: "000000001").
     * @param codigoNumerico Código numérico generado aleatoriamente.
     * @return Clave de acceso generada (49 caracteres).
     */
    public static String generarClaveAcceso(Date fechaEmision, String tipoComprobante, String ruc,
            String tipoAmbiente, String serie, String secuencial,
            String codigoNumerico) {
        try {
            // Formatear la fecha en formato ddMMyyyy
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            String fecha = dateFormat.format(fechaEmision);

            // Concatenar los 48 dígitos iniciales
            String claveAccesoSinDV = fecha + tipoComprobante + ruc + tipoAmbiente
                    + serie + secuencial + codigoNumerico + "1"; // Tipo de emisión (1: Normal)

            // Calcular el dígito verificador
            int digitoVerificador = calcularDigitoVerificador(claveAccesoSinDV);

            // Concatenar el dígito verificador
            return claveAccesoSinDV + digitoVerificador;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la clave de acceso: " + e.getMessage());
        }
    }

    /**
     * Calcula el dígito verificador usando el algoritmo módulo 11.
     *
     * @param clave La clave de acceso sin el dígito verificador (48
     * caracteres).
     * @return Dígito verificador (1 caracter).
     */
    private static int calcularDigitoVerificador(String clave) {
        int suma = 0;
        int peso = 2;

        // Iterar de derecha a izquierda
        for (int i = clave.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(clave.charAt(i)) * peso;
            peso = (peso == 7) ? 2 : peso + 1;
        }

        int residuo = suma % 11;
        return (residuo == 0) ? 0 : 11 - residuo;
    }

    public static String generarCodigoNumerico() {
        // Genera un número aleatorio de 8 dígitos
        return String.format("%08d", (int) (Math.random() * 100000000));
    }
}
