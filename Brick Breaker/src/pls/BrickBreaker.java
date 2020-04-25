package pls;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;



import java.util.ArrayList;
import java.util.Date;
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
    private static Random ran;
    //private static Ball ball;
    private static final List<Ball> balls = new ArrayList<Ball>(3);
    private static Bat bat;
    private static double BALL_SPEED = 0.3;
    private static int BAT_LENGTH = 80;
    private static int BAT_HEIGHT = 15;
    private static int stage = 2;
    private static int timeScore = 600;
    private static int score = 0;
    private static double BAT_SPEED = 0.6;
    private static int blockVal = 0;
    private static boolean isWide = false;
    private static boolean isSlow = false;
    private static final List<Brick> bricks = new ArrayList<Brick>(50);
    private static int[] sections = new int[8];
    private static long wide;
    private static long slow;



    private static long lastFrame;

    public static void main(String args[]){
    	
    	
    	
    	
    	
        setUpDisplay();
        setUpOpenGL();
        setUpEntities();
        setUpTimer();
        
        

        
        //System.out.println(bat.getX() + " , " + bat.getY());
        
        Date start = new Date();
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
        
        Date end = new Date();
        timeScore -= ((int) ((end.getTime() - start.getTime())/1000))*5;
        if(timeScore < 0) {
        	timeScore = 0;
        }
        if(balls.size() > 0) {
        	score += timeScore;
        }
        
        System.out.println(score);
        System.out.println(stage);
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
    	ran = new Random();
        
        for(int i = 1; i <= 8; i++)
        {
        	sections[i - 1] = (BAT_LENGTH / 8) * i;
        	//System.out.println(sections[i-1]);
        }
        
        
        //MAKE BRICK LAYOUT
        
        if(stage == 1)
        {
        	BAT_LENGTH += 20;
        	BAT_HEIGHT += 5;
        	blockVal = 10;
        	BALL_SPEED = 0.3;
        	BAT_SPEED -= 0.1;
        	int xPos = 20, yPos = 20;
	        for (int i = 0; i < 40; i++) {
	        	if(i < 10 || i>19 && i<30) {
		        	if(i%2 == 0) {
		        		bricks.add(new Brick(xPos, yPos, true, 2));
		        	}
		        	else {
		        		bricks.add(new Brick(xPos, yPos, true, 1));
		        	}
	        	}
	        	else{
		        	if(i%2 == 1) {
		        		bricks.add(new Brick(xPos, yPos, true, 2));
		        	}
		        	else {
		        		bricks.add(new Brick(xPos, yPos, true, 1));
		        	}
	        	}
	            xPos += 60;
	            if (xPos > 560) {
	                xPos = 20;
	                yPos += 30;
	            }
	        }
        }else if(stage == 2)
        {
        	blockVal = 20;
        	BALL_SPEED = 0.4;
        	int xPos = 20, yPos = 20;
        	for (int i = 0; i < 50; i++) {
		        if(i < 20) {
	        			bricks.add(new Brick(xPos, yPos, true, 1));
		        	}
		        else if((i > 19 && i < 33) || (i > 36 && i < 42) || (i > 47 && i < 50)){
		        		bricks.add(new Brick(xPos, yPos, true, 2));
		        }
	            xPos += 60;
	            if (xPos > 560) {
	                xPos = 20;
	                yPos += 30;
	            }
	        }
	    }else if(stage == 3)
	    {
        	BAT_LENGTH -= 20;
        	BAT_HEIGHT -= 5;
        	blockVal = 30;
        	BALL_SPEED = 0.5;
        	BAT_SPEED += 0.1;
        	int xPos = 20, yPos = 20;
        	for (int i = 0; i < 60; i++) {
	        	if(i == 0 || i == 9 || i == 21 || i == 28 || i == 31 || i == 32 || i == 37 || i == 38 || (i >50 && i <59)) {
	        			bricks.add(new Brick(xPos, yPos, true, 1));
		        	}
		        else if((i > 10 && i < 19)|| i == 20 || i == 29 || i == 30 || (i > 38 && i < 50)){
		        		bricks.add(new Brick(xPos, yPos, true, 2));
		        }
		        xPos += 60;
		        if (xPos > 560) {
		            xPos = 20;
		            yPos += 30;
		        }
	        }
        	
        }
        
        bat = new Bat(WIDTH / 2, HEIGHT -30 , BAT_LENGTH, BAT_HEIGHT);
        bat.setSpeed(BAT_SPEED);
        balls.add(new Ball(bat.getX() + 20, bat.getY() - 30, 10, 10));
        
        //SET BALL SPEED
        balls.get(0).setSpeed(BALL_SPEED);
        balls.get(0).setDY(balls.get(0).getSpeed());
        

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

        for(Ball ball : balls) {
        	ball.draw();
        }
        bat.draw();
        boolean hasBricks = false;
        for (Brick brick : bricks) {
            if (brick.isUse()){
                brick.draw();
                hasBricks = true;
            }
        }
        if(!hasBricks) {
        	isRunning = false;
        }
    }

    private static void logic(int delta) {
    	for(Ball ball : balls) {
    		ball.update(delta);
    	}
        bat.update(delta);
        int powerUp = 0;
        for(Ball ball : balls) {
        	double x = ball.getX();
        	double y = ball.getY();
        	for (Brick brick : bricks) {
        		if(x > brick.getX()-20 && x < brick.getX()+70 && y > brick.getY() - 10 && y < brick.getY() +30) {
	        		if(brick.isUse()) {
			            if (ball.intersects(brick)) {
			            	//System.out.println("ball x: " + ball.getX());
			            	//System.out.println("ball y: " + ball.getY());
			            	//System.out.println("brick x: " + brick.getX());
			            	//System.out.println("brick y: " + brick.getY());
			            	
			            	if(ball.getX() > (brick.getX()) && ball.getX() < (brick.getX() + brick.getWidth()))
			            	{
			            		//System.out.println("Hit X");
			            		ball.setDY(-1*((ball.getDY())));
			            	}
			            	else if(ball.getY() > (brick.getY()+2) && ball.getY() < (brick.getY() + brick.getHeight()-2))
			            	{
			            		//System.out.println("Hit Y");
			            		ball.setDX(-1*((ball.getDX())));
			            	}

			            	brick.hit();
			            	if(!brick.isUse()) { //If the brick was destroyed, roll for a power up
			            		powerUp = ran.nextInt(25);
			            	}
			            	score += blockVal;
			            }
	        		}
        		}
        	}
        }

    	if(powerUp == 12 && balls.size() < 3) {
            balls.add(new Ball(bat.getX() + 20, bat.getY() - 30, 10, 10));
            if(!isSlow) {
            	balls.get(balls.size()-1).setSpeed(BALL_SPEED);
            }
            else {
            	balls.get(balls.size()-1).setSpeed(BALL_SPEED/2); //spawn the ball with a low speed if isSlow is active
            }
            balls.get(balls.size()-1).setDY(balls.get(balls.size()-1).getSpeed());
    	}
    	else if(powerUp == 13 && !isWide) {
    		bat = new Bat(bat.getX(), bat.getY() , BAT_LENGTH+20, BAT_HEIGHT);
    		wide = Sys.getTime();
    		isWide = true;
    	}
    	else if(powerUp == 14 && !isSlow) {
    		slow = getTime();
    		for(Ball ball : balls) {
    			ball.setDY(ball.getDY()/2);
    			ball.setDX(ball.getDX()/2);
    		}
    		isSlow = true;
    	}
    	

    	if(isWide) { //check if power up needs to expire
			if((Sys.getTime() - wide) >= 5000){
    			bat = new Bat(bat.getX(), bat.getY() , BAT_LENGTH, BAT_HEIGHT);
    			isWide = false;
    		}
    	}
    	
    	if(isSlow) {
    		if((Sys.getTime() - slow) >= 5000) {
        		for(Ball ball : balls) {
        			ball.setDY(ball.getDY()*2);
        			ball.setDX(ball.getDX()*2);
        		}
    			isSlow = false;
    		}
    	}
        
        
        
        //ball.getX() >= bat.getX() && ball.getY() >= bat.getY()
        //ball hit the bat
        for(int z = 0; z < balls.size(); z++) {
        	Ball ball = balls.get(z);
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
	        	
	        	//System.out.println("ball coord = " + ball.getX());
	        	//System.out.println("bat coord = " + bat.getX());
	
	            ball.setDY(-Math.abs(ball.getDY()));
	            //ball.setDX(-Math.abs(ball.getDX())+0.1/3);
	
	
	            //ball.setDX(Math.abs(ball.getDX()));
	            
	            
	            
	            //System.out.println(curSec);
	            
	            ball.setDX((curSec - 3.5) * 0.1);
	
	        }
	
	        //Top Bound
	        if (ball.getY() <= 2) {
	            ball.setDY(-ball.getDY());
	        }
	
	        //left
	        if (ball.getX() <= 2) {
	            ball.setDX(Math.abs(ball.getDX()));
	            ball.setDX(ball.getDX() + bat.getDX() / 3 + (double) (1 - ran.nextInt(2)) / 200);
	        }
	
	        //right
	        if (ball.getX() >= WIDTH-2){
	            ball.setDX(-ball.getDX());
	        }
	        //ball.getY() > HEIGHT - ball.getHeight()
	
	        //bottom
	        if (ball.getY() >= HEIGHT-2) {
	            //remove the ball
	            balls.remove(z);
	        }
        }
        
        //If no balls remaining
        if(balls.size() == 0) {
        	isRunning = false;
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