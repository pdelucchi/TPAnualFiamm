package Notificaciones;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import excepciones.ErrorEnvioMailException;
import queMePongoClases.Usuario;
 
public class EnviarMail implements Accion {
	private final Properties properties = new Properties();
	private final String MAIL_EMISOR = "example@gmail.com";
 
	private Session session;
	
	private String mailReceptor;
	private String passwordMailReceptor;
	private String asunto;
 
	private void init() {
 
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",25);
		properties.put("mail.smtp.mail.sender",this.mailReceptor);
		properties.put("mail.smtp.user", this.mailReceptor);
		properties.put("mail.smtp.auth", "true");
 
		session = Session.getDefaultInstance(properties);
	}
 
	public void sendEmail(String mensaje){
 
		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.MAIL_EMISOR));
			message.setSubject(this.asunto);
			message.setText(mensaje);
			Transport t = session.getTransport("smtp");
			t.connect((String)properties.get("mail.smtp.user"), this.passwordMailReceptor);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		}catch (MessagingException me){
			throw new ErrorEnvioMailException("Hubo un error al momento de enviar el mail: " + me.getMessage());
		}	
	}

	@Override
	public void notificarSugerenciasListas(Usuario usuario) {
		this.asunto = "Notificacion sugerencias";
		this.mailReceptor = usuario.getMail();
		this.passwordMailReceptor = usuario.getPassword();
		usuario.getEventos().stream().forEach(evento -> {
			if(evento.getPoseeSugerencias()) {
				String mensaje = "Las sugerencias estan listos para el evento: " + evento.getDescripcionEvento();
				this.sendEmail(mensaje);
			}
		});
	}

	@Override
	public void notificarAlertaMeteorologica(Usuario usuario) {
		//TODO: habria que chequear si hay una alerta meteorologica que nos avise el Servicio Meteorologico, antes de enviar el mail
		this.asunto = "Alerta Meteorologica";
		String mensaje = "Hay una alerta meteorologica para Buenos Aires, se recomienda cambiar de ropa";
		this.mailReceptor = usuario.getMail();
		this.passwordMailReceptor = usuario.getPassword();
		this.sendEmail(mensaje);
	}
}