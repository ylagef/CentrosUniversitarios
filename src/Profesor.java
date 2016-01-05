public class Profesor extends Persona {

    private String categoria; //TITULAR O ASOCIADO
    private String departamento; //DEPARTAMENTO AL QUE EST� ADSCRITO EL PROFESOR
    private String horasAsignables; //CU�NTAS HORAS ASIGNABLES SEMANALES PARA TAREAS DOCENTES (MAX 20 TITULARES, MAX 15 ASOCIADOS)
    private String docenciaImpartida; //DOCENCIA IMPARTIDA POR EL PROFESOR

    //CONSTRUCTOR DE PROFESOR

    public Profesor(String perfil, String nombre, String apellidos, String dni, String fecha1, String categoria,
                    String departamento, String horasAsignables, String docenciaImpartida) {
        super(perfil, nombre, apellidos, dni, fecha1);
        this.categoria = categoria;
        this.departamento = departamento;
        this.horasAsignables = horasAsignables;
        this.docenciaImpartida = docenciaImpartida;
    }

    //GETTERS Y SETTERS

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getHorasAsignables() {
        return horasAsignables;
    }

    public void setHorasAsignables(String horasAsignables) {
        this.horasAsignables = horasAsignables;
    }

    public String getDocenciaImpartida() {
        return docenciaImpartida;
    }

    public void setDocenciaImpartida(String docenciaImpartida) {
        this.docenciaImpartida = docenciaImpartida;
    }

    //MÉTODOS TOSTRING DE PROFESOR

    @Override
    public String toString() {
        return "Profesor " + super.toString() + ", Categoria: " + categoria + ", Departamento: " + departamento + ", Horas asignables: " + horasAsignables + ", Docencia impartida: " + docenciaImpartida + ".";
    }

    public String toStringOUT() {
        return super.toStringOUT() + categoria + "\n" + departamento + "\n" + horasAsignables + "\n" + docenciaImpartida;
    }

}
