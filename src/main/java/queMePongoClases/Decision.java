package queMePongoClases;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import enums.EstadoAtuendo;

@DiscriminatorColumn(name = "decision")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class Decision {
	
	@Id
	@GeneratedValue
	private long Id;
	
	@ManyToOne
	public Atuendo atuendo;
	
	@Enumerated
	protected EstadoAtuendo estadoAtuendoActual;
	
	@Enumerated
	protected EstadoAtuendo estadoAtuendoAnterior;
	
	public Decision() {
		
	}
	
	public Decision(Atuendo atuendo) {
		this.atuendo = atuendo;
	}
	
	public abstract void tomarDecision();
	
	public abstract  void deshacerDecision(); //Me permite ir para atras en la que sea la decision que haya tomado, va a depender de cada decision (si estoy en aceptado voy a volver a nuevo, si estoy en calificado vuelvo a aceptado... etc)

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public EstadoAtuendo getEstadoAtuendoActual() {
		return estadoAtuendoActual;
	}

	public void setEstadoAtuendoActual(EstadoAtuendo estadoAtuendoActual) {
		this.estadoAtuendoActual = estadoAtuendoActual;
	}

	public EstadoAtuendo getEstadoAtuendoAnterior() {
		return estadoAtuendoAnterior;
	}

	public void setEstadoAtuendoAnterior(EstadoAtuendo estadoAtuendoAnterior) {
		this.estadoAtuendoAnterior = estadoAtuendoAnterior;
	}
	
	public Atuendo getAtuendo() {
		return this.atuendo;
	}

	public void setAtuendo(Atuendo atuendo) {
		this.atuendo = atuendo;
	}
}