import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GoLPanel extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 4012667849953579057L;

	GoLCell[][] cells = new GoLCell[100][100];
	JToggleButton startStopButton = new JToggleButton("Start");
	JButton resetButton = new JButton("Clear");
	JButton nextStepButton = new JButton("Next step");
	JLabel stepsLabel = new JLabel("0");
	int steps = 0;
	
	public GoLPanel() {
		setSize(1000, 1000);
		
		startStopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(startStopButton.isSelected())
					startStopButton.setText("Stop");
				else
					startStopButton.setText("Start");
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < 100; i++) 
					for(int j = 0; j < 100; j++)
						cells[i][j].isAlive = 0;
				
				steps = 0;
				stepsLabel.setText("" + steps);
				if(startStopButton.isSelected()) {startStopButton.doClick();}
				repaint();
			}				
		});
		
		nextStepButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cells = cells[0][0].nextGen(cells);
				steps++;
				stepsLabel.setText("" + steps);
			}
		});
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.black);
		
		//grid
		
		for(int i = 0; i < 100; i++) {
			g2.drawLine(i*10, 0, i*10, 1000);
			g2.drawLine(0, i*10, 1000, i*10);
		}
		
		//cells
		
		for(int i = 0; i < 100; i++) 
			for(int j = 0; j < 100; j++) {
				if(cells[i][j].isAlive == 1)
					g2.fillRect(cells[i][j].x, cells[i][j].y, 10, 10);
			}
		
		//applying rules
		
		if(startStopButton.isSelected()) {
			cells = new GoLCell(1,1).nextGen(cells);
			steps++;
			stepsLabel.setText("" + steps);
		}
		try {Thread.sleep(30);} catch (InterruptedException e) {e.printStackTrace();}

		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		/*if(cells[e.getX()/10][e.getY()/10].isAlive != 1)
			cells[e.getX()/10][e.getY()/10].isAlive = 1;
		else
			cells[e.getX()/10][e.getY()/10].isAlive = 0;
	*/}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		if(cells[e.getX()/10][e.getY()/10].isAlive != 1 && e.getX() < getWidth())
			cells[e.getX()/10][e.getY()/10].isAlive = 1;
		else
			cells[e.getX()/10][e.getY()/10].isAlive = 0;
	}

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) {
		if(cells[e.getX()/10][e.getY()/10].isAlive == 0 && e.getX() < getWidth()) {
			cells[e.getX()/10][e.getY()/10].isAlive = 1;
			repaint();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) { }
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		JPanel buttonsPanel = new JPanel();
		
		f.setSize(1103, 1035);
		f.setLayout(new BorderLayout());
		f.setResizable(false);
		GoLPanel p = new GoLPanel();
		for(int i = 0; i < 100; i++) 
			for(int j = 0; j < 100; j++)
				p.cells[i][j] = new GoLCell(i * 10,j * 10);
		//p.cells[0][20].isAlive = true;
		f.add(p, BorderLayout.CENTER);
		buttonsPanel.setLayout(new GridLayout(20,1));
		buttonsPanel.add(p.startStopButton);
		buttonsPanel.add(p.nextStepButton);
		buttonsPanel.add(p.resetButton);
		buttonsPanel.add(new JLabel("Number of steps:"));
		buttonsPanel.add(p.stepsLabel);
		f.add(buttonsPanel, BorderLayout.WEST);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	
}
