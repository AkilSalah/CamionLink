package org.aura.camionlink.Exceptions;

public class TrajetException extends RuntimeException {
    public TrajetException(Long id){
        super("Traget avec id " + id + "n'existe pas");
    }
}
