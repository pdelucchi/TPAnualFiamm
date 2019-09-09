package Clima;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.gson.Gson;

import excepciones.ConexionConServicioMeteorologicoException;
import excepciones.URLInvalidaException;

public class ServicioApixU extends ServicioMeteorologico {

	public double obtenerTemperatura(){
		HttpURLConnection con;
		try{
			URL url = new URL(
					"http://api.apixu.com/v1/current.json?key=4bbc11298d18402eb07201229192505&q=buenos%20aires");
			
			con = (HttpURLConnection) url.openConnection();
		}
		catch(MalformedURLException mue) {
			throw new URLInvalidaException("La URL ingresada es incorrecta");
		}
		catch(IOException ioe) {
			throw new ConexionConServicioMeteorologicoException("Error al conectarnos con el servicio meteorologico de ApixU");
		}
		
		String response = obtenerRespuesta(con);
		Gson gson = new Gson();
	    ApixUResponse weatherResponse = gson.fromJson(response, ApixUResponse.class);
	    
	    return weatherResponse
				.getCurrent()
				.getTemp_c();
	}
	
	public double obtenerTemperaturaFutura(DateTime fecha){
		DateTime fechaRedondeada = redondearHorario(fecha);

		long timestamp = this.obtenerTimestamp(fechaRedondeada);
		
		HttpURLConnection con;
		try {
			URL url = new URL(
					"http://api.apixu.com/v1/forecast.json?key=4bbc11298d18402eb07201229192505&q=buenos%20aires&days=3");
	
			con = (HttpURLConnection) url.openConnection();
		}
		catch(MalformedURLException mue) {
			throw new URLInvalidaException("La URL ingresada es incorrecta");
		}
		catch(IOException ioe) {
			throw new ConexionConServicioMeteorologicoException("Error al conectarnos con el servicio meteorologico de ApixU");
		}

		String response = obtenerRespuesta(con);

		Gson gson = new Gson();

		ApixUResponse weatherResponse = gson.fromJson(response, ApixUResponse.class);

		double temperatura = weatherResponse
								.getForecast()
								.getForecastDay()
								.stream()
								.filter(forecastDay -> forecastDay.getDate_epoch() == timestamp)
								.collect(Collectors.toList()).get(0)
								.getDay()
								.getAvgtemp_c();

		return temperatura;
    }


	public DateTime redondearHorario(DateTime fecha) {
		DateTime fechaRedondeada = new DateTime(fecha.getYear(), fecha.getMonthOfYear(), fecha.getDayOfMonth(),
				00, 00, DateTimeZone.forOffsetHours(0)); //offset para el timezone

		return fechaRedondeada;
	}

}
