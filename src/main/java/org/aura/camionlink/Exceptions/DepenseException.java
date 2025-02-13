package org.aura.camionlink.Exceptions;

public class DepenseException extends RuntimeException {
    public DepenseException(Long id) {
        super("Depense avec id " + id + " n'existe pas");
    }

}
