package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureLoader {
	private static int[] loadTexture(String location){
		BufferedImage buffer=null;
		try {
			buffer =ImageIO.read(new TextureLoader().getClass().getResource(location));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.getRGB(0, 0, buffer.getWidth(), buffer.getHeight(), null, 0, buffer.getWidth());
	}
	
	public static IntBuffer getBufferedTexture(String location){
		int[] data=loadTexture(location);
		IntBuffer i= BufferUtils.createIntBuffer(data.length);
		i.put(data).flip();
		return i;
	}
	
}
