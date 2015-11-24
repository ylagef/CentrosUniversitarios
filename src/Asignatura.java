public class Asignatura {

    private String id, nombre, siglas, curso, coordinador, prerrequisitos, gruposA, gruposB;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    public String getPrerrequisitos() {
        return prerrequisitos;
    }

    public void setPrerrequisitos(String prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    public String getGruposA() {
        return gruposA;
    }

    public void setGruposA(String gruposA) {
        this.gruposA = gruposA;
    }

    public String getGruposB() {
        return gruposB;
    }

    public void setGruposB(String gruposB) {
        this.gruposB = gruposB;
    }

    @Override
    public String toString() {
        return "Asignatura " + "Id: " + id + ", Nombre: " + nombre + ", Siglas: " + siglas + ", Curso: " + curso + "º, Coordinador: " + coordinador + ", Pre-requisitos: " + prerrequisitos + ", Grupos A: " + gruposA + ", Grupos B: " + gruposB + ".";
    }
}
