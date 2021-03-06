import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ejercicio3 {

	public static void main(String[] args) {
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream("C:\\Users\\Julio\\Desktop\\Programación\\tiger.bmp"));
			String tipo = "";
			tipo += (char) in.read();
			tipo += (char) in.read();
			System.out.println("Tipo: " + tipo);
			System.out.println("Longitud del fichero: " + Integer.reverseBytes(in.readInt()));
			in.skip(4);
			int offset = Integer.reverseBytes(in.readInt());
			System.out.println("Offset de la imagen: " + offset);
			System.out.println("Longitud cabecera de imagen: " + Integer.reverseBytes(in.readInt()));
			int width = Integer.reverseBytes(in.readInt());
			System.out.println("Ancho: " + width);
			int height = Integer.reverseBytes(in.readInt());
			System.out.println("Alto: " + height);
			System.out.println("Planos: " + Short.reverseBytes(in.readShort()));
			int colorDepth = Short.reverseBytes(in.readShort());
			System.out.println("Profundidad de color: " + colorDepth);
			System.out.println("Compresión: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Tamaño de la imagen: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Resolución horizontal: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Resolución vertical: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Número de colores: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Número de colores importantes: " + Integer.reverseBytes(in.readInt()));
			in.skip(offset - 54);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			for (int f=height-1; f>=0; f--)
				for (int c=0; c<width; c++) {
					int pixel = 0;
					pixel += in.read();
					pixel += in.read() << 8;
					pixel += in.read() << 16;
					image.setRGB(c, f, pixel);
				}
			JFrame ventana = new JFrame("Visor de BMP");
			ventana.getContentPane().add(new Surface(image));
			ventana.pack();
			ventana.setLocationRelativeTo(null);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			SwingUtilities.invokeLater(() -> {ventana.setVisible(true);});
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
