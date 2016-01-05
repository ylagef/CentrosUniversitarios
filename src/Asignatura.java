public class Asignatura {

    private String id, nombre, siglas, curso, coordinador, prerrequisitos, gruposA, gruposB;

    /**
     * Método constructor de la clase Asignatura.
     *
     * @param id             Identificador.
     * @param nombre         Nombre de la asignatura.
     * @param siglas         Siglas de la asignatura.
     * @param curso          Curso al que pertenece.
     * @param coordinador    Coordinador de la asignatura (DNI del profesor).
     * @param prerrequisitos Prerrequisitos para poder cursarla.
     * @param gruposA        Grupos A de la asignatura.
     * @param gruposB        Grupos B de la asignatura.
     */
    public Asignatura(String id, String nombre, String siglas, String curso, String coordinador, String prerrequisitos, String gruposA, String gruposB) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
        this.curso = curso;
        this.coordinador = coordinador;
        this.prerrequisitos = prerrequisitos;
        this.gruposA = gruposA;
        this.gruposB = gruposB;
    }

    /**
     * Getter de Asignatura.
     *
     * @return ID de la asignatura.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter de Asignatura.
     *
     * @param id ID de la asignatura.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Nombre de la asignatura.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter de Asignatura.
     *
     * @param nombre Nombre de la asignatura.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Siglas de la asignatura.
     */
    public String getSiglas() {
        return siglas;
    }

    /**
     * Getter de Asignatura.
     *
     * @param siglas Siglas de la asignatura.
     */
    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Curso al que pertenece.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Getter de Asignatura.
     *
     * @param curso Curso al que pertenece.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Coordinador de la asignatura.
     */
    public String getCoordinador() {
        return coordinador;
    }

    /**
     * Getter de Asignatura.
     *
     * @param coordinador Coordinador de la asignatura.
     */
    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Prerrequisitos.
     */
    public String getPrerrequisitos() {
        return prerrequisitos;
    }

    /**
     * Getter de Asignatura.
     *
     * @param prerrequisitos Prerrequisitos.
     */
    public void setPrerrequisitos(String prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Grupos A de la asignatura.
     */
    public String getGruposA() {
        return gruposA;
    }

    /**
     * Getter de Asignatura.
     *
     * @param gruposA Grupos A de la asignatura.
     */
    public void setGruposA(String gruposA) {
        this.gruposA = gruposA;
    }

    /**
     * Getter de Asignatura.
     *
     * @return Grupos B de la asignatura.
     */
    public String getGruposB() {
        return gruposB;
    }

    /**
     * Getter de Asignatura.
     *
     * @param gruposB Grupos B de la asignatura.
     */
    public void setGruposB(String gruposB) {
        this.gruposB = gruposB;
    }

    @Override
    public String toString() {
        return "Asignatura " + "Id: " + id + ", Nombre: " + nombre + ", Siglas: " + siglas + ", Curso: " + curso + "º, Coordinador: " + coordinador + ", Pre-requisitos: " + prerrequisitos + ", Grupos A: " + gruposA + ", Grupos B: " + gruposB + ".";
    }

    /**
     * Método toString para escribir la asignatura en el fichero.
     *
     * @return Cadena de la asignatura.
     */
    public String toStringOUT() {
        return id + "\n" + nombre + "\n" + siglas + "\n" + curso + "\n" + coordinador + "\n" + prerrequisitos + "\n" + gruposA + "\n" + gruposB;
    }
}
