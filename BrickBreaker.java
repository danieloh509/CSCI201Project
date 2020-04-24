package pls;


import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;


public class BrickBreaker {

    //private static final int FRAME_WIDTH = 400;
    //private static final int FRAME_HEIGHT = FRAME_WIDTH / 16 * 9;
    //private static final int SCALE = 3;

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static boolean isRunning = true;
    private static Ball ball;
    private static Bat bat;
    private static double BALL_SPEED = 0.3;
    private static int BAT_LENGTH = 80;

    private static final List<Brick> bricks = new ArrayList<Brick>(50);
    private static int[] sections = new int[8];


    private static long lastFrame;

    public static void main(String args[]){
    	
    	
    	
    	
    	
        setUpDisplay();
        setUpOpenGL();
        setUpEntities();
        setUpTimer();
        
        

        
        //System.out.println(bat.getX() + " , " + bat.getY());
        
        
        while (isRunning) {
            //System.out.println(ball.getX() + " , " + ball.getY()); //ball coord checking
            render();
            logic(getDelta());
            input();
            Display.update();
            Display.sync(60);
            if (Display.isCloseRequested()) {
                isRunning = false;
            }
        }
        Display.destroy();
        System.exit(0);

    }

    public static void setUpDisplay(){
        try {
        	
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Brick Breaker");
            Display.create();
            glClear(GL_COLOR_BUFFER_BIT);
            //glClearColor(157, 34, 53, 1);
            glClearColor((float)0.615, (float)0.13333, (float)0.2078, 1);
            
        } catch (LWJGLException e) {
            Display.destroy();
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void setUpOpenGL(){
        // Initialization code OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    
   
    
    public static void setUpEntities(){
        bat = new Bat(WIDTH / 2, HEIGHT -30 , BAT_LENGTH, 20);
        
        for(int i = 1; i <= 8; i++)
        {
        	sections[i - 1] = (BAT_LENGTH / 8) * i;
        	System.out.println(sections[i-1]);
        }

        ball = new Ball(bat.getX() + 20, bat.getY() - 30, 10, 10);
        
        //SET BALL SPEED
        ball.setSpeed(BALL_SPEED);
        ball.setDY(ball.getSpeed());
        
        
        //MAKE BRICK LAYOUT
        int xPos = 20, yPos = 20;
        for (int i = 0; i < 50; i++) {
            bricks.add(new Brick(xPos, yPos, true));
            xPos += 60;
            if (xPos > 560) {
                xPos = 20;
                yPos += 30;
            }
        }

    }

    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }
    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    public static void setUpTimer(){
        lastFrame = getTime();
    }

    public static void render(){
        glClear(GL_COLOR_BUFFER_BIT);

        ball.draw();
        bat.draw();
        for (Brick brick : bricks) {
            if (brick.isUse()){
                brick.draw();
            }
        }
    }

    private static void logic(int delta) {
        ball.update(delta);
        bat.update(delta);

        for (Brick brick : bricks) {
            if (ball.intersects(brick)) {
            	
            	
            
            	/*
            	if(ball.getY() <= brick.getY() + (brick.getHeight() / 2))
            	{
            		 ball.setDY(-1*((ball.getDY())));
            	}else if(ball.getY() >= brick.getY() - (brick.getHeight() / 2)){
            		 ball.setDY(-1*((ball.getDY())));
            	}else if(ball.getX() < brick.getX()){
            		System.out.println("Hit Side");
            		ball.setDX(-1 * ball.getDX());
            	}else if(ball.getX() > brick.getX()){
            		System.out.println("Hit Side");
            		ball.setDX(-1 * ball.getDX());
            	}
            	*/
            	
            	//if(ball.getX() == brick.getX() || ball.getX() == (brick.getX() + brick.getWidth()))
            	//{
            	//	System.out.println("Hit Side");
            	//	ball.setDX(-1 * ball.getDX());
            	//	
            	//}else{
	                
            	//}
            	
            
            	
            	ball.setDY(-1*((ball.getDY())));
            	brick.setUsed(false);
                brick.setX(0);
                brick.setY(0);
            }
        }
        
        
        
        //ball.getX() >= bat.getX() && ball.getY() >= bat.getY()
        //ball hit the bat
        if (ball.intersects(bat)) {
        	
        	int curSec = 0;
        	
        	for(int i = 0; i < 8; i++)
        	{
        		if(ball.getX() - bat.getX() < sections[i])
        		{
        			curSec = i;
        			i = 8;
        		}
        	}
        	
        	System.out.println("ball coord = " + ball.getX());
        	System.out.println("bat coord = " + bat.getX());

            ball.setDY(-Math.abs(ball.getDY()));
            //ball.setDX(-Math.abs(ball.getDX())+0.1/3);


            //ball.setDX(Math.abs(ball.getDX()));
            
            
            
            System.out.println(curSec);
            
            ball.setDX((curSec - 3.5) * 0.1);

        }

        //Top Bound
        if (ball.getY() < 0) {
            ball.setDY(-ball.getDY());
        }

        //left
        if (ball.getX() <= 0) {
            ball.setDX(Math.abs(ball.getDX()));
            Random ran = new Random();
            ball.setDX(ball.getDX() + bat.getDX() / 3 + (double) (1 - ran.nextInt(2)) / 200);
        }

        //right
        if (ball.getX() > WIDTH){
            ball.setDX(-ball.getDX());
        }
        //ball.getY() > HEIGHT - ball.getHeight()

        //bottom
        if (ball.getY() > HEIGHT) {
            //reset game
            setUpEntities();
        }

        //fix DY if DY is too large
        //if (Math.abs(ball.getDY()) > Math.abs(ball.getDX() * 1.5)) {
        //  ball.setDY(ball.getDY() / 1.5);
        //}

    }

    private static void input() {
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && bat.getX() > 0) {
        	
            bat.setDX(-bat.getSpeed());
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && bat.getX() + bat.getWidth() < WIDTH) {
            bat.setDX(bat.getSpeed());
        } else {
            bat.setDX(0);
        }
    }

}