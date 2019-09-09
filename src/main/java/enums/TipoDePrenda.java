package enums;
import java.util.Arrays;
import java.util.List;

import queMePongoClases.RangoTemperatura;

public enum TipoDePrenda {

	/////// Prendas Superiores ///////
	MUSCULOSA(Categoria.PARTE_SUPERIOR, Arrays.asList(Material.ALGODON), Arrays.asList(TipoDeEvento.INFORMAL),new RangoTemperatura(20.0,50.0),15.0, NivelSuperposicion.INFERIOR),
	REMERA(Categoria.PARTE_SUPERIOR, Arrays.asList(Material.ALGODON, Material.SEDA), Arrays.asList(TipoDeEvento.FORMAL,TipoDeEvento.INFORMAL),new RangoTemperatura(0.0,50.0),23.0, NivelSuperposicion.INFERIOR),
	CAMISAMANGACORTA(Categoria.PARTE_SUPERIOR, Arrays.asList(Material.ALGODON, Material.SEDA, Material.SINTETICO), Arrays.asList(TipoDeEvento.INFORMAL),new RangoTemperatura(35.0,50.0),18.0, NivelSuperposicion.MEDIO),
	CAMISA(Categoria.PARTE_SUPERIOR, Arrays.asList(Material.ALGODON, Material.SEDA, Material.SINTETICO), Arrays.asList(TipoDeEvento.FORMAL,TipoDeEvento.INFORMAL),new RangoTemperatura(0.0,25.0),25.0, NivelSuperposicion.MEDIO),
	SACO(Categoria.PARTE_SUPERIOR, Arrays.asList(Material.GABARDINA, Material.CORDEROY), Arrays.asList(TipoDeEvento.FORMAL),new RangoTemperatura(0.0,25.0),35.0, NivelSuperposicion.SUPERIOR),
	CAMPERA(Categoria.PARTE_SUPERIOR, Arrays.asList(Material.GABARDINA, Material.CORDEROY), Arrays.asList(TipoDeEvento.INFORMAL),new RangoTemperatura(0.0,20.0),45.0, NivelSuperposicion.SUPERIOR),

	/////// Prendas Inferiores ///////
	SHORT(Categoria.PARTE_INFERIOR, Arrays.asList(Material.ALGODON), Arrays.asList(TipoDeEvento.INFORMAL),new RangoTemperatura(25.0,50.0),15.0, NivelSuperposicion.INFERIOR),
	PANTALON(Categoria.PARTE_INFERIOR, Arrays.asList(Material.JEAN, Material.CORDEROY, Material.GABARDINA), Arrays.asList(TipoDeEvento.FORMAL,TipoDeEvento.INFORMAL),new RangoTemperatura(0.0,45.0),85.0, NivelSuperposicion.INFERIOR),
	
	/////// Calzados ///////
	OJOTAS(Categoria.CALZADO, Arrays.asList(Material.GOMA), Arrays.asList(TipoDeEvento.INFORMAL),new RangoTemperatura(35.0,50.0),15.0, NivelSuperposicion.INFERIOR),
	ZAPATILLA(Categoria.CALZADO, Arrays.asList(Material.ALGODON), Arrays.asList(TipoDeEvento.INFORMAL),new RangoTemperatura(0.0, 32.0),100.0, NivelSuperposicion.INFERIOR),
	ZAPATO(Categoria.CALZADO, Arrays.asList(Material.CUERO, Material.SINTETICO), Arrays.asList(TipoDeEvento.FORMAL),new RangoTemperatura(0.0,45.0),100.0, NivelSuperposicion.INFERIOR),
	
	/////// Accesorios ///////
	GORRO(Categoria.ACCESORIOMANOS, Arrays.asList(Material.ALGODON, Material.LANA), Arrays.asList(TipoDeEvento.INFORMAL), new RangoTemperatura(0.0,15.0),100, NivelSuperposicion.INFERIOR),
	GUANTES(Categoria.ACCESORIOCABEZA, Arrays.asList(Material.LANA), Arrays.asList(TipoDeEvento.INFORMAL, TipoDeEvento.FORMAL), new RangoTemperatura(0.0,15.0),100, NivelSuperposicion.INFERIOR),
	BUFANDA(Categoria.ACCESORIOCUELLO, Arrays.asList(Material.ALGODON, Material.LANA), Arrays.asList(TipoDeEvento.INFORMAL, TipoDeEvento.FORMAL), new RangoTemperatura(0.0,15.0),100, NivelSuperposicion.INFERIOR);

	private Categoria categoria;
	private List<Material> materialesValidos;
	private List<TipoDeEvento> evento;
	private RangoTemperatura rangoTemperatura;
	private double nivelDeAbrigo;
	private NivelSuperposicion nivelSuperposicion;

	TipoDePrenda(Categoria _categoria, List<Material> _materialesValido, List<TipoDeEvento> _tipoDeEvento, RangoTemperatura _rangoTemperatura, double _nivelDeAbrigo, NivelSuperposicion _nivelSuperposicion) {
		this.categoria = _categoria;
		this.materialesValidos = _materialesValido;
		this.evento = _tipoDeEvento;
		this.rangoTemperatura = _rangoTemperatura;
		this.nivelDeAbrigo = _nivelDeAbrigo;
		this.nivelSuperposicion = _nivelSuperposicion;
	}

	/////// Getters ///////

	public Categoria getCategoria() {
		return this.categoria;
	}

	public List<Material> getMaterialesValidos() {
		return this.materialesValidos;
	}

	public List<TipoDeEvento> getTipoEvento() {
		return this.evento;
	}

	public double getNivelDeAbrigo(){
		return this.nivelDeAbrigo;
	};
	
	public NivelSuperposicion getNivelSuperposicion() {
		return this.nivelSuperposicion;
	}

	/////// Validacion material para tipo de prenda ///////

	public boolean validarMaterial(Material material) {
		return this.materialesValidos.contains(material);
	}
	
	public boolean cumpleRangoDeTemperatura(double temperatura) {
		return this.rangoTemperatura.cumpleRangoDeTemperatura(temperatura);
	}
}