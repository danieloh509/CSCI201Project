package pls;

import Entity.AbstractEntity;

import static org.lwjgl.opengl.GL11.*;

public class Brick extends AbstractEntity {

    private boolean used;
    
    private int life;

    public Brick(double x, double y, boolean used, int life) {
        super(x, y, 50, 20);
        this.used = used;
        this.life = life;
    }

    @Override
    public void draw() {
        if(life == 2) {
            glColor3f(1.0f, 1.0f, 1.0f);
        }
        glRectd(x, y, x + width, y + height);
        
        glBegin(GL_QUADS);
        
        glEnd();
        
        glColor3f(1.0f, 0.8f, 0.0f);
        
    }

    @Override
    public void update(int delta) {
        // Do nothing
    }
    
    public void hit() {
        life -= 1;
        if(life == 0) {
            setUsed(false);
        }
        
    }

    public void setUsed(boolean used){
        this.used = used;
    }

    public boolean isUse(){
        return used;
    }
}