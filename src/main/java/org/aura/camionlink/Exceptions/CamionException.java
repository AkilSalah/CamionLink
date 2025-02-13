package org.aura.camionlink.Exceptions;

public class CamionException extends RuntimeException {
    public CamionException(Long id){
        super("Camion avec id: " + id +" n'existe pas");
    }
}
