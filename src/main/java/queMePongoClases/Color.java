package queMePongoClases;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
public class Color {
	private int rojo;
	private int verde;
	private int azul;
	
	public Color() {
		
	}

	public Color(int _rojo, int _verde, int _azul) {
		this.rojo = _rojo;
		this.verde = _verde;
		this.azul = _azul;
	}

	/////// Getters ///////
	public int getRojo() {
		return this.rojo;
	}

	public int getVerde() {
		return this.verde;
	}

	public int getAzul() {
		return this.azul;
	}
	
	public void setRojo(int rojo) {
		this.rojo = rojo;
	}

	public void setVerde(int verde) {
		this.verde = verde;
	}

	public void setAzul(int azul) {
		this.azul = azul;
	}

	/////// Comparacion con otro color ///////
	public boolean esIgualA(Color color) {
		return color != null && this.rojo == color.getRojo() && this.verde == color.getVerde() && this.azul == color.getAzul();
	}
}