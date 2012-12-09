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
public class EssayPanel extends JPanel {
	BufferedWriter writer;
	private JLabel titleLbl = new JLabel("Question Title (optional)");
	private JTextField titleTxt = new JTextField();
	private JLabel questionLbl = new JLabel("Question");
	private JTextArea questionTxt = new JTextArea();
	private JScrollPane questionScrl = new JScrollPane(questionTxt);
	private JButton clearBtn = new JButton("Clear Text");
	private JButton saveBtn = new JButton("Save to File");

	public EssayPanel(BufferedWriter writer){
		super(new MigLayout());
		this.writer = writer;
		
		add(titleLbl, "");
		add(titleTxt, "pushx, growx, wrap");
		add(questionLbl, "");
		add(questionScrl, "push, grow, wrap");
		clearBtn.addActionListener(new ClearListener());
		add(clearBtn, "skip, right, wrap");
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
				writer.append(questionTxt.getText()+" {}\n\n");
				questionTxt.setText("");
				writer.flush();
				JOptionPane.showMessageDialog(null, "Question added");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
