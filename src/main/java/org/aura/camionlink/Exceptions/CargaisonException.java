package org.aura.camionlink.Exceptions;

public class CargaisonException extends RuntimeException {
    public CargaisonException (Long id){
        super("Cargaison avec id" + id +" n'existe pas");
    }
}
