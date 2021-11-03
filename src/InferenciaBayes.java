import java.util.*;
import java.io.*;

public class InferenciaBayes {

    public static void main(String[] args) throws Exception {

        // se leen los datos del archivo
        ArrayList<VariableProbabilidad> variables = crearVariables("Datos.txt");

        // Se imprimen los valores de las variables leidas
        System.out.println();
        System.out.println(variables.get(0).getNombre());
        System.out.println(variables.get(0).getCasos());
        System.out.println(variables.get(0).getMatriz() + "\n");

        System.out.println(variables.get(1).getNombre());
        System.out.println(variables.get(1).getCasos());
        System.out.println(variables.get(1).getMatriz() + "\n");

        System.out.println(variables.get(2).getNombre());
        System.out.println(variables.get(2).getCasos());
        System.out.println(variables.get(2).getMatriz() + "\n");

        System.out.println(variables.get(3).getNombre());
        System.out.println(variables.get(3).getCasos());
        System.out.println(variables.get(3).getMatriz() + "\n");

        // Ingreso de la consulta por consola P(light ∧ no ∧ delayed ∧ miss)
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingrese Su consulta: ");
        String consulta = entrada.nextLine();

        consulta = consulta.substring(consulta.indexOf("(") + 1, consulta.indexOf(")"));
        System.out.println(consulta);

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
        String[] casos = partes.split("∧");

        // En caso de ser una consulta normal
        if (casos.length == variables.size()) {

        } else { // En caso de necesitar inferencia

        }

    }
}
