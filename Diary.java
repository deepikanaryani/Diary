package com.schongeproductions.texteditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
@SuppressWarnings("serial")
public class Diary extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new Diary();
    }
    

    private JMenu fileMenu;
    private JMenu editMenu;
    //private JMenu formatMenu;
    private JMenuItem newFile, openFile, saveFile, saveAsFile, pageSetup, printFile, exit;
    private JMenuItem undoEdit, redoEdit, selectAll, copy, paste, cut;
    //private JMenuItem colorText,colorScreen;
    private JFrame editorWindow;
    private Border textBorder;
    private JScrollPane scroll;
    private JTextArea textArea;
    private Font textFont;
    private JFrame window;
    private PrinterJob job;
    public PageFormat format;
    private boolean opened = false;
    private boolean saved = false;
    private File openedFile;
    private UndoManager undo;

   

    public Diary() {
        super("Dear Diary");

        fileMenu();
        editMenu();
        //formatMenu();
        createTextArea();
        undoMan();
        createEditorWindow();
    }

    private JFrame createEditorWindow() {
    	
        
        editorWindow = new JFrame("Dear Diary");
        editorWindow.setLayout(new GridLayout());
        Label headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        headerLabel.setBackground(Color.cyan);
        headerLabel.setForeground(Color.black);
        
        headerLabel.setText("Shake off your sorrows as you write. Courage will be reborn!");
        editorWindow.setVisible(true);
        editorWindow.add(headerLabel);
        Font textFont1 = new Font("Arial Black",0, 20);
        headerLabel.setFont(textFont1);
        editorWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        editorWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        editorWindow.setJMenuBar(createMenuBar());
        editorWindow.add(scroll, BorderLayout.CENTER);
        editorWindow.pack();
        editorWindow.setLocationRelativeTo(null);
        return editorWindow;
    }
    
    private JTextArea createTextArea() {
    	Label headerLabel = new Label();
    	
    	
    	setLayout(new BorderLayout());
    	JLabel background=new JLabel(new ImageIcon("C:\\hello.jpeg"));
    	add(background);
        headerLabel.setAlignment(Label.CENTER);
        textBorder = BorderFactory.createBevelBorder(0, Color.BLACK, Color.BLACK);
        //textArea = new JTextArea(30, 50);
        headerLabel.setText("Control in action: TextArea"); 
        final ImageIcon imageIcon = new ImageIcon("hello.jpeg");
        textArea = new JTextArea(30,50) {
          Image image = imageIcon.getImage();

          Image grayImage = GrayFilter.createDisabledImage(image);
          {
            setOpaque(false);
          }

          public void paint(Graphics g){
            g.drawImage(grayImage, 0, 0, this);
            super.paint(g);
          }
        };
        //textArea.setForeground(Color.BLUE);
        //textArea.append("Shake off everything as you write! \nSorrows will dissappear, and courage will be reborn!\n\n\n\n***");
        textArea.setForeground(Color.black);
        textArea.setEditable(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(2, 5, 0, 0)));

        textFont = new Font("Bookman Old Style",Font.ITALIC, 16);
        textArea.setFont(textFont);
        //textArea.setEnabled(false);
        //textArea.setDisabledTextColor(Color.red);
        //Color c = new Color(0,0,0,100);
        //textArea.setBackground(c);

        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        return textArea;        
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(Box.createGlue());
        menuBar.add(fileMenu);
        //fileMenu.setMargin(new Insets(0, 100, 10, 10));
        menuBar.add(editMenu);

        return menuBar;
    }

    private UndoManager undoMan() {
      
        undo = new UndoManager();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {

            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
            }
        });

        return undo;
    }
    
    

    private void fileMenu() {
        // Create File Menu
        fileMenu = new JMenu("Diary Entry");
        fileMenu.setPreferredSize(new Dimension(100, 50));

        // Add file menu items
        newFile = new JMenuItem("New entry");
        newFile.addActionListener(this);
        newFile.setPreferredSize(new Dimension(100, 20));
        newFile.setEnabled(true);

        openFile = new JMenuItem("Open Entry");
        openFile.addActionListener(this);
        openFile.setPreferredSize(new Dimension(100, 20));
        openFile.setEnabled(true);

        saveFile = new JMenuItem("Save Entry");
        saveFile.addActionListener(this);
        saveFile.setPreferredSize(new Dimension(100, 20));
        saveFile.setEnabled(true);

        saveAsFile = new JMenuItem("Save Entry As");
        saveAsFile.addActionListener(this);
        saveAsFile.setPreferredSize(new Dimension(100, 20));
        saveAsFile.setEnabled(true);

        pageSetup = new JMenuItem("Page Setup");
        pageSetup.addActionListener(this);
        pageSetup.setPreferredSize(new Dimension(100, 20));
        pageSetup.setEnabled(true);

        printFile = new JMenuItem("Print Entry");
        printFile.addActionListener(this);
        printFile.setPreferredSize(new Dimension(100, 20));
        printFile.setEnabled(true);

        exit = new JMenuItem("Exit Diary");
        exit.addActionListener(this);
        exit.setPreferredSize(new Dimension(100, 20));
        exit.setEnabled(true);
        
        
        
        
        // Add items to menu
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        fileMenu.add(pageSetup);
        fileMenu.add(printFile);
        fileMenu.add(exit);
    }

    private void editMenu() {
        editMenu = new JMenu("Edit Diary");
        editMenu.setPreferredSize(new Dimension(100, 50));
        

        selectAll = new JMenuItem("Select All");
        selectAll.addActionListener(this);
        selectAll.setPreferredSize(new Dimension(100, 20));
        selectAll.setEnabled(true);
        undoEdit = new JMenuItem("Undo");
        undoEdit.addActionListener(this);
        undoEdit.setPreferredSize(new Dimension(100, 20));
        undoEdit.setEnabled(true);
        redoEdit = new JMenuItem("Redo");
        redoEdit.addActionListener(this);
        redoEdit.setPreferredSize(new Dimension(100, 20));
        redoEdit.setEnabled(true);
        cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        cut.setPreferredSize(new Dimension(100, 20));
        cut.setEnabled(true);
        copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        copy.setPreferredSize(new Dimension(100, 20));
        copy.setEnabled(true);
        paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        paste.setPreferredSize(new Dimension(100, 20));
        paste.setEnabled(true);
        editMenu.add(selectAll);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(cut);
        editMenu.add(undoEdit);
        editMenu.add(redoEdit);
        
        
    }
    private void saveFile(File filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(textArea.getText());
            writer.close();
            saved = true;
            window.setTitle("TextEditor@DN:  - " + filename.getName());
        } catch (IOException err) {
            err.printStackTrace();
        }
    }


    private void quickSave(File filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(textArea.getText());
            writer.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

 
    private void openingFiles(File filename) {
        try {
            openedFile = filename;
            FileReader reader = new FileReader(filename);
            textArea.read(reader, null);
            opened = true;
            window.setTitle("TextEditor@DN: " + filename.getName());
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == newFile) {
            new Diary();
        } else if(event.getSource() == openFile) {
            JFileChooser open = new JFileChooser();
            open.showOpenDialog(null);
            File file = open.getSelectedFile();                
            openingFiles(file);
        } else if(event.getSource() == saveFile) {
            JFileChooser save = new JFileChooser();
            File filename = save.getSelectedFile();
            if(opened == false && saved == false) {
                save.showSaveDialog(null);
                int confirmationResult;
                if(filename.exists()) {
                    confirmationResult = JOptionPane.showConfirmDialog(saveFile, "Replace existing file?");
                    if(confirmationResult == JOptionPane.YES_OPTION) {
                        saveFile(filename);                        
                    }
                } else {
                    saveFile(filename);
                }
            } else {
                quickSave(openedFile);
            }
        } else if(event.getSource() == saveAsFile) {
            JFileChooser saveAs = new JFileChooser();
            saveAs.showSaveDialog(null);
            File filename = saveAs.getSelectedFile();
            int confirmationResult;
            if(filename.exists()) {
                confirmationResult = JOptionPane.showConfirmDialog(saveAsFile, "Replace existing file?");
                if(confirmationResult == JOptionPane.YES_OPTION) {
                    saveFile(filename);                        
                }
            } else {
                saveFile(filename);
            }
        } else if(event.getSource() == pageSetup) {
            job = PrinterJob.getPrinterJob();
            format = job.pageDialog(job.defaultPage());    
        } else if(event.getSource() == printFile) {
            job = PrinterJob.getPrinterJob();
            if(job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException err) {
                    err.printStackTrace();
                }
            }
        } else if(event.getSource() == exit) {
            System.exit(0);
        } else if(event.getSource() == undoEdit) {
            try {
                undo.undo();
            } catch(CannotUndoException cu) {
                cu.printStackTrace();
            }
        } else if(event.getSource() == redoEdit) {
            try {
                undo.redo();
            } catch(CannotUndoException cur) {
                cur.printStackTrace();
            }
        } else if(event.getSource() == selectAll) {
            textArea.selectAll();
        }  else if(event.getSource() == copy) {
            textArea.copy();
        } else if(event.getSource() == paste) {
            textArea.paste();
        } else if(event.getSource() == cut) {
            textArea.cut();
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea text) {
        textArea = text;
    }
}