package org.aura.camionlink.Exceptions;

public class EntretienException extends RuntimeException {
    public EntretienException(Long id){
        super("Entretien avec id " + id + "n'existe pas");
    }
}
