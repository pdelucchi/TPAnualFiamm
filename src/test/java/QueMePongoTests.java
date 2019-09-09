import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import DAOs.EventoDAO;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import com.google.common.collect.Sets;

import Clima.ServicioApixU;
import Clima.ServicioMeteorologico;
import Clima.ServicioMock;
import Clima.ServicioOpenWeather;
import DAOs.GuardarropaDAO;
import DAOs.UsuarioDAO;
import DAOs.EventoDAO;
import Notificaciones.EnviarMail;
import decisiones.Aceptar;
import decisiones.Calificar;
import decisiones.Rechazar;
import enums.Calificacion;
import enums.Categoria;
import enums.EstadoAtuendo;
import enums.Material;
import enums.TipoDeEvento;
import enums.TipoDePrenda;
import enums.Trama;
import excepciones.CalificacionInvalidaException;
import excepciones.ColorIgualException;
import excepciones.FechaAnteriorException;
import excepciones.GuardarropaExistenteException;
import excepciones.MaterialInvalidoException;
import excepciones.MaximoDePrendasAlcanzadoException;
import excepciones.PrendaCompartidaException;
import excepciones.PrendaExistenteException;
import queMePongoClases.Atuendo;
import queMePongoClases.Color;
import queMePongoClases.Evento;
import queMePongoClases.Guardarropa;
import queMePongoClases.Prenda;
import queMePongoClases.PrendaBorrador;
import queMePongoClases.RepositorioUsuarios;
import queMePongoClases.Usuario;
import queMePongoClases.UsuarioGratuito;
import queMePongoClases.UsuarioPremium;

public class QueMePongoTests extends AbstractPersistenceTest implements WithGlobalEntityManager{

	private PrendaBorrador prendaBorrador;

	private Prenda prendaRemera1;
	private Prenda prendaRemera2;
	private Prenda prendaPantalon1;
	private Prenda prendaPantalon2;
	private Prenda prendaCalzado1;
	private Prenda prendaCalzado2;
	private Prenda prendaSaco1;
	private Prenda prendaMusculosa1;
	private Prenda prendaShort1;
	private Prenda prendaOjotas1;
	private Prenda prendaZapatilla1;

	private Atuendo atuendo1, atuendo2, atuendo3, atuendo4, atuendo5, atuendo6, atuendo7, atuendo8, atuendo9;

	private Prenda prendaRemera_a;
	private Prenda prendaRemera_b;
	private Prenda prendaPantalon_a;
	private Prenda prendaPantalon_b;
	private Prenda prendaCalzado_a;
	private Prenda prendaCalzado_b;
	private Prenda prendaGorro;
	private Prenda prendaBufanda;
	private Prenda prendaGuantes;

	private Guardarropa guardarropa1;
	private Guardarropa guardarropa2;
	private Guardarropa guardarropa3;
	private Guardarropa guardarropa4;
	private Guardarropa guardarropa5;
	private Guardarropa guardarropaUsuarioGratuito;
	private Guardarropa guardarropaUsuarioFriolento;

	private UsuarioPremium tipoDeUsuario;
	private Usuario usuario;

	private UsuarioGratuito tipoDeUsuarioGratuito;
	private Usuario usuarioGratuito;
	private Usuario usuarioFriolento;

	private Set<Prenda> listaDePrendas;
	private Set<Prenda> listaDePrendas2;
	private Set<Prenda> listaDePrendas3;
	private Set<Prenda> listaDePrendas4;
	private Set<Prenda> listaDePrendas5;
	private Set<Prenda> listaDePrendas6;
	private Set<Atuendo> atuendos;

	/*
	 * private Set<Atuendo> lista1; private Set<Atuendo> lista2;
	 */
	private Set<Set<Atuendo>> listaDeAtuendos;

	private ServicioMeteorologico servicioImpostor;

	private ServicioOpenWeather servicioOWMock;
	private ServicioApixU servicioAUMock;

	private Prenda prendaCamisa1;
	private Prenda prendaCamisa2;
	private Prenda prendaCampera1;
	private Prenda prendaCampera2;

	private DateTime fechaFutura;

	private Evento evento1;
	private Evento evento2;
	private Evento evento3;

	private Aceptar decision1;
	private Calificar decision2;
	private Rechazar decision3;

	Guardarropa guardarropaCompartido;
	UsuarioGratuito tipoDeUsuarioQueComparte1;
	UsuarioGratuito tipoDeUsuarioQueComparte2;
	Usuario usuarioQueComparte1;
	Usuario usuarioQueComparte2;

	private Usuario usuarioNotificar;
	
	private GuardarropaDAO GuardarropaDAO;
	private UsuarioDAO UsuarioDAO;
	private EventoDAO EventoDAO;

