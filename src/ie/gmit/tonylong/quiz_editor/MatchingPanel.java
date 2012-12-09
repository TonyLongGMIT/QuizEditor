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
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MatchingPanel extends JPanel {
	private BufferedWriter writer;
	private boolean allowedToRemove = false;
	
	private JLabel titleLbl = new JLabel("Question Title (optional)");
	private JTextField titleTxt = new JTextField();
	private ArrayList<MatchPair> pairs = new ArrayList<MatchPair>();
	private RemoveListener remover = new RemoveListener();
	private JPanel answerPnl = new JPanel(new MigLayout());
	private JButton newPairBtn = new JButton("Add new matching pair");
	private JButton saveBtn = new JButton("Save to file");
	
	private class MatchPair extends JPanel{
		private JTextField left = new JTextField();
		private JLabel separator = new JLabel("->");
		private JTextField right = new JTextField();
		private JButton removeBtn = new JButton("remove");
		
		public MatchPair(){
			super(new MigLayout());
			add(left, "pushx, growx");
			add(separator);
			add(right, "pushx, growx");
			removeBtn.addActionListener(remover);
			if(!allowedToRemove){
				lock();
			}
			add(removeBtn, "wrap");
			answerPnl.add(this, "pushx, growx, wrap");
		}
		
		public String getLeft(){
			String str = left.getText();
			left.setText("");
			return str;
		}
		
		public String getRight(){
			String str = right.getText();
			right.setText("");
			return str;
		}
		
		public void lock(){
			removeBtn.setEnabled(false);
		}
		
		public void unlock(){
			removeBtn.setEnabled(true);
		}
		
		public void remove(){
			answerPnl.remove(this);
		}
	}

	public MatchingPanel(BufferedWriter writer){
		super(new MigLayout("", "", ""));
		this.writer = writer;
		
		add(titleLbl);
		add(titleTxt, "pushx, growx, wrap");
		pairs.add(new MatchPair());
		pairs.add(new MatchPair());
		pairs.add(new MatchPair());
		add(answerPnl, "skip, pushx, grow, wrap");
		newPairBtn.addActionListener(new NewPairListener());
		add(newPairBtn, "skip, pushy, right, bottom, wrap");
		saveBtn.addActionListener(new SaveListener());
		add(saveBtn, "skip, right, bottom, wrap");
	}
	
	private class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try {
				writer.append("::"+titleTxt.getText()+":: { ");
				titleTxt.setText("");
				for(MatchPair pair : pairs){
					writer.append("="+pair.getLeft()+"->"+pair.getRight()+" ");
				}
				writer.append("}\n\n");
				writer.flush();
				JOptionPane.showMessageDialog(null, "Question Added");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class NewPairListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			pairs.add(new MatchPair());
			if(pairs.size()>3 && !allowedToRemove){
				for(MatchPair pair : pairs){
					pair.unlock();
				}
				allowedToRemove = true;
			}
			updateUI();
		}
	}
	
	private class RemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			JButton source = (JButton)event.getSource();
			MatchPair pair = (MatchPair)source.getParent();
			pair.remove();
			pairs.remove(pair);
			if(pairs.size()<=3){
				for(MatchPair in : pairs){
					in.lock();
				}
				allowedToRemove = false;
			}
			updateUI();
		}
	}
}
