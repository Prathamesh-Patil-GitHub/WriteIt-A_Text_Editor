import javax.swing.*;
import java.awt.event.*;
class ContactUsDialog extends JDialog{
	JButton ok=new JButton("OK");
	ImageIcon icon=new ImageIcon("Icons\\contact_us_icon.png");
	JTextArea textArea=new JTextArea();
	JScrollPane sp;

	public ContactUsDialog(WriteIt parent){
		super(parent,"Contact Us",false);
		setSize(500,400);

		ok.addActionListener((ActionEvent e)->{
			this.dispose();
		});

		textArea.setText("\n\n\n\n\n\n\nWhatsApp: +91-7798410704\n\nLinkedIn: https://linkedin.com/in/prathamesh-patil-jalgaon"+
		"\n\nGitHub: https://github.com/Prathamesh-Patil-GitHub\n\nInstagram: @pro14.1");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		sp=new JScrollPane(textArea);

		add(new JLabel(icon),"West");
		add(ok,"South");
		add(sp,"Center");
	}
}