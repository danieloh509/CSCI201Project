package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class difficulty extends JPanel implements KeyListener, ActionListener{
	private BufferedImage image;
	private JLabel title;
	private JButton hard;
	private JButton medium;
	private JButton easy;
	private boolean nextScreen = false;
	private String level = "";

	
	
	public difficulty() {
		this.setLayout(null);
		this.setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setFields();
		this.add(title);
		this.add(hard);
		this.add(medium);
		this.add(easy);
	}


	private void setFields() {
		try {
			image = ImageIO.read(new File("BrickBreaker.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		title = new JLabel(new ImageIcon(image));
		title.setBounds(-180, 80, 1000, 100);

		hard = new JButton("Hard");
		hard.setBounds(270, 240, 100, 25);
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level = "hard";
				nextScreen = true;
			}
			
		});
		
		medium = new JButton("Medium");
		medium.setBounds(270, 280, 100, 25);
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level = "hard";
				nextScreen = true;
			}
			
		});
		
		easy = new JButton("Easy");
		easy.setBounds(270, 320, 100, 25);
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				level = "hard";
				nextScreen = true;
			}
			
		});
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

	public void keyPressed(KeyEvent arg0) {
		
	}

	public void keyReleased(KeyEvent arg0) {
		
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	public boolean isNextScreen() {
		return nextScreen;
	}
	public String getLevel() {
		return level;
	}
}
