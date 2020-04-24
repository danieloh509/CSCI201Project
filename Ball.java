package pls;

import Entity.AbstractMovableEntity;

import static org.lwjgl.opengl.GL11.glRectd;


public class Ball extends AbstractMovableEntity {
	private double speed = 0.3;
    public Ball(double x, double y, double width, double height) {
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
    }
}