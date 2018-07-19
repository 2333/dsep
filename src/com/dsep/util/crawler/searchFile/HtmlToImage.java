package com.dsep.util.crawler.searchFile;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HtmlToImage {  
	  
    protected static void generateOutput() throws Exception {  
          
        //load the webpage into the editor  
        //JEditorPane ed = new JEditorPane(new URL("http://www.google.com"));  
        JEditorPane ed = new JEditorPane(new URL("file:///D:/1021537817119.html"));  
        ed.setSize(1000,1000);  
  
        //create a new image  
        BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),  
                                                BufferedImage.TYPE_INT_ARGB);  
  
        //paint the editor onto the image  
        SwingUtilities.paintComponent(image.createGraphics(),   
                                      ed,   
                                      new JPanel(),   
                                      0, 0, image.getWidth(), image.getHeight());  
        //save the image to file  
        ImageIO.write((RenderedImage)image, "png", new File("D:/html.png"));  
    }  
    public static void main(String[] args) {  
        try {  
            generateOutput();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}  
