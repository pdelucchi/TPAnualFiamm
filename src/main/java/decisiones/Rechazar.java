package decisiones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import enums.EstadoAtuendo;
import queMePongoClases.Atuendo;
import queMePongoClases.Decision;

@DiscriminatorValue(value = "rechazar")
@Entity
public class Rechazar extends Decision {  
	
	public Rechazar() {
		
	}
	
	public Rechazar(Atuendo atuendo) {
		super(atuendo);
		this.estadoAtuendoActual = EstadoAtuendo.RECHAZADO;
		this.estadoAtuendoAnterior = EstadoAtuendo.NUEVO;
	}
	
	public void tomarDecision() {
		atuendo.setEstadoAtuendo(this.estadoAtuendoActual);
	}
	
	public void deshacerDecision() {
		atuendo.setEstadoAtuendo(this.estadoAtuendoAnterior);
	}
	
}