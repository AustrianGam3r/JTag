package util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class BufferUtil2 {
	public static FloatBuffer loadFloatData(float[] data){
		return (FloatBuffer) BufferUtils.createFloatBuffer(data.length).put(data).flip();
	}
	
	public static IntBuffer loadIntData(int[] data){
		return (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
	}
	
	public static int getObject(IntBuffer buffer,int pos){
		return buffer.get(pos);
	}
	
	public static IntBuffer createObjectBuffer(int count){
		return BufferUtils.createIntBuffer(count);
	}
	
}
