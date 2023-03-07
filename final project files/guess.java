//code for a "Guess the Character" game

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.lang.NullPointerException;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class guess extends JPanel implements ActionListener{
	JLabel picture; 
	FlowLayout grid;
	JLabel text;
	
	JTextArea area; //the text displayed to the user
	JTextField field; //where the user will be able to enter their guesses
	ArrayList<String> pics; //for the png files
	ArrayList<String> picHints; //all of the hints will be kept here
	
	//everything (except for the title) will be contained in their
	//own seperate JPanels, and will then be combined into the main one.
	JPanel panel;
	JPanel picPanel;
	JPanel textPanel;
	
	//r and n will be used to randomly select an image at the beginning,
	//and n2 will keep track of the number of guesses the player makes
	Random r; 
	int n;
	int n2;
	
	String s2; //image file path
	String s3; //image name (without the ".png" part)
	
	//I added some sound effects in just for fun
	String name;
	String name2;
	Long currFrame;
	AudioInputStream ais;
	AudioInputStream ais2;
    Clip c;
	Clip c2;
	
	public guess() throws IOException, UnsupportedAudioFileException, LineUnavailableException, FileNotFoundException {
		super(new BorderLayout());
		//for the audio files
		name = "victory.wav";
		name2 = "tryAgain.wav";
		ais = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
		ais2 = AudioSystem.getAudioInputStream(new File(name2).getAbsoluteFile());
		c = AudioSystem.getClip();
		c2 = AudioSystem.getClip();
		
		n2 = 0;
		pics = new ArrayList<String>();
		pics.add("mario.png"); //decided to use mario characters for the images
		pics.add("luigi.png");
		pics.add("peach.png");
		pics.add("toad.png");
		pics.add("bowser.png");
		pics.add("yoshi.png");
		pics.add("daisy.png");
		pics.add("wario.png");
		pics.add("waluigi.png");
		pics.add("rosalina.png");
		pics.add("bowser_jr.png");
		pics.add("boo.png");
		pics.add("donkey_kong.png");
		pics.add("diddy_kong.png");
		
		picHints = new ArrayList<String>();
		picHints.add("A famous red plumber"); //hints that will be shown in the text area
		picHints.add("A famous green plumber");
		picHints.add("The princess that Mario saves often");
		picHints.add("Very short, similar to a type of plant");
		picHints.add("The main villian in the Mario series");
		picHints.add("Mario's dependable dino companion");
		picHints.add("The princess of Sarasaland");
		picHints.add("Mario's self-proclaimed archrival");
		picHints.add("Luigi's self-proclaimed archrival");
		picHints.add("Galaxy princess with a family of Lumas");
		picHints.add("Bowser's only son");
		picHints.add("Very tricky ghosts");
		picHints.add("Necktie-wearing king of the jungle");
		picHints.add("DK's friend and parter");
		
		r = new Random(); //settin the picture up
		n = r.nextInt(13);
		s2 = pics.get(n);
		s3 = s2.substring(0, s2.length() - 4);
		picture = new JLabel(new ImageIcon("temp.png")); //a black image will hide the character from the user at first
		picture.setHorizontalAlignment(JLabel.CENTER);
		
		text = new JLabel("Guess the Character!", SwingConstants.CENTER); //game title
		
		field = new JTextField(20); //adding the text field + area
		area = new JTextArea(10, 30);
		area.setEditable(false);
		area.append("Note: Please use underscores (_) instead of spaces when entering names." + "\n");
		field.addActionListener(this);
		
		//finally putting everything into the panels
		panel = new JPanel();
		picPanel = new JPanel();
		textPanel = new JPanel();
		grid = new FlowLayout();
		
		grid.setHgap(60); //spacing all of the panels/components out
        grid.setVgap(8);
        grid.setAlignment(FlowLayout.CENTER);
		
		panel.setLayout(grid);
		picPanel.setLayout(grid);
		textPanel.setLayout(grid);
		
		picPanel.add(picture);
		textPanel.add(field);
		textPanel.add(area);
		
		panel.add(text);
		panel.add(picPanel);
		panel.add(textPanel);
		add(panel);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = field.getText();
		field.selectAll();
		try{
		if(s.equals(s3)) { //if user input matches the name of the character, 
			area.append("Correct! You Win!" + "\n"); //display win message
			area.setCaretPosition(area.getDocument().getLength());
			picture.setIcon(new ImageIcon(s2)); //and also reveal the photo
			//and then play victory sound clip
			c2.close();
			c.start();
			c.open(ais);
			c.loop(0);
			resetClip();
		}
		else { //if not, increment the number of guesses
			n2++;
			area.append("Sorry! Try again!" + "\n"); //also display wrong answer message
			area.setCaretPosition(area.getDocument().getLength());
			//play an "interesting" wrong answer clip
			c.close();
			c2.start();
			c2.open(ais2);
			c2.loop(0);
			resetClip2();
		}
		
		if(n2 >= 5) { //after 5 wrong guesses, display a hint for the character
			area.append("Hint: " + picHints.get(n) + "\n");
			area.setCaretPosition(area.getDocument().getLength());
			n2 = 0; //had to reset it here so that the hint didn't keep on showing up even after the player wins
		}	
		}
		catch(Exception f) {
			System.out.println(f);
		}
	}
	//methods that reset the clips so that they'll play again after the first time
	public void resetClip() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        ais = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
		c = AudioSystem.getClip();
    }
	public void resetClip2() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        ais2 = AudioSystem.getAudioInputStream(new File(name2).getAbsoluteFile());
		c2 = AudioSystem.getClip();
    }
	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		
		frame.setSize(700, 650);
		frame.setTitle("Guessing Game");
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComponent newContentPane = new guess();
		newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
		frame.setVisible(true);
	}
}
