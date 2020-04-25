package HighscoreScreenTransitionLogic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Highscore extends JPanel implements KeyListener, ActionListener {
	private boolean nextScreen = false;
	private boolean mainMenu = false;
	private boolean changeDifficulty = false;
	private boolean playAgain = false;
	
	private boolean isNewHighscore;
	private static int highscore;
	
	private BufferedImage image;
	
	private JLabel GameOverImage;
	private JLabel newHighscore;
	private JLabel YourScore;
	private JLabel PersonalBest;
	
	private JButton Restart;
	private JButton MainMenu;
	private JButton DifferentDiff;
	
	
	public Highscore(int score, String username, String difficulty) {
		if (!isGuest(username)) {
			isNewHighscore = NewHighscore(score, username, difficulty);
		}
		
		else {
			isNewHighscore = false;
		}
		
		this.setLayout(null);
		this.setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		setFields(score, isNewHighscore, username);
	}
	
	public static boolean isGuest(String username) { 
		if (username.equalsIgnoreCase("guest")) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public static boolean NewHighscore(int score, String username, String difficulty) {
		highscore = Database.getHighscore("com.mysql.jdbc.Driver", username, difficulty);
		
		if(score > highscore) {
			Database.setHighscore("com.mysql.jdbc.Driver", username, difficulty, score);
			return true;
		}
		
		else {
			return false;
		}
	}	
	
	public void setFields(int score, boolean isNewHighscore, String username) {		
		try {
			image = ImageIO.read(new File("GameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameOverImage = new JLabel(new ImageIcon(image));
		GameOverImage.setBounds(70, 80, 500, 100);
		GameOverImage.setHorizontalAlignment(JLabel.CENTER);
		this.add(GameOverImage);
		
		if (isNewHighscore) {
			newHighscore = new JLabel("<html><font size='4' color=white> New Personal Best!</font></html>");
		}
		else {
			newHighscore = new JLabel("");
		}
		newHighscore.setBounds(230, 180, 180, 50);
		newHighscore.setHorizontalAlignment(JLabel.CENTER);
		this.add(newHighscore);
		
		YourScore = new JLabel("<html><font size='3' color=white>" + username + "'s score: " + score + "</font></html>");
		YourScore.setBounds(230, 200, 180, 50);
		YourScore.setHorizontalAlignment(JLabel.CENTER);
		this.add(YourScore);
		
		if (!isGuest(username)) { // checks if user is a guest or is logged in 
			String content;
			if(isNewHighscore) {
				content = "<html><font size='3' color=white> Previous Personal Best: " + highscore +"</font></html>";
			}
			else {
				content = "<html><font size='3' color=white> Your Personal Best: " + highscore +"</font></html>";
			}
			PersonalBest = new JLabel(content);
			PersonalBest.setBounds(230, 230, 180, 50);
			PersonalBest.setHorizontalAlignment(JLabel.CENTER);
			this.add(PersonalBest);
		}
		
		Restart = new JButton("Play Again");
		Restart.setBounds(270, 280, 100, 25);
		Restart.setHorizontalAlignment(JLabel.CENTER);
		Restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextScreen = true;
				playAgain = true;
			}
		});
		this.add(Restart);
		
		MainMenu = new JButton("Main Menu");
		MainMenu.setBounds(270, 320, 100, 25);
		MainMenu.setHorizontalAlignment(JLabel.CENTER);
		MainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextScreen = true;
				mainMenu = true;
			}
		});
		this.add(MainMenu);
		
		DifferentDiff = new JButton("Change Difficulty");
		DifferentDiff.setBounds(256, 360, 129, 25);
		DifferentDiff.setHorizontalAlignment(JLabel.CENTER);
		DifferentDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextScreen = true;
				changeDifficulty = true;
			}
		});
		this.add(DifferentDiff);
	}	
	
	public boolean isNextScreen() {
		return nextScreen;
	}
	public boolean isMainMenuScreen() {
		return mainMenu;
	}	
	public boolean isDifficultyScreen() {
		return changeDifficulty;
	}
	public boolean isGameScreen() { 
		return playAgain;
	}
	
	public void actionPerformed(ActionEvent arg0) {
	}
	public void keyPressed(KeyEvent arg0) {
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {			
	}
}
	

