package main;

import java.awt.*;
import acm.graphics.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import acm.program.*;
import acm.util.RandomGenerator;

import javax.swing.*;

public class GUI extends Program implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6429812284490953026L;
	private JButton label;
	private JButton settings;
	private GCanvas background;
	private Markov m;
	private JLabel followers;
	private double count;
	private RandomGenerator rand;

	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;

	public void init() {
		setUpComponents();	
		
		add(background);
	}

	public void run() {
		m = new Markov();
		
		//GridLayout gl = new GridLayout(background.getWidth(), background.getHeight());
		//background.setLayout(gl);
		addActionListeners();
	}

	private void setUpComponents() {
		count = 15.0;
		
		new RandomGenerator();
		rand = RandomGenerator.getInstance();
		
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
		setTitle("Donald J. Trump, 45th President of the United States - Official Tweet Generator");
		setLayout(new BorderLayout());

		background = new GCanvas();
		background.setBackground(Color.WHITE);	
		add(background, BorderLayout.CENTER);
		
		label = new JButton("Make Donald Tweet Again!");
		label.setFont(new Font("Helvetica Neue", Font.PLAIN, 40));
		label.setActionCommand("tweet");
		label.setIcon(new ImageIcon("res/trump.jpg"));
		add(label, BorderLayout.SOUTH);
		label.addActionListener(this);

		settings = new JButton("For nerds only. Sad!");
		settings.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
		settings.setActionCommand("settings");
		add(settings, BorderLayout.NORTH);
		settings.addActionListener(this);
		
		followers = new JLabel(count + "M Following!");
		followers.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
		add(followers, BorderLayout.NORTH);

		
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("tweet")) {
			background.clear();
			//System.out.println(m.toString());
			String s = m.tweet();
			JTextArea tweet = new JTextArea(s, 3, 30);
			tweet.setLocation(getWidth() / 2 - Math.min(30 * 9, s.length() * 9), getHeight() / 2);
			tweet.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
			tweet.setLineWrap(true);
			tweet.setWrapStyleWord(true);
			background.add(tweet);
			updateFollowerCount();
		} else {
			try {
				String result = JOptionPane.showInputDialog("New analysis chunk size?");	
				m.setChunkSize(Integer.parseInt(result.trim()));
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(background, "You dummy. That's the worst number anyone's ever seen. I know it, you know it, everybody knows it!", "FAKE NUMBERS!", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e) {
				//stay the same
			}
			
		}
	}

	
	private void updateFollowerCount() {
		count += rand.nextInt(10) / 10.0;
		DecimalFormat df = new DecimalFormat("#.#");
		followers.setText(df.format(count) + "M Following!");	
	}
	
	
}