	@Before
	public void initialize() {

		prendaBorrador = new PrendaBorrador();

		/////////////////////////// Primer guardarropa///////////////////////////

		prendaBorrador.setTipoDePrenda(TipoDePrenda.CAMISA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(0, 0, 0));
		prendaCamisa1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.SACO);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaSaco1 = prendaBorrador.crear();
		prendaSaco1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.MUSCULOSA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaMusculosa1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.SHORT);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaShort1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.OJOTAS);
		prendaBorrador.setMaterial(Material.GOMA);
		prendaBorrador.setColor(new Color(13, 3, 41));
		prendaOjotas1 = prendaBorrador.crear();

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

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(12, 34, 51));
		prendaRemera1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.SEDA);
		prendaBorrador.setColor(new Color(14, 20, 10));
		prendaRemera2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.CORDEROY);
		prendaBorrador.setColor(new Color(14, 20, 10));
		prendaPantalon1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.JEAN);
		prendaBorrador.setColor(new Color(14, 24, 50));
		prendaPantalon2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.CUERO);
		prendaBorrador.setColor(new Color(12, 24, 60));
		prendaCalzado1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.SINTETICO);
		prendaBorrador.setColor(new Color(14, 24, 50));
		prendaCalzado2 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATILLA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(4, 24, 10));
		prendaZapatilla1 = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.GORRO);
		prendaBorrador.setMaterial(Material.LANA);
		prendaBorrador.setColor(new Color(15, 20, 34));
		prendaGorro = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.BUFANDA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(12, 12, 12));
		prendaBufanda = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.GUANTES);
		prendaBorrador.setMaterial(Material.LANA);
		prendaBorrador.setColor(new Color(13, 15, 54));
		prendaGuantes = prendaBorrador.crear();

		guardarropa1 = new Guardarropa();

		guardarropa1.incluirEnGuardarropa(prendaRemera1);
		guardarropa1.incluirEnGuardarropa(prendaRemera2);
		guardarropa1.incluirEnGuardarropa(prendaPantalon1);
		guardarropa1.incluirEnGuardarropa(prendaPantalon2);
		guardarropa1.incluirEnGuardarropa(prendaCalzado1);
		guardarropa1.incluirEnGuardarropa(prendaCalzado2);
		guardarropa1.incluirEnGuardarropa(prendaCamisa1);
		guardarropa1.incluirEnGuardarropa(prendaCamisa2);
		guardarropa1.incluirEnGuardarropa(prendaCampera1);
		guardarropa1.incluirEnGuardarropa(prendaCampera2);
		guardarropa1.incluirEnGuardarropa(prendaSaco1);

		/////////////////// Atuendos esperados (para primer
		/////////////////// guardarropa)///////////////////////////
		listaDePrendas = Sets.newHashSet();
		listaDePrendas.add(prendaRemera1);
		listaDePrendas.add(prendaPantalon1);
		listaDePrendas.add(prendaCalzado1);

		atuendo1 = new Atuendo(listaDePrendas);
		atuendo2 = new Atuendo(listaDePrendas);
		atuendo3 = new Atuendo(listaDePrendas);
		atuendo4 = new Atuendo(listaDePrendas);
		atuendo5 = new Atuendo(listaDePrendas);
		atuendo6 = new Atuendo(listaDePrendas);
		atuendo7 = new Atuendo(listaDePrendas);
		atuendo8 = new Atuendo(listaDePrendas);

		/////////////////////////// Segundo guardarropa///////////////////////////

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.ALGODON);
		prendaBorrador.setColor(new Color(45, 66, 10));
		prendaRemera_a = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.REMERA);
		prendaBorrador.setMaterial(Material.SEDA);
		prendaBorrador.setColor(new Color(44, 50, 11));
		prendaRemera_b = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.GABARDINA);
		prendaBorrador.setColor(new Color(19, 41, 22));
		prendaPantalon_a = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.PANTALON);
		prendaBorrador.setMaterial(Material.JEAN);
		prendaBorrador.setColor(new Color(14, 24, 50));
		prendaPantalon_b = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.CUERO);
		prendaBorrador.setColor(new Color(66, 12, 22));
		prendaCalzado_a = prendaBorrador.crear();

		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.SINTETICO);
		prendaBorrador.setColor(new Color(33, 23, 53));
		prendaCalzado_b = prendaBorrador.crear();

		guardarropa2 = new Guardarropa();

		/////////////////////////// Usuario///////////////////////////
		tipoDeUsuario = new UsuarioPremium();
		usuario = new Usuario(tipoDeUsuario);

		usuario.agregarGuardarropa(guardarropa1); // guardarropa1 es un nuevo guardarropa con valores ya cargados
		usuario.agregarGuardarropa(guardarropa2); // guardarropa2 es un nuevo guardarropa sin valores, solo instanciado

		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaRemera_a);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaRemera_b);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaPantalon_a);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaPantalon_b);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaCalzado_a);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaCalzado_b);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaGorro);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaBufanda);
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaGuantes);

		listaDeAtuendos = Sets.newHashSet();
		/*
		 * lista1.add(atuendo1); lista2.add(atuendo2); listaDeAtuendos.add(lista1);
		 * listaDeAtuendos.add(lista2);
		 */

		guardarropa3 = new Guardarropa();
		guardarropa4 = new Guardarropa();
		guardarropa5 = new Guardarropa();

		servicioImpostor = new ServicioMock();
		servicioOWMock = Mockito.mock(ServicioOpenWeather.class);
		servicioAUMock = Mockito.mock(ServicioApixU.class);

		/////////////////////////// Usuario Gratuito///////////////////////////
		guardarropaUsuarioGratuito = new Guardarropa();

		guardarropaUsuarioGratuito.incluirEnGuardarropa(prendaRemera_a);
		guardarropaUsuarioGratuito.incluirEnGuardarropa(prendaRemera_b);
		guardarropaUsuarioGratuito.incluirEnGuardarropa(prendaPantalon_a);
		guardarropaUsuarioGratuito.incluirEnGuardarropa(prendaGorro);
		guardarropaUsuarioGratuito.incluirEnGuardarropa(prendaBufanda);
		guardarropaUsuarioGratuito.incluirEnGuardarropa(prendaGuantes);

		tipoDeUsuarioGratuito = new UsuarioGratuito(3);
		usuarioGratuito = new Usuario(tipoDeUsuarioGratuito);

		usuarioGratuito.agregarGuardarropa(guardarropaUsuarioGratuito);

		/////////////////////////// Usuarios que comparten////////////////////

		/////////////////////////// Eventos///////////////////////////
		fechaFutura = new DateTime().plusHours(1);


		evento1 = new Evento("qwe", fechaFutura, "Buenos Aires", TipoDeEvento.FORMAL);
		evento2 = new Evento("qwe", new DateTime(2029, 5, 30, 23, 00), "Buenos Aires", TipoDeEvento.INFORMAL);
		evento3 = new Evento("Salir con amigos", fechaFutura, "Buenos Aires", TipoDeEvento.INFORMAL);

		////////////////////////// Decisiones////////////////////////
		decision1 = new Aceptar(atuendo1);
		decision2 = new Calificar(atuendo2);
		decision3 = new Rechazar(atuendo3);

		/////////

		guardarropaCompartido = new Guardarropa();
		tipoDeUsuarioQueComparte1 = new UsuarioGratuito(3);
		tipoDeUsuarioQueComparte2 = new UsuarioGratuito(3);

		usuarioQueComparte1 = new Usuario(tipoDeUsuarioQueComparte1);
		usuarioQueComparte2 = new Usuario(tipoDeUsuarioQueComparte2);

		usuarioQueComparte1.agregarGuardarropa(guardarropaCompartido);
		usuarioQueComparte2.agregarGuardarropa(guardarropaCompartido);

		////////////////////////// Acciones////////////////////////

		usuarioNotificar = new Usuario(tipoDeUsuario);

		usuarioNotificar.setMail("usuario.ejemplo20@gmail.com");
		usuarioNotificar.setPassword("aq1sw2de3fr4");

		usuarioNotificar.agregarGuardarropa(guardarropa1);
		usuarioNotificar.agregarEvento("Salir con amigos", fechaFutura, "Buenos Aires", TipoDeEvento.INFORMAL);
		usuarioNotificar.agregarEvento("Reunion de trabajo", fechaFutura, "Buenos Aires", TipoDeEvento.FORMAL);

		///////////////////////// Usuario friolento//////////////
		usuarioFriolento = new Usuario(tipoDeUsuario);
		guardarropaUsuarioFriolento = new Guardarropa();

		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaBufanda);
		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaGorro);
		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaGuantes);
		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaRemera1);
		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaCampera1);
		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaPantalon1);
		guardarropaUsuarioFriolento.incluirEnGuardarropa(prendaZapatilla1);
		
		GuardarropaDAO = new GuardarropaDAO(entityManager());
		UsuarioDAO = new UsuarioDAO(entityManager());
		EventoDAO = new EventoDAO(entityManager());
	}

	@Test(expected = MaterialInvalidoException.class)
	public void fallaPorMaterialInvalido() {
		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.ORO);
		prendaBorrador.setColor(new Color(12, 34, 51));
		prendaBorrador.setColorSecundario(new Color(12, 34, 51));
		prendaBorrador.crear();
	}

	@Test(expected = ColorIgualException.class)
	public void fallaPorColorIgual() {
		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.CUERO);
		prendaBorrador.setColor(new Color(20, 20, 20));
		prendaBorrador.setColorSecundario(new Color(20, 20, 20));
		prendaBorrador.crear();
	}

	@Test
	public void tipoDePrendaConMaterialValido() {
		prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
		prendaBorrador.setMaterial(Material.CUERO);
		prendaBorrador.setColor(new Color(12, 34, 51));
		prendaBorrador.crear();
	}

	@Test(expected = RuntimeException.class)
	public void creoPrendaSinMaterial() {
		try {
			prendaBorrador = new PrendaBorrador();
			prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
			prendaBorrador.crear();
		} catch (RuntimeException re) {
			String message = "Material es obligatorio";
			Assert.assertEquals(message, re.getMessage());
			throw re;
		}
	}

	@Test(expected = RuntimeException.class)
	public void creoPrendaSinColor() {
		try {
			prendaBorrador = new PrendaBorrador();
			prendaBorrador.setTipoDePrenda(TipoDePrenda.ZAPATO);
			prendaBorrador.setMaterial(Material.CUERO);
			prendaBorrador.crear();
		} catch (RuntimeException re) {
			String message = "color es obligatorio";
			Assert.assertEquals(message, re.getMessage());
			throw re;
		}
	}

	@Test(expected = RuntimeException.class)
	public void creoPrendaSinTipoDePrenda() {
		try {
			prendaBorrador = new PrendaBorrador();
			prendaBorrador.crear();
			// Run exception throwing operation here
		} catch (RuntimeException re) {
			String message = "Tipo de prenda es obligatorio";
			Assert.assertEquals(message, re.getMessage());
			throw re;
		}
	}

	/*
	 * @Test public void verificoListadePrendasSuperiorEnGuardarropa1 (){
	 * List<Prenda> listaPrendasSuperiores =
	 * Arrays.asList(prendaRemera1,prendaRemera2, prendaCamisa1, prendaCamisa2,
	 * prendaCampera1, prendaCampera2, prendaSaco1);
	 * Assert.assertEquals(guardarropa1.getPrendasPorCategoria(Categoria.
	 * PARTE_SUPERIOR),listaPrendasSuperiores); }
	 */

	@Test
	public void verificarCantidadDeAtuendosSugeridos() throws IOException {
		guardarropa1.incluirEnGuardarropa(prendaZapatilla1);

		atuendos = guardarropa1.sugerir(servicioImpostor.obtenerTemperatura(), TipoDeEvento.INFORMAL, usuarioQueComparte1);
		Assert.assertEquals(16, atuendos.size());
	}

	@Test
	public void verificarCantidadDeAtuendosSugeridosConAccesorios() throws IOException {
		guardarropa5.incluirEnGuardarropa(prendaZapatilla1);
		guardarropa5.incluirEnGuardarropa(prendaCamisa1);
		guardarropa5.incluirEnGuardarropa(prendaRemera1);
		guardarropa5.incluirEnGuardarropa(prendaRemera2);
		guardarropa5.incluirEnGuardarropa(prendaGorro);
		guardarropa5.incluirEnGuardarropa(prendaGuantes);
		guardarropa5.incluirEnGuardarropa(prendaBufanda);
		guardarropa5.incluirEnGuardarropa(prendaPantalon1);
		guardarropa5.incluirEnGuardarropa(prendaCampera1);

		atuendos = guardarropa5.sugerir(10.0, TipoDeEvento.INFORMAL, usuarioQueComparte1);
		Assert.assertEquals(16, atuendos.size());
	}

	/*
	 * @Test public void verificoGuardarropa1LuegoDeSugerirAtuendos () throws
	 * IOException{ //COMPARO LOS DIFERENTES ATUENDOS PRE-ARMADOS CON LOS GENERADOS
	 * POR GUARDARROPA.SUGERIR. -> VER DE HACERLO ITERATIVO PARA VERIFICAR TODASLAS
	 * COMBINACIONES POSIBLES
	 * Mockito.when(servicioOWMock.obtenerTemperatura()).thenReturn(25.0);
	 * 
	 * List<Atuendo> listaDeAtuendos =
	 * Arrays.asList(atuendo1,atuendo2,atuendo3,atuendo4,atuendo5,atuendo6,atuendo7,
	 * atuendo8); atuendos =
	 * guardarropa1.sugerir(servicioOWMock.obtenerTemperatura());
	 * Assert.assertEquals(listaDeAtuendos.get(0).getParteSuperior(),atuendos.get(0)
	 * .getParteSuperior());
	 * Assert.assertEquals(listaDeAtuendos.get(0).getParteInferior(),atuendos.get(0)
	 * .getParteInferior());
	 * Assert.assertEquals(listaDeAtuendos.get(0).getCalzado(),atuendos.get(0).
	 * getCalzado()); }
	 */

	@Test
	public void verificarCantidadTotalDeAtuendosSugeridosPorTodosLosGuardarropas() throws IOException {
		Mockito.when(servicioOWMock.obtenerTemperatura()).thenReturn(35.0);

		listaDeAtuendos = usuario.sugerirATodosLosGuardarropas(servicioOWMock.obtenerTemperatura(),
				TipoDeEvento.FORMAL);
		Assert.assertEquals(2, listaDeAtuendos.size());
	}

	@Test(expected = GuardarropaExistenteException.class)
	public void fallaGuardarropaExistenteEnUsuario() {
		usuario.agregarGuardarropa(guardarropa1);
	}

	@Test(expected = PrendaCompartidaException.class)
	public void fallaPrendaCompartidaConOtroGuardarropa() {
		guardarropa3.incluirEnGuardarropa(prendaRemera1);
		usuario.agregarGuardarropa(guardarropa3);
	}

	@Test(expected = PrendaExistenteException.class)
	public void fallaAgregadoDePrendaExistenteEnAlgunGuardarropa() {
		usuario.agregarPrendaAGuardarropa(guardarropa2, prendaPantalon_b);
	}

	@Test
	public void obtenerTemperaturaServicioImpostor() throws IOException {
		double temperatura;
		temperatura = servicioImpostor.obtenerTemperatura();
		Assert.assertEquals(14.0, temperatura, 0);
	}

	@Test(expected = MaximoDePrendasAlcanzadoException.class)
	public void fallaPorMaximoDePrendasAlcanzado() {
		usuarioGratuito.agregarPrendaAGuardarropa(guardarropaUsuarioGratuito, prendaPantalon1);
	}

	@Test
	public void esUnEventoProximo() {
		Assert.assertTrue(evento1.estaProximo());
	}

	@Test
	public void esUnEventoLejano() {
		Assert.assertFalse(evento2.estaProximo());
	}

	@Test(expected = FechaAnteriorException.class)
	public void fallaPorFechaAnterior() {
		usuario.agregarEvento("Ir a trabajar", new DateTime(2010, 5, 30, 23, 00), "Buenos Aires", TipoDeEvento.FORMAL);
	}

	@Test
	public void verificarTemperaturaFutura() throws IOException {
		Mockito.when(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento)).thenReturn(20.0);
		double temperaturaFutura = evento3.getTemperatura(servicioAUMock);
