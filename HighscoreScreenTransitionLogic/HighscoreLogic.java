package HighscoreScreenTransitionLogic;

import javax.swing.*;
import java.awt.*;

public class HighscoreLogic {
	private JFrame app;
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	public HighscoreLogic() { 
		
		// logic for login/signup screens should go here
		

		// COPY AND PASTE THE CODE BELOW INTO Main.java
		boolean MainMenu = true; // these booleans need to be included either before the while loop or at private data members of main 
		boolean ChangeDifficulty = true;
		
		while (true) {
			if (MainMenu) { 
				//all code relating to the Main Menu screen should be put here
				// wrote this code assuming we are not making multiplayer option 
			}
			
			if (ChangeDifficulty) {
				// all code relating to the change difficulty screen should be put here 
				
				//this includes adding anf removing the panle form JFrame
			}
	
			// insert calling the game code here, before the highscore
			//this area should include removing the game screen from view
			
			Highscore score = new Highscore(160, "Patrick", "Easy");
			app.add(score);
			
			while (!score.isNextScreen()) { // keeps on looping until button is clicked
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("test");
			
			if (score.isMainMenuScreen()) { // if main menu button was clicked
				MainMenu = true;
				ChangeDifficulty = false;
			}
			else if (score.isDifficultyScreen()) { // if change difficulty was clicked
				MainMenu = false;
				ChangeDifficulty = true;
			}
			else if (score.isGameScreen()) { // if play again was clicked
				MainMenu = false;
				ChangeDifficulty = false;
			}
		}
	}
	
	public static void main(String[] args) {
		new HighscoreLogic();
	}
}
