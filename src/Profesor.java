public class Profesor extends Persona {

    private String categoria; //TITULAR O ASOCIADO
    private String departamento; //DEPARTAMENTO AL QUE EST� ADSCRITO EL PROFESOR
    private String horasAsignables; //CU�NTAS HORAS ASIGNABLES SEMANALES PARA TAREAS DOCENTES (MAX 20 TITULARES, MAX 15 ASOCIADOS)
    private String docenciaImpartida; //DOCENCIA IMPARTIDA POR EL PROFESOR

    //CONSTRUCTOR DE PROFESOR

    /**
     * Metodo constructor para la clase Profesor.
     *
     * @param perfil            Profesor.
     * @param nombre            Nombre del profesor.
     * @param apellidos         Apellidos del profesor.
     * @param dni               DNI del profesor.
     * @param fecha1            Fecha de nacimiento del profesor.
     * @param categoria         Titular o asociado.
     * @param departamento      Nombre del departamento al que se encuentra adscrito el profesor.
     * @param horasAsignables   Cuantas horas semanales se le pueden asignar, para la realizacion de tareas docentes. Sera de 20 como maximo para los titulares y de 15 como maximo para asociados.
     * @param docenciaImpartida Son los grupos A y B en los que imparte docencia un profesor.
     */
    public Profesor(String perfil, String nombre, String apellidos, String dni, String fecha1, String categoria,
                    String departamento, String horasAsignables, String docenciaImpartida) {
        super(perfil, nombre, apellidos, dni, fecha1);
        this.categoria = categoria;
        this.departamento = departamento;
        this.horasAsignables = horasAsignables;
        this.docenciaImpartida = docenciaImpartida;
    }

    //GETTERS Y SETTERS

    /**
     * Getter de Profesor.
     *
     * @return Titular o asociado.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Setter de Profesor.
     *
     * @param categoria Tirular o asociado.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Getter de Profesor.
     *
     * @return Departamento al que esta adscrito.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Setter de Profesor.
     *
     * @param departamento Departamento al que esta adscrito el profesor.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Getter de Profesor.
     *
     * @return Numero de horas asignables.
     */
    public String getHorasAsignables() {
        return horasAsignables;
    }

    /**
     * Setter de Profesor.
     *
     * @param horasAsignables Numero de horas asignables.
     */
    public void setHorasAsignables(String horasAsignables) {
        this.horasAsignables = horasAsignables;
    }

    /**
     * Getter de Profesor.
     *
     * @return Docencia impartida por el profesor.
     */
    public String getDocenciaImpartida() {
        return docenciaImpartida;
    }

    /**
     * Setter de Profesor.
     *
     * @param docenciaImpartida Docencia impartida por el profesor.
     */
    public void setDocenciaImpartida(String docenciaImpartida) {
        this.docenciaImpartida = docenciaImpartida;
    }

    //METODOS TOSTRING DE PROFESOR

    @Override
    public String toString() {
        return "Profesor " + super.toString() + ", Categoria: " + categoria + ", Departamento: " + departamento + ", Horas asignables: " + horasAsignables + ", Docencia impartida: " + docenciaImpartida + ".";
    }

    /**
     * ToString para escritura en el fichero de salida.
     *
     * @return Cadena del profesor.
     */
    public String toStringOUT() {
        return super.toStringOUT() + categoria + "\n" + departamento + "\n" + horasAsignables + "\n" + docenciaImpartida;
    }

}