//    	usuarioQueComparte1.calificarAtuendo(atuendo1, evento3, 2);
		Assert.assertEquals(20.0, temperaturaFutura, 0);
	}

	@Test
	public void verificarEventoFormal() throws IOException {
		guardarropa3.incluirEnGuardarropa(prendaSaco1);
		guardarropa3.incluirEnGuardarropa(prendaPantalon1);
		guardarropa3.incluirEnGuardarropa(prendaCalzado1);
		guardarropa3.incluirEnGuardarropa(prendaRemera1);
		guardarropa3.incluirEnGuardarropa(prendaCamisa1);
		guardarropa3.incluirEnGuardarropa(prendaCalzado2);

		Mockito.when(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento)).thenReturn(25.0);
		Assert.assertEquals(2, guardarropa3.sugerir(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento),
				TipoDeEvento.FORMAL, usuarioQueComparte1).size());
	}

	@Test
	public void verificarEventoInformalA35Grados() throws IOException {

		guardarropa4.incluirEnGuardarropa(prendaSaco1);
		guardarropa4.incluirEnGuardarropa(prendaPantalon1);
		guardarropa4.incluirEnGuardarropa(prendaCalzado1);
		guardarropa4.incluirEnGuardarropa(prendaRemera1);
		guardarropa4.incluirEnGuardarropa(prendaCamisa1);
		guardarropa4.incluirEnGuardarropa(prendaMusculosa1);
		guardarropa4.incluirEnGuardarropa(prendaCalzado2);
		guardarropa4.incluirEnGuardarropa(prendaOjotas1);
		guardarropa4.incluirEnGuardarropa(prendaShort1);

		Mockito.when(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento)).thenReturn(35.0);

		Assert.assertEquals(4, guardarropa4.sugerir(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento),
				evento3.getTipoEvento(), usuarioQueComparte1).size());
	}

	@Test
	public void verificarEventoInformalA35GradosConPrendas() {

//        listaDePrendas2 = Sets.newHashSet();
//        listaDePrendas2.add(prendaRemera1);
//        listaDePrendas2.add(prendaMusculosa1);
//        listaDePrendas2.add(prendaShort1);
//        listaDePrendas2.add(prendaOjotas1);
//        atuendo9 = new Atuendo(listaDePrendas2);

		listaDePrendas3 = Sets.newHashSet();
		listaDePrendas3.add(prendaRemera1);
		listaDePrendas3.add(prendaShort1);
		listaDePrendas3.add(prendaOjotas1);
		atuendo4 = new Atuendo(listaDePrendas3);

		listaDePrendas4 = Sets.newHashSet();
		listaDePrendas4.add(prendaMusculosa1);
		listaDePrendas4.add(prendaShort1);
		listaDePrendas4.add(prendaOjotas1);
		atuendo5 = new Atuendo(listaDePrendas4);

		listaDePrendas5 = Sets.newHashSet();
		listaDePrendas5.add(prendaPantalon1);
		listaDePrendas5.add(prendaRemera1);
		listaDePrendas5.add(prendaOjotas1);
		atuendo6 = new Atuendo(listaDePrendas5);

		listaDePrendas6 = Sets.newHashSet();
		listaDePrendas6.add(prendaPantalon1);
		listaDePrendas6.add(prendaMusculosa1);
		listaDePrendas6.add(prendaOjotas1);
		atuendo7 = new Atuendo(listaDePrendas6);

		atuendos = Sets.newHashSet();
		atuendos.add(atuendo5);
		atuendos.add(atuendo4);
//        atuendos.add(atuendo9);
		atuendos.add(atuendo6);
		atuendos.add(atuendo7);

		guardarropa4.incluirEnGuardarropa(prendaSaco1);
		guardarropa4.incluirEnGuardarropa(prendaPantalon1);
		guardarropa4.incluirEnGuardarropa(prendaCalzado1);
		guardarropa4.incluirEnGuardarropa(prendaRemera1);
		guardarropa4.incluirEnGuardarropa(prendaCamisa1);
		guardarropa4.incluirEnGuardarropa(prendaMusculosa1);
		guardarropa4.incluirEnGuardarropa(prendaCalzado2);
		guardarropa4.incluirEnGuardarropa(prendaOjotas1);
		guardarropa4.incluirEnGuardarropa(prendaShort1);

		Mockito.when(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento)).thenReturn(35.0);
		Assert.assertTrue(this
				.compararSugerencias(guardarropa4.sugerir(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento),
						TipoDeEvento.INFORMAL, usuarioQueComparte1), atuendos));
	}

	public boolean compararSugerencias(Set<Atuendo> sugerenciasActual, Set<Atuendo> sugerenciasExpected) {

		// Sugerencia = Set<Atuendo>
		// Atuendo = Set<Prenda>

		return sugerenciasActual.size() == sugerenciasExpected.size()
				&& sugerenciasActual.stream().allMatch(atuendoActual -> {
					return sugerenciasExpected.stream().anyMatch(atuendoExpected -> {
						return atuendoActual.getPrendas().size() == atuendoExpected.getPrendas().size()
								&& atuendoActual.getPrendas().stream().anyMatch(prendaActual -> {
									return atuendoExpected.getPrendas().stream()
											.anyMatch(prendaExpected -> prendaActual.equals(prendaExpected));
								});
					});
				});
	}

	@Test
	public void verificarCantidadAtuendosQueCubrenTodoConCincoPrendas() {
		// Usamos los filters para obtener los atuendos que cubren todo el cuerpo y solo
		// tienen un calzado y una prenda inferior
		guardarropa4.incluirEnGuardarropa(prendaPantalon1);
		guardarropa4.incluirEnGuardarropa(prendaCalzado1);
		guardarropa4.incluirEnGuardarropa(prendaRemera1);
		guardarropa4.incluirEnGuardarropa(prendaShort1);
		guardarropa4.incluirEnGuardarropa(prendaOjotas1);

		Set<Set<Prenda>> combinacionesDePrendasPosibles = Sets.powerSet(guardarropa4.getPrendas());
		Set<Atuendo> atuendosPosibles = combinacionesDePrendasPosibles.stream()
				.map(unaCombinacion -> new Atuendo(unaCombinacion)).collect(Collectors.toSet());
		Set<Atuendo> atuendosAEvaluar = atuendosPosibles.stream().filter(unAtuendo -> unAtuendo.cubreTodoElCuerpo())
				.filter(unAtuendo -> unAtuendo.noRepiteCategoria(Categoria.CALZADO))
				.filter(unAtuendo -> unAtuendo.noRepiteCategoria(Categoria.PARTE_INFERIOR)).collect(Collectors.toSet());

		Assert.assertEquals(4, atuendosAEvaluar.size());
	}

	@Test
	public void verificarCantidadAtuendosQueCubrenTodoConSeisPrendas() {
		// Usamos los filters para obtener los atuendos que cubren todo el cuerpo y solo
		// tienen un calzado y una prenda inferior
		guardarropa4.incluirEnGuardarropa(prendaMusculosa1);
		guardarropa4.incluirEnGuardarropa(prendaPantalon1);
		guardarropa4.incluirEnGuardarropa(prendaCalzado1);
		guardarropa4.incluirEnGuardarropa(prendaRemera1);
		guardarropa4.incluirEnGuardarropa(prendaShort1);
		guardarropa4.incluirEnGuardarropa(prendaOjotas1);

		Set<Set<Prenda>> combinacionesDePrendasPosibles = Sets.powerSet(guardarropa4.getPrendas());
		Set<Atuendo> atuendosPosibles = combinacionesDePrendasPosibles.stream()
				.map(unaCombinacion -> new Atuendo(unaCombinacion)).collect(Collectors.toSet());
		Set<Atuendo> atuendosAEvaluar = atuendosPosibles.stream().filter(unAtuendo -> unAtuendo.cubreTodoElCuerpo())
				.filter(unAtuendo -> unAtuendo.noRepiteCategoria(Categoria.CALZADO))
				.filter(unAtuendo -> unAtuendo.noRepiteCategoria(Categoria.PARTE_INFERIOR)).collect(Collectors.toSet());

		Assert.assertEquals(12, atuendosAEvaluar.size());
	}

	@Test
	public void verificarEventoFormalA14Grados() throws IOException {
		guardarropa4.incluirEnGuardarropa(prendaCampera1);
		guardarropa4.incluirEnGuardarropa(prendaPantalon1);
		guardarropa4.incluirEnGuardarropa(prendaZapatilla1);
		guardarropa4.incluirEnGuardarropa(prendaRemera1);
		guardarropa4.incluirEnGuardarropa(prendaCamisa1);
		guardarropa4.incluirEnGuardarropa(prendaMusculosa1);
		guardarropa4.incluirEnGuardarropa(prendaOjotas1);
		guardarropa4.incluirEnGuardarropa(prendaShort1);
		guardarropa4.incluirEnGuardarropa(prendaBufanda);
		guardarropa4.incluirEnGuardarropa(prendaGorro);
		guardarropa4.incluirEnGuardarropa(prendaGuantes);

		Mockito.when(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento)).thenReturn(14.0);

		Assert.assertEquals(8, guardarropa4.sugerir(servicioAUMock.obtenerTemperaturaFutura(evento3.fechaEvento),
				TipoDeEvento.INFORMAL, usuarioQueComparte1).size());
	}

	@Test
	public void verificarCargaImagen() {
		String path = "./src/main/java/imagenes/camisa_negra.jpg";

		File file = new File(path);
		byte[] fileArray = new byte[(int) file.length()];
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(fileArray);
			fileInputStream.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		prendaCamisa1.cargarImagen(fileArray, "camisa_negra_redimensionado");

		byte[] bytesImagen = prendaCamisa1.obtenerImagen();

		Assert.assertTrue(bytesImagen.length != 0);
	}

	@Test
	public void verificarGuardarropaCompartido() {
		RepositorioUsuarios.getInstance().agregarNuevoUsuario(usuarioQueComparte1);
		RepositorioUsuarios.getInstance().agregarNuevoUsuario(usuarioQueComparte2);
		Assert.assertTrue(guardarropaCompartido.estaCompartido());
	}

	@Test
	public void verificarCalificacionAtuendo2() {
		atuendo2.setEstadoAtuendo(EstadoAtuendo.ACEPTADO);
		decision2.setCalificacion(Calificacion.ABRIGADO);
		decision2.tomarDecision();

		Assert.assertEquals(Calificacion.ABRIGADO, atuendo2.getCalificacion());
	}

	@Test
	public void verificarAceptacionAtuendoPrendasNoDisponibles() {
		decision1.tomarDecision();

		Assert.assertFalse(atuendo1.estaDisponible());
	}

	@Test
	public void enviarNotificacionMailAUsuario() {
		usuarioNotificar.agregarAccion(new EnviarMail());

		Mockito.when(servicioAUMock.obtenerTemperaturaFutura(fechaFutura)).thenReturn(15.0);
		usuarioNotificar.sugerirAtuendosParaEventosProximos(servicioAUMock);
		usuarioNotificar.recibirAlerta();
	}

	@Test
	public void verificarAtuendosUsuarioFriolento() {
		usuarioFriolento.setSensacionTermicaEspecifica(true, false, false);

		Set<Atuendo> atuendos = guardarropaUsuarioFriolento.sugerir(14.0, TipoDeEvento.INFORMAL, usuarioFriolento);

		Assert.assertTrue(
				atuendos.stream().allMatch(unAtuendo -> unAtuendo.contieneDeCategoria(Categoria.ACCESORIOCABEZA)));
	}
	
	@Test(expected = GuardarropaExistenteException.class)
	public void obtenerGuardarropa_NoExisteGuardarropaConId() {
		long idGuardarropa = 100;
		
		Guardarropa guardarropa = GuardarropaDAO.obtenerGuardarropa(idGuardarropa);
	}
	
	@Test
	public void persistirGuardarropa_GuardarropaConUnaSolaPrenda() {
		prendaRemera1.setTrama(Trama.CUADRILLE);
		prendaRemera1.setColorSecundario(new Color(21,23,22));
		prendaRemera1.setPathImagen("C:\\users\\saraza");
		prendaRemera1.setEstaDisponible(true);
		guardarropa3.incluirEnGuardarropa(prendaRemera1);
		
		long idGuardarropa = GuardarropaDAO.guardarGuardarropa(guardarropa3);
		
		Guardarropa guardarropaDB = GuardarropaDAO.obtenerGuardarropa(idGuardarropa);
		
		assertEquals(guardarropaDB, guardarropa3);
	}
	
	@Test
	public void actualizarGuardarropa_CambioPathImagenTodasLasPrendas() {
		long idGuardarropa = 1;
		String pathModificado = "D:\\unaCarpeta\\otraCarpeta\\carpetaFinal";
		
		Guardarropa guardarropaDB = GuardarropaDAO.obtenerGuardarropa(idGuardarropa);
		
		Set<Prenda> prendas = guardarropaDB.getPrendas();
		
		prendas.forEach(prenda -> prenda.setPathImagen(pathModificado));
		
		GuardarropaDAO.actualizarGuardarropa(guardarropaDB);
		
		Guardarropa guardarropaModificado = GuardarropaDAO.obtenerGuardarropa(idGuardarropa);
		
		Set<Prenda> prendasModificadas = guardarropaModificado.getPrendas();
		
		assertTrue(prendasModificadas.stream().allMatch(prenda -> prenda.getPathImagen().equals(pathModificado)));
	}
	
	@Test
	public void persistirUsuario() {
		long idUsuario = UsuarioDAO.guardarUsuario(usuarioNotificar);
		
		Usuario usuarioDB = UsuarioDAO.obtenerUsuario(idUsuario);
		
		assertEquals(usuarioDB, usuarioNotificar);
	}
	

