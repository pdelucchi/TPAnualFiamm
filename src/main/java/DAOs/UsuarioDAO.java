package DAOs;

import javax.persistence.EntityManager;

import excepciones.GuardarropaExistenteException;
import excepciones.GuardarropaNoExisteException;
import excepciones.UsuarioExisteException;
import excepciones.UsuarioNoExisteException;
import queMePongoClases.Guardarropa;
import queMePongoClases.Usuario;

public class UsuarioDAO {
	
	private EntityManager entityManager;
	
	public UsuarioDAO(EntityManager entityManager) {
		this.entityManager = entityManager;		
	}

	public Usuario obtenerUsuario(long idUsuario) {
		
		Usuario usuario = entityManager.find(Usuario.class, idUsuario);
		
		if(usuario == null) {
			throw new UsuarioNoExisteException("No existe un usuario con el id: " + idUsuario);
		}
		
		return usuario;
	}
	
	public long guardarUsuario(Usuario usuario) {
		
		long idUsuario;
		
		if(usuario.getId() != 0) {
			throw new UsuarioExisteException("El usuario no es nuevo");
		}
		
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		idUsuario = usuario.getId();

		return idUsuario;
	}
	

	public void actualizarUsuario(Usuario usuario) {
		
		if(usuario.getId() == 0) {
			throw new UsuarioNoExisteException("El usuario es nuevo y no existe en la base de datos");
		}
		
		entityManager.getTransaction().commit();
	}
}
