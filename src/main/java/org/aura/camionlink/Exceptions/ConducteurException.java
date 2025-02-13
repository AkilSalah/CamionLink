package org.aura.camionlink.Exceptions;

public class ConducteurException extends RuntimeException {
    public ConducteurException (Long id){
        super("Conducteur avec id " + id+ "n'existe pas");
    }
    
}
