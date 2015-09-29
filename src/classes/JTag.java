package classes;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import com.hackoeur.jglm.*;

import util.*;

import java.io.IOException;
import java.nio.ByteBuffer;
 



import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.*;
public class JTag {
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    //Constants
    private final int FLOATLENGTH = 4;
    
    // The window handle
    private long window; //Window handle from GLFW
    private int _VAO; 
    private int _PROGRAM;
    
    
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
 
        try {
            init();
            loop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        
        //Disabling older GL versions
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
 
        int WIDTH = 800;
        int HEIGHT = 600;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        
        
        
        // Make the window visible
        glfwShowWindow(window);
    }
 
    private void loop() {
    	float vertex_positions[] =
    		{
    		-0.25f, 0.25f, -0.25f,
    		-0.25f, -0.25f, -0.25f,
    		
    		0.25f, -0.25f, -0.25f,
    		
    		0.25f, -0.25f, -0.25f,
    		
    		0.25f, 0.25f, -0.25f,
    		-0.25f, 0.25f, -0.25f,
    		
    		-0.25f, 0.25f, -0.25f,
    		0.25f, 0.25f, -0.25f,
    		
    		0.25f, 0.25f, 0.25f,
    		
    		0.25f, 0.25f, 0.25f,
    		
    		-0.25f, 0.25f, 0.25f,
    		-0.25f, 0.25f, -0.25f
    };
    	float verts[] = {
    	    -0.25f,-0.25f,-0.25f, // triangle 1 : begin
    	    -0.25f,-0.25f, 0.25f,
    	    -0.25f, 0.25f, 0.25f, // triangle 1 : end
    	    0.25f, 0.25f,-0.25f, // triangle 2 : begin
    	    -0.25f,-0.25f,-0.25f,
    	    -0.25f, 0.25f,-0.25f, // triangle 2 : end
    	    0.25f,-0.25f, 0.25f,
    	    -0.25f,-0.25f,-0.25f,
    	    0.25f,-0.25f,-0.25f,
    	    0.25f, 0.25f,-0.25f,
    	    0.25f,-0.25f,-0.25f,
    	    -0.25f,-0.25f,-0.25f,
    	    -0.25f,-0.25f,-0.25f,
    	    -0.25f, 0.25f, 0.25f,
    	    -0.25f, 0.25f,-0.25f,
    	    0.25f,-0.25f, 0.25f,
    	    -0.25f,-0.25f, 0.25f,
    	    -0.25f,-0.25f,-0.25f,
    	    -0.25f, 0.25f, 0.25f,
    	    -0.25f,-0.25f, 0.25f,
    	    0.25f,-0.25f, 0.25f,
    	    0.25f, 0.25f, 0.25f,
    	    0.25f,-0.25f,-0.25f,
    	    0.25f, 0.25f,-0.25f,
    	    0.25f,-0.25f,-0.25f,
    	    0.25f, 0.25f, 0.25f,
    	    0.25f,-0.25f, 0.25f,
    	    0.25f, 0.25f, 0.25f,
    	    0.25f, 0.25f,-0.25f,
    	    -0.25f, 0.25f,-0.25f,
    	    0.25f, 0.25f, 0.25f,
    	    -0.25f, 0.25f,-0.25f,
    	    -0.25f, 0.25f, 0.25f,
    	    0.25f, 0.25f, 0.25f,
    	    -0.25f, 0.25f, 0.25f,
    	    0.25f,-0.25f, 0.25f
    	};
    	/*float vertex_positions[] =
    		{
    		-0.25f, 0.25f, -0.25f,
    		-0.25f, -0.25f, -0.25f,
    		0.25f, -0.25f, -0.25f,
    		};*/
    	
    	float custVert[] = {
    			-1,1,0,  -1,-1,0, 1,-1,0, 1,-1,0, 1,1,0, -1,1,0
    	};
    		
    	
    	//Activate the Usage of OpenGl
        GLContext.createFromCurrent();
        
        create_shaders();
        
        
        //create vertex arraay object
        _VAO = glCreateVertexArrays();
        glBindVertexArray(_VAO);
        
        
        IntBuffer ids = BufferUtil2.createObjectBuffer(2);
        glCreateBuffers(ids);
        glBindBuffer(GL_ARRAY_BUFFER,BufferUtil2.getObject(ids,0));
        glBufferData(GL_ARRAY_BUFFER,BufferUtil2.loadFloatData(custVert),GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, NULL);
        glEnableVertexAttribArray(0);
        //Enable Vertex Array for Vertex input
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_DEPTH_TEST);

      
       
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
        	
