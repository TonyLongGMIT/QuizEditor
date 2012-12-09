package ie.gmit.tonylong.quiz_editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class TrueFalsePanel extends JPanel {
	private BufferedWriter writer;
	
	private JLabel title_lbl			= new JLabel("Question Title (optional)");
	private JTextField title_txt		= new JTextField("");
	private JLabel question_lbl			= new JLabel("Question");
	private JTextArea question_txt		= new JTextArea();
	private JScrollPane question_scrl	= new JScrollPane(question_txt);
	private JRadioButton true_btn 		= new JRadioButton("True");
	private JRadioButton false_btn		= new JRadioButton("False");
	private ButtonGroup radioGroup		= new ButtonGroup();
	private JPanel radioPanel			= new JPanel();
	private JButton clear_btn			= new JButton("Clear Text");
	private JButton save_btn			= new JButton("Save to file");

	public TrueFalsePanel(BufferedWriter writer){
		super(new MigLayout());
		this.writer = writer;
		
		add(title_lbl, "");
		add(title_txt, "spanx, growx, wrap");
		add(question_lbl, "");
		add(question_scrl, "span, push, grow, wrap");
		radioGroup.add(true_btn);
		radioGroup.add(false_btn);
		radioPanel.setBorder(new TitledBorder("Statement is "));
		radioPanel.add(true_btn);
		radioPanel.add(false_btn);
		add(radioPanel, "skip");
		clear_btn.addActionListener(new ClearListener());
		add(clear_btn, "right, wrap");
		save_btn.addActionListener(new SaveListener());
		add(save_btn, "span, right");
		
	}
	
	private class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(true_btn.isSelected()){
				setContent("{TRUE} ");
			}else if(false_btn.isSelected()){
				setContent("{FALSE} ");
			}else{
				JOptionPane.showMessageDialog(null, 
						"Statement must be either 'true' or 'false'.", 
						"ERROR!", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void setContent(String str){
			try {
				writer.append("::"+title_txt.getText()+":: ");
				title_txt.setText("");
				writer.append(question_txt.getText()+" ");
				question_txt.setText("");
				writer.append(str+"\n\n");
				radioGroup.clearSelection();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Question added.");
		}
	}
	
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			question_txt.setText("");			
		}
	}
}
