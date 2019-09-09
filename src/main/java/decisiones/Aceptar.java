package decisiones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import enums.EstadoAtuendo;
import queMePongoClases.Atuendo;
import queMePongoClases.Decision;

@DiscriminatorValue(value = "aceptar")
@Entity
public class Aceptar extends Decision {  
	
	public Aceptar() {
		
	}
	
	public Aceptar(Atuendo atuendo) {
		super(atuendo);
		this.estadoAtuendoActual = EstadoAtuendo.ACEPTADO;
		this.estadoAtuendoAnterior = EstadoAtuendo.NUEVO;
	}
	
	public void tomarDecision() {
		atuendo.setEstadoAtuendo(this.estadoAtuendoActual);
		atuendo.setPrendasDisponibles(false);
	}
	
	public void deshacerDecision() {
		atuendo.setEstadoAtuendo(this.estadoAtuendoAnterior);
		atuendo.setPrendasDisponibles(true);
	}
	
}