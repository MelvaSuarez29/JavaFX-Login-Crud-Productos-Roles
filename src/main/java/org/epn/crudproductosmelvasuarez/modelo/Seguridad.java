package org.epn.crudproductosmelvasuarez.modelo;


import org.mindrot.jbcrypt.BCrypt;

public class Seguridad {

    public static String generarHash(String passwordPlana) {
        return BCrypt.hashpw(passwordPlana, BCrypt.gensalt());
    }
    public static boolean validarPassword(String passwordPlana, String passHash) {
        return BCrypt.checkpw(passwordPlana, passHash);
    }
}