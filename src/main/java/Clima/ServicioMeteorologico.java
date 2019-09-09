package Clima;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.joda.time.DateTime;

import excepciones.ObtencionRespuestaServicioMeteorologicoException;

public abstract class ServicioMeteorologico{

	public abstract double obtenerTemperatura();
	
	public abstract double obtenerTemperaturaFutura(DateTime fechaFutura);
	
	public abstract DateTime redondearHorario(DateTime fecha);

	public long obtenerTimestamp(DateTime fechaFutura){
		return fechaFutura.getMillis()/1000;
	}

	public static String obtenerRespuesta(HttpURLConnection con) {
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}
		catch(IOException e) {
			throw new ObtencionRespuestaServicioMeteorologicoException("Error al momento de obtener una respuesta del servicio meteorologico");
		}
		return response.toString();
	}
}