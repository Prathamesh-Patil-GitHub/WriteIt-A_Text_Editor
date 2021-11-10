import javax.swing.*;
import java.awt.event.*;
class AboutDialog extends JDialog{
	JButton ok=new JButton("OK");
	ImageIcon icon=new ImageIcon("Icons\\main_icon.png");
	JTextArea textArea;
	JScrollPane sp;

	public AboutDialog(WriteIt parent){
		super(parent,"About Us",false);
		setSize(550,400);

		textArea=new JTextArea("\n\n\n\n\n\n\n\nHello, I'm Prathamesh Patil the developer of *WriteIt the Text Editor*."+
		"\n*WriteIt* is basically a Text Editor where you can edit your text,\n"+
		"change the fonts and colors, save it, print it,\nand many more things to do!!!");
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