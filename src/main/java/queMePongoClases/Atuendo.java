package queMePongoClases;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import enums.Calificacion;
import enums.Categoria;
import enums.EstadoAtuendo;
import enums.NivelSuperposicion;
import enums.TipoDeEvento;
import excepciones.CalificacionInvalidaException;

@Entity
public class Atuendo {
	
	@Id
	@GeneratedValue
	private long Id;
	
	@OrderColumn(name = "orden")
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdAtuendo")
	private Set<Prenda> prendas;
	
	@Enumerated
	private EstadoAtuendo estado;
	
	@Enumerated
	private Calificacion calificacion;
	
	public Atuendo() {
		
	}

	public Atuendo(Set<Prenda> prendas) {
		this.prendas = prendas;
		this.estado = EstadoAtuendo.NUEVO;
		this.calificacion = null;
	}
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public EstadoAtuendo getEstado() {
		return estado;
	}

	public void setEstado(EstadoAtuendo estado) {
		this.estado = estado;
	}

	public void setPrendas(Set<Prenda> prendas) {
		this.prendas = prendas;
	}

	public Calificacion getCalificacion() {
		return this.calificacion;
	}

	public Set<Prenda> getPrendas() {
		return this.prendas;
	}

	public Set<Prenda> getPrendasSuperior() {
		return filtrarPorCategoria(Categoria.PARTE_SUPERIOR);
	}

	public Set<Prenda> getPrendasInferior() {
		return filtrarPorCategoria(Categoria.PARTE_INFERIOR);
	}

	public Set<Prenda> getCalzado() {
		return filtrarPorCategoria(Categoria.CALZADO);
	}

	public Set<Prenda> getAccesorioCabeza() {
		return filtrarPorCategoria(Categoria.ACCESORIOCABEZA);
	}

	public Set<Prenda> getAccesorioManos() {
		return filtrarPorCategoria(Categoria.ACCESORIOMANOS);
	}

	public Set<Prenda> getAccesorioCuello() {
		return filtrarPorCategoria(Categoria.ACCESORIOCUELLO);
	}

	public EstadoAtuendo getEstadoAtuendo() {
		return this.estado;
	}

	public void setEstadoAtuendo(EstadoAtuendo nuevoEstado) {
		this.estado = nuevoEstado;
	}

	public void calificar(Calificacion unaCalificacion) {
		this.setCalificacion(unaCalificacion);
	}

	public void setCalificacion(Calificacion calificacion) {
		this.calificacion = calificacion;
	}

	public boolean cubreTodoElCuerpo() {
		return this.contieneDeCategoria(Categoria.PARTE_SUPERIOR) && this.contieneDeCategoria(Categoria.PARTE_INFERIOR)
				&& this.contieneDeCategoria(Categoria.CALZADO);
	}

	public boolean contieneDeCategoria(Categoria unaCategoria) {
		return this.prendas.stream().anyMatch(unaPrenda -> unaPrenda.getCategoria() == unaCategoria);
	}

	public boolean esAcordeATemperatura(double temperatura, int modificacionNivelDeAbrigo) {
		double nivelDeAbrigoRecomendado;

		if (temperatura < 30.0) {
			nivelDeAbrigoRecomendado = 125.0 - 2 * temperatura;
		} else {
			nivelDeAbrigoRecomendado = 50.0 - temperatura;
		}

		double nivelDeAbrigoMinimo = nivelDeAbrigoRecomendado - 12.0 + modificacionNivelDeAbrigo;
		double nivelDeAbrigoMaximo = nivelDeAbrigoRecomendado + 12.0 + modificacionNivelDeAbrigo;

		return this.parteSuperiorAcordeATemperatura(nivelDeAbrigoMinimo, nivelDeAbrigoMaximo);
		//CREO que no habria que chequear que la parte inferior y el calzado
		//sean acordes a la temperatura porque eso nos limita un poco mas el tipo
		//de evento, revisar
		
	}

	private boolean parteSuperiorAcordeATemperatura(double nivelDeAbrigoMinimo, double nivelDeAbrigoMaximo) {
		return sumaNivelDeAbrigo(getPrendasSuperior()) >= nivelDeAbrigoMinimo
				&& sumaNivelDeAbrigo(getPrendasSuperior()) <= nivelDeAbrigoMaximo;
	}

	private double sumaNivelDeAbrigo(Set<Prenda> prendas) {
		return prendas.stream().mapToDouble(unaPrenda -> unaPrenda.getNivelDeAbrigo()).sum();
	}

	public Set<Prenda> filtrarPorCategoria(Categoria unaCategoria) {
		return this.prendas.stream().filter(unaPrenda -> unaPrenda.getCategoria() == unaCategoria)
				.collect(Collectors.toSet());
	}

//	public Set<Prenda> filtrarPorTipoDePrenda(TipoDePrenda unTipo) {
//		return this.prendas.stream().filter(unaPrenda -> unaPrenda.getTipoDePrenda() == unTipo).collect(Collectors.toSet());
//	}

//Funcion que permite hacer un distinct con un argumento en particular de una clase. // Revisar si puede incluirse como una funcion general.
	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
//

	public boolean filtrarPorSubcategoria() {
		return this.prendas.stream().allMatch(distinctByKey(unaPrenda -> unaPrenda.getTipoDePrenda()));
	}

