package queMePongoClases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import excepciones.*;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Clima.ServicioMeteorologico;
import Notificaciones.Accion;
import decisiones.Aceptar;
import decisiones.Calificar;
import decisiones.Rechazar;
import enums.Calificacion;
import enums.Categoria;
import enums.EstadoAtuendo;
import enums.TipoDeEvento;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private long Id;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Usuario_Guardarropa", 
			   joinColumns = @JoinColumn(name = "IdUsuario"), 
			   inverseJoinColumns = @JoinColumn(name = "IdGuardarropa"))
	public List<Guardarropa> guardarropas;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	public TipoDeUsuario tipoDeUsuario;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Usuario_Evento", 
	   		   joinColumns = @JoinColumn(name = "IdUsuario"), 
	   		   inverseJoinColumns = @JoinColumn(name = "IdEvento"))
	private List<Evento> eventos;
	
	@OrderColumn(name = "orden")
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idUsuario")
	public List<Decision> decisiones;
	
	@Transient
	public HashMap<Integer, ArrayList<Calificacion>> sensacionTermica; // En la key del hashmap guardo la temperatura, en el valor de esa key el nivel de abrigo promedio en base a las calificaciones que recibieron los atuendos para eso
	
	@Transient
	public HashMap<Categoria, Boolean> sensacionTermicaEspecifica;
	
	@Transient
	private HashMap<Integer, Integer> modificacionSegunCalificacion;	
	
	@Transient
	private String mail;
	
	@Transient
	private String password;
	
	@Transient
	private String telefono;
	
	@Transient
	private List<Accion> acciones;

	@Transient
	private List<Evento> listaFiltradaPorEvento;
	
	public Usuario() {
		
	}

	public Usuario(TipoDeUsuario tipoDeUsuario) {
		this.guardarropas = new ArrayList<Guardarropa>();
		this.tipoDeUsuario = tipoDeUsuario;
		this.eventos = new ArrayList<Evento>();
		this.decisiones = new ArrayList<Decision>();
		this.acciones = new ArrayList<Accion>();
		this.sensacionTermica = new HashMap<Integer, ArrayList<Calificacion>>();
		this.sensacionTermicaEspecifica = createSensacionTermicaEspecificaMap();
		this.modificacionSegunCalificacion = createCalificationMap();
	} 
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}

	public void setGuardarropas(List<Guardarropa> guardarropas) {
		this.guardarropas = guardarropas;
	}

	public TipoDeUsuario getTipoDeUsuario() {
		return tipoDeUsuario;
	}

	public void setTipoDeUsuario(TipoDeUsuario tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}

	public List<Decision> getDecisiones() {
		return decisiones;
	}

	public void setDecisiones(List<Decision> decisiones) {
		this.decisiones = decisiones;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
	/////// Agregado de guardarropa ///////

	public void agregarGuardarropa(Guardarropa guardarropa) {
		if (this.poseeGuardarropa(guardarropa)) {
			throw new GuardarropaExistenteException("El guardarropa a agregar ya lo posee el usuario");
		}
		if (this.poseePrendaCompartidaConOtroGuardarropa(guardarropa)) {
			throw new PrendaCompartidaException(
					"El guardarropa a agregar posee alguna prenda que pertenece a alguno de los guardarropas del usuario");
		}
		this.guardarropas.add(guardarropa);
	}

	/////// Agregado de prendas a guardarropa ///////

	public void agregarPrendaAGuardarropa(Guardarropa guardarropa, Prenda prenda) { //Se asume que el guardarropa pasado por parametro es perteneciente al usuario
		if (this.poseePrendaEnOtroGuardarropa(prenda)) {
			throw new PrendaExistenteException("La prenda a agregar pertenece a un guardarropa del usuario");
		}
		
		if(!this.tipoDeUsuario.permiteAgregarPrendaA(guardarropa)){
			throw new MaximoDePrendasAlcanzadoException("El guardarropas alcanzo el maximo de prendas permitido");
		}
		guardarropa.incluirEnGuardarropa(prenda);
	}

	/////// Contiene la prenda/guardarropa en su lista ///////

	public boolean poseeGuardarropa(Guardarropa guardarropa) {
		return guardarropas.contains(guardarropa);
	}

	public boolean poseePrendaCompartidaConOtroGuardarropa(Guardarropa _guardarropa) {
		return guardarropas.stream().anyMatch(guardarropa -> guardarropa.poseePrendaCompartida(_guardarropa));
	}

	public boolean poseePrendaEnOtroGuardarropa(Prenda prenda) {
		return guardarropas.stream().anyMatch(guardarropa -> guardarropa.contienePrenda(prenda));
	}

	/////// Solicitud de sugerencia/s ///////
	
	public Set<Set<Atuendo>> sugerirATodosLosGuardarropas(Double temperatura, TipoDeEvento evento) {  //Si son sugerencias random, le mando la temperatura actual (servicioMeteorologico.obtenerTemperatura())
		return guardarropas.stream().map(guardarropa -> {
			return guardarropa.sugerir(temperatura, evento, this); //Se manda a el mismo para que el guardarropas sepa a quien le sugiere (por los guardarropas compartidos)
	}).collect(Collectors.toSet());
	}
	
	public void sugerirAtuendosParaEventosProximos(ServicioMeteorologico servicioMeteorologico){ //Este servicio podria pasarse por constructor o algo mas prolijo ??
		List<Evento> eventosProximos = this.eventosProximos();
		eventosProximos = eventosProximos.stream().filter(evento -> !evento.getPoseeSugerencias()).collect(Collectors.toList()); //Filtra los eventos proximos que aun no tengan sugerencias
		
		eventosProximos.stream().forEach(evento -> {
			Set<Set<Atuendo>> atuendosSugeridosPorDiferentesGuardarropas;
			atuendosSugeridosPorDiferentesGuardarropas = this.sugerirATodosLosGuardarropas(evento.getTemperatura(servicioMeteorologico),evento.getTipoEvento()); //A cada evento, le hace sugerir a todos los guardarropas del usuario
			Set<Atuendo>atuendosSugeridosParaEvento = atuendosSugeridosPorDiferentesGuardarropas.stream().flatMap(atuendos -> atuendos.stream()).collect(Collectors.toSet());  //Como queda una lista de listas de atuendos, hago flatMap
			evento.setAtuendosSugeridos(atuendosSugeridosParaEvento);
		});
	}
	
 // TODO: 29/05/2019   ARREGLAR
		
	/////// Eventos ///////
	
	public void agregarEvento(String descripcion, DateTime fecha, String ubicacion, TipoDeEvento tipoDeEvento){
		Evento evento = new Evento(descripcion, fecha, ubicacion, tipoDeEvento);
		this.eventos.add(evento);
	}

	public void eliminarPrimerEvento(){
		this.eventos.remove(0);
	}

	public Evento filtrarEvento(DateTime fechaAFiltrar,String descripcion) {

		listaFiltradaPorEvento = eventos.stream()
				.filter(unEvento -> unEvento.matchPorFecha(fechaAFiltrar))
				.filter(unEvento -> unEvento.matchPorDesc(descripcion)).collect(Collectors.toList());

		if (listaFiltradaPorEvento.isEmpty()) {
			System.out.println("No tengo nada");
			throw new EventoNoExistenteException("No existe un Evento ");
		} else {
			return listaFiltradaPorEvento.get(0);
		}
	}

	public long eliminarEvento(DateTime fechaAFiltrar,String descripcion){
		Evento eventoParaId = filtrarEvento(fechaAFiltrar, descripcion);
		long idEventoABorrar = eventoParaId.getId();
		this.eventos.remove(filtrarEvento(fechaAFiltrar,descripcion));
		return idEventoABorrar;
	}

	public List<Evento> getEventos(){
		return this.eventos;
	}
	
	public List<Evento> eventosProximos() {
		return eventos.stream().filter(unEvento -> unEvento.estaProximo()).collect(Collectors.toList());
	}
	
	/////// Decisiones //////

	public void aceptarAtuendo(Evento evento, Atuendo atuendo) {
		//INTERFAZ
		Aceptar aceptado = new Aceptar(atuendo);
		aceptado.tomarDecision();
		agregarDecision(aceptado);
		evento.agregarAtuendoAceptado(atuendo);
	}
	
	public void rechazarAtuendo(Evento evento, Atuendo atuendo) {
		//INTERFAZ
		Rechazar rechazado = new Rechazar(atuendo);
		rechazado.tomarDecision();
		agregarDecision(rechazado);
		evento.agregarAtuendoRechazado(atuendo);
	}
	
	public void calificarAtuendo(Atuendo atuendo, Evento evento, Calificacion calificacion) {
		//INTERFAZ
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		ServicioMeteorologico servicioMeteorologico= (ServicioMeteorologico)context.getBean("servicioMeteorologico");
		
		Calificar calificado = new Calificar(atuendo);
		calificado.setCalificacion(calificacion);
		calificado.tomarDecision();
		agregarDecision(calificado);
		evento.agregarAtuendoCalificado(atuendo);
		
		ArrayList<Calificacion> calificaciones;
		
		int temperatura = evento.getTemperatura(servicioMeteorologico).intValue();
		if(sensacionTermica.get(temperatura) == null){
			calificaciones = new ArrayList<Calificacion>();
			calificaciones.add(calificacion);
			sensacionTermica.put(temperatura, calificaciones);
		}
		else {
			calificaciones = sensacionTermica.get(temperatura);
			calificaciones.add(calificacion);
		}
	}
	
	public void deshacerDecision() {
		
		//Obtengo la ultima decision tomada
		int posUltimaDecision = decisiones.size()-1;
		Decision ultimaDecision = decisiones.get(posUltimaDecision);
		Atuendo ultimoAtuendo = ultimaDecision.getAtuendo();
		
		//Deshago la ultima decision tomada
		ultimaDecision.deshacerDecision();
		
		//Elimino la decision de la lista de decisiones del Usuario
		eliminarDecision(ultimaDecision);
		
//		//Elimino el atuendo (relacionado de la decision) de la lista de atuendos sugeridos asociados a un evento perteneciente a la lista de eventos que posee un Usuario
//		Evento evento = this.getEventoByAtuendo(ultimaDecision.getAtuendo());
//		evento.eliminarAtuendoSugerido(ultimaDecision.getAtuendo());
	}
	
//	private Evento getEventoByAtuendo(Atuendo atuendo) {
//		//La lista solo deberia retorna un evento debido a que la instancia del objeto atuendo en si deberia ser unica y no deberia repetirse en otra lista de atuendos sugeridos de algun otro evento
//		return this.eventos.stream().filter(evento -> evento.contieneAtuendo(atuendo)).collect(Collectors.toList()).get(0); 
//	}
	
	private void eliminarDecision(Decision decision) {
		this.decisiones.remove(decision);
	}
	
	private void agregarDecision(Decision decision) {
		this.decisiones.add(decision);
	}
	
	/////// Acciones ///////
	
	public void agregarAccion(Accion accion) {
		this.acciones.add(accion);
	}
	
	public void quitarAccion(Accion accion) {
		this.acciones.remove(accion);
	}
	
	public void recibirAlerta() {
		this.acciones.stream().forEach(accion -> {
			accion.notificarSugerenciasListas(this);
			//accion.notificarAlertaMeteorologica(this);
		});
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/////// Calificaciones y sensacion termica ///////
	
	private static HashMap<Integer, Integer> createCalificationMap() {
		HashMap<Integer, Integer> myMap = new HashMap<Integer,Integer>();
	    myMap.put(1, 6);
	    myMap.put(2, 3);
	    myMap.put(3, 0);
	    myMap.put(4, -3);
	    myMap.put(5, -6);
	    return myMap;
	}
	
	// Se setea el hashmap de sensibilidad especifico por defecto como false.
	
	private static HashMap<Categoria, Boolean> createSensacionTermicaEspecificaMap(){
		HashMap<Categoria, Boolean> myMap = new HashMap<Categoria, Boolean>();
		myMap.put(Categoria.ACCESORIOCABEZA, false);
		myMap.put(Categoria.ACCESORIOMANOS, false);
		myMap.put(Categoria.ACCESORIOCUELLO, false);
		return myMap;
	}
	
	public int getModificacionNivelDeAbrigo(Double unaTemperatura) {
		Integer temperatura = unaTemperatura.intValue();
		
		if(sensacionTermica.isEmpty()) {
			return 0;
		} else {
			return this.getVariacionAbrigo(this.getPromedioCalificaciones(temperatura));
		}  //Ver si este if es necesario

	}
	
	public int getVariacionAbrigo(int calificacionPromedio) {
		return modificacionSegunCalificacion.get(calificacionPromedio);
	}
	
	public void setSensacionTermicaEspecifica(Boolean stCabeza, Boolean stCuello, Boolean stManos) {
		sensacionTermicaEspecifica.put(Categoria.ACCESORIOCABEZA, stCabeza);
		sensacionTermicaEspecifica.put(Categoria.ACCESORIOCUELLO, stCuello);
		sensacionTermicaEspecifica.put(Categoria.ACCESORIOMANOS, stManos);
	}
	
	public int getPromedioCalificaciones(Integer temperatura) {
		if(!sensacionTermica.containsKey(temperatura)){
			return 3;
		}
		ArrayList<Calificacion> calificaciones = sensacionTermica.get(temperatura);

		int cantidadDeCalificaciones = calificaciones.size();
		int sumaCalificaciones = calificaciones.stream().mapToInt(unaCalificacion -> unaCalificacion.getCalificacion()).sum();

		return sumaCalificaciones/cantidadDeCalificaciones;
	}
}
