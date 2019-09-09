package enums;

public enum Calificacion {
	MUY_POCO_ABRIGADO(1),
	POCO_ABRIGADO(2),
	BIEN(3),
	ABRIGADO(4),
	DEMASIADO_ABRIGADO(5);
	
	public int calificacion;
	
	Calificacion(int calificacion){
		this.calificacion = calificacion;
	}
	
	public int getCalificacion() {
		return this.calificacion;
	}
}
