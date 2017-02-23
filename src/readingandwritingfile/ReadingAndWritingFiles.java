package readingandwritingfiles;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//@author: Celena Williams
//Last edited: February 23, 2017

//Code may be kept and modified, so long as my name stays the creator.
//Parts of code may be used without documentation if code is understood.

public final class ReadingAndWritingFiles extends JFrame {
    JTextArea textArea;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem saveMenuItem, openMenuItem, saveAsMenuItem;

    File fileName;
    RandomAccessFile file;

    public static void main (String [] args) {
        ReadingAndWritingFiles application = new ReadingAndWritingFiles ();
        application.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }//END public static void main (String [] args)
    
    //Creates and adds the content to the window.
    public ReadingAndWritingFiles () {
        setResizable (false);
        setLayout (new BorderLayout ());
        menuBar = new JMenuBar ();
        
        //Menus can be set with mnemonic. However, they do not work. Instead
        //set menus with keyevents, which can be accessed via the keyboard
        //with the keys ALT and the letter assigned.
        
        //Menu items can be set and accessed via mnemonic. To set, menu items,
        //you set the mnemonic you want. To access that menu item, press only
        //the key assigned.
        
        //The actionevents are coded with the methods they call. It made sense
        //to just code the method calls and actionevents up here instead of in
        //one actionPerformed down below.
        menu = new JMenu ("Menu (ALT + M)");
        menu.setMnemonic(KeyEvent.VK_M);
        saveMenuItem = new JMenuItem ("Save... (s)");
        saveMenuItem.setMnemonic ('S');
        saveMenuItem.setEnabled (false);
        saveMenuItem.addActionListener ((ActionEvent e) -> {
            saveFile ();
        });//END saveMenuItem.addActionListener ((ActionEvent e)
        
        openMenuItem = new JMenuItem ("Open... (o)");
        openMenuItem.setMnemonic ('O');
        openMenuItem.addActionListener ((ActionEvent e) -> {
            saveAsMenuItem.setEnabled (false);
            saveMenuItem.setEnabled (true);
            textArea.setEditable (true);
            openFile ();
        });//END openMenuItem.addActionListener ((ActionEvent e)
        openMenuItem.setEnabled (true);
        
        saveAsMenuItem = new JMenuItem ("Save As... (a)");
        saveAsMenuItem.setMnemonic ('A');
        saveAsMenuItem.addActionListener ((ActionEvent e) -> {
            saveAsMenuItem.setEnabled (false);
            saveMenuItem.setEnabled (true);
            textArea.setEditable (true);
            saveFileAs ();
        });//END saveAsMenuItem.addActionListener ((ActionEvent e)
        saveAsMenuItem.setEnabled (true);

        setJMenuBar (menuBar);
        menuBar.add (menu);
        menu.add (saveMenuItem);
        menu.add (saveAsMenuItem);
        menu.add (openMenuItem);

        textArea = new JTextArea ();
        textArea.setEditable (false);
        add (textArea, BorderLayout.CENTER);

        setSize (500, 500);
        setVisible (true);
    }//END public ReadingAndWritingFiles ()

    //JFileChooser is used to save and open the files.
    
    //To use the saveFile method, you must have a file created already.
    public void saveFile () {
        try {
            //Sets cursor to the beginning of the file that is to be written to.
            file.seek (0);
            //Writes everything in the text area to the file. Allows for UTF-8
            //characters to be written. Doing this can throw an IOException.
            file.writeUTF (textArea.getText ());
        }//END try

        catch (IOException ioException) {
            JOptionPane.showMessageDialog (null, "Error Writing to File", 
                                                 "Error Writing to File",
                                                 JOptionPane.ERROR_MESSAGE);
        }//END catch (IOException ioException)
    }//END public void saveFile ()

    public void saveFileAs () {
        //Since this program only deals with files, I choose to have the File
        //Chooser show and select files only.
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileSelectionMode (JFileChooser.FILES_ONLY);

        //Shows a save dialog to create the file.
        int result = fileChooser.showSaveDialog (this);

        //If the user clicks cancel or there is an error, return nothing)
        if (result == JFileChooser.CANCEL_OPTION || 
            result == JFileChooser.ERROR_OPTION)
            return;
        
        //Gets the file name (and location) and displays as the window title.
        fileName = fileChooser.getSelectedFile ();
        setTitle (fileName.toString ());

        //If the user does not type in a name, show an error message.
        if (fileName == null || fileName.getName ().equals (""))
            JOptionPane.showMessageDialog (null, "Invalid File Name", 
                                                 "Invalid File Name",
                                                 JOptionPane.ERROR_MESSAGE);
        else {
            try {
                //Creates a file, with the assigned file name that can be read
                //and written to. Doing this can throw a FileNotFoundException.
                file = new RandomAccessFile (fileName, "rw");
            }//END try
            
            catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog (null, "File Not Found",
                                                     "File Not Found",
                                                     JOptionPane.ERROR_MESSAGE);
            }//END catch (FileNotFoundException fileNotFoundException)
        }//END else
    }//ENd public void saveFileAs ()

    public void openFile () {
        JFileChooser fileChooser = new JFileChooser ();
        fileChooser.setFileSelectionMode (JFileChooser.FILES_ONLY);

        //Shows an open dialog to open a file.
        int result = fileChooser.showOpenDialog (this);

        if (result == JFileChooser.CANCEL_OPTION || 
            result == JFileChooser.ERROR_OPTION)
            return;

        fileName = fileChooser.getSelectedFile ();
        setTitle (fileName.toString ());

        if (fileName == null || fileName.getName ().equals (""))
            JOptionPane.showMessageDialog (null, "Invalid File Name",
                                                 "Invalid File Name",
                                                 JOptionPane.ERROR_MESSAGE);
        else {
            try {
                //Sets the text area with the text from the specified file.
                //Doing this can throw an IOException.
                file = new RandomAccessFile (fileName, "rw");
                textArea.setText (file.readUTF ());
            }//END try

            catch (IOException ioException) {
                JOptionPane.showMessageDialog (null, "Error Reading File",
                                                     "Error Reading File",
                                                     JOptionPane.ERROR_MESSAGE);
            }//END catch (IOException ioException)
        }//END else
    }//END public void openFile ()
}//END public class ReadingAndWritingFiles