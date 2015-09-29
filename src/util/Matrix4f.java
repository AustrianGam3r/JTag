package util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f {
	
	private float[][] matrix = new float[4][4];
	
	public Matrix4f() {
		setIdentity();
	}
	
	public Matrix4f createProjectionMatrix(float l,float r,float t,float b,float n,float f){
		matrix= new float[][]{
				{(2f*n)/(r-l),0f,(r+l)/(r-l),0f},
				{0f,(2f*n)/(t-b),(t+b)/(t-b),0f},
				{0f,0f,-(f+n)/(f-n),(-2f*f*n)/(f-n)},
				{0f,0f,-1f,0f}
		};
		return this;
	}

	public void setIdentity(){
		for(int i=0;i<4;i++){
			matrix[i][i]=1f;
		}
	}
	
	public static Matrix4f getIdentity(){
		return new Matrix4f();
	}
	
	public void rotate(float angle,float x,float y,float z){
		float c=(float)Math.cos(Math.toRadians(angle));
		float s=(float)Math.sin(Math.toRadians(angle));
		matrix = new float[][]{
				{x*x*(1-c)+c,x*y*(1-c)-z*s,x*z*(1-c)+y*s,0},
				{x*y*(1-c)+z*s,y*y*(1-c)+c,y*z*(1-c)-x*s,0},
				{x*z*(1-c)-y*s,y*z*(1-c)+x*s,z*z*(1-c)+c,0},
				{0,0,0,1}
		};
	}
	
	public FloatBuffer getBuffer(){
		float flArray[] = new float[16];
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				flArray[i*j]=matrix[i][j];
			}
		}
		return (FloatBuffer) BufferUtils.createFloatBuffer(16).put(flArray).flip();
	}
}
