/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CifradoAES {

    private static final String CLAVE = "clave12345678901"; // 16 caracteres (AES-128)

    public static String encriptar(String texto) {
        try {
            SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encriptado = cipher.doFinal(texto.getBytes());
            return Base64.getEncoder().encodeToString(encriptado);
        } catch (Exception e) {
            return null;
        }
    }

    public static String desencriptar(String textoEncriptado) {
        try {
            SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
            return new String(desencriptado);
        } catch (Exception e) {
            return null;
        }
    }
}
