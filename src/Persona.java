public class Persona {

    private String perfil; //ALUMNO O PROFESOR
    private String nombre, apellidos; //NOMBRE Y APELLIDOS DE LA PERSONA
    private String dni; //ID DE LA PERSONA
    private String fecha1; //FECHA DE NACIMIENTO DE LA PERSONA.

    //CONSTRUCTOR CLASE PERSONA

    public Persona(String perfil, String nombre, String apellidos, String dni, String fecha1) {
        super();
        this.perfil = perfil;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fecha1 = fecha1;
    }

    //GETTERS Y SETTERS

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    //TOSTRING DE PERSONA

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellidos: " + apellidos + ", Dni: " + dni
                + ", Fecha de nacimiento: " + fecha1;
    }

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