	public boolean noRepiteCategoria(Categoria categoria) {
		List<Categoria> categoriasAccesorios = Arrays.asList(Categoria.ACCESORIOCABEZA, Categoria.ACCESORIOMANOS,
				Categoria.ACCESORIOCUELLO);
		return categoriasAccesorios.contains(categoria) ? this.cantidadPrendasPorCategoria(categoria) <= 1
				: this.cantidadPrendasPorCategoria(categoria) == 1;
	}

	private int cantidadPrendasPorCategoria(Categoria categoria) {
		return this.filtrarPorCategoria(categoria).size();
	}

//	public boolean noRepiteAccesorios(){
//		int cantidadAccesoriosCabeza = cantidadPrendasPorCategoria(Categoria.ACCESORIOCABEZA);
//		int cantidadAccesoriosDeMano = cantidadPrendasPorCategoria(Categoria.ACCESORIOMANOS);
//		int cantidadAccesoriosCuello = cantidadPrendasPorCategoria(Categoria.ACCESORIOCUELLO);
//
//	    return cantidadAccesoriosCabeza <= 1 && cantidadAccesoriosDeMano <= 1 && cantidadAccesoriosCuello <= 1;
//	}

	public boolean cumpleRangoDeTemperatura(double temperatura) {
		return this.prendas.stream().allMatch(unaPrenda -> unaPrenda.cumpleRangoDeTemperatura(temperatura));
	}

	public boolean esAtuendoParaEvento(TipoDeEvento tipoDeEvento) {
		return this.prendas.stream()
				.allMatch(unaPrenda -> unaPrenda.getTipoDePrenda().getTipoEvento().contains(tipoDeEvento));
	}

	public boolean estaDisponible() {
		return this.prendas.stream().allMatch(unaPrenda -> unaPrenda.estaDisponible());
	}

	public void setPrendasDisponibles(boolean disponibilidad) {
		this.prendas.forEach(unaPrenda -> unaPrenda.setDisponible(disponibilidad));
	}

	public boolean cumpleSensibilidadEspecifica(Boolean stCabeza, Boolean stManos, Boolean stCuello) {
		return cumpleSensibilidadEn(Categoria.ACCESORIOCABEZA, stCabeza)
				&& cumpleSensibilidadEn(Categoria.ACCESORIOMANOS, stManos)
				&& cumpleSensibilidadEn(Categoria.ACCESORIOCUELLO, stCuello);
	}

	private boolean cumpleSensibilidadEn(Categoria categoria, Boolean sensibilidad) {
		if (sensibilidad) {
			return this.prendas.stream()
					.anyMatch(unaPrenda -> unaPrenda.getTipoDePrenda().getCategoria().equals(categoria));
		}
		return true;
	}

	public boolean existeCategoria(Categoria categoria) {
		return this.cantidadPrendasPorCategoria(categoria) >= 1;
	}

	public boolean respetaNivelDeSuperposicionSegunCategoria(Categoria categoria) {
		Set<Prenda> prendasSuperiores = this.filtrarPorCategoria(categoria);
		int cantidadPrendasNivelInferior = this.cantidadPrendasPorNivelDeSuperposicion(prendasSuperiores,
				NivelSuperposicion.INFERIOR);
		int cantidadPrendasNivelMedio = this.cantidadPrendasPorNivelDeSuperposicion(prendasSuperiores,
				NivelSuperposicion.MEDIO);
		int cantidadPrendasNivelSuperior = this.cantidadPrendasPorNivelDeSuperposicion(prendasSuperiores,
				NivelSuperposicion.SUPERIOR);

		return cantidadPrendasNivelInferior == 1 && // Existe prenda con nivel inferior (obligatorio)
				(cantidadPrendasNivelMedio <= 1 || cantidadPrendasNivelSuperior <= 1); // Puede o no existir una prenda
																						// con nivel medio y otra con
																						// nivel superior
//				((cantidadPrendasNivelMedio == 0 && cantidadPrendasNivelSuperior == 0) || //No existe una prenda con nivel medio o una con nivel superior
//				 (cantidadPrendasNivelMedio == 1 && cantidadPrendasNivelSuperior == 0) || //Existe una prenda con nivel medio pero no una con nivel superior
//				 (cantidadPrendasNivelMedio == 1 && cantidadPrendasNivelSuperior == 1)); // Existe una prenda con nivel medio y una con nivel superior
	}

	private int cantidadPrendasPorNivelDeSuperposicion(Set<Prenda> prendas, NivelSuperposicion nivelSuperposicion) {
		return this.filtrarPorNivelDeSuperposicion(prendas, nivelSuperposicion).size();
	}

	private Set<Prenda> filtrarPorNivelDeSuperposicion(Set<Prenda> prendas, NivelSuperposicion nivelSuperposicion) {
		return prendas.stream().filter(prenda -> prenda.getNivelSuperposicion() == nivelSuperposicion)
				.collect(Collectors.toSet());
	}

	public boolean noRepiteNingunaCategoria() {
		return noRepiteCategoria(Categoria.PARTE_INFERIOR) && noRepiteCategoria(Categoria.CALZADO)
				&& noRepiteCategoria(Categoria.ACCESORIOCABEZA) && noRepiteCategoria(Categoria.ACCESORIOMANOS)
				&& noRepiteCategoria(Categoria.ACCESORIOCUELLO);
	}
}