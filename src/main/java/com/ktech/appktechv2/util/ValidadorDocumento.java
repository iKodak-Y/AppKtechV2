package com.ktech.appktechv2.util;

public class ValidadorDocumento {

    /**
     * Valida un número de cédula ecuatoriana.
     *
     * @param cedula Número de cédula a validar.
     * @return true si la cédula es válida, false en caso contrario.
     */
    public static boolean validarCedula(String cedula) {
        if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d{10}")) {
            return false;
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;

        for (int i = 0; i < coeficientes.length; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            suma += (digito > 9) ? digito - 9 : digito;
        }

        int verificador = (10 - (suma % 10)) % 10;
        return verificador == Character.getNumericValue(cedula.charAt(9));
    }

    /**
     * Valida un número de RUC ecuatoriano.
     *
     * @param ruc Número de RUC a validar.
     * @return true si el RUC es válido, false en caso contrario.
     */
    public static boolean validarRUC(String ruc) {
        if (ruc == null || ruc.length() != 13 || !ruc.matches("\\d{13}") || !ruc.endsWith("001")) {
            return false;
        }
        return validarCedula(ruc.substring(0, 10));
    }

    /**
     * Verifica si un campo de texto está vacío o nulo.
     *
     * @param campo Texto a verificar.
     * @return true si el campo está vacío o nulo, false si contiene texto.
     */
    public static boolean esCampoVacio(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}
