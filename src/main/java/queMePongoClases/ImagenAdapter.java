package queMePongoClases;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import excepciones.CargaBytesImagenException;
import excepciones.EscrituraImagenException;
import excepciones.LecturaImagenException;
import excepciones.NoExisteImagenCargadaException;
import excepciones.ObtencionBytesImagenException;
import excepciones.ObtencionPathDirectorioImagenesException;
import excepciones.PathImagenNoSeteadoException;

public class ImagenAdapter {
	private static final ImagenAdapter instancia = new ImagenAdapter();
	private static final String pathImagenesServidor = "./imagenesServidor";
	private static final int width = 963;
	private static final int height = 640;

    private ImagenAdapter() {}

    public static ImagenAdapter getInstance() {
        return instancia;
    }
    
    public static String getDirectorioImagenes(){
		File directorioImagenes = new File(pathImagenesServidor);
		directorioImagenes.mkdir(); //si la carpeta 'imagenes' no existe, lo crea. Si existe, no hace nada.
		try {
			return directorioImagenes.getCanonicalPath(); //retorna el path absoluto del file (en este caso, del directorio)
		}
		catch(Exception e) {
			throw new ObtencionPathDirectorioImagenesException("Error al momento de obtener el path del directorio de imagenes");
		}
	}
    
	public static void cargarBytesEnFileImagen(byte[] bytesImagen, String pathImagen, File fileImagen) {
		try {
			fileImagen.createNewFile(); //si no existe el file, se crea
			FileOutputStream fileOuputStream = new FileOutputStream(pathImagen);
			fileOuputStream.write(bytesImagen); //escribo el file de la imagen con el array de bytes
			fileOuputStream.close();
		} catch (Exception e) {
			throw new CargaBytesImagenException("Error al momento de cargar los bytes en el archivo de la imagen");
		}
	}
	
	public static void redimensionarImagen(File fileImagen) {
		BufferedImage imagen; 
		
		try {
			imagen = ImageIO.read(fileImagen); //leo el file de la imagen mediante la clase ImageIO
		}
		catch (Exception e) {
			throw new LecturaImagenException("Error al momento de leer la imagen");
		}
		
		BufferedImage imagenRedimensionado = new BufferedImage(width,height,imagen.getType()); //creo una instancia de BufferedImage con los valore de width y height que necesito
		Graphics2D graphic = imagenRedimensionado.createGraphics(); //la clase Graphics2D nos provee metodos para trabajar con imagenes, en este caso nos servir� para redimensionar 
		graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);//al parecer, se define una especie de liezo  
		graphic.drawImage(imagen, 0, 0, width, height, 0, 0, imagen.getWidth(), imagen.getHeight(), null); //se plasma en ese "lienzo" la imagen redimensionada
		graphic.dispose(); //"cerramos el graficador (graphic)"
		try {
			ImageIO.write(imagenRedimensionado, "jpg", fileImagen); //escribimos la imagen redimensionada en el file de la imagen (le indicamos tambien el formato)
		}
		catch (Exception e) {
			throw new EscrituraImagenException("Error al momento de escribir la imagen");
		}
	}
	
	public static byte[] obtenerBytesFileImagen(String pathImagen) {
		if(pathImagen == "") {
			throw new PathImagenNoSeteadoException("No hay ninguna asociacion a una imagen. Primero hay que cargar la imagen");
		}
		File fileImagen = new File(pathImagen);
		if(!fileImagen.exists()) {
			throw new NoExisteImagenCargadaException("No existe una imagen cargada en el path: " + pathImagen); 
		}
		byte[] bytesImagen = new byte[(int) fileImagen.length()];
		try {
			FileInputStream fileInputStream = new FileInputStream(fileImagen);
			fileInputStream.read(bytesImagen);//leemos desde el file de la imagen y obtenemos los bytes
			fileInputStream.close();
			return bytesImagen;
		}
		catch (Exception e) {
			throw new ObtencionBytesImagenException("Error al momento de obtener los bytes del archivo de la imagen");
		}
	}
}
