package TP12;

import java.awt.EventQueue;

public class Launcher {
	
	public static void main(String[] args){
		EventQueue.invokeLater( () ->
		    {
		    	ImageEditeModel image = new ImageEditeModel("images/Geralt.png"); 
				ImageEditView view = new ImageEditView(image);
		});	
	}
}
