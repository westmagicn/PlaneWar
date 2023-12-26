package cn.darren.PlaneWar;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

public class MyGameFrame extends Frame {
	Image bg = GameUtil.getImage("images/bg.jpg");
	Image planeImage = GameUtil.getImage("images/plane.png");
	 Explode explodion;
	 Date startTime = new Date();
	 Date endTime;
	 int period;
Plane plane = new Plane(planeImage,250,250);
Shell[] shells = new Shell[50];
	public void paint(Graphics g){
		Color c = g.getColor();
		g.drawImage(bg,0,0,null);
		plane.drawSelf(g);	
		for(int i=0;i<shells.length;i++){
		shells[i].draw(g);
		boolean collision = shells[i].getRect().intersects(plane.getRect());
		if(collision){
			plane.live=false;
			if(explodion == null){
			  explodion= new Explode(plane.x,plane.y);
				endTime = new Date();
				period=(int)((endTime.getTime()-startTime.getTime())/1000);
			};
			
			 explodion.draw(g);
		}
		if(!plane.live){
		g.setColor(Color.red);
		Font f = new Font("ËÎÌו",Font.BOLD,50);
		g.setFont(f);
		g.drawString("Time:"+period+" "+"second", (int)plane.x,(int)plane.y);
		}
      }
		g.setColor(c);
	}
	class PaintThread extends Thread{
		public void run(){
			while(true){
				repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		
	}	
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			plane.DelDirection(e);
		}
	}
	
	public  void  launchFrame(){
		this.setTitle("PlaneWar.by.darren");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 300);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new PaintThread().start();
		addKeyListener(new KeyMonitor());
		for(int i=0;i<shells.length;i++){
			shells[i]=new Shell();
		}
	}
	
	public static void main(String[] args) {
		MyGameFrame  f = new MyGameFrame();
		f.launchFrame();	
	}
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}
	}
