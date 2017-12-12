package TP12;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import TP12.ImageEditView.ImagePane.Selection;


public class ImageEditView extends JFrame{
	
	Container contentPane;
	
	JButton cutButton;
	JButton undoButton;
	JButton redoButton;
	JMenuBar menuBar;
	
	ImagePane imagePane;
	ImageEditeModel model;
	
	public ImageEditView(ImageEditeModel imageEditeModel){
		//JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		contentPane = getContentPane();

		model = imageEditeModel;
		menuBar = new JMenuBar();
		cutButton = new JButton("Cut");
		//cutButton.setEnabled(false);
		cutButton.setBounds(20,20,50,30);
		cutButton.addActionListener((ActionEvent e) -> {		
			                                                model.savaCut(imagePane.selection.getRectangle());
			                                                imagePane.repaint();
		                                                });
		undoButton = new JButton("Undo");
		undoButton.setBounds(20,20,50,30);
		undoButton.addActionListener((ActionEvent e) -> {
			                                                model.undoManager.undo();
			                                                imagePane.repaint();
			                                            });
		redoButton = new JButton("Redo");
		redoButton.setBounds(20, 20, 50, 30);
		redoButton.addActionListener((ActionEvent e) -> {
			                                                model.undoManager.redo();
			                                                imagePane.repaint();
		                                                });
		imagePane = new ImagePane();
		
		this.setTitle("TP12 InterfaceEtGraphics");
		
		menuBar.add(cutButton);
		menuBar.add(redoButton);
		menuBar.add(undoButton);

		contentPane.add(menuBar);
		contentPane.add(imagePane);
		this.setJMenuBar(menuBar);
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	class ImagePane extends JPanel{
		
		Selection selection = new Selection();
		
		public ImagePane(){
			this.setPreferredSize(new Dimension(model.getImage().getWidth(),model.getImage().getHeight()));
			addMouseListener(selection);
			addMouseMotionListener(selection);
		}
		
		class Selection extends MouseAdapter implements MouseMotionListener{
			int xD,xA,yD,yA;
			int xx,yy;
			
			public Rectangle getRectangle(){
				int w,h;
				if(xD<xA) w = xA - xD;
				else w = xD - xA;
				if(yD<yA) h = yA - yD;
				else h = yD - yA;
				xx = min(xD, xA);
				yy = min(yD, yA);
				return new Rectangle(min(xA,xD),min(yA,yD),w,h);
			}
			
			private int min(int a, int b) {
				if(a<b) return a;
				else return b;
			}

			public void mousePressed(MouseEvent e){
				xD = e.getX();
				yD = e.getY();
				cutButton.setEnabled(false);
				imagePane.repaint();
			}
			
			public void mouseDragged(MouseEvent e){
				xA = e.getX();
				yA = e.getY();
				cutButton.setEnabled(true);
				imagePane.repaint();
			}
			
			public void mouseMoved(MouseEvent e){
				
			}
		}
		
		public void paintComponent(Graphics g){
			
			super.paintComponent(g);
			g.drawImage(model.getImage(),0,0,this);
			((Graphics2D) g).draw(selection.getRectangle());
			
		}
	}
}
