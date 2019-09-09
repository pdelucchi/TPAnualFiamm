package queMePongoClases;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.google.common.collect.Sets;

import enums.Categoria;
import enums.TipoDeEvento;

@Entity
public class Guardarropa {

	@Id
	@GeneratedValue
	private long Id;
	
	@OrderColumn(name = "orden")
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "IdGuardarropa")
	private Set<Prenda> prendas;

	public Guardarropa() {
		this.prendas = Sets.newHashSet();
	}
	
	/////// Getters y Setters ///////
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public void setPrendas(Set<Prenda> prendas) {
		this.prendas = prendas;
	}

	public Set<Prenda> getPrendas() {
		return prendas;
	}

	/////// Agregado de prendas ///////

	public void incluirEnGuardarropa(Prenda prenda) {
		this.prendas.add(prenda);
	}

	/////// Contiene prenda ///////

	public boolean contienePrenda(Prenda prenda) {
		return this.prendas.contains(prenda);
	}

	/////// Comparacion con otro guardarropa ///////

	public boolean poseePrendaCompartida(Guardarropa guardarropa) {
		return this.prendas.stream().anyMatch(unaPrenda -> guardarropa.prendas.contains(unaPrenda));
	}

	/////// Sugerencia de prenda ///////

	public Set<Atuendo> sugerir(Double temperatura, TipoDeEvento tipoDeEvento, Usuario unUsuarie) {
		Set<Set<Prenda>> combinacionesDePrendasPosibles = Sets.powerSet(prendas);
		Set<Atuendo> atuendosPosibles = combinacionesDePrendasPosibles.stream()
				.map(unaCombinacion -> new Atuendo(unaCombinacion)).collect(Collectors.toSet());
		Set<Atuendo> atuendosAcordesAlEvento;

		Boolean stCabeza = unUsuarie.sensacionTermicaEspecifica.get(Categoria.ACCESORIOCABEZA);
		Boolean stCuello = unUsuarie.sensacionTermicaEspecifica.get(Categoria.ACCESORIOCUELLO);
		Boolean stManos = unUsuarie.sensacionTermicaEspecifica.get(Categoria.ACCESORIOMANOS);

		atuendosAcordesAlEvento = atuendosPosibles.stream()
				.filter(unAtuendo -> unAtuendo.cumpleSensibilidadEspecifica(stCabeza, stCuello, stManos))
				.filter(unAtuendo -> unAtuendo.cubreTodoElCuerpo())
				.filter(unAtuendo -> unAtuendo.noRepiteNingunaCategoria())
				.filter(unAtuendo -> unAtuendo.existeCategoria(Categoria.PARTE_SUPERIOR))
				.filter(unAtuendo -> unAtuendo.respetaNivelDeSuperposicionSegunCategoria(Categoria.PARTE_SUPERIOR))
				.filter(unAtuendo -> unAtuendo.cumpleRangoDeTemperatura(temperatura))
				.filter(unAtuendo -> unAtuendo.filtrarPorSubcategoria())
				.filter(unAtuendo -> unAtuendo.esAtuendoParaEvento(tipoDeEvento))
				.filter(unAtuendo -> unAtuendo.estaDisponible()).collect(Collectors.toSet());

		int modificacionNivelDeAbrigo = unUsuarie.getModificacionNivelDeAbrigo(temperatura);
		atuendosAcordesAlEvento = atuendosAcordesAlEvento.stream()
				.filter(unAtuendo -> unAtuendo.esAcordeATemperatura(temperatura, modificacionNivelDeAbrigo))
				.collect(Collectors.toSet());
		return atuendosAcordesAlEvento;
	}

	public int cantidadDePrendas() {
		return this.prendas.size();
	}

	public boolean estaCompartido() {
		return RepositorioUsuarios.getInstance().esGuardarropasCompartido(this);
	}

}
