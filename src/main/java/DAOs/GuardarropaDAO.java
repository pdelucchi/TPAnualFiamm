package DAOs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import excepciones.GuardarropaExistenteException;
import excepciones.GuardarropaNoExisteException;
import queMePongoClases.Guardarropa;

public class GuardarropaDAO{
	
	private EntityManager entityManager;

	public GuardarropaDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Guardarropa obtenerGuardarropa(long idGuardarropa) {
		
		Guardarropa guardarropa = entityManager.find(Guardarropa.class, idGuardarropa);
		
		if(guardarropa == null) {
			throw new GuardarropaExistenteException("No existe un guardarropa con el id: " + idGuardarropa);
		}
		
		return guardarropa;
	}
	
	public long guardarGuardarropa(Guardarropa guardarropa) {
		
		long idGuardarropa;
		
		if(guardarropa.getId() != 0) {
			throw new GuardarropaExistenteException("El guardarropa no es nuevo");
		}
		
		entityManager.persist(guardarropa);
		entityManager.getTransaction().commit();
		idGuardarropa = guardarropa.getId();

		return idGuardarropa;
	}
	

	public void actualizarGuardarropa(Guardarropa guardarropa) {
		
		if(guardarropa.getId() == 0) {
			throw new GuardarropaNoExisteException("El guardarropa es nuevo y no existe en la base de datos");
		}
		
		entityManager.getTransaction().commit();
	}
}
