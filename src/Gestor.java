import java.io.*;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Gestor {

    //FICHEROS NECESARIOS PARA EL PROGRAMA
    File ejecucion = new File("ejecucion.txt");
    File fpersonas = new File("personas.txt");
    File fasignaturas = new File("asignaturas.txt");
    File avisos = new File("avisos.txt");

    //TREEMAPS DE PERSONAS Y ASIGNATURAS, CON ID EL DNI O IDENTIFICADOR DE LA ASIGNATURA
    TreeMap<String, Persona> personas = new TreeMap<>();
    TreeMap<String, Asignatura> asignaturas = new TreeMap<>();

    public void Ejecucion() throws IOException {

        CrearPersonas();
        CrearAsignaturas();

        FileReader fr = new FileReader(ejecucion);
        BufferedReader br = new BufferedReader(fr);
        String linea, instruccion;

        while ((linea = br.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(linea);

            while (st.hasMoreTokens()) {

                instruccion = st.nextToken();

                if (instruccion.contains("InsertaPersona")) {

                    String perfil, id, nombre, apellidos, fecha1, fecha2, categoria, departamento, horasAsignables;
                    String asignaturasSuperadas = "";
                    String docenciaImpartida = "";
                    String docenciaRecibida = "";

                    perfil = st.nextToken();
                    id = st.nextToken();
                    nombre = st.nextToken();
                    apellidos = st.nextToken("\"");
                    fecha1 = st.nextToken();

                    if (perfil.contains("profesor")) {
                        categoria = st.nextToken();
                        departamento = st.nextToken();
                        horasAsignables = st.nextToken();

                        Persona p = new Profesor(perfil, nombre, apellidos, id, fecha1, categoria, departamento, horasAsignables, docenciaImpartida);

                        InsertaPersona(p);
                    } else if (perfil.contains("alumno")) {
                        fecha2 = st.nextToken();
                        Persona a = new Alumno(perfil, nombre, apellidos, id, fecha1, fecha2, asignaturasSuperadas, docenciaRecibida);

                        InsertaPersona(a);
                    }

                }

            }

        }

        System.out.println(personas.get("42536471R"));

    }

    public void CrearPersonas() throws IOException {

        //DECLARACIÓN DE LAS VARIABLES NECESARIAS PARA CREAR UNA PERSONA
        String id, horasAsignables, nombre, apellidos, departamento, categoria, fecha1, fecha2, docenciaImpartida, docenciaRecibida, asignaturasSuperadas;

        //SE LEE EL FICHERO PERSONAS.TXT PARA PODER ACCEDER A LOS DATOS
        FileReader fr = new FileReader(fpersonas);
        BufferedReader br = new BufferedReader(fr);

        String linea;

        while ((linea = br.readLine()) != null) { //MIENTRAS EXISTA OTRA LÍNEA

            if (linea.contains("*")) {
                linea = br.readLine();
            }

            id = br.readLine();
            nombre = br.readLine();
            apellidos = br.readLine();
            fecha1 = br.readLine();

            if (linea.contains("profesor")) {
                categoria = br.readLine();
                departamento = br.readLine();
                horasAsignables = br.readLine();
                docenciaImpartida = br.readLine();

                Persona p = new Profesor("profesor", nombre, apellidos, id, fecha1, categoria, departamento, horasAsignables, docenciaImpartida);

                personas.put(id, p);

            } else if (linea.contains("alumno")) {
                fecha2 = br.readLine();
                asignaturasSuperadas = br.readLine();
                docenciaRecibida = br.readLine();

                Persona a = new Alumno("alumno", nombre, apellidos, id, fecha1, fecha2, asignaturasSuperadas, docenciaRecibida);

                personas.put(id, a);
            }
        }
    }

    public void CrearAsignaturas() throws IOException {

        //DECLARACIÓN DE LAS VARIABLES NECESARIAS PARA CREAR UNA PERSONA
        String id, nombre, siglas, curso, coordinador, prerrequisitos, gruposA, gruposB;

        //SE LEE EL FICHERO PERSONAS.TXT PARA PODER ACCEDER A LOS DATOS
        FileReader fr = new FileReader(fasignaturas);
        BufferedReader br = new BufferedReader(fr);

        while ((id = br.readLine()) != null) { //MIENTRAS EXISTA OTRA LÍNEA

            if (id.contains("*")) {
                id = br.readLine();
            }

            nombre = br.readLine();
            siglas = br.readLine();
            curso = br.readLine();
            coordinador = br.readLine();
            prerrequisitos = br.readLine();
            gruposA = br.readLine();
            gruposB = br.readLine();

            Asignatura a = new Asignatura(id, nombre, siglas, curso, coordinador, prerrequisitos, gruposA, gruposB);

            asignaturas.put(id, a);

        }
    }

    public void InsertaPersona(Persona p) {
        personas.put(p.getDni(), p);
    }

    public void AsignaCoordinador(String persona, String asignatura) {

    }

    public void AsignaCargaDocente(String persona, String asignatura, String tipoGrupo, String grupo) {


    }

    public void Matricula(String alumno, String asignatura) {


    }

    public void AsignaGrupo(String alumno, String asignatura, String tipoGrupo, String grupo) {

    }

    public void Evalua(String asignatura, String cursoAcademico, String fichero) {

    }

    public void Expediente(String alumno, String salida) {

    }

    public void ObtenerCalendario(String profesor, String salida) {

    }
}