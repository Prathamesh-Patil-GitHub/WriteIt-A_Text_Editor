
import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;

public class WIMenuBar extends JMenuBar {
	WriteIt parentFrame;
	File currentFile;
	//DECLARATIONS OF ALL MENUS AND MENU-ITEMS

    JMenu fileMenu=new JMenu("File");
    JMenu editMenu=new JMenu("Edit");
    JMenu optionsMenu=new JMenu("Options");
    JMenu helpMenu=new JMenu("Help");

    //JMenuItem newItem=new JMenuItem("New",new ImageIcon("Icons\\new_file_icon.png"));
    JMenuItem openItem=new JMenuItem("Open",new ImageIcon("Icons\\open_file_icon.png"));
    //JMenuItem closeItem=new JMenuItem("Close",new ImageIcon("Icons\\close_file_icon.png"));
    //JMenuItem closeAllItem=new JMenuItem("Close All",new ImageIcon("Icons\\closeall_file_icon.png"));
    JMenuItem saveItem=new JMenuItem("Save",new ImageIcon("Icons\\save_file_icon.png"));
    //JMenuItem saveAllItem=new JMenuItem("Save All",new ImageIcon("Icons\\saveall_file_icon.png"));
    JMenuItem saveasItem=new JMenuItem("Save As",new ImageIcon("Icons\\saveas_file_icon.png"));
    JMenuItem printItem=new JMenuItem("Print",new ImageIcon("Icons\\print_file_icon.png"));
    //JMenuItem printPreviewItem=new JMenuItem("Print Priview",new ImageIcon("Icons\\printpriview_file_icon.png"));
    //JMenuItem printSetupItem=new JMenuItem("Print Setup",new ImageIcon("Icons\\printsetup_file_icon.png"));
    JMenuItem exitItem=new JMenuItem("Exit",new ImageIcon("Icons\\exit_file_icon.png"));

    JMenuItem undoItem=new JMenuItem("Undo");
    JMenuItem redoItem=new JMenuItem("Redo");
    JMenuItem clearItem=new JMenuItem("Clear");

    JMenuItem editFontItem=new JMenuItem("Edit Font");
    JMenuItem fontColorItem=new JMenuItem("Edit Font Color");
    JMenuItem textBackgroundColorItem=new JMenuItem("Text Background Color");
	JMenu lookAndFeelItem=new JMenu("Set Look and Feel");
	ButtonGroup group=new ButtonGroup();
	JRadioButtonMenuItem metalLAF=new JRadioButtonMenuItem("Metal Look and Feel",true);
	JRadioButtonMenuItem nimbusLAF=new JRadioButtonMenuItem("Nimbus Look and Feel");
	JRadioButtonMenuItem motifLAF=new JRadioButtonMenuItem("Motif Look and Feel");
	JRadioButtonMenuItem windowsLAF=new JRadioButtonMenuItem("Windows Look and Feel");
	JRadioButtonMenuItem windowsClassicLAF=new JRadioButtonMenuItem("Windows Classic Look and Feel");

    JMenuItem aboutItem=new JMenuItem("About");
    JMenuItem contactItem=new JMenuItem("Contact Us");
    JMenuItem helpItem=new JMenuItem("Help");

