package cellautomata;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class CellUI extends Frame implements Runnable,MouseMotionListener,MouseListener{

	
	public CellUI() {
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		addMouseMotionListener(this);
		addMouseListener(this);
		
		setIgnoreRepaint(true);
		setSize(600,600);
		setVisible(true);
		
		cellLife = new CellLife();
		
		new Thread(this).start();
	}
	
	private CellLife cellLife;
	
	private BufferedImage bi ;
	public void run() {
		//int gen_count = 10000;
		if(bi == null){
			bi = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(600,600);
			//bi = new BufferedImage(600,600,BufferedImage.TYPE_INT_ARGB);
		}
		
		while(true){
			//long st = System.currentTimeMillis();

			//cellLife.nextGeneration();
			
			Graphics g = bi.getGraphics();
			
			//draw below
			g.setColor(Color.BLACK);
			g.fillRect(0,0,bi.getWidth(),bi.getHeight());
			//g.setColor(Color.WHITE);
			//cellLife.drawCells(bi,bi.getWidth(),bi.getHeight());
			cellLife.nextGeneration(bi);
			//draw above
			
			g.dispose();
	
			//draw to screen
			g = getGraphics();
			g.drawImage(bi,0,0,this);
			g.dispose();
			
			//if(--gen_count < 0) return;
			//System.out.println(" ,dt = "+(System.currentTimeMillis()-st));
			//try{Thread.sleep(1);}catch(Exception e){}
		}
	}
	
	public void update(Graphics g) {
		
	}
	
	int mx1,my1,mx2,my2;
	public void mouseDragged(MouseEvent e) {
		
		//if(mx2 == e.getX() && my2 == e.getY()) return;
		
		mx1 = mx2; my1 = my2;
		mx2 = e.getX();
		my2 = e.getY();
		cellLife.drawCellWithMouse(bi,mx1,my1,mx2,my2);
	}
	public void mouseMoved(MouseEvent e) {
		mx2 = e.getX();
		my2 = e.getY();
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		cellLife.setPause(true);
	}
	public void mouseReleased(MouseEvent e) {
		cellLife.setPause(false);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CellUI();
	}

}
