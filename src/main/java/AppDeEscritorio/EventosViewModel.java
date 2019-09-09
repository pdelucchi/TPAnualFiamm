package AppDeEscritorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.uqbar.commons.model.annotations.Observable;

import queMePongoClases.Evento;
import queMePongoClases.RepositorioUsuarios;
import queMePongoClases.Usuario;

@Observable
public class EventosViewModel {
	
	private int diaInicio;
	private int mesInicio;
	private int anioInicio;
	private int diaFinal;
	private int mesFinal;
	private int anioFinal;
	private List<Evento> eventos = new ArrayList<Evento>();

	// ********************************************************
	// ** Acciones
	// ********************************************************
	
	public void cargarEventos() {
		DateTime fechaInicio = new DateTime(this.anioInicio, this.mesInicio, this.diaInicio, 0, 0);
		DateTime fechaFinal = new DateTime(this.anioFinal, this.mesFinal, this.diaFinal, 0, 0);
		
		List<Usuario> usuarios = RepositorioUsuarios.getInstance().getUsuariosActuales();
		
		this.eventos = this.getTodosLosEventos(usuarios).stream().filter(evento -> fechaInicio.isBefore(evento.fechaEvento) && fechaFinal.isAfter(evento.fechaEvento)).collect(Collectors.toList());
	}
	
	private List<Evento> getTodosLosEventos(List<Usuario> usuarios){
		List<List<Evento>> eventosDeTodosLosUsuarios = usuarios.stream().map(usuario -> usuario.getEventos()).collect(Collectors.toList());
		return eventosDeTodosLosUsuarios.stream().flatMap(eventos -> eventos.stream()).collect(Collectors.toList());
	}
	
	// ********************************************************
	// ** Atributos
	// ********************************************************
	
	public int getDiaInicio() {
		return diaInicio;
	}
	public void setDiaInicio(int diaInicio) {
		this.diaInicio = diaInicio;
	}
	public int getMesInicio() {
		return mesInicio;
	}
	public void setMesInicio(int mesInicio) {
		this.mesInicio = mesInicio;
	}
	public int getAnioInicio() {
		return anioInicio;
	}
	public void setAnioInicio(int anioInicio) {
		this.anioInicio = anioInicio;
	}
	public int getDiaFinal() {
		return diaFinal;
	}
	public void setDiaFinal(int diaFinal) {
		this.diaFinal = diaFinal;
	}
	public int getMesFinal() {
		return mesFinal;
	}
	public void setMesFinal(int mesFinal) {
		this.mesFinal = mesFinal;
	}
	public int getAnioFinal() {
		return anioFinal;
	}
	public void setAnioFinal(int anioFinal) {
		this.anioFinal = anioFinal;
	}
	public List<Evento> getEventos() {
		return eventos;
	}
	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
}
