import java.util.*;
import java.io.*;

public class InferenciaBayes {

    public static void main(String[] args) throws Exception {

        // se leen los datos del archivo
        ArrayList<VariableProbabilidad> variables = crearVariables("Datos.txt");

        // Se imprimen los valores de las variables leidas
        // System.out.println();
        // System.out.println(variables.get(0).getNombre());
        // System.out.println(variables.get(0).getCasos());
        // System.out.println(variables.get(0).getMatriz() + "\n");

        // System.out.println(variables.get(1).getNombre());
        // System.out.println(variables.get(1).getCasos());
        // System.out.println(variables.get(1).getMatriz() + "\n");

        // System.out.println(variables.get(2).getNombre());
        // System.out.println(variables.get(2).getCasos());
        // System.out.println(variables.get(2).getMatriz() + "\n");

        // System.out.println(variables.get(3).getNombre());
        // System.out.println(variables.get(3).getCasos());
        // System.out.println(variables.get(3).getMatriz() + "\n");

        // Ingreso de la consulta por consola P(light ∧ no ∧ delayed ∧ miss)
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingrese Su consulta: ");
        String consulta = entrada.nextLine();

        consulta = consulta.substring(consulta.indexOf("(") + 1, consulta.indexOf(")"));

        System.err.println();

        procesarConsulta(consulta, variables);

        entrada.close();

    }

    static ArrayList<VariableProbabilidad> crearVariables(String nombreArchivo) throws Exception {

        ArrayList<VariableProbabilidad> variables = new ArrayList<>();

        File archivo = new File(nombreArchivo);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        VariableProbabilidad auxiliar = new VariableProbabilidad();
        HashMap<String, ArrayList<Object>> matrizAux = new HashMap<>();
        String linea;
        String[] llaves = new String[0];

        while ((linea = br.readLine()) != null) {

            if (linea.contains(":")) {
                auxiliar = new VariableProbabilidad();
                auxiliar.setNombre(linea.substring(0, linea.length() - 1));
                variables.add(auxiliar);

            } else {

                if (linea.contains("-")) {

                    matrizAux = new HashMap<>();
                    int indice = 0;
                    ArrayList<ArrayList<Object>> datos = new ArrayList<>();

                    for (int i = 0; i < linea.split(",").length; i++) {

                        ArrayList<Object> aux = new ArrayList<>();
                        datos.add(aux);
                    }

                    llaves = linea.substring(0, linea.length() - 1).split(",");
                    ArrayList<String> nombreVariables = new ArrayList<>();

                    for (String a : llaves) {

                        for (VariableProbabilidad var : variables) {

                            nombreVariables.add(var.getNombre());

                            if (var.getNombre().equals(a)) {

                                auxiliar.getPrevios().add(var);

                            }

                        }

                        if (!nombreVariables.contains(a)) {
                            auxiliar.getCasos().add(a);
                        }

                        matrizAux.put(a, datos.get(indice));
                        indice++;
                    }

                    auxiliar.setMatriz(matrizAux);

                } else {

                    if (linea.length() != 0) {

                        String[] probabilidades = linea.split(",");

                        for (String llave : matrizAux.keySet()) {

                            for (int j = 0; j < llaves.length; j++) {

                                if (llave.equals(llaves[j])) {

                                    matrizAux.get(llave).add(probabilidades[j]);

                                }

                            }

                        }

                    } else {

                    }

                }

            }

        }

        br.close();

        return variables;

    }

    static void procesarConsulta(String consulta, ArrayList<VariableProbabilidad> variables) {

        String partes = consulta.replace(" ", "");
        List<String> casos = Arrays.asList(partes.split("∧"));

        String consultaActualizada = new String();
        String aux1;

        // En caso de ser una consulta sin variables ocultas
        if (casos.size() == variables.size()) {

            for (String caso : casos) {

                aux1 = "";
                consultaActualizada = consultaActualizada + "P()";

                for (VariableProbabilidad variable : variables) {

                    if (variable.getCasos().contains(caso)) {

                        // si tiene previos
                        if (variable.getPrevios().size() != 0) {

                            aux1 = aux1 + caso + "|";

                            for (VariableProbabilidad previo : variable.getPrevios()) {

                                for (String casoAux : casos) {

                                    if (previo.getCasos().contains(casoAux)) {

                                        aux1 = aux1 + casoAux + " ";

                                    }

                                }

                            }

                        } else { // si no tiene previos

                            aux1 = aux1 + caso;

                        }

                    }

                }

                consultaActualizada = consultaActualizada.replace("P()", "P(" + aux1 + ")");

            }

            consultaActualizada = consultaActualizada.replace(" )", ")");
            System.out.println(consultaActualizada);

            // calcular probabilidad:

            System.out.println(calcularProbabilidad(consultaActualizada, variables));

        } else { // En caso de tener variables ocultas











            
        }

    }

    static double calcularProbabilidad(String consulta, ArrayList<VariableProbabilidad> variables) {

        // P(light ∧ no ∧ delayed ∧ miss)
        consulta = consulta.replaceFirst("P", "");
        List<String> partes = Arrays.asList(consulta.split("P"));
        double retorno = 1;
        Double[] probabilidades = new Double[partes.size()];

        int indice = 0;
        int i;
        for (String parte : partes) {

            parte = parte.substring(1, parte.length() - 1);

            if (parte.contains("|")) {

                parte = parte.replace('|', ' ');

                List<String> datos = Arrays.asList(parte.split(" "));

               

                for (VariableProbabilidad variable : variables) {

                    if (variable.getCasos().contains(datos.get(0))) {

                        i = buscarIndice(datos, variable.getMatriz());
                        
                        
                        probabilidades[indice] = Double.parseDouble((String)(variable.getMatriz().get(datos.get(0)).get(i))); 
                        System.out.println(probabilidades[indice]);
                        


                    }

                }

            } else {

                for (VariableProbabilidad variable : variables) {

                    if (variable.getCasos().contains(parte)) {

                        probabilidades[indice] = Double.parseDouble((String)(variable.getMatriz().get(parte).get(0))); 
                        System.out.println(probabilidades[indice]);

                    }

                }

            }

            indice++;

        }


        for (int j = 0; j < probabilidades.length; j++) {
            
            retorno = retorno * probabilidades[j];

        }


        return retorno;
    }

    static int buscarIndice(List<String> datos, HashMap<String, ArrayList<Object>> matriz) {

        String[] llaves = new String[datos.size() - 1];
        ArrayList<ArrayList<Object>> arreglos = new ArrayList<>();

        for (int i = 1; i < datos.size(); i++) {

            for (String llave : matriz.keySet()) {

                if (matriz.get(llave).contains(datos.get(i))) {

                    llaves[i - 1] = llave;
                    arreglos.add(matriz.get(llave));

                }

            }

        }

        if (llaves.length == 1) {

            int retorno = -1;
            for (int i = 0; i < arreglos.get(0).size(); i++) {                

                if(arreglos.get(0).get(i).equals(datos.get(1))){
                    

                    retorno = i;
                }

            }         

            
            return retorno;

        }
        
        // P(light ∧ no ∧ delayed ∧ miss)
        boolean encontro = false;
        int j = 0;
        int indiceA = 0;

        while(!encontro){


            for (int i = 0; i < arreglos.size(); i++) {
                

                

                if(arreglos.get(i).get(j).equals(datos.get(i+1))){

                    
                    indiceA++;

                }else{
                    
                    i++;
                    
                    indiceA = 0;
                }

                

                if(indiceA == datos.size()-1){
                    
                   
                    return j;
                }



            }

            j++;

        }
        
        return 0;
    }
}
