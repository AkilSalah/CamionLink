package org.aura.camionlink.Exceptions;

public class PanneException extends RuntimeException{
    PanneException(long id){
        super("Panne avec id " + id + " non trouvable");
    }
}
