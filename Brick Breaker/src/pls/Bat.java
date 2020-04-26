package pls;

import Entity.AbstractMovableEntity;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glRectd;




public class Bat extends AbstractMovableEntity {
	
	public float a;
	public float b;
	public float c;
	
	private double speed = 0.5;
	
	 public void color(float a, float b, float c)
    {
    	this.a = a;
    	this.b = b;
    	this.c = c;
    }
	
    public Bat(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    public double getSpeed(){
    	return speed;
    }
    
    public void setSpeed(double ns){
    	speed = ns;
    }

    @Override
    public void draw() {
        glRectd(x, y, x + width, y + height);
        if(BrickBreaker.multiplayer == 2){
        	
        
        	glColor3f(a, b, c);
        }
        
    }
}