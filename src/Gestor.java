import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("ALL")
public class Gestor {

    //FICHEROS NECESARIOS PARA EL PROGRAMA

    File ejecucion = new File("ejecucion.txt");
    File fpersonas = new File("personas.txt");
    File fasignaturas = new File("asignaturas.txt");
    static File avisos = new File("avisos.txt");
    public static BufferedWriter aw;

    static {
        try {
            aw = new BufferedWriter(new FileWriter(avisos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TREEMAPS DE PERSONAS Y ASIGNATURAS, CON ID EL DNI O IDENTIFICADOR DE LA ASIGNATURA

    TreeMap<String, Persona> personas = new TreeMap<>();
    TreeMap<Integer, Asignatura> asignaturas = new TreeMap<>();

    /**
     * Método Ejecucución.
     * Es llamado desde el Main y lo que hace es:
     * -Crear las asignaturas y personas recibidas de los ficheros para poder utilizarlas en el programa.
     * -Leer el fichero ejecucion para seleccionar lo que debe hacer en cada caso.
     * -Gestionar los ficheros.
     *
     * @throws IOException
     */
    public void Ejecucion() throws IOException {
        String linea, instruccion, s;

        CrearPersonas();
        CrearAsignaturas();

        FileReader fr = new FileReader(ejecucion);
        BufferedReader br = new BufferedReader(fr);

        while ((linea = br.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(linea);

            while (st.hasMoreTokens()) {

                instruccion = st.nextToken();

                if (instruccion.contentEquals("InsertaPersona") || instruccion.contentEquals("AsignaCoordinador") || instruccion.contentEquals("AsignaCargaDocente") || instruccion.contentEquals("Matricula") || instruccion.contentEquals("AsignaGrupo") || instruccion.contentEquals("Evalua") || instruccion.contentEquals("Expediente") || instruccion.contentEquals("ObtenerCalendarioClases") || instruccion.contentEquals("OrdenaAlumnosXNota")) {
                    try {
                        switch (instruccion) {
                            case "InsertaPersona":
                                String perfil, id, nombre, apellidos, fecha1, fecha2, categoria, departamento, horasAsignables;
                                String asignaturasSuperadas = "";
                                String docenciaImpartida = "";
                                String docenciaRecibida = "";

                                perfil = st.nextToken();
                                id = st.nextToken();
                                nombre = st.nextToken(" \"").concat(st.nextToken("\""));
                                apellidos = st.nextToken(" \"").concat(st.nextToken("\""));
                                st.nextToken(" ");
                                fecha1 = st.nextToken();

                                if (perfil.contentEquals("profesor")) {
                                    categoria = st.nextToken();
                                    departamento = st.nextToken(" \"").concat(" ").concat(st.nextToken(" ").replace("\"", ""));
                                    horasAsignables = st.nextToken().replace(" ", "");

                                    Persona p = new Profesor(perfil, nombre, apellidos, id, fecha1, categoria, departamento, horasAsignables, docenciaImpartida);
                                    InsertaPersona(p);
                                } else if (perfil.contentEquals("alumno")) {
                                    fecha2 = st.nextToken();

                                    Persona a = new Alumno(perfil, nombre, apellidos, id, fecha1, fecha2, asignaturasSuperadas, docenciaRecibida);
                                    InsertaPersona(a);
                                }
                                break;
                            case "AsignaCoordinador": //AsignaCoordinador persona asignatura
                                String persona = st.nextToken();
                                String asignatura = st.nextToken();
                                AsignaCoordinador(persona, asignatura);
                                break;
                            case "AsignaCargaDocente": //AsignaCargaDocente persona asignatura tipoGrupo grupo
                                String profesor = st.nextToken();
                                String siglasAsig = st.nextToken();
                                String tipoGrupo = st.nextToken();
                                String idGrupo = st.nextToken();
                                AsignaCargaDocente(profesor, siglasAsig, tipoGrupo, idGrupo);
                                break;
                            case "Matricula": //Matricula alumno asignatura
                                String alumno = st.nextToken();
                                String siglasAsignatura = st.nextToken();
                                Matricula(alumno, siglasAsignatura);
                                break;
                            case "AsignaGrupo": //AsignaGrupo alumno asignatura tipoGrupo grupo
                                String DNIalumno = st.nextToken();
                                String IDasignatura = st.nextToken();
                                String tGrupo = st.nextToken();
                                String iGrupo = st.nextToken();
                                AsignaGrupo(DNIalumno, IDasignatura, tGrupo, iGrupo);
                                break;
                            case "Evalua": //Evalua asignatura cursoAcademico fichero
                                String asignaturaID = st.nextToken();
                                String cursoAcademico = st.nextToken();
                                String fichero = st.nextToken();
                                Evalua(asignaturaID, cursoAcademico, fichero);
                                break;
                            case "Expediente": //Expediente alumno salida
                                String alumnID = st.nextToken();
                                String salida = st.nextToken();
                                Expediente(alumnID, salida);
                                break;
                            case "ObtenerCalendarioClases": //ObtenerCalendarioClases profesor salida
                                String profesID = st.nextToken();
                                String fsalida = st.nextToken();
                                ObtenerCalendario(profesID, fsalida);
                                break;
                            case "OrdenaAlumnosXNota": //OrdenaAlumnosXNota salida
                                String salidaOrdenados = st.nextToken();
                                OrdenaAlumnosXNota(salidaOrdenados);
                                break;
                        }
                    } catch (NoSuchElementException e) {
                        s = "Numero de argumentos incorrecto\n";
                        aw.write(s);
                    }
                } else {
                    if (instruccion.contains("*")) {
                        break;
                    }
                    s = "Comando incorrecto\n";
                    aw.write(s);
                    break;
                }
            }
        }

        GestionaFicheros();
        aw.close();
    }

    //MÉTODOS PARA CREAR LAS PERSONAS Y ASIGNATURAS

    /**
     * Método que lee el fichero personas.txt y crea las personas, introduciendolas en el TreeMap con su DNI como key.
     *
     * @throws IOException
     */
    public void CrearPersonas() throws IOException {

        //DECLARACIÓN DE LAS VARIABLES NECESARIAS PARA CREAR UNA PERSONA
        String id, horasAsignables, nombre, apellidos, departamento, categoria, fecha1, fecha2, docenciaImpartida, docenciaRecibida, asignaturasSuperadas;

        //SE LEE EL FICHERO PERSONAS.TXT PARA PODER ACCEDER A LOS DATOS
        FileReader fr = new FileReader(fpersonas);
        BufferedReader br = new BufferedReader(fr);

        String linea;

        while ((linea = br.readLine()) != null) { //MIENTRAS EXISTA OTRA LINEA

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

    /**
     * Método que lee el fichero asignaturas.txt, creando las asignaturas e introduciendolas en el TreeMap con su ID como key.
     *
     * @throws IOException
     */
    public void CrearAsignaturas() throws IOException {

        //DECLARACIÓN DE LAS VARIABLES NECESARIAS PARA CREAR UNA PERSONA
        String id, nombre, siglas, curso, coordinador, prerrequisitos, gruposA, gruposB;

        //SE LEE EL FICHERO PERSONAS.TXT PARA PODER ACCEDER A LOS DATOS
        FileReader fr = new FileReader(fasignaturas);
        BufferedReader br = new BufferedReader(fr);

        while ((id = br.readLine()) != null) { //MIENTRAS EXISTA OTRA LINEA

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

            asignaturas.put(Integer.parseInt(id.replace(" ", "")), a);

        }
    }

    //MÉTODOS PARA LAS FUNCIONALIDADES

    /**
     * Método para introducir una persona nueva.
     * Comprueba que no haya errores, y si los hay crea el aviso.
     * En caso de que no haya errores crea la persona y la añade al TreeMap y al fichero personas.txt.
     *
     * @param p Persona a añadir.
     * @throws IOException
     */
    public void InsertaPersona(Persona p) throws IOException {
        String s, clave = "IP -- ";
        if (!p.getDni().matches("(\\d{8})([-]?)([A-Z]{1})")) {
            s = clave + "DNI incorrecto\n";
            aw.write(s);
            return;
        }
        if (p instanceof Alumno) {
            if (!CompruebaFecha(((Alumno) p).getFecha1(), ((Alumno) p).getFecha2())) {
                s = clave + "Fecha incorrecta\n";
                aw.write(s);
                return;
            }
            if (!CompruebaFechaIngreso(((Alumno) p).getFecha1(), ((Alumno) p).getFecha2())) {
                s = clave + "Fecha de ingreso incorrecta\n";
                aw.write(s);
                return;
            }
        } else {
            if (!CompruebaFecha(((Profesor) p).getFecha1())) {
                s = clave + "Fecha incorrecta\n";
                aw.write(s);
                return;
            }
            if (!CompruebaHorasAsignables((Profesor) p)) {
                s = clave + "Numero de horas incorrecto\n";
                aw.write(s);
                return;
            }
            if (CompruebaExistencia(p)) {
                if (p instanceof Alumno) {
                    s = clave + "Alumno ya existente\n";
                } else {
                    s = clave + "Profesor ya existente\n";
                }
                aw.write(s);
                return;
            }

        }

        personas.put(p.getDni(), p);

        s = "OK\n";
        aw.write(s);

    }

    /**
     * Métdodo que comprueba que no haya los errores pertinentes y en caso de que este correcto asigna el coordinador a la asignatura.
     *
     * @param persona    Profesor que se asignara como coordinador.
     * @param asignatura Asignatura de la que se quiere que sea coordinador.
     * @throws IOException
     */
    public void AsignaCoordinador(String persona, String asignatura) throws IOException {
        String s, clave = "ACOORD -- ";
        int idAsignatura = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();
        if (!personas.containsKey(persona)) {
            s = clave + "Profesor inexistente\n";
            aw.write(s);
            return;
        }
        if (!((Profesor) personas.get(persona)).getCategoria().contentEquals("titular")) {
            s = clave + "Profesor no titular\n";
            aw.write(s);
            return;
        }
        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contains(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId().replaceAll(" ", ""));
                break;
            }
            if (!it.hasNext()) {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }
        if (NumeroAsignaturasCoordinador(persona) >= 2) {
            s = clave + "Profesor ya es coordinador de 2 materias\n";
            aw.write(s);
            return;
        }

        asignaturas.get(idAsignatura).setCoordinador(persona);

        s = clave + "OK\n";
        aw.write(s);
    }

    /**
     * Metodo para asignar un profesor a un grupo de una asignatura.
     *
     * @param persona    Profesor que se asignará.
     * @param asignatura Asignatura a la que se asignará.
     * @param tipoGrupo  Tipo de grupo (A/B).
     * @param idGrupo    Id del grupo.
     * @throws IOException
     */
    public void AsignaCargaDocente(String persona, String asignatura, String tipoGrupo, String idGrupo) throws IOException {
        String s, clave = "ACDOC -- ";
        int idAsignatura = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        if (!personas.containsKey(persona)) {
            s = clave + "Profesor inexistente\n";
            aw.write(s);
            return;
        }
        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contains(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId().replaceAll(" ", ""));
                break;
            }
            if (!it.hasNext()) {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }
        if (!(tipoGrupo.contains("A") || tipoGrupo.contains("B"))) {
            s = clave + "Tipo de grupo incorrecto\n";
            aw.write(s);
            return;
        }
        if (!GrupoDeAsignatura(asignatura, tipoGrupo, idGrupo)) {
            s = clave + "Grupo inexistente\n";
            aw.write(s);
            return;
        }
        if (GrupoYaAsignado(asignatura, tipoGrupo, idGrupo)) {
            s = clave + "Grupo ya asignado\n";
            aw.write(s);
            return;
        }

        if (GeneraSolape(persona, asignatura, tipoGrupo, idGrupo)) {
            s = clave + "Se genera solape\n";
            aw.write(s);
            return;
        }

        ((Profesor) personas.get(persona)).setDocenciaImpartida(idAsignatura + " " + tipoGrupo + " " + idGrupo);

    }

    /**
     * Metodo para enrolar un alumno en una asignatura.
     *
     * @param alumno     Alumno a matricular.
     * @param asignatura Asignatura en la que se desea matricular.
     * @throws IOException
     */
    public void Matricula(String alumno, String asignatura) throws IOException {
        String s, stringId = "", clave = "MAT -- ";
        int idAsignatura = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        if (!personas.containsKey(alumno)) {
            s = clave + "Alumno inexistente\n";
            aw.write(s);
            return;
        }
        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contains(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId().replaceAll(" ", ""));
                stringId = asignaturas.get(key).getId().replaceAll(" ", "");
                break;
            }
            if (!it.hasNext()) {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }
        if (((Alumno) personas.get(alumno)).getDocenciaRecibida().contains(stringId)) {
            s = clave + "Ya es alumno de la asignatura indicada\n";
            aw.write(s);
            return;
        }
        if (!((Alumno) personas.get(alumno)).getAsignaturasSuperadas().contains(asignaturas.get(idAsignatura).getPrerrequisitos())) {
            s = clave + "No cumple requisitos\n";
            aw.write(s);
            return;
        }

        ((Alumno) personas.get(alumno)).setDocenciaRecibida(stringId);

        s = clave + "OK\n";
        aw.write(s);

    }

    /**
     * Metodo para asignar un grupo de una asignatura a un alumno previamente matriculado en ella
     *
     * @param alumno     Alumno a asignar.
     * @param asignatura Asignatura en la que se desea asignar.
     * @param tipoGrupo  (A/B)
     * @param idGrupo    Id del grupo.
     * @throws IOException
     */
    public void AsignaGrupo(String alumno, String asignatura, String tipoGrupo, String idGrupo) throws IOException {
        String s, stringId = "", clave = "AGRUPO -- ";
        int idAsignatura = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();
        if (!personas.containsKey(alumno)) {
            s = clave + "Alumno inexistente\n";
            aw.write(s);
            return;
        }
        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contains(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId().replaceAll(" ", ""));
                stringId = asignaturas.get(key).getId().replaceAll(" ", "");
                break;
            }
            if (!it.hasNext()) {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }
        if (!((Alumno) personas.get(alumno)).getDocenciaRecibida().contains(stringId)) {
            s = clave + "Alumno no matriculado\n";
            aw.write(s);
            return;
        }
        if (!(tipoGrupo.contains("A") || tipoGrupo.contains("B"))) {
            s = clave + "Tipo de grupo incorrecto\n";
            aw.write(s);
            return;
        }
        if (!GrupoDeAsignatura(asignatura, tipoGrupo, idGrupo)) {
            s = clave + "Grupo inexistente\n";
            aw.write(s);
            return;
        }

        String nuevo = "; " + asignatura + " " + tipoGrupo + " " + idGrupo;
        String viejo = ((Alumno) personas.get(alumno)).getDocenciaRecibida();

        ((Alumno) personas.get(alumno)).setDocenciaRecibida(viejo + nuevo);

        s = clave + "OK\n";
        aw.write(s);
    }

    /**
     * Metodo que permite introducir las notas de una asignatura y modificar de modo consecuente toda la información en el sistema
     *
     * @param asignatura     Asignatura.
     * @param cursoAcademico Curso del que son las notas.
     * @param fichero        Nombre del fichero de entrada.
     * @throws IOException
     */
    public void Evalua(String asignatura, String cursoAcademico, String fichero) throws IOException {
        String s, stringId = "", clave = "EVALUA -- ", linea, alumno = null;
        int idAsignatura = 0, numeroLinea = 0;
        float notaA = 0, notaB = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contains(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId().replaceAll(" ", ""));
                stringId = asignaturas.get(key).getId().replaceAll(" ", "");
                break;
            }
            if (!it.hasNext()) {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }

        try {
            File evaluacion = new File(fichero);
            FileReader fr = new FileReader(evaluacion);
            BufferedReader br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                numeroLinea++;

                StringTokenizer st = new StringTokenizer(linea);

                while (st.hasMoreTokens()) {
                    alumno = st.nextToken().replaceAll(" ", "");
                    notaA = Integer.parseInt(st.nextToken().replaceAll(" ", ""));
                    notaB = Integer.parseInt(st.nextToken().replaceAll(" ", ""));
                }
                if (personas.containsKey(alumno)) {
                    String superadas = ((Alumno) personas.get(alumno)).getAsignaturasSuperadas();
                    StringTokenizer supt = new StringTokenizer(superadas);
                    String sup;

                    while (supt.hasMoreTokens()) {
                        sup = supt.nextToken(";");
                        if (sup.contains(cursoAcademico)) {
                            s = clave + "Asignatura ya evaluada este curso académico\n";
                            aw.write(s);
                            return;
                        }
                    }
                }

                if (GestionaFicheroEval(alumno, notaA, notaB, numeroLinea, stringId)) {
                    float notaTotal = notaA + notaB;

                    if (notaTotal >= 5) {
                        String anteriores = ((Alumno) personas.get(alumno)).getAsignaturasSuperadas();

                        if (anteriores.replaceAll(" ", "") == "") {
                            ((Alumno) personas.get(alumno)).setAsignaturasSuperadas(anteriores + "; " + stringId + " " + cursoAcademico + " " + notaTotal);
                        } else {
                            ((Alumno) personas.get(alumno)).setAsignaturasSuperadas(stringId + " " + cursoAcademico + " " + notaTotal);
                        }

                        String docenciaRecibida = ((Alumno) personas.get(alumno)).getDocenciaRecibida();
                        StringTokenizer doct = new StringTokenizer(docenciaRecibida);
                        String recibida, nueva = "";

                        while (doct.hasMoreTokens()) {
                            recibida = doct.nextToken(";");
                            if (!recibida.contains(stringId)) {
                                if (recibida.replaceAll(" ", "") == "") {
                                    nueva = recibida + "; ";
                                }
                                nueva = recibida;
                            }
                        }
                        ((Alumno) personas.get(alumno)).setDocenciaRecibida(nueva);
                    } else if (notaTotal < 5) {
                        String docenciaRecibida = ((Alumno) personas.get(alumno)).getDocenciaRecibida();
                        StringTokenizer dt = new StringTokenizer(docenciaRecibida);
                        String recibida, nueva = "";

                        while (dt.hasMoreTokens()) {
                            recibida = dt.nextToken(";");
                            if (!recibida.contains(stringId)) {
                                nueva = recibida + "; ";
                            }
                        }

                        ((Alumno) personas.get(alumno)).setDocenciaRecibida(nueva);

                    }
                    s = clave + "OK\n";
                    aw.write(s);
                }
            }
        } catch (FileNotFoundException e) {
            s = clave + "Fichero de notas inexistente\n";
            aw.write(s);
            return;
        }
    }

    /**
     * Metodo que genera el expediente del alumno en el fichero con el nombre introducido como salida.
     *
     * @param alumno Alumno del que se desea el expediente.
     * @param salida Nombre del fichero de salida.
     * @throws IOException
     */
    public void Expediente(String alumno, String salida) throws IOException {
        String s, clave = "EXP -- ";

        Iterator<Integer> it = asignaturas.keySet().iterator();

        if (!personas.containsKey(alumno)) {
            s = clave + "Alumno inexistente\n";
            aw.write(s);
            return;
        }
        if (((Alumno) personas.get(alumno)).getAsignaturasSuperadas().replaceAll(" ", "") == "") {
            s = clave + "Expediente vacío\n";
            aw.write(s);
            return;
        }

        File expediente = new File(salida);
        BufferedWriter ew;
        ew = new BufferedWriter(new FileWriter(expediente));

        String cursoAcademico, idAsignatura, nota;
        String superadas = ((Alumno) personas.get(alumno)).getAsignaturasSuperadas();
        StringTokenizer st = new StringTokenizer(superadas);
        String sup;
        float sumaNotas = 0, cantidadNotas = 0, notaMedia = 0;
        while (st.hasMoreTokens()) {
            idAsignatura = st.nextToken();
            cursoAcademico = st.nextToken();
            nota = st.nextToken();

            ew.write(asignaturas.get(Integer.parseInt(idAsignatura)).getCurso() + "; ");
            ew.write(asignaturas.get(Integer.parseInt(idAsignatura)).getNombre() + "; ");
            ew.write(nota + "; ");
            sumaNotas = sumaNotas + Float.parseFloat(nota.replaceAll(";", ""));
            cantidadNotas++;
            ew.write(cursoAcademico + "\n");
        }
        notaMedia = sumaNotas / cantidadNotas;
        String aux = "Nota media del expediente: " + notaMedia;
        ew.write(aux);

        ew.close();
        s = clave + "OK\n";
        aw.write(s);
    }

    /**
     * Metodo que genera el horario semanal de docencia del profesor.
     *
     * @param profesor DNI del profesor.
     * @param salida   Nombre del fichero de salida.
     * @throws IOException
     */
    public void ObtenerCalendario(String profesor, String salida) throws IOException {
        String s, clave = "CALENP -- ";
        File calendario = new File(salida);
        PonerBlanco(calendario);

        if (!personas.containsKey(profesor)) {
            s = clave + "Profesor inexistente\n";
            aw.write(s);
            return;
        }
        if (((Profesor) personas.get(profesor)).getDocenciaImpartida().replaceAll(" ", "").contentEquals("")) {
            s = clave + "Profesor sin asignaciones\n";
            aw.write(s);
            return;
        }

        BufferedWriter cw;
        cw = new BufferedWriter(new FileWriter(calendario));

        String docencia = ((Profesor) personas.get(profesor)).getDocenciaImpartida();
        StringTokenizer dt = new StringTokenizer(docencia);
        String idAsignatura, tipoGrupo, idGrupo, horario, dia = "", hora = "";
        cw.write("Día; Hora; Asignatura; Tipo grupo; Id grupo \n");

        while (dt.hasMoreTokens()) {
            idAsignatura = dt.nextToken();
            tipoGrupo = dt.nextToken();
            idGrupo = dt.nextToken().replace(";", "");

            Iterator<Integer> it = asignaturas.keySet().iterator();

            while (it.hasNext()) {
                Integer key = it.next();
                if (asignaturas.get(key).getId().equalsIgnoreCase(idAsignatura)) {
                    if (tipoGrupo.contains("A")) {
                        StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposA());

                        while (tok.hasMoreTokens()) {
                            horario = tok.nextToken(";");
                            if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                                StringTokenizer sel = new StringTokenizer(horario);

                                while (sel.hasMoreTokens()) {
                                    sel.nextToken();
                                    dia = sel.nextToken();
                                    hora = sel.nextToken();
                                    break;
                                }
                            }
                        }
                    } else {
                        StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposB());

                        while (tok.hasMoreTokens()) {
                            horario = tok.nextToken(";");
                            if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                                StringTokenizer sel = new StringTokenizer(horario);

                                while (sel.hasMoreTokens()) {
                                    sel.nextToken();
                                    dia = sel.nextToken();
                                    hora = sel.nextToken();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            cw.write(dia + "; ");
            cw.write(hora + "; ");
            cw.write(asignaturas.get(Integer.parseInt(idAsignatura)).getSiglas() + "; ");
            cw.write(tipoGrupo + "; ");
            cw.write(idGrupo + "\n");
        }

        cw.close();
        s = clave + "OK\n";
        aw.write(s);
    }

    /**
     * Metodo que genera una salida a fichero de todos los alumnos ordenados por la nota del expediente, de mayor a menor.
     *
     * @param salida Nombre del fichero de salida.
     * @throws IOException
     */
    public void OrdenaAlumnosXNota(String salida) throws IOException {
        String s, clave = "OAXNOTA -- ";
        float notaMedia;
        String nombre, apellidos, dni;

        File ordenados = new File(salida);
        BufferedWriter ow;
        ow = new BufferedWriter(new FileWriter(ordenados));

        Iterator<String> it = personas.keySet().iterator();
        TreeMap<Float, String> alumnos = new TreeMap<>();
        while (it.hasNext()) {
            String key = it.next();
            if (personas.get(key) instanceof Alumno) {
                nombre = personas.get(key).getNombre();
                apellidos = personas.get(key).getApellidos();
                dni = personas.get(key).getDni();
                notaMedia = NotaMedia(((Alumno) personas.get(key)).getDni());
                String linea = nombre + " " + apellidos + " " + dni + " " + notaMedia;

                alumnos.put(notaMedia, linea);
            }
        }

        Iterator<Float> exp = alumnos.descendingKeySet().iterator();
        while (exp.hasNext()) {
            Float key = exp.next();
            ow.write(alumnos.get(key) + "\n");
        }
        ow.close();

        s = clave + "OK\n";
        aw.write(s);
    }

    //COMPROBACIONES Y OTROS.

    /**
     * Metodo que obtiene la nota media del expediente.
     *
     * @param alumno DNI del alumno.
     * @return Nota media.
     */
    public float NotaMedia(String alumno) {
        float sumaNotas = 0, cantidadNotas = 0, notaMedia = 0;
        String nota;
        String superadas = ((Alumno) personas.get(alumno)).getAsignaturasSuperadas();
        if (superadas.contentEquals("")) {
            notaMedia = 0;
            return notaMedia;
        }
        StringTokenizer st = new StringTokenizer(superadas);

        while (st.hasMoreTokens()) {
            st.nextToken();
            st.nextToken();
            nota = st.nextToken();
            sumaNotas = sumaNotas + Float.parseFloat(nota.replaceAll(";", ""));
            cantidadNotas++;
        }

        notaMedia = sumaNotas / cantidadNotas;

        return notaMedia;
    }

    /**
     * Comprueba que las fechas sean validas.
     *
     * @param f1 Fecha 1.
     * @param f2 Fecha 2.
     * @return True / false.
     */
    public boolean CompruebaFecha(String f1, String f2) {
        Date fecha1, fecha2, fechaInicial, fechaFinal;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

        try {
            fecha1 = formatoDelTexto.parse(f1);
            fecha2 = formatoDelTexto.parse(f2);
            fechaInicial = formatoDelTexto.parse("01/01/1950");
            fechaFinal = formatoDelTexto.parse("01/01/2020");
        } catch (ParseException e) {
            return false;
        }

        if (!fecha1.after(fechaInicial) || !fecha2.after(fechaInicial) || !fecha1.before(fechaFinal) || !fecha2.before(fechaFinal)) {
            return false;
        }

        return true;

    }

    /**
     * Comprueba que la fecha sea valida.
     *
     * @param f1 Fecha a comprobar.
     * @return True / false.
     */
    public boolean CompruebaFecha(String f1) {
        Date fecha1, fechaInicial, fechaFinal;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

        try {
            fecha1 = formatoDelTexto.parse(f1);
            fechaInicial = formatoDelTexto.parse("01/01/1950");
            fechaFinal = formatoDelTexto.parse("01/01/2020");
        } catch (ParseException e) {
            return false;
        }

        if (!fecha1.after(fechaInicial) || !fecha1.before(fechaFinal)) {
            return false;
        }

        return true;

    }

    /**
     * Comprueba que la fecha de ingreso sea correcta.
     *
     * @param f1 Fecha nacimiento.
     * @param f2 Fecha ingreso.
     * @return True / false.
     */
    public boolean CompruebaFechaIngreso(String f1, String f2) {
        Date fecha1, fecha2, fechaInicial, fechaFinal;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");

        try {
            fecha1 = formatoDelTexto.parse(f1);
            fecha2 = formatoDelTexto.parse(f2);
        } catch (ParseException e) {
            return false;
        }

        long diferencia_ms = fecha2.getTime() - fecha1.getTime();
        long dias = diferencia_ms / (1000 * 60 * 60 * 24);
        long anhos = (dias / 365);
        if (anhos < 15 || anhos > 65)
            return false;
        else
            return true;

    }

    /**
     * Comprueba que el numero de horas asignables no sobrepase el maximo.
     *
     * @param p Profesor.
     * @return True /false.
     */
    public boolean CompruebaHorasAsignables(Profesor p) {
        if (p.getCategoria().contentEquals("titular")) {
            if (Integer.parseInt(p.getHorasAsignables()) < 0 || Integer.parseInt(p.getHorasAsignables()) > 20) {
                return false;
            }
        } else {
            if (Integer.parseInt(p.getHorasAsignables()) < 0 || Integer.parseInt(p.getHorasAsignables()) > 15) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si existe la persona.
     *
     * @param p Persona.
     * @return True / false.
     */
    public boolean CompruebaExistencia(Persona p) {
        if (personas.containsKey(p.getDni())) {
            return true;
        }
        return false;
    }

    /**
     * Metodo para saber el numero de asignaturas de las que un profesor es coordinador.
     *
     * @param profesor DNI del profesor.
     * @return Numero de asignaturas.
     */
    public int NumeroAsignaturasCoordinador(String profesor) {
        int contador = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getCoordinador().contentEquals(profesor))
                contador++;
        }
        return contador;
    }

    /**
     * Metodo para comprobar si el grupo de una asignatura existe.
     *
     * @param asignatura Asignatura (ID).
     * @param tipoGrupo  (A/B).
     * @param idGrupo    Identificador.
     * @return True /false.
     */
    public boolean GrupoDeAsignatura(String asignatura, String tipoGrupo, String idGrupo) {
        int idAsignatura = 0;
        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().replaceAll(" ", "").contentEquals(asignatura.replaceAll(" ", ""))) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId());
                break;
            }
        }