            /*float f= (float) glfwGetTime()*0.01f * (float) Math.PI * 0.1f;
            Mat4 mv_matrix;
            Mat4 tran1 = new Mat4().translate2(new Vec3(0,0,-15));
            Mat4 tran2 = new Mat4().translate2(new Vec3((float) Math.sin(2.1f *f)*0.5f,(float) Math.cos(1.7f *f)* 0.5f,(float) Math.sin(1.3f * f)* (float) Math.cos(1.5f * f )*2.0f));
            Mat4 rot1 = Matrices.rotate((float)Math.toRadians(glfwGetTime()*45f), new Vec3(0f,1f,0f));
            Mat4 rot2 = Matrices.rotate((float)Math.toRadians(glfwGetTime()*81f), new Vec3(1f,0f,0f));
            mv_matrix=tran1;
            mv_matrix= mv_matrix.add(tran2);
            mv_matrix= mv_matrix.multiply(rot1);
            mv_matrix= mv_matrix.multiply(rot2);*/
            
            Mat4 proj= Matrices.perspective(50f, 800/600, 0.1f, 1000f);
        	
        	
        	glClearColor(0.0f, 0.3f,0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            glUseProgram(_PROGRAM);
            
            glUniformMatrix4fv(2, false, proj.getBuffer());
            //glUniformMatrix4fv(1, false, mv_matrix.getBuffer());
            
            
            float [] tD ={0,0,0,1,1,1,1,0};
            int textureid= glCreateTextures(GL_TEXTURE_2D);
            IntBuffer textureData= TextureLoader.getBufferedTexture("../sample.png");
            glBindTexture(GL_TEXTURE_2D, textureid);
            glTexStorage2D(GL_TEXTURE_2D, 1, GL_RGB8, 512, 512);
            glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 512, 512, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            for(int i=0;i<1;i++){
            	float f= (float) i + (float) glfwGetTime() *0.3f;
                Mat4 mv_matrix;
                Mat4 tran1 = new Mat4().translate2(new Vec3(0,0,-10));
                
//                Mat4 rot1 = Matrices.rotate((float)Math.toRadians(90f), new Vec3(0f,0f,1f));
//                Mat4 rot2 = Matrices.rotate((float)Math.toRadians(glfwGetTime()*21f), new Vec3(1f,0f,0f));
                 Mat4 rot1 = Matrices.rotate((float)Math.toRadians(0), new Vec3(0f,0f,1f));
                Mat4 rot2 = Matrices.rotate((float)Math.toRadians(0), new Vec3(1f,0f,0f));
               
                Mat4 tran2 = new Mat4().translate2(
               new Vec3((float) Math.sin(2.1f *f)*2f,
                		(float) Math.cos(1.7f *f)*2f,
                		(float) Math.sin(1.3f * f)* (float) Math.cos(1.5f * f )*2.0f));
                mv_matrix=tran1;
                mv_matrix= mv_matrix.multiply(rot1);
                mv_matrix= mv_matrix.multiply(rot2);
                mv_matrix= mv_matrix.multiply(tran2);
              glUniformMatrix4fv(1, false, mv_matrix.getBuffer());
                
                
                 glDrawArrays(GL_TRIANGLES,0,6);
            }
            
            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
   
    private void create_shaders(){
    	//Init Shaders
        
        int vertexShader = 0;
        int fragmentShader = 0;
		try {
			vertexShader = ShaderUtil.loadShaderFromFile("vertex", GL_VERTEX_SHADER);
			fragmentShader = ShaderUtil.loadShaderFromFile("fragment", GL_FRAGMENT_SHADER);
		} catch (Exception e) {
			e.printStackTrace();//Check if Shaders are correctly loaded!
		}
		

		//Send the projection Matrix
		//glUniformMatrix4fv(glGetUniformLocation(_PROGRAM, "proj"),  false,new Matrix4f().createProjectionMatrix(0, 1, 1, 0, 0.1f, 10).getBuffer());
		
		
        // make a programm
        _PROGRAM = glCreateProgram(); // Create the Shaderprogramm
        glAttachShader(_PROGRAM, vertexShader); //Attach the vert shader
        glAttachShader(_PROGRAM, fragmentShader); //Attach the frag shader
        glBindFragDataLocation(_PROGRAM, 0, "outColor");
        glLinkProgram(_PROGRAM); //Link both shaders into the program   
    }
    
    public static void main(String[] args) {
    	//SharedLibraryLoader.load();
        new JTag().run();
    }
 
}