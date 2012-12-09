package ie.gmit.tonylong.quiz_editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class QuizPane extends JTabbedPane {
	private JFileChooser fileChooser = new JFileChooser();
	private File file;
	private BufferedWriter writer = null;

	public QuizPane(){
		super();
		
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Create or choose a GIFT file to edit");
		if( fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			file = fileChooser.getSelectedFile();
			try {
				if(!file.exists()){
					file.createNewFile();
				}
				writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.exit(0);
		}
		addTab("Numerical", new NumericRangePanel(writer));
		addTab("True/False", new TrueFalsePanel(writer));
		addTab("Essay", new EssayPanel(writer));
		addTab("Matching", new MatchingPanel(writer));
		addTab("Multiple Choice", new MultipleChoicePanel(writer));
		
	}
}
