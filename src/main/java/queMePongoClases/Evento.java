package queMePongoClases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.uqbar.commons.model.annotations.Observable;

import Clima.ServicioMeteorologico;
import enums.EstadoAtuendo;
import enums.TipoDeEvento;
import excepciones.FechaAnteriorException;

@Observable
@Entity
public class Evento {

	@Id
	@GeneratedValue
	private long Id;

	private String descripcionEvento;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime fechaEvento;
	
	private String ubicacion;

	@Enumerated
	private TipoDeEvento tipoDeEvento;

	private boolean poseeSugerencias;
	
//	@OrderColumn(name = "orden")
//	@OneToMany
//	@JoinColumn(name = "IdEvento")
	@Transient
	Set<Atuendo> atuendosSugeridos; // Los atuendos que se sugieren para los eventos pr�ximos se guardar�n en el
									// evento. Esto se debe a que en un futuro el programa debe poder tener en
									// cuenta qu� atuendos acept� y rechaz� el usuario para cierto tipo de
									// evento.
	@Transient
	Set<Atuendo> atuendosAceptados;
	@Transient
	Set<Atuendo> atuendosRechazados;
	@Transient
	Set<Atuendo> atuendosCalificados; // Los atuendos calificados van a ir ac� para poder acceder a la temperatura del
									  // evento

	public Evento() {
		
	}
	
	public Evento(String descripcionEvento, DateTime fechaEvento, String ubicacion, TipoDeEvento tipoDeEvento) {
		this.descripcionEvento = descripcionEvento;
		this.fechaEvento = fechaEvento;
		this.ubicacion = ubicacion;
		this.tipoDeEvento = tipoDeEvento;
		this.poseeSugerencias = false;
		if (this.esFechaPasada()) {
			throw new FechaAnteriorException("La fecha introducida ya ha pasado");
		}
	}
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public TipoDeEvento getTipoDeEvento() {
		return tipoDeEvento;
	}

	public void setTipoDeEvento(TipoDeEvento tipoDeEvento) {
		this.tipoDeEvento = tipoDeEvento;
	}

	public boolean estaProximo() {
		return this.diferenciaConAhora() < 2;
	}

	public boolean esFechaPasada() {
		return this.diferenciaConAhora() < 0;
	}

	public int diferenciaConAhora() {
		DateTime fechaActual = new DateTime();
		int horas = Hours.hoursBetween(fechaActual, fechaEvento).getHours();
		return horas;
	}

	public Double getTemperatura(ServicioMeteorologico servicioMeteorologico) {
		return servicioMeteorologico.obtenerTemperaturaFutura(this.fechaEvento);
	}

	public TipoDeEvento getTipoEvento() {
		return this.tipoDeEvento;
	}

	public void setAtuendosSugeridos(Set<Atuendo> atuendos) {
		this.atuendosSugeridos = atuendos;
		this.poseeSugerencias = true;
	}

	public void setAtuendosAceptados(Set<Atuendo> atuendos) {
		this.atuendosSugeridos = atuendos;
	}

	public void setAtuendosRechazados(Set<Atuendo> atuendos) {
		this.atuendosSugeridos = atuendos;
	}

	public void setAtuendosCalificados(Set<Atuendo> atuendos) {
		this.atuendosSugeridos = atuendos;
	}

	public void agregarAtuendoAceptado(Atuendo atuendo) {
		this.atuendosAceptados.add(atuendo);
	}

	public void agregarAtuendoRechazado(Atuendo atuendo) {
		this.atuendosRechazados.add(atuendo);
	}

	public void agregarAtuendoCalificado(Atuendo atuendo) {
		this.atuendosCalificados.add(atuendo);
	}
	
//	public boolean contieneAtuendo(Atuendo atuendo) {
//		Set<Atuendo> atuendosSolicitados = this.getAtuendosSugeridos(atuendo.getEstadoAtuendo());
//		return atuendosSolicitados.contains(atuendo);
//	}
//	
//	public void eliminarAtuendoSugerido(Atuendo atuendo) {
//		Set<Atuendo> atuendosSolicitados = this.getAtuendosSugeridos(atuendo.getEstadoAtuendo());
//		atuendosSolicitados.remove(atuendo);
//	}

	/////// M�todos necesarios para View ///////

	public DateTime getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(DateTime fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getDescripcionEvento() {
		return descripcionEvento;
	}

	public void setDescripcionEvento(String descripcionEvento) {
		this.descripcionEvento = descripcionEvento;
	}

	public boolean getPoseeSugerencias() {
		//return this.atuendosSugeridos != null && !this.atuendosSugeridos.isEmpty();
		return this.poseeSugerencias;
	}

	public void setPoseeSugerencias(boolean poseeSugerencias) {
		this.poseeSugerencias = poseeSugerencias;
	}

	public boolean matchPorFecha(DateTime date){
		//DateFormat outputData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
		//DateFormat inputData = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		//formatter.format(date);
		//formatter.format(fechaEvento);


		//DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		//String dateAsAString = formatter.format(date);
		System.out.println(date);
		System.out.println(fechaEvento);
		if(date.compareTo(this.fechaEvento) == 0){
			System.out.println("Soy igual");
			return true;
		}
		else{
			System.out.println("No soy igual");
			return false;
		}
	}

	public boolean matchPorDesc(String desc) {
		if (desc.compareTo(this.descripcionEvento) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
