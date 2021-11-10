
/**
 * @author Prathamesh Patil
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.UndoManager;

public class WriteIt extends JFrame{

    JTextArea textArea;
    WIMenuBar menuBar;
	Font basicFont;
    JScrollPane scrollPane;
    boolean saved=true;
    boolean opened=false;
    UndoManager undoManager=new UndoManager();
    JPanel statusPanel=new JPanel();
    String caretString="Line: 1||Column:1";
    String timeString="TIME: "+java.time.LocalDateTime.now().toString().substring(11,19);
    JLabel caretLabel=new JLabel(caretString,JLabel.RIGHT);
    JLabel timeLabel=new JLabel(timeString,JLabel.LEFT);

    public WriteIt(){
        this.setSize(1000,700);
        this.setTitle("WriteIt - Untitled.txt");
        caretLabel.setFont(new Font("Century",Font.PLAIN,14));
        timeLabel.setFont(new Font("Century",Font.PLAIN,14));

        this.basicFont=new Font("Arial",Font.PLAIN,14);
        this.textArea=new JTextArea();
		this.menuBar=new WIMenuBar(this);
		this.scrollPane=new JScrollPane(textArea);
		this.textArea.setFont(basicFont);

        this.setJMenuBar(menuBar);

        this.add(scrollPane,SwingConstants.CENTER);

		//IMPLEMENTATION OF SAVE FOR CHANGES IN DOCUMENT
    	textArea.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				saved=false;
				if(getTitle().indexOf('*') == -1)
					WriteIt.this.setTitle(WriteIt.this.getTitle()+"*");
			}
			public void insertUpdate(DocumentEvent e){
				saved=false;
				if(getTitle().indexOf('*') == -1)
					WriteIt.this.setTitle(WriteIt.this.getTitle()+"*");
			}
			public void removeUpdate(DocumentEvent e){
				saved=false;
				if(getTitle().indexOf('*') == -1)
					WriteIt.this.setTitle(WriteIt.this.getTitle()+"*");
			}
		});

		//ATTACH UNDOMANAGER TO THE TEXTAREA
		textArea.getDocument().addUndoableEditListener(undoManager);

		//REGISTER WINDOWLISTENER TO THE FRAME
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(!saved){
					int choice=JOptionPane.showConfirmDialog(WriteIt.this,"Do you want to save the file?","Save file",
								JOptionPane.YES_NO_CANCEL_OPTION);
					if(choice==JOptionPane.YES_OPTION)
						menuBar.saveFile();
					else if(choice==JOptionPane.NO_OPTION)
						System.exit(0);
					else if(choice==JOptionPane.CANCEL_OPTION)
						return;
				}
				System.exit(0);
			}
		});

		//SETTING THE MAIN FRAME ICON TO THE WRITEIT ICON
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Icons\\main_icon.png"));

		//DESIGNING THE STATUS PANEL

		//TIME THREAD
		Thread timeThread=new Thread(()->{
			//Implementation of run() Method of Runnable interface
			while(true){
				timeString="TIME: "+java.time.LocalDateTime.now().toString().substring(11,19);
				timeLabel.setText(timeString);
				try{Thread.sleep(1000);}catch(Exception exc){System.out.println(exc);}
			}
		});
		//STARTED THE TIME THREAD
		timeThread.start();

		statusPanel.setLayout(new GridLayout(1,2,100,0));
		caretLabel.setText(caretString);
		statusPanel.add(caretLabel);
		timeLabel.setText(timeString);
		statusPanel.add(timeLabel);
		this.add(statusPanel,"South");

    	//ADDING CARET LISTENER TO THE TEXT AREA TO CHANGE CARET VALUE
    	textArea.addCaretListener((CaretEvent e)->{
			try{
				int caretPos=textArea.getCaretPosition();
				int row=textArea.getLineOfOffset(caretPos);
				int column=caretPos-textArea.getLineStartOffset(row);
				row++;
				caretString="Line:"+row+"||Column:"+column;
				caretLabel.setText(caretString);
			}
			catch(Exception exc){System.out.println(exc);}
		});
    }

    public static void main(String[] args) {
        WriteIt mainFrame=new WriteIt();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
