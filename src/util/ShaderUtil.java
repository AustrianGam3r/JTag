package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;


public class ShaderUtil {
	
	static public int loadShaderFromFile(String filename, int type) throws Exception {
			StringBuilder data = new StringBuilder();
			List<String> source = null;
			try {
				source = Files.readAllLines(Paths.get(new ShaderUtil().getClass().getResource("/shaders/"+filename+ ((type==GL20.GL_VERTEX_SHADER) ? ".vert" : ".frag")).toURI()));
			} catch (IOException | URISyntaxException e) {
				System.out.println("No shaderfile found !\nShadertype: "+((type==GL20.GL_VERTEX_SHADER) ? "Vertex" : "Fragment"));
				e.printStackTrace();
			} //Get the Shader
			
			for(String bit: source){
				data.append(bit+"\n");
			}
			if(data.toString().trim().length()<1) throw new Exception("Shaders are empty!");
		//System.out.println(src);
		int shader=GL20.glCreateShader(type); //Create Shader
		GL20.glShaderSource(shader, data); //Load Shaderdata
		GL20.glCompileShader(shader); //Compile Shader
		ByteBuffer status = BufferUtils.createByteBuffer(4);
		GL20.glGetShaderiv(shader, GL20.GL_COMPILE_STATUS,status); // Get the compile status
		System.out.println("Shader loaded: "+(status.getInt()==GL11.GL_TRUE));
		System.out.println(GL20.glGetShaderInfoLog(shader));
		
		return shader;
	}
}
