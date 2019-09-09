package queMePongoClases;

public class RangoTemperatura {
	private double temperaturaMinima;
	private double temperaturaMaxima;
	
	public RangoTemperatura(double temperaturaMinima, double temperaturaMaxima) {
		this.temperaturaMinima = temperaturaMinima;
		this.temperaturaMaxima = temperaturaMaxima;
	}
	
	public double getTemperaturaMinima() {
		return temperaturaMinima;
	}

	public double getTemperaturaMaxima() {
		return temperaturaMaxima;
	}

	public boolean cumpleRangoDeTemperatura(double temperatura) {
		return this.temperaturaMinima <= temperatura && this.temperaturaMaxima >= temperatura;
	}	
}
