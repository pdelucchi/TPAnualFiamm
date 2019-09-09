package decisiones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import enums.Calificacion;
import enums.EstadoAtuendo;
import queMePongoClases.Atuendo;
import queMePongoClases.Decision;

@DiscriminatorValue(value = "calificar")
@Entity
public class Calificar extends Decision { 
	
	@Enumerated
	private Calificacion calificacion;
	
	public Calificar() {
		
	}
	
	public Calificar(Atuendo atuendo) {
		super(atuendo);
		//this.calificacion = 0;
		this.estadoAtuendoActual = EstadoAtuendo.CALIFICADO;
		this.estadoAtuendoAnterior = EstadoAtuendo.ACEPTADO;
	}
	
	public void tomarDecision() {
		atuendo.setEstadoAtuendo(this.estadoAtuendoActual);
		atuendo.calificar(this.calificacion); 
	}
	
	public void deshacerDecision() {
		atuendo.setEstadoAtuendo(this.estadoAtuendoAnterior);
		atuendo.setCalificacion(null); //No uso calificar() porque me tiraria la excepcion
	}
	
	public void setCalificacion(Calificacion calificacion) {
		this.calificacion = calificacion;
	}
	
	
	//Se califica el nivel de abrigo de un atuendo, para tener en cuenta en futuras sugerencias
	//cuan abrigado es el usuario.
	//1-2 le resulto poco abrigado - ABRIGAS MAS para ese rango de temperatura  (Para 1 le sumo 6, para 2 le sumo 3)
	//3 le resulto bien - ABRIGAR IGUAL
	//4-5 le resulto demasiado abrigad - ABRIGAR MENOS para ese rango de temperatura (Para 4 le resto 3, para 5 le resto 6)
}