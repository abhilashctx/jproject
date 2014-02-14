package win.testpoly;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;


public class TestPolygons extends Applet {
	int width = 500;
	int height = 500;
	Vector<Point> v = new Vector<Point>();
	int x1, y1, x2=0, y2=0;
	Point testPoint;
	
	public void init() {
		setBackground(Color.black);
		setSize(width, height);
		setVisible(true);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();
				Point point = new Point(x1,y1);
				if(e.getButton()==MouseEvent.BUTTON1) {
					System.out.println("left click");
					v.add(point);
					repaint();
					x2 = x1; y2 = y1;
				} else if(e.getButton()==MouseEvent.BUTTON3) {
					System.out.println("right click");
					testPoint = point;
					repaint();
					boolean result = isPointInPolygon(testPoint);
					System.out.println("result = "+result);
				}
			}
		});
	}
	public void start() {
		
	}
	public void stop() {
		
	}
	public void paint(Graphics g) {
		if(x1>0 && y1>0 && x2>0 && y2>0) {
			g.setColor(Color.white);
			int xx1 = v.get(0).x;
			int yy1 = v.get(0).y;
			for(int x=1;x<v.size();++x) {
				g.drawLine(xx1, yy1, v.get(x).x, v.get(x).y);
				xx1 = v.get(x).x;
				yy1 = v.get(x).y;
			}
			g.drawLine(xx1, yy1, v.get(0).x, v.get(0).y);
		}
		if(testPoint!=null) {
			g.setColor(Color.red);
			g.drawOval(testPoint.x-2, testPoint.y-2, 4, 4);
		}
	}
	public boolean isPointInPolygon(Point p) {
		boolean result = false;
		int x = p.x;
		int y = p.y;
		for(int i = 0, j = v.size()-1; i < v.size(); j = i++) {
			if ( ((v.get(i).y>p.y) != (v.get(j).y>p.y)) &&
					(p.x < (v.get(j).x-v.get(i).x) * (p.y-v.get(i).y) / (v.get(j).y-v.get(i).y) + v.get(i).x) ) {
		       result = !result;
			}
		}
		return result;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
