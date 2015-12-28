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

    public void Ejecucion() throws IOException {

        CrearPersonas();
        CrearAsignaturas();

        FileReader fr = new FileReader(ejecucion);
        BufferedReader br = new BufferedReader(fr);

        String linea, instruccion, s;

        while ((linea = br.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(linea);

            while (st.hasMoreTokens()) {

                instruccion = st.nextToken();

                if (instruccion.contentEquals("InsertaPersona") || instruccion.contentEquals("AsignaCoordinador") || instruccion.contentEquals("AsignaCargaDocente") || instruccion.contentEquals("Matricula") || instruccion.contentEquals("AsignaGrupo") || instruccion.contentEquals("Evalua") || instruccion.contentEquals("Expediente") || instruccion.contentEquals("ObtenerCalendarioClases")) {

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
                            break;
                        case "Evalua": //Evalua asignatura cursoAcademico fichero
                            break;
                        case "Expediente": //Expediente alumno salida
                            break;
                        case "ObtenerCalendarioClases": //ObtenerCalendarioClases profesor salida
                            break;
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

            asignaturas.put(Integer.parseInt(id.replace(" ", "")), a);

        }
    }

    //MÉTODOS PARA LAS FUNCIONALIDADES

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

    }

    public void AsignaCoordinador(String persona, String asignatura) throws IOException {
        String s, clave = "ACOORD -- ";
        int idAsignatura = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contentEquals(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId());
                break;
            } else {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }
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
        if (NumeroAsignaturasCoordinador(persona) >= 2) {
            s = clave + "Profesor ya es coordinador de 2 materias\n";
            aw.write(s);
            return;
        }

        asignaturas.get(idAsignatura).setCoordinador(persona);

    }

    public void AsignaCargaDocente(String persona, String asignatura, String tipoGrupo, String idGrupo) throws IOException {
        String s, clave = "ACDOC -- ";
        int idAsignatura = 0;

        Iterator<Integer> it = asignaturas.keySet().iterator();

        while (it.hasNext()) {
            Integer key = it.next();
            if (asignaturas.get(key).getSiglas().contentEquals(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId());
                break;
            } else {
                s = clave + "Asignatura inexistente\n";
                aw.write(s);
                return;
            }
        }
        if (!personas.containsKey(persona)) {
            s = clave + "Profesor inexistente\n";
            aw.write(s);
            return;
        }
        if (!tipoGrupo.contentEquals("A") || !tipoGrupo.contentEquals("B")) {
            s = clave + "Profesor inexistente\n";
            aw.write(s);
            return;
        }
        if (!GrupoDeAsignatura(asignatura, tipoGrupo, idGrupo)) {
            s = clave + "Grupo inexistente\n";
            aw.write(s);
            return;
        }
        if (GeneraSolape(tipoGrupo, idGrupo)) {
            s = clave + "Se genera solape\n";
            aw.write(s);
            return;
        }

        ((Profesor) personas.get(persona)).setDocenciaImpartida(idAsignatura + " " + tipoGrupo + " " + idGrupo);

    }

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
            if (asignaturas.get(key).getSiglas().contentEquals(asignatura)) {
                idAsignatura = Integer.parseInt(asignaturas.get(key).getId());
                stringId = asignaturas.get(key).getId();
                break;
            } else {
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

    }

    public void AsignaGrupo(String alumno, String asignatura, String tipoGrupo, String grupo) {

    }

    public void Evalua(String asignatura, String cursoAcademico, String fichero) {

    }

    public void Expediente(String alumno, String salida) {

    }

    public void ObtenerCalendario(String profesor, String salida) {

    }

    //COMPROBACIONES.

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

    public boolean CompruebaExistencia(Persona p) {
        if (personas.containsKey(p.getDni())) {
            return true;
        }
        return false;
    }

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

    public boolean GrupoDeAsignatura(String asignatura, String tipoGrupo, String idGrupo) {

        int idAsignatura = Integer.parseInt(asignaturas.get(asignatura).getId());

        if (tipoGrupo.contentEquals("A")) {
            if (asignaturas.get(idAsignatura).getGruposA().contains(idGrupo)) {
                return true;
            }
        } else if (tipoGrupo.contentEquals("B")) {
            if (asignaturas.get(idAsignatura).getGruposB().contains(idGrupo)) {
                return true;
            }
        }
        return false;
    }

    //----------------------------- FALTA COMPROBAR SOLAPE -----------------------------
    public boolean GeneraSolape(String tipoGrupo, String idGrupo) {
        /*
        Iterator<String> it = personas.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            if (personas.get(key) instanceof Profesor) {


            }
        }*/
        return false;
    }

    //OTROS

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