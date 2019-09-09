package queMePongoClases;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value = "premium")
@Entity
public class UsuarioPremium extends TipoDeUsuario{
	
	public UsuarioPremium() {
		
	}
	
	public boolean permiteAgregarPrendaA(Guardarropa guardarropa) {
		return true;
	}
}
