package ie.gmit.tonylong.quiz_editor;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class QuizEditor extends JFrame {
	
	public QuizEditor(){
		super("Quiz Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new QuizPane());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new QuizEditor();
	}

}