        if (tipoGrupo.contentEquals("A")) {
            if (asignaturas.get(idAsignatura).getGruposA().contains(idGrupo)) {
                return true;
            }
        }

        if (tipoGrupo.contentEquals("B")) {
            if (asignaturas.get(idAsignatura).getGruposB().contains(idGrupo)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Comprueba si el grupo ya esta asignado.
     *
     * @param idAsignatura Asignatura.
     * @param tipoGrupo    (A/B).
     * @param idGrupo      Identificador.
     * @return True / false.
     */
    public boolean GrupoYaAsignado(String idAsignatura, String tipoGrupo, String idGrupo) {

        Iterator<Integer> it = asignaturas.keySet().iterator();
        String horario;
        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getId().equalsIgnoreCase(idAsignatura)) {
                if (tipoGrupo.contains("A")) {
                    StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposA());

                    while (tok.hasMoreTokens()) {
                        horario = tok.nextToken(";");
                        if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                            return true;
                        }
                    }
                } else {
                    StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposB());

                    while (tok.hasMoreTokens()) {
                        horario = tok.nextToken(";");
                        if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Comprobar las horas asignables a un profesor.
     *
     * @param persona      Profesor.
     * @param idAsignatura Identificador de la asignatura.
     * @param tipoGrupo    (A/B).
     * @param idGrupo      Identificador del grupo.
     * @return True / false.
     */
    public boolean ComprobarHorasAsignables(String persona, String idAsignatura, String tipoGrupo, String idGrupo) {
        int horas = Integer.parseInt(((Profesor) personas.get(persona)).getHorasAsignables());
        int horaInicio, horaFin, horasTotales = 0;
        String horario;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getId().equalsIgnoreCase(idAsignatura)) {
                if (tipoGrupo.contains("A")) {
                    StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposA());

                    while (tok.hasMoreTokens()) {
                        horario = tok.nextToken(";");
                        if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                            StringTokenizer sel = new StringTokenizer(horario);

                            while (sel.hasMoreTokens()) {
                                sel.nextToken();
                                sel.nextToken();
                                horaInicio = Integer.parseInt(sel.nextToken());
                                horaFin = Integer.parseInt(sel.nextToken());
                                horasTotales = horasTotales + (horaFin - horaInicio);
                                break;
                            }
                        }
                    }
                } else {
                    StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposB());

                    while (tok.hasMoreTokens()) {
                        horario = tok.nextToken(";");
                        if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                            StringTokenizer sel = new StringTokenizer(horario);

                            while (sel.hasMoreTokens()) {
                                sel.nextToken();
                                sel.nextToken();
                                horaInicio = Integer.parseInt(sel.nextToken());
                                horaFin = Integer.parseInt(sel.nextToken());
                                horasTotales = horasTotales + (horaFin - horaInicio);
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (horasTotales > horas) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Comprueba si se genera solape.
     *
     * @param persona      Profesor.
     * @param idAsignatura Identificador de la asignatura.
     * @param tipoGrupo    (A/B)
     * @param idGrupo      Identificador.
     * @return True / false.
     */
    public boolean GeneraSolape(String persona, String idAsignatura, String tipoGrupo, String idGrupo) {
        int horas = Integer.parseInt(((Profesor) personas.get(persona)).getHorasAsignables());
        int horaInicio, horaFin, horasTotales = 0;
        String horario;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getId().equalsIgnoreCase(idAsignatura)) {
                if (tipoGrupo.contains("A")) {
                    StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposA());

                    while (tok.hasMoreTokens()) {
                        horario = tok.nextToken(";");
                        if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                            StringTokenizer sel = new StringTokenizer(horario);

                            while (sel.hasMoreTokens()) {
                                sel.nextToken();
                                sel.nextToken();
                                horaInicio = Integer.parseInt(sel.nextToken());
                                horaFin = Integer.parseInt(sel.nextToken());
                                break;
                            }
                        }
                    }
                } else {
                    StringTokenizer tok = new StringTokenizer(asignaturas.get(key).getGruposB());

                    while (tok.hasMoreTokens()) {
                        horario = tok.nextToken(";");
                        if (horario.replaceAll(" ", "").startsWith(idGrupo)) {
                            StringTokenizer sel = new StringTokenizer(horario);

                            while (sel.hasMoreTokens()) {
                                sel.nextToken();
                                sel.nextToken();
                                horaInicio = Integer.parseInt(sel.nextToken());
                                horaFin = Integer.parseInt(sel.nextToken());
                                break;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Comprueba si existen errores en el fichero de evaluacion.
     *
     * @param alumno      Alumno. DNI.
     * @param notaA       Nota grupo A.
     * @param notaB       Nota grupo B.
     * @param numeroLinea Numero de linea del fichero.
     * @param stringId    Identificador de la asignatura (String).
     * @return True / false.
     * @throws IOException
     */
    public boolean GestionaFicheroEval(String alumno, float notaA, float notaB, int numeroLinea, String stringId) throws IOException {
        String s, clave = "EVALUA -- ";
        if (!personas.containsKey(alumno)) {
            s = clave + "Error en línea " + numeroLinea + ": Alumno inexistente: " + alumno + "\n";
            aw.write(s);
            return false;
        }
        if (!((Alumno) personas.get(alumno)).getDocenciaRecibida().contains(stringId)) {
            s = clave + "Error en línea " + numeroLinea + ": Alumno no matriculado: " + alumno + "\n";
            aw.write(s);
            return false;
        }
        if (notaA < 0 || notaA > 5 || notaB < 0 || notaB > 5) {
            s = clave + "Error en línea " + numeroLinea + ": Nota grupo A/B incorrecta\n";
            aw.write(s);
            return false;
        }

        return true;
    }

    /**
     * Pone en blanco el fichero.
     *
     * @param fichero Fichero a borrar.
     */
    public void PonerBlanco(File fichero) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Reescribe los ficheros.
     *
     * @throws IOException
     */
    public void GestionaFicheros() throws IOException {
        PonerBlanco(fpersonas);
        PonerBlanco(fasignaturas);

        BufferedWriter pw = new BufferedWriter(new FileWriter(fpersonas));
        BufferedWriter asw = new BufferedWriter(new FileWriter(fasignaturas));

        int lengthP = personas.size();
        int lengthA = asignaturas.size();
        int P = 0, A = 0;

        Iterator<String> it1 = personas.keySet().iterator();
        Iterator<Integer> it2 = asignaturas.keySet().iterator();
        it1 = personas.keySet().iterator();

        while (it1.hasNext()) {
            String key = it1.next();
            P++;
            if (P != lengthP)
                pw.write(personas.get(key).toStringOUT().replace("*", "") + "\n*\n");
            else
                pw.write(personas.get(key).toStringOUT());
        }
        pw.close();

        while (it2.hasNext()) {
            Integer key = it2.next();
            A++;
            if (A != lengthA)
                asw.write(asignaturas.get(key).toStringOUT().replace("*", "") + "\n*\n");
            else
                asw.write(asignaturas.get(key).toStringOUT());
        }
        asw.close();
    }

}