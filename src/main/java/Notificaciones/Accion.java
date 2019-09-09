package Notificaciones;

import queMePongoClases.Usuario;

public interface Accion {
	
	public abstract void notificarSugerenciasListas(Usuario usuario);
	public abstract void notificarAlertaMeteorologica(Usuario usuario);
}
