package com.example.demo.validaciones;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidacionesGenerales {

    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{1,50}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^[0-9]{8,15}$");
    private static final Pattern DIRECCION_PATTERN = Pattern.compile("^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ ,.#\\-]{1,100}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern CEDULA_PATTERN = Pattern.compile("^[0-9]{6,12}$"); // ajustar si necesitas otro formato
    private static final Pattern RUC_PATTERN = Pattern.compile("^[0-9]{6,20}$"); // ajustar segun tu RUC

    public static boolean esNombreValido(String nombre) {
        return nombre != null && nombre.trim().length() > 0 && NOMBRE_PATTERN.matcher(nombre.trim()).matches();
    }

    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        return TELEFONO_PATTERN.matcher(telefono.trim()).matches();
    }

    public static boolean esDireccionValida(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) return false;
        return DIRECCION_PATTERN.matcher(direccion.trim()).matches();
    }

    public static boolean esFechaValida(java.time.LocalDate fecha) {
        if (fecha == null) return false;
        if (!fecha.isBefore(LocalDate.now())) return false; // no futura
        int year = fecha.getYear();
        return year >= 1900 && year <= LocalDate.now().getYear();
    }

    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches() && email.trim().length() <= 100;
    }

    public static boolean esCedulaValida(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) return false;
        return CEDULA_PATTERN.matcher(cedula.trim()).matches();
    }

    public static boolean esRucValido(String ruc) {
        if (ruc == null || ruc.trim().isEmpty()) return false;
        return RUC_PATTERN.matcher(ruc.trim()).matches();
    }
}