/*
	@Test
	public void actualizarUsuario_EliminoEvento() {
		long idUsuario = 2;

		Usuario usuarioDB = UsuarioDAO.obtenerUsuario(idUsuario);

		int cantidadOriginal = usuarioDB.getEventos().size();

		usuarioDB.eliminarPrimerEvento();

		UsuarioDAO.actualizarUsuario(usuarioDB);

		Usuario usuarioModificado = UsuarioDAO.obtenerUsuario(idUsuario);

		int cantidadEventosModificados = usuarioModificado.getEventos().size();

		assertTrue(cantidadEventosModificados + 1 == cantidadOriginal);
	}*/

	@Test
	public void actualizarUsuario_EliminoEventoEspecifico() {
		long idUsuario = 2;
		long idEvento;
		DateTime fechaEventoABorrar = new DateTime(2019, 9, 8, 20, 57, 45);
		Usuario usuarioDB = UsuarioDAO.obtenerUsuario(idUsuario);

		int cantidadOriginal = usuarioDB.getEventos().size();

		idEvento = usuarioDB.eliminarEvento(fechaEventoABorrar, "Reunion de trabajo");

		Evento eventoABorrar = EventoDAO.obtenerEvento(idEvento);
		System.out.println(eventoABorrar.getId());

		EventoDAO.eliminarEvento(eventoABorrar);
		UsuarioDAO.actualizarUsuario(usuarioDB);



		Usuario usuarioModificado = UsuarioDAO.obtenerUsuario(idUsuario);

		int cantidadEventosModificados = usuarioModificado.getEventos().size();

		assertTrue(cantidadEventosModificados + 1 == cantidadOriginal);
	}

	@Test
	public void actualizarUsuario_CambioPathImagenTodasLasPrendasDeGuardarropaPertenecienteAUnUsuario() {
		long idUsuario = 1;
		String pathModificado = "D:\\unaCarpeta\\otraCarpeta\\carpetaFinal";
		
		Usuario usuarioDB = UsuarioDAO.obtenerUsuario(idUsuario);
		Guardarropa guardarropaDB = usuarioDB.getGuardarropas().get(0);
		Set<Prenda> prendasDB = guardarropaDB.getPrendas();
		
		prendasDB.stream().forEach(prenda -> prenda.setPathImagen(pathModificado));
		
		UsuarioDAO.actualizarUsuario(usuarioDB);
		
		Usuario usuarioModificado = UsuarioDAO.obtenerUsuario(idUsuario);
		Guardarropa guardarropaModificado = usuarioModificado.getGuardarropas().get(0);
		Set<Prenda> prendasModificadas = guardarropaModificado.getPrendas();
		
		assertTrue(prendasModificadas.stream().allMatch(prenda -> prenda.getPathImagen().equals(pathModificado)));
	}
}
