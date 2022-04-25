// version 0.17.0.4.22.17.02
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;

import javax.swing.*;

public class mainMenu extends JFrame {
	final int sizeX = 400;
	final int sizeY = 400;

	
	public mainMenu(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(sizeX, sizeY);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
		buildMenu();
		
	}
	

	
	
	
	private void buildMenu() {
		JButton schedule = new JButton("Schedule");
		JButton homework = new JButton("Homework");
		/*
		 TODO :
		Make a label
		Make a log-inn menu
		Text size and font
		Colours
		*/

		schedule.setBounds(sizeX/4, sizeY/4, sizeX/2, sizeY/4);
		schedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new scheduleMaker("Your schedule");
			
			
			}	
		});
		
		
		
		homework.setBounds(sizeX/4, sizeY/2, sizeX/2, sizeY/4);
		homework.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new homework("Homework");
			
			
			}	
		});
		
		
		
		add(schedule);
		add(homework);
	}





	public static void main(String[] args) {
		new mainMenu("Student helper");
		//System.out.println("This is  ааааааа бббббб вввввв Понеделник and this ьяья яьяьяуъа ее");

	}

}
