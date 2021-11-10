import javax.swing.*;
import java.awt.event.*;
class HelpDialog extends JDialog{
	JButton ok=new JButton("OK");
	ImageIcon icon=new ImageIcon("Icons\\help_icon.png");
	JTextArea textArea;
	JScrollPane sp;

	public HelpDialog(WriteIt parent){
		super(parent,"Help",false);
		setSize(550,400);

		textArea=new JTextArea("\n\n\n\nThis is a Simple Text Editor, which has 4 Menus(File, Edit , Options, Help),"+
		" in which you can find several Menu Items. \n\nThe File Menu contains Open to open your text files,"+
		" Save to Save your file, Save as to save your file with different name, Print to print your text file and Exit to exit the Editor.\n"+
		"The Edit menu contains Undo, Redo and Clear operations to edit the text in the text box\n"+
		"The Options menu is to customize your text editor, it contains menu items to change the font style, font color, font background color and look and feel of the application."+
		"\nThe Help Menu contains all the information about the developer, the application and how to contact the developer."+
		"\nBelow in the window you can see the current caret position and current system time\n"+
		"\n\nThat is all !!! Thank You :)");
		sp=new JScrollPane(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		ok.addActionListener((ActionEvent e)->{
			this.dispose();
		});


		add(new JLabel(icon),"West");
		add(ok,"South");
		add(sp,"Center");
	}
}