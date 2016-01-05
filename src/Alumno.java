public class Alumno extends Persona {

    private String fecha2; //FECHA DE INGRESO EN EL CENTRO (DD/MM/AAAA)
    private String asignaturasSuperadas; //ASIGNATURAS SUPERADAS POR EL ALUMNO
    private String docenciaRecibida; //DOCENCIA RECIBIDA POR EL ALUMNO

    //CONSTRUCTOR DE ALUMNO

    /**
     * Metodo constructor de la clase Alumno.
     *
     * @param perfil               Alumno.
     * @param nombre               Nombre del alumno.
     * @param apellidos            Apellidos del alumno.
     * @param dni                  DNI del alumno.
     * @param fecha1               Fecha de nacimiento.
     * @param fecha2               Fecha de ingreso en el Centro.
     * @param asignaturasSuperadas Asignaturas ya superadas por el Alumno.
     * @param docenciaRecibida     Asignaturas en las que esta matriculado.
     */
    public Alumno(String perfil, String nombre, String apellidos, String dni, String fecha1,
                  String fecha2, String asignaturasSuperadas, String docenciaRecibida) {
        super(perfil, nombre, apellidos, dni, fecha1);
        this.fecha2 = fecha2;
        this.asignaturasSuperadas = asignaturasSuperadas;
        this.docenciaRecibida = docenciaRecibida;
    }

    //GETTERS Y SETTERS

    /**
     * Getter de Alumno.
     *
     * @return Fecha de ingreso en el Centro.
     */
    public String getFecha2() {
        return fecha2;
    }

    /**
     * Setter de Alumno.
     *
     * @param fecha2 Fecha de ingreso en el centro.
     */
    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    /**
     * Getter de Alumno.
     *
     * @return Asignaturas ya superadas por el alumno.
     */
    public String getAsignaturasSuperadas() {
        return asignaturasSuperadas;
    }

    /**
     * Setter de Alumno.
     *
     * @param asignaturasSuperadas Asignaturas ya superadas por el alumno.
     */
    public void setAsignaturasSuperadas(String asignaturasSuperadas) {
        this.asignaturasSuperadas = asignaturasSuperadas;
    }

    /**
     * Getter de Alumno.
     *
     * @return Asignaturas en las que esta matriculado el alumno.
     */
    public String getDocenciaRecibida() {
        return docenciaRecibida;
    }

    /**
     * Setter de Alumno.
     *
     * @param docenciaRecibida Asignaturas en las que esta matriculado.
     */
    public void setDocenciaRecibida(String docenciaRecibida) {
        this.docenciaRecibida = docenciaRecibida;
    }

    //TOSTRING DE ALUMNO

    @Override
    public String toString() {
        return "Alumno " + super.toString() + ", Fecha de ingreso: " + fecha2 + ", Asignaturas superadas: " + asignaturasSuperadas + ", Docencia recibida: " + docenciaRecibida + ".";
    }

    /**
     * ToString para escritura en el fichero de salida.
     *
     * @return Cadena del alumno.
     */
    public String toStringOUT() {
        return super.toStringOUT() + fecha2 + "\n" + asignaturasSuperadas + "\n" + docenciaRecibida;
    }
}
