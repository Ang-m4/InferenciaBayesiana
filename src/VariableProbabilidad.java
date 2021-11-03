import java.util.*;

public class VariableProbabilidad {

    String nombre;
    int cantidad;
    HashMap<String, ArrayList<Object>> matriz;

    ArrayList<String> casos;
    ArrayList<VariableProbabilidad> prev;

    VariableProbabilidad() {
        prev = new ArrayList<>();
    }

    String getNombre() {
        return this.nombre;
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    int getCantidad() {
        return cantidad;
    }

    void setMatriz(HashMap<String, ArrayList<Object>> matriz) {
        this.matriz = matriz;
    }

    HashMap<String, ArrayList<Object>> getMatriz() {
       return this.matriz;
    }

    ArrayList<String> getCasos() {
        return casos;
    }

    ArrayList<VariableProbabilidad> getPrevios() {
        return prev;
    }

    void agregarPrev(VariableProbabilidad previo) {
        this.prev.add(previo);
    }

}
