package org.aura.camionlink.Exceptions;

public class PanneException extends RuntimeException{
    public PanneException(Long id){
        super("Panne avec id " + id + " non trouvable");
    }
}
