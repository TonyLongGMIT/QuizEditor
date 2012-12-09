package ie.gmit.tonylong.quiz_editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class NumericRangePanel extends JPanel {
	private BufferedWriter writer;
	private JLabel titleLbl = new JLabel("Question Title (otional)");
	private JTextField titleTxt = new JTextField();
	private JLabel questionLbl = new JLabel("Question");
	private JTextArea questionTxt = new JTextArea();
	private JScrollPane questionScrl = new JScrollPane(questionTxt);
	private JButton clearBtn = new JButton("Clear Text");
	private JLabel answerLbl = new JLabel("Answer Range from: ");
	private JTextField fromTxt = new JTextField();
	private JLabel toLbl = new JLabel("to: ");
	private JTextField toTxt = new JTextField();
	private JButton saveBtn = new JButton("Save to File");

	public NumericRangePanel(BufferedWriter writer){
		super(new MigLayout());
		this.writer = writer;
		
		add(titleLbl, "");
		add(titleTxt, "spanx, growx, wrap");
		add(questionLbl, "");
		add(questionScrl, "spanx, push, grow, wrap");
		clearBtn.addActionListener(new ClearListener());
		add(clearBtn, "skip 3, right, wrap");
		add(answerLbl, "");
		add(fromTxt, "pushx, growx");
		add(toLbl, "");
		add(toTxt, "pushx, growx, wrap");
		saveBtn.addActionListener(new SaveListener());
		add(saveBtn, "skip 3, right");
	}
	
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			questionTxt.setText("");
		}
	}
	
	private class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(hasValidInput(fromTxt)&&hasValidInput(toTxt)){
				try {
					writer.append("::"+titleTxt.getText()+":: ");
					titleTxt.setText("");
					writer.append(questionTxt.getText()+" ");
					questionTxt.setText("");
					writer.append("{#"+fromTxt.getText()+".."+toTxt.getText()+"}\n\n");
					fromTxt.setText("");
					toTxt.setText("");
					writer.flush();
					JOptionPane.showMessageDialog(null, "Question Added");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}
		
		private boolean hasValidInput(JTextField field){
			String str = field.getText();
			for(int index=0; index<str.length(); index++){
				char ch = str.charAt(index);
				if(ch!='0'&&ch!='1'&&ch!='2'&&ch!='3'&&ch!='4'&&ch!='5'&&ch!='6'&&ch!='7'&&ch!='8'&&ch!='9'&&ch!='.'){
					field.setText("");
					JOptionPane.showMessageDialog(null, "Answer fields must be real numbers.", 
							"ERROR!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			return true;
		}
	}
}
