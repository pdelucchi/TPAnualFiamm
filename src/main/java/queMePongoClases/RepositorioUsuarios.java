package queMePongoClases;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import enums.Material;
import enums.TipoDeEvento;
import enums.TipoDePrenda;
import excepciones.UsuarioExistenteException;

public class RepositorioUsuarios {
	private static RepositorioUsuarios repositorio; // Aca almacenamos nuestra unica instancia
	public List<Usuario> usuarios;

	private RepositorioUsuarios() { // Constructor privado porque es un SINGLETON
		this.usuarios = new ArrayList<Usuario>();
	}

	public static RepositorioUsuarios getInstance() {
		if (repositorio == null) {
			repositorio = new RepositorioUsuarios();
		}
		return repositorio;
	}

	public void agregarNuevoUsuario(Usuario nuevoUsuario) {
		if (usuarios.contains(nuevoUsuario)) {
			throw new UsuarioExistenteException("El usuario ingresado ya se encuentra en el repositorio");
		}
		this.usuarios.add(nuevoUsuario);
	}

	public List<Usuario> getUsuariosActuales() {
		if (this.noHayUsuarios()) {
			this.cargarUsuarios();
		}
		return usuarios;
	}

	private boolean noHayUsuarios() {
		return this.usuarios.isEmpty();
	}

	public boolean esGuardarropasCompartido(Guardarropa unGuardarropa) {
		int cantidadUsuariosQueLoContienen = 0;

		for (int i = 0; i < (this.usuarios.size()); i++) {
			if (usuarios.get(i).guardarropas.contains(unGuardarropa)) {
				cantidadUsuariosQueLoContienen++;
			}
		} // Hice un count a manopla porque al parecer no esta para list, si alguien lo
			// encuentra o algo que funque saque esta asquerosidad

		return cantidadUsuariosQueLoContienen >= 2;
	}

