package queMePongoClases;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@DiscriminatorColumn(name = "tipoDeUsuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class TipoDeUsuario {
	
	@Id
	@GeneratedValue
	private long Id;
	
	public TipoDeUsuario() {
		
	}

	public abstract boolean permiteAgregarPrendaA(Guardarropa guardarropa);
	
	public long getId() {
		return Id;
	}
	
	public void setId(long id) {
		Id = id;
	}
}