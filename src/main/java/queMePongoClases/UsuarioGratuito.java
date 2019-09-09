package queMePongoClases;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value = "gratuito")
@Entity
public class UsuarioGratuito extends TipoDeUsuario{
	
	int numeroMaximoPrendas;
	
	public UsuarioGratuito() {
		
	}
	
	public UsuarioGratuito(int numeroMaximoPrendas) {
		this.numeroMaximoPrendas = numeroMaximoPrendas;
	}
	
	public boolean permiteAgregarPrendaA(Guardarropa guardarropa) {
		return numeroMaximoPrendas > guardarropa.cantidadDePrendas();
	}
	
	public int getNumeroMaximoPrendas() {
		return numeroMaximoPrendas;
	}
	
	public void setNumeroMaximoPrendas(int numeroMaximoPrendas) {
		this.numeroMaximoPrendas = numeroMaximoPrendas;
	}
}