    public WIMenuBar(WriteIt parent){
		parentFrame=parent;
		fileMenu.setFont(new Font("Century",Font.PLAIN,14));
		editMenu.setFont(new Font("Century",Font.PLAIN,14));
		optionsMenu.setFont(new Font("Century",Font.PLAIN,14));
		helpMenu.setFont(new Font("Century",Font.PLAIN,14));

		//ADDING RADIOBUTTONS IN GROUP
		group.add(metalLAF);
		group.add(motifLAF);
		group.add(nimbusLAF);
		group.add(windowsLAF);
		group.add(windowsClassicLAF);


		//SETTING ACCELERATORS WITH THE MENU ITEMS

		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_DOWN_MASK));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_DOWN_MASK));
		printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_DOWN_MASK));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.ALT_DOWN_MASK));

		undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
		redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
		clearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_DOWN_MASK));

		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));

		//ADDING MENU-ITEMS INTO MENUS AND MENUS INTO MENUBAR

        //fileMenu.add(newItem);
        fileMenu.add(openItem);
        //fileMenu.add(closeItem);
        //fileMenu.add(closeAllItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        //fileMenu.add(saveAllItem);
        fileMenu.add(saveasItem);
        saveasItem.setEnabled(false);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        //fileMenu.add(printPreviewItem);
        //fileMenu.add(printSetupItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        add(fileMenu);

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(clearItem);
        add(editMenu);

        optionsMenu.add(editFontItem);
        optionsMenu.add(fontColorItem);
        optionsMenu.add(textBackgroundColorItem);
        lookAndFeelItem.add(metalLAF);
        lookAndFeelItem.add(nimbusLAF);
        lookAndFeelItem.add(motifLAF);
        lookAndFeelItem.add(windowsLAF);
        lookAndFeelItem.add(windowsClassicLAF);
        optionsMenu.add(lookAndFeelItem);

        add(optionsMenu);

        helpMenu.add(aboutItem);
        helpMenu.add(contactItem);
        helpMenu.add(helpItem);
		add(helpMenu);

		//HANDLING EVENTS WHEN MENU-ITEMS ARE SELECTED

		//OPEN ITEM HANDLING
		openItem.addActionListener((ActionEvent e)->{
			try{
				if(!parent.saved){
					int choice=JOptionPane.showConfirmDialog(parent,"Do you want to save the file?","Save file",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if(choice==JOptionPane.CANCEL_OPTION || choice==JOptionPane.CLOSED_OPTION )
						return;

					if(choice==JOptionPane.YES_OPTION){
						saveFile();
						parent.saved=true;
					}
				}
				JFileChooser chooser=new JFileChooser();
				chooser.showOpenDialog(parent);
				chooser.setMultiSelectionEnabled(false);
				parent.textArea.setText("");
				currentFile=chooser.getSelectedFile();
				BufferedReader reader=new BufferedReader(new FileReader(currentFile));
				parent.setTitle("WriteIt - "+currentFile);

				String line=reader.readLine();
				while(line!=null){
					parent.textArea.append(line+"\n");

					line=reader.readLine();
				}
				reader.close();
				parentFrame.opened=true;
				saveasItem.setEnabled(true);
			}
			catch(Exception exc){System.out.println(exc);};
		});

		//SAVE ITEM HANDLING
		saveItem.addActionListener((ActionEvent e)->{
			saveFile();
		});

		//SAVE AS ITEM HANDLING
		saveasItem.addActionListener((ActionEvent e)->{
			try{
				JFileChooser chooser=new JFileChooser();
				chooser.showSaveDialog(parentFrame);
				File newFile=chooser.getSelectedFile();
				PrintWriter pw=new PrintWriter(newFile);
				pw.print(parentFrame.textArea.getText());
				pw.close();
				JOptionPane.showMessageDialog(parent,"File Saved Successfully ! ! !");
			}
			catch(Exception exc){System.out.println(exc);}
		});

		//PRINT ITEM HANDLING
		printItem.addActionListener((ActionEvent e)->{
			try{
				parent.textArea.print();
			}
			catch(Exception exc){System.out.println(exc);}
		});

		//EXIT ITEM HANDLING
		exitItem.addActionListener((ActionEvent e)->{
			if(!parent.saved){
				int choice=JOptionPane.showConfirmDialog(parent,"Do you want to save the file?","Save file",
							JOptionPane.YES_NO_CANCEL_OPTION);
				if(choice==JOptionPane.YES_OPTION)
					saveFile();
				else if(choice==JOptionPane.NO_OPTION)
					System.exit(0);
				else if(choice==JOptionPane.CANCEL_OPTION)
					return;
			}
			System.exit(0);
		});

		undoItem.addActionListener((ActionEvent e)->{
			parent.undoManager.undo();
		});

		redoItem.addActionListener((ActionEvent e)->{
					parent.undoManager.redo();
		});

		clearItem.addActionListener((ActionEvent e)->{
			parent.textArea.setText("");
		});

		editFontItem.addActionListener((ActionEvent e)->{
			JFontChooser fontChooser=new JFontChooser();
			Font newFont=fontChooser.showDialog(parent,"Choose The Font");
			if(newFont==null)
				return;
			parent.textArea.setFont(newFont);
		});

		fontColorItem.addActionListener((ActionEvent e)->{
			Color color=JColorChooser.showDialog(parent,"Choose The Font Color",Color.BLACK);
			if(color==null)
				return;
			parent.textArea.setForeground(color);
		});

		textBackgroundColorItem.addActionListener((ActionEvent e)->{
			Color color=JColorChooser.showDialog(parent,"Choose The Text Background Color",Color.BLACK);
			if(color==null)
				return;
			parent.textArea.setBackground(color);
		});

		metalLAF.addActionListener((ActionEvent e)->{
			try{
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			}
			catch(Exception exc){System.out.println(exc);}
		});

		nimbusLAF.addActionListener((ActionEvent e)->{
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception exc){System.out.println(exc);}
		});

		motifLAF.addActionListener((ActionEvent e)->{
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception exc){System.out.println(exc);}
		});

		windowsLAF.addActionListener((ActionEvent e)->{
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception exc){System.out.println(exc);}
		});

		windowsClassicLAF.addActionListener((ActionEvent e)->{
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		}
		catch(Exception exc){System.out.println(exc);}
		});

		aboutItem.addActionListener((ActionEvent e)->{
			AboutDialog about=new AboutDialog(parent);
			about.setVisible(true);
		});

		contactItem.addActionListener((ActionEvent e)->{
			ContactUsDialog contact=new ContactUsDialog(parent);
			contact.setVisible(true);
		});

		helpItem.addActionListener((ActionEvent e)->{
			HelpDialog help=new HelpDialog(parent);
			help.setVisible(true);
		});
    }

    void saveFile(){
		try{
			if(parentFrame.opened){
				PrintWriter pw=new PrintWriter(currentFile);
				pw.print(parentFrame.textArea.getText());
				parentFrame.setTitle("WriteIt- "+currentFile);
				pw.close();
				parentFrame.saved=true;
			}
			else{
				JFileChooser chooser=new JFileChooser();
				chooser.showSaveDialog(parentFrame);
				currentFile=chooser.getSelectedFile();
				PrintWriter pw=new PrintWriter(currentFile);
				pw.print(parentFrame.textArea.getText());
				parentFrame.setTitle("WriteIt- "+currentFile);
				pw.close();
				parentFrame.opened=true;
				saveasItem.setEnabled(true);
				JOptionPane.showMessageDialog(parentFrame,"File Saved Successfully ! ! !");
				parentFrame.saved=true;
			}
		}
		catch(Exception ex){System.out.println(ex);}
	}
}
