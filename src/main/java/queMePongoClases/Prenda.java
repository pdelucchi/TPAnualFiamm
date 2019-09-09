package queMePongoClases;

import java.io.File;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import enums.*;

@Entity
public class Prenda {
	
	@Id
	@GeneratedValue
	private long Id;
	
	@Enumerated
	private TipoDePrenda tipoDePrenda;
	
	@Enumerated
	private Material material;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="rojo", column=@Column(name="rojoPrincipal")),
	    @AttributeOverride(name="verde", column=@Column(name="verdePrincipal")),
	    @AttributeOverride(name="azul", column=@Column(name="azulPrincipal"))
	})
	private Color color;
	
	@Enumerated
	private Trama trama;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="rojo", column=@Column(name="rojoSecundario")),
	    @AttributeOverride(name="verde", column=@Column(name="verdeSecundario")),
	    @AttributeOverride(name="azul", column=@Column(name="azulSecundario"))
	})
	private Color colorSecundario;
	
	private String pathImagen;
	private boolean estaDisponible = true;

	public Prenda() {

	}
	
	public Prenda(TipoDePrenda _tipoDePrenda, Material _material, Color _color, Trama _trama, Color _colorSecundario) {
		this.tipoDePrenda = _tipoDePrenda;
		this.material = _material;
		this.color = _color;
		this.trama = _trama;
		this.colorSecundario = _colorSecundario;
		this.pathImagen = "";
	}
	
	

	/////// Getters ///////
	
	
	
	public double getNivelDeAbrigo() {
		return this.tipoDePrenda.getNivelDeAbrigo();
	}

	public Categoria getCategoria() {
		return tipoDePrenda.getCategoria();
	}
//
//
//	public TipoDePrenda getTipoDePrenda() {
//		return this.tipoDePrenda;
//	}
//
//	public Color getColor() {
//		return this.color;
//	}
//
//	public Trama getTrama() {
//		return this.trama;
//	}
//	
	public NivelSuperposicion getNivelSuperposicion() {
		return this.tipoDePrenda.getNivelSuperposicion();
	}
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public TipoDePrenda getTipoDePrenda() {
		return tipoDePrenda;
	}

	public void setTipoDePrenda(TipoDePrenda tipoDePrenda) {
		this.tipoDePrenda = tipoDePrenda;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Trama getTrama() {
		return trama;
	}

	public void setTrama(Trama trama) {
		this.trama = trama;
	}

	public Color getColorSecundario() {
		return colorSecundario;
	}

	public void setColorSecundario(Color colorSecundario) {
		this.colorSecundario = colorSecundario;
	}

	public String getPathImagen() {
		return pathImagen;
	}

	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	public boolean isEstaDisponible() {
		return estaDisponible;
	}

	public void setEstaDisponible(Boolean estaDisponible) {
		this.estaDisponible = estaDisponible;
	}

	
	/////// Carga de Imagen ///////
	
	public void cargarImagen(byte[] bytesImagen, String nombreImagen){
		String pathImagen = ImagenAdapter.getDirectorioImagenes() + "\\" + nombreImagen + ".jpg";
		File fileImagen = new File(pathImagen);
		ImagenAdapter.cargarBytesEnFileImagen(bytesImagen, pathImagen, fileImagen);
		this.pathImagen = pathImagen; //seteo el atributo 'pathImagen' luego de que se haya escrito los bytes. Me guardo una referencia al file de la imagen
		ImagenAdapter.redimensionarImagen(fileImagen);
	}
	
	/////// Obtencion de Imagen ///////
	
	public byte[] obtenerImagen() {
		return ImagenAdapter.obtenerBytesFileImagen(this.pathImagen);
	}

	public List<TipoDeEvento> getTipoEvento(){
		return this.tipoDePrenda.getTipoEvento();
	}
	
	public void setDisponible(boolean disponibilidad) {
		this.estaDisponible = disponibilidad;
	}
	
	public boolean estaDisponible() {
		return this.estaDisponible;
	}
	
	public boolean cumpleRangoDeTemperatura(double temperatura) {
		return this.tipoDePrenda.cumpleRangoDeTemperatura(temperatura);
	}
	
	public boolean esDeTipoIgualA(Prenda otraPrenda){
	    return this.tipoDePrenda.equals(otraPrenda.getTipoDePrenda());
	}
	
}
