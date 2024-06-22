package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {
    private static final String[][] provincias = {
            {"09", "Guayas", "Guayaquil"},
            {"10", "Imbabura", "Ibarra"},
            {"17", "Pichincha", "Quito"},
            {"12", "Los Ríos", "Babahoyo"},
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la cédula o el número de RUC: ");
        String identificacion = scanner.nextLine();

        boolean esValido = false;
        String provinciaYCiudad = "";

        if (identificacion.length() == 10) {
            esValido = validarCedula(identificacion);
            if (esValido) {
                provinciaYCiudad = obtenerCiudad(identificacion);
            }
        } else if (identificacion.length() == 13) {
            int tercerDigito = Character.getNumericValue(identificacion.charAt(2));
            if (tercerDigito < 6) {
                esValido = validarRUCPersonaNatural(identificacion);
            } else if (tercerDigito == 6) {
                esValido = validarRUCPublico(identificacion);
            } else if (tercerDigito == 9) {
                esValido = validarRUCJuridico(identificacion);
            }
            if (esValido) {
                provinciaYCiudad = obtenerCiudad(identificacion);
            }
        } else {
            System.out.println("La identificación ingresada no es válida. Debe tener 10 dígitos para cédula o 13 dígitos para RUC.");
            return;
        }

        System.out.println("La identificación " + identificacion + " es " + (esValido ? "válida" : "inválida"));
        if (esValido) {
            System.out.println(provinciaYCiudad);
        }
    }

    public static boolean validarCedula(String cedula) {
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || provincia > 24) {
                return false;
            }

            int tercerDigito = Character.getNumericValue(cedula.charAt(2));
            if (tercerDigito < 0 || tercerDigito > 5) {
                return false;
            }

            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;

            for (int i = 0; i < coeficientes.length; i++) {
                int digito = Character.getNumericValue(cedula.charAt(i));
                int resultado = digito * coeficientes[i];
                if (resultado >= 10) {
                    resultado -= 9;
                }
                suma += resultado;
            }

            int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
            int modulo = suma % 10;
            int resultado2 = modulo == 0 ? 0 : 10 - modulo;

            // Verificar el dígito verificador
            return resultado2 == digitoVerificador;

        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean validarRUC(String ruc) {
        if (ruc == null || ruc.length() != 13) {
            return false;
        }

        try {
            // Verificar que no contenga letras ni caracteres especiales
            if (!ruc.matches("\\d{13}")) {
                return false;
            }

            // Verificar los dos primeros dígitos (provincia)
            int provincia = Integer.parseInt(ruc.substring(0, 2));
            if (provincia < 1 || provincia > 22) {
                return false;
            }

            // Verificar el tercer dígito (6 ó 9)
            int tercerDigito = Character.getNumericValue(ruc.charAt(2));
            if (tercerDigito != 6 && tercerDigito != 9) {
                return false;
            }

            // Verificar los últimos tres dígitos (001, 002, 003, etc.)
            int establecimiento = Integer.parseInt(ruc.substring(10, 13));
            if (establecimiento == 0) {
                return false;
            }

            // Calcular el dígito verificador usando "Módulo 11"
            int[] coeficientes = tercerDigito == 6 ? new int[]{3, 2, 7, 6, 5, 4, 3, 2} : new int[]{4, 3, 2, 7, 6, 5, 4, 3, 2};
            int suma = 0;

            for (int i = 0; i < coeficientes.length; i++) {
                int digito = Character.getNumericValue(ruc.charAt(i));
                suma += digito * coeficientes[i];
            }

            int modulo = suma % 11;
            int resultado = modulo == 0 ? 0 : 11 - modulo;

            // Verificar el dígito verificador
            int digitoVerificador = tercerDigito == 6 ? Character.getNumericValue(ruc.charAt(8)) : Character.getNumericValue(ruc.charAt(9));
            return resultado == digitoVerificador;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarRUCPersonaNatural(String ruc) {
        if (ruc == null || ruc.length() != 13) {
            return false;
        }

        if (!ruc.matches("\\d{13}")) {
            return false;
        }

        String cedula = ruc.substring(0, 10);
        if (!validarCedula(cedula)) {
            return false;
        }

        int establecimiento = Integer.parseInt(ruc.substring(10, 13));
        if (establecimiento == 0) {
            return false;
        }

        return true;
    }

    public static boolean validarRUCPublico(String ruc) {
        if (ruc == null || ruc.length() != 13) {
            return false;
        }

        if (!ruc.matches("\\d{13}")) {
            return false;
        }

        int provincia = Integer.parseInt(ruc.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int tercerDigito = Character.getNumericValue(ruc.charAt(2));
        if (tercerDigito != 6) {
            return false;
        }

        int establecimiento = Integer.parseInt(ruc.substring(10, 13));
        if (establecimiento == 0) {
            return false;
        }

        int[] coeficientes = {3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;

        for (int i = 0; i < coeficientes.length; i++) {
            int digito = Character.getNumericValue(ruc.charAt(i));
            suma += digito * coeficientes[i];
        }

        int modulo = suma % 11;
        int resultado = modulo == 0 ? 0 : 11 - modulo;

        int digitoVerificador = Character.getNumericValue(ruc.charAt(8));
        return resultado == digitoVerificador;
    }

    public static boolean validarRUCJuridico(String ruc) {
        if (ruc == null || ruc.length() != 13) {
            return false;
        }

        if (!ruc.matches("\\d{13}")) {
            return false;
        }

        int provincia = Integer.parseInt(ruc.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int tercerDigito = Character.getNumericValue(ruc.charAt(2));
        if (tercerDigito != 9) {
            return false;
        }

        int establecimiento = Integer.parseInt(ruc.substring(10, 13));
        if (establecimiento == 0) {
            return false;
        }

        int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;

        for (int i = 0; i < coeficientes.length; i++) {
            int digito = Character.getNumericValue(ruc.charAt(i));
            suma += digito * coeficientes[i];
        }

        int modulo = suma % 11;
        int resultado = modulo == 0 ? 0 : 11 - modulo;

        int digitoVerificador = Character.getNumericValue(ruc.charAt(9));
        return resultado == digitoVerificador;
    }

    public static String obtenerCiudad(String cedula) {
        String codigoProvincia = cedula.substring(0, 2);
        for (String[] provincia : provincias) {
            if (provincia[0].equals(codigoProvincia)) {
                return "Ciudad: " + provincia[2];
            }
        }
        return "Desconocida";
    }

}
