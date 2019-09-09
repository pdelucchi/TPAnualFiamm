package queMePongoClases;
import static java.util.Objects.requireNonNull;

import enums.Material;
import enums.TipoDePrenda;
import enums.Trama;
import excepciones.ColorIgualException;
import excepciones.MaterialInvalidoException;

//BUILDER
public class PrendaBorrador {
	private TipoDePrenda tipoDePrenda;
	private Material material;
	private Color color;
	private Color colorSecundario;
	private Trama trama = Trama.LISA;

	/////// Setters ///////

	public void setTipoDePrenda(TipoDePrenda _tipoDePrenda) {
		this.tipoDePrenda = _tipoDePrenda;
	}

	public void setMaterial(Material _material) {
		this.material = _material;
	}

	public void setColor(Color _color) {
		this.color = _color;
	}

	public void setColorSecundario(Color _color) {
		this.colorSecundario = _color;
	}

	public void setTrama(Trama _trama) {
		this.trama = _trama;
	}


	/////// Validaciones ///////

	public void validarTipoPrenda() {
		requireNonNull(tipoDePrenda, "Tipo de prenda es obligatorio");
	}

	public void validarMaterial() {
		requireNonNull(material, "Material es obligatorio");
		if (!tipoDePrenda.validarMaterial(material)) {
			throw new MaterialInvalidoException("Material es invalido para ese tipo de prenda");
		}
	}

	public void validarColor() {
		requireNonNull(color, "color es obligatorio");
	}


	public void validarColorSecundario() {
		if (this.color.esIgualA(this.colorSecundario)) {
			throw new ColorIgualException("El color secundario no puede ser igual al primario");
		}
	}

	/////// Creacion de prenda ///////

	public Prenda crear() {
		this.validarTipoPrenda();
		this.validarMaterial(); // valido si hay input y si ademas el material es valido para la prenda
		this.validarColor();
		this.validarColorSecundario();

		return new Prenda(this.tipoDePrenda, this.material, this.color, this.trama, this.colorSecundario);
	}
}