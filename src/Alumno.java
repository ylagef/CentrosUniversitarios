public class Alumno extends Persona {

    private String fecha2; //FECHA DE INGRESO EN EL CENTRO (DD/MM/AAAA)
    private String asignaturasSuperadas; //ASIGNATURAS SUPERADAS POR EL ALUMNO
    private String docenciaRecibida; //DOCENCIA RECIBIDA POR EL ALUMNO

    //CONSTRUCTOR DE ALUMNO
    public Alumno(String perfil, String nombre, String apellidos, String dni, String fecha1,
                  String fecha2, String asignaturasSuperadas, String docenciaRecibida) {
        super(perfil, nombre, apellidos, dni, fecha1);
        this.fecha2 = fecha2;
        this.asignaturasSuperadas = asignaturasSuperadas;
        this.docenciaRecibida = docenciaRecibida;
    }

    //GETTERS Y SETTERS
    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    public String getAsignaturasSuperadas() {
        return asignaturasSuperadas;
    }

    public void setAsignaturasSuperadas(String asignaturasSuperadas) {
        this.asignaturasSuperadas = asignaturasSuperadas;
    }

    public String getDocenciaRecibida() {
        return docenciaRecibida;
    }

    public void setDocenciaRecibida(String docenciaRecibida) {
        this.docenciaRecibida = docenciaRecibida;
    }

    //TOSTRING DE ALUMNO

    @Override
    public String toString() {
        return "Alumno " + super.toString() + ", Fecha de ingreso: " + fecha2 + ", Asignaturas superadas: " + asignaturasSuperadas + ", Docencia recibida: " + docenciaRecibida + ".";
    }

    public String toStringOUT() {
        return super.toStringOUT() + fecha2 + "\n" + asignaturasSuperadas + "\n" + docenciaRecibida;
    }
}
