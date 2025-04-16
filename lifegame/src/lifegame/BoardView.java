package lifegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardView extends JPanel implements  MouseListener, MouseMotionListener {
	
	private BoardModel model;
	private int prevX;
	private int prevY;
	private int alive;
	private JLabel label;
	
	public BoardView() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.prevX=-21;
		this.prevY=-21;
		this.alive = 0;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); // JPanel の描画処理（背景塗りつぶし）
		this.setBackground(Color.white);

		
		for(int i=0;i<=model.getCols();i++) {
			
				g.drawLine(40+60*i,40,40+60*i,40+60*model.getRows());
		}
		for(int i=0;i<=model.getRows();i++) {
				g.drawLine(40,40+60*i,40+60*model.getCols(),40+60*i);
			
		}
		
		this.alive=0;
		for(int i=0;i<model.getRows();i++) {
			for(int t=0;t<model.getCols();t++) {
				if(model.getCells(t,i)) {
					g.fillRect(40+60*t, 40+60*i, 60, 60);
					this.alive++;
				}
			}
		}
		label.setText("Alive: "+this.alive);
	}
	

	public void setBoard(BoardModel model) {
		this.model=model;
	}
	
	public void setLabel(JLabel label) {
		this.label=label;
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getX()>=40&&e.getX()<=40+60*model.getCols()&&e.getY()>=40&&e.getY()<=40+60*model.getRows()) {
			model.changeCellState((e.getX()-40)/60, (e.getY()-40)/60);
			this.repaint();
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
		
		if((int)(e.getX()-40)/60!=(int)(prevX-40)/60||(int)(e.getY()-40)/60!=(int)(prevY-40)/60) {
			if(e.getX()>=40&&e.getX()<40+60*model.getCols()&&e.getY()>=40&&e.getY()<40+60*model.getRows()) {
				model.changeCellState((e.getX()-40)/60, (e.getY()-40)/60);
				this.repaint();

		}
	}
		prevX=e.getX();
		prevY=e.getY();
}
	public void mouseMoved(MouseEvent e) {
	}
}