	// Como necesito utilizar el repositorio para obtener todos los eventos y
	// mostrarlos en la View, debo tener algunos usuarios existentes.
	private void cargarUsuarios() {

		/////////////////////////// Creación objetos
		/////////////////////////// necesarios///////////////////////////

		PrendaBorrador prendaBorrador = new PrendaBorrador();

		Prenda prendaMusculosa1;
		Prenda prendaRemera1;
		Prenda prendaRemera2;
		Prenda prendaCamisa1;
		Prenda prendaCamisa2;
		Prenda prendaCampera1;
		Prenda prendaCampera2;
		Prenda prendaSaco1;
		Prenda prendaShort1;
		Prenda prendaPantalon1;
		Prenda prendaPantalon2;
		Prenda prendaOjotas1;
		Prenda prendaZapatilla1;
		Prenda prendaCalzado1;
		Prenda prendaCalzado2;

		Guardarropa guardarropa1 = new Guardarropa();
		Guardarropa guardarropa2 = new Guardarropa();

		DateTime fechaFutura = new DateTime().plusDays(1);

		TipoDeUsuario tipoDeUsuario = new UsuarioPremium();
		Usuario usuario1 = new Usuario(tipoDeUsuario);
		Usuario usuario2 = new Usuario(tipoDeUsuario);

		/////////////////////////// Usuario n°1///////////////////////////

		prendaBorrador.setTipoDePrenda(TipoDePrenda.MUSCULOSA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaMusculosa1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(12, 34, 51));
		prendaRemera1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.SEDA);
		prendaBorrador.setColor(new Color(14, 20, 10));
		prendaRemera2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMISA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(0, 0, 0));
		prendaCamisa1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMISA);
		prendaBorrador.setMaterial(Material.SEDA);
		prendaBorrador.setColor(new Color(75, 34, 1));
		prendaCamisa2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMPERA);
		prendaBorrador.setMaterial(Material.CORDEROY);
		prendaBorrador.setColor(new Color(0, 0, 0));
		prendaCampera1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMPERA);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(33, 2, 24));
		prendaCampera2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.SACO);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaSaco1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.SHORT);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaShort1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.CORDEROY);
		prendaBorrador.setColor(new Color(14, 20, 10));
		prendaPantalon1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.JEAN);
		prendaBorrador.setColor(new Color(14, 24, 50));
		prendaPantalon2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.OJOTAS);
		prendaBorrador.setMaterial(Material.GOMA);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaOjotas1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATILLA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(4, 24, 10));
		prendaZapatilla1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.CUERO);
		prendaBorrador.setColor(new Color(12, 24, 60));
		prendaCalzado1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.SINTETICO);
		prendaBorrador.setColor(new Color(14, 24, 50));
		prendaCalzado2 = prendaBorrador.crear();

		guardarropa1.incluirEnGuardarropa(prendaMusculosa1);
		guardarropa1.incluirEnGuardarropa(prendaRemera1);
		guardarropa1.incluirEnGuardarropa(prendaRemera2);
		guardarropa1.incluirEnGuardarropa(prendaCamisa1);
		guardarropa1.incluirEnGuardarropa(prendaCamisa2);
		guardarropa1.incluirEnGuardarropa(prendaCampera1);
		guardarropa1.incluirEnGuardarropa(prendaCampera2);
		guardarropa1.incluirEnGuardarropa(prendaSaco1);
		guardarropa1.incluirEnGuardarropa(prendaShort1);
		guardarropa1.incluirEnGuardarropa(prendaPantalon1);
		guardarropa1.incluirEnGuardarropa(prendaPantalon2);
		guardarropa1.incluirEnGuardarropa(prendaOjotas1);
		guardarropa1.incluirEnGuardarropa(prendaZapatilla1);
		guardarropa1.incluirEnGuardarropa(prendaCalzado1);
		guardarropa1.incluirEnGuardarropa(prendaCalzado2);

		usuario1.agregarGuardarropa(guardarropa1);

		usuario1.agregarEvento("Casamiento", fechaFutura, "Buenos Aires", TipoDeEvento.FORMAL);
		usuario1.agregarEvento("Cena familiar", new DateTime(2029, 5, 30, 23, 00), "Buenos Aires",
				TipoDeEvento.INFORMAL);
		usuario1.agregarEvento("Salir con amigos", fechaFutura, "Buenos Aires", TipoDeEvento.INFORMAL);

		/////////////////////////// Usuario n°2///////////////////////////

		prendaBorrador.setTipoDePrenda(TipoDePrenda.MUSCULOSA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(1, 1, 1));
		prendaMusculosa1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(45, 66, 10));
		prendaRemera1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.SEDA);
		prendaBorrador.setColor(new Color(44, 50, 11));
		prendaRemera2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMISA);
		prendaBorrador.setMaterial(Material.SINTETICO);
		prendaBorrador.setColor(new Color(12, 2, 46));
		prendaCamisa1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMISA);
		prendaBorrador.setMaterial(Material.SEDA);
		prendaBorrador.setColor(new Color(9, 33, 3));
		prendaCamisa2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMPERA);
		prendaBorrador.setMaterial(Material.CORDEROY);
		prendaBorrador.setColor(new Color(12, 34, 4));
		prendaCampera1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMPERA);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(3, 22, 44));
		prendaCampera2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.SACO);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(0, 0, 0));
		prendaSaco1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.SHORT);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(82, 31, 22));
		prendaShort1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(19, 41, 22));
		prendaPantalon1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.JEAN);
		prendaBorrador.setColor(new Color(1, 2, 5));
		prendaPantalon2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.OJOTAS);
		prendaBorrador.setMaterial(Material.GOMA);
		prendaBorrador.setColor(new Color(33, 32, 41));
		prendaOjotas1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATILLA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(1, 1, 87));
		prendaZapatilla1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.CUERO);
		prendaBorrador.setColor(new Color(66, 12, 22));
		prendaCalzado1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.SINTETICO);
		prendaBorrador.setColor(new Color(33, 23, 53));
		prendaCalzado2 = prendaBorrador.crear();

		guardarropa2.incluirEnGuardarropa(prendaMusculosa1);
		guardarropa2.incluirEnGuardarropa(prendaRemera1);
		guardarropa2.incluirEnGuardarropa(prendaRemera2);
		guardarropa2.incluirEnGuardarropa(prendaCamisa1);
		guardarropa2.incluirEnGuardarropa(prendaCamisa2);
		guardarropa2.incluirEnGuardarropa(prendaCampera1);
		guardarropa2.incluirEnGuardarropa(prendaCampera2);
		guardarropa2.incluirEnGuardarropa(prendaSaco1);
		guardarropa2.incluirEnGuardarropa(prendaShort1);
		guardarropa2.incluirEnGuardarropa(prendaPantalon1);
		guardarropa2.incluirEnGuardarropa(prendaPantalon2);
		guardarropa2.incluirEnGuardarropa(prendaOjotas1);
		guardarropa2.incluirEnGuardarropa(prendaZapatilla1);
		guardarropa2.incluirEnGuardarropa(prendaCalzado1);
		guardarropa2.incluirEnGuardarropa(prendaCalzado2);

		usuario2.agregarGuardarropa(guardarropa2);

		usuario2.agregarEvento("Reunion de trabajo", fechaFutura, "Buenos Aires", TipoDeEvento.FORMAL);
		usuario2.agregarEvento("Fiesta con amigos", new DateTime(2030, 2, 22, 23, 00), "Buenos Aires",
				TipoDeEvento.INFORMAL);
		usuario2.agregarEvento("Comida entre amigos", fechaFutura, "Buenos Aires", TipoDeEvento.INFORMAL);

		/////////////////////////// Guardado de usuarios en
		/////////////////////////// repositorio///////////////////////////

		this.usuarios.add(usuario1);
		this.usuarios.add(usuario2);
	}

}
