
public class Persona {

    private String perfil; //ALUMNO O PROFESOR
    private String nombre, apellidos; //NOMBRE Y APELLIDOS DE LA PERSONA
    private String dni; //ID DE LA PERSONA
    private String fecha1; //FECHA DE NACIMIENTO DE LA PERSONA.

    //CONSTRUCTOR CLASE PERSONA

    /**
     * Constructor de la clase persona.
     *
     * @param perfil    Perfil de la persona (Alumno o Profesor).
     * @param nombre    Nombre de la persona.
     * @param apellidos Apellidos de la persona.
     * @param dni       DNI de la persona.
     * @param fecha1    Fecha de nacimiento.
     */
    public Persona(String perfil, String nombre, String apellidos, String dni, String fecha1) {
        super();
        this.perfil = perfil;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fecha1 = fecha1;
    }

    //GETTERS Y SETTERS

    /**
     * Getter de Persona.
     * @return Alumno/Profesor.
     */
    public String getPerfil() {
        return perfil;
    }

    /**
     * Setter de Persona.
     * @param perfil Alumno/Profesor.
     */
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    /**
     * Getter de Persona.
     * @return Nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter de Persona.
     * @param nombre Nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de Persona.
     * @return Apellidos de la persona.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Setter de Persona.
     * @param apellidos Apellidos de la persona.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Getter de Persona.
     * @return DNI de la persona.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Setter de Persona.
     * @param dni DNI de la persona.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Getter de Persona.
     * @return Fecha de nacimiento.
     */
    public String getFecha1() {
        return fecha1;
    }

    /**
     * Setter de Persona.
     * @param fecha1 Fecha de nacimiento.
     */
    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    //TOSTRING DE PERSONA

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellidos: " + apellidos + ", Dni: " + dni
                + ", Fecha de nacimiento: " + fecha1;
    }

    /**
     * ToString para escritura en el fichero de salida.
     * @return Cadena de la persona.
     */
    public String toStringOUT() {
        String tipo;
        if (this instanceof Alumno) {
            tipo = "alumno";
        } else {
            tipo = "profesor";
        }

        return tipo + "\n" + dni + "\n" + nombre + "\n" + apellidos + "\n" + fecha1 + "\n";
    }
}
