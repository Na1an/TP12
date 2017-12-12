package TP12;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

public class ImageEditeModel {
	
	BufferedImage image;
	UndoManager undoManager = new UndoManager();
	
	public ImageEditeModel(String path){
		URL u = ImageEditeModel.class.getClassLoader().getResource(path);
		this.image = null;
		try {
			this.image = ImageIO.read(u);
		} catch (Exception e) {
			//e.printStackTrace();
			e.getMessage();
		}
	}

	public BufferedImage getImage(){
		return image;
	}
	
	public void savaCut(Rectangle z) {
		
		Coupe coupe = new Coupe(z.x,         z.y,                                         //pointDepart
				                z.x+z.width, z.y+z.height,                                //pointArrive
				                image.getSubimage(z.x, z.y, z.width, z.height));  //subimage
		coupe.doit();
		CutEdit cutEdit = new CutEdit(coupe);
		undoManager.addEdit(cutEdit);
	}
	
	
	public void fillzone(Rectangle z,int[][] pixels){
		for(int i=0;i<z.width;i++){
			for(int j=0;j<z.height;j++){
				image.setRGB(z.x+i, z.y+j, pixels[i][j]);
			}
		}
	}
	
	public void clearzone(Rectangle z){
		Color color = Color.white;
		int cl = color.getRGB();
		for(int i=0;i<z.width;i++){
			for(int j=0;j<z.height;j++){
				image.setRGB(z.x+i,z.y+j, cl);
			}
		}
	}
	
	class Coupe{
		Rectangle z;
		int [][] pixels;
		
		public Coupe(int xD,int yD,int xA,int yA,BufferedImage bImage){
			int w,h;
			if(xD<xA) w = xA - xD;
			else w = xD - xA;
			if(yD<yA) h = yA - yD;
			else h = yD - yA;
			
			this.z = new Rectangle(min(xA,xD),min(yA,yD),w,h);
			pixels = new int[z.width][z.height];
			
			for(int i=0;i<z.width;i++){
				for(int j=0;j<z.height;j++){
					pixels[i][j] = bImage.getRGB(i, j);
				}
			}
		}

		private int min(int a, int b) {
			if(a<b) return a;
			else return b;
		}
		void doit(){
			clearzone(z);
		}
		
		void undo(){
			fillzone(z, pixels);
		}
	}
	
	class CutEdit extends AbstractUndoableEdit{
		
		Coupe c;
		
		public CutEdit(Coupe cut) {
			c= cut;
		}
		
		public void undo() {
			c.undo();
		}
		
		public void redo() {
			c.doit();
		}
	}

}
