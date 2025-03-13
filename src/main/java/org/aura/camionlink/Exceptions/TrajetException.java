package org.aura.camionlink.Exceptions;

public class TrajetException extends RuntimeException {
    public TrajetException(Long id){
        super("Trajet avec id " + id + " n'existe pas");
    }
}
