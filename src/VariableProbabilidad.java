import java.util.*;

public class VariableProbabilidad {

    String nombre;
    HashSet<String> casos;
    HashMap<String, ArrayList<Object>> matriz;
    ArrayList<VariableProbabilidad> prev;

    VariableProbabilidad() {
        prev = new ArrayList<>();
        casos = new HashSet<>();
    }

    String getNombre() {
        return this.nombre;
    }

    HashSet<String> getCasos(){
        return this.casos;
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    void setMatriz(HashMap<String, ArrayList<Object>> matriz) {
        this.matriz = matriz;
    }

    HashMap<String, ArrayList<Object>> getMatriz() {
        return this.matriz;
    }

    ArrayList<VariableProbabilidad> getPrevios() {
        return prev;
    }

    void agregarPrev(VariableProbabilidad previo) {
        this.prev.add(previo);
    }

}
