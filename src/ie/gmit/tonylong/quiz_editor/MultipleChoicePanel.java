package ie.gmit.tonylong.quiz_editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MultipleChoicePanel extends JPanel {
	private BufferedWriter writer;
	private JLabel titleLbl = new JLabel("Question Title (optional)");
	private JTextField titleTxt = new JTextField();
	private JLabel questionLbl = new JLabel("Question");
	private JTextArea questionTxt = new JTextArea();
	private JScrollPane questionScrl = new JScrollPane(questionTxt);
	private JButton clearBtn = new JButton("Clear Text");
	private JPanel answerPnl = new JPanel(new MigLayout("", "", ""));
	private ArrayList<Answer> answers = new ArrayList<Answer>();
	private RemoveListener removeListener = new RemoveListener();
	private JButton addNewChoiceBtn = new JButton("Add new choice");
	private JButton saveBtn = new JButton("Save to file");

	private class Answer extends JPanel{
		private JTextField answerField = new JTextField();
		private JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		private JButton button = new JButton("remove");
		
		public Answer(){
			super(new MigLayout("", "", ""));
			button.addActionListener(removeListener);
			add(answerField, "pushx, growx");
			add(spinner, "");
			add(button, "right");
			answerPnl.add(this, "pushx, growx, wrap");
		}
		
		public String getAnswer(){
			return answerField.getText();
		}
		
		public int getWeight(){
			return (int)spinner.getModel().getValue();
		}
		
		public void reset(){
			answerField.setText("");
			spinner.getModel().setValue(0);
		}
		
		public void remove(){
			answerPnl.remove(this);
		}
	}
	
	public MultipleChoicePanel(BufferedWriter writer){
		super(new MigLayout("", "", ""));
		this.writer = writer;
		
		add(titleLbl, "right");
		add(titleTxt, "grow, wrap");
		add(questionLbl, "right");
		add(questionScrl, "push, grow, wrap");
		clearBtn.addActionListener(new ClearListener());
		add(clearBtn, "span, right, wrap");
		answers.add(new Answer());
		answers.add(new Answer());
		add(answerPnl, "skip, grow, wrap");
		addNewChoiceBtn.addActionListener(new AddNewChoiceListener());
		add(addNewChoiceBtn, "skip, right, wrap");
		saveBtn.addActionListener(new SaveListener());
		add(saveBtn, "skip, right, wrap");
	}
	
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			questionTxt.setText("");
		}
	}
	
	private class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try {
				writer.append("::"+titleTxt.getText()+":: ");
				titleTxt.setText("");
				writer.append(questionTxt.getText()+" {");
				questionTxt.setText("");
				for(Answer answer : answers){
					int weight = answer.getWeight();
					String text = answer.getAnswer();
					if(weight<100){
						writer.append("~%"+weight+"%"+text+" ");
					}else{
						writer.append("="+text+" ");
					}
					answer.reset();
				}
				writer.append("}\n\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Question added.");
		}
	}
	
	private class AddNewChoiceListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			answers.add(new Answer());
			updateUI();
		}
	}
	
	private class RemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			JButton source = (JButton)event.getSource();
			Answer answer = (Answer)source.getParent();
			answer.remove();
			answers.remove(answer);
			updateUI();
		}
	}
}
