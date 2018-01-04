package proyecto2;

public class Nodo {
   
    
    private Nodo siguiente;
    private Nodo anterior;
    private String dato;
    
    public Nodo(String dat,Nodo ant,Nodo sig ){
    
        siguiente = sig;
        anterior = ant;
        dato = dat;
    
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
    
    
}
