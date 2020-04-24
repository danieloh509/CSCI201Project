package pls;

import Entity.AbstractEntity;

import static org.lwjgl.opengl.GL11.*;



public class Brick extends AbstractEntity {

    private boolean used;

    public Brick(double x, double y, boolean used) {
        super(x, y, 50, 20);
        this.used = used;
        glColor3f(1.0f, 0.8f, 0.0f);
        
    }

    @Override
    public void draw() {
        glRectd(x, y, x + width, y + height);
        
        

        glBegin(GL_QUADS);

        glEnd();
    }

    @Override
    public void update(int delta) {
        // Do nothing
    }

    public void setUsed(boolean used){
        this.used = used;
    }

    public boolean isUse(){
        return used;
    }
}