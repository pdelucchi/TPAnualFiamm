package Clima;

import org.joda.time.DateTime;

public class ServicioMock extends ServicioMeteorologico{

	public double obtenerTemperatura(){
		return 14.0;
	}
	
	public double obtenerTemperaturaFutura(DateTime temperatura) {
		return 14.0;
	}

	@Override
	public DateTime redondearHorario(DateTime fecha) {
		// TODO Auto-generated method stub
		return null;
	}

}
