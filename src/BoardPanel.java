import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardPanel extends JPanel implements ActionListener,
                                                  MouseMotionListener {
	private int mouseX;
	private int mouseY;
	
	private int cols;
	private int rows;

    private int duration;
    private int delay;
    private int time;

    private String action;
    private Color actionColor;
    private int actionColumn;
    private Timer timer;
    private Color currentColor;

    private  int pad;
    private  int h;
    private  int w;
    private  int x;
    private  int y;
    private  int squareSize;
    private  int boardWidth;
    private  int boardHeight;

    private ArrayList<ArrayList<Color>> board;
	/**
	 * Constructor for a soduku panel
	 */
	public BoardPanel(int col, int row) {
        super();
        this.setOpaque(true);
        this.setBackground(Color.WHITE);

    	mouseX = 0;
    	mouseY = 0;
        duration = 500;
        delay = 40;
        time = 0;
        
        cols = col;
        rows = row;

        action = "";
        actionColor = Color.YELLOW;
        actionColumn = 0;
        currentColor = null;
        
        board = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
        	board.add(new ArrayList<Color>());
        } 

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                time += delay;
                time = Math.min(time, duration);
                repaint();  // Refresh the JFrame, callback paintComponent()
            }
        });
        addComponentListener(new ComponentAdapter(){
        	public void componentResized(ComponentEvent e) {
                pad = 20;
                h = getHeight();
                w = getWidth();
                
                squareSize = (w-pad*2)/cols;
                squareSize = Math.min(squareSize, (h-pad*2)/(rows+1));
                boardWidth = squareSize * cols;
                boardHeight = squareSize * rows;
                x = (w - boardWidth)/2;
                
                y = (h - boardHeight - squareSize)/2;
                y += squareSize;

        	}
        	
        });
        
        timer.setRepeats(true);
        addMouseMotionListener(this);
    }
	
    /**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(460,460);
    }

	/**
	 * Method to paint the board on the screen
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        Ellipse2D token;
        ArrayList<Color> column;

        // convert the graphics component into graphics 2D
        Graphics2D g2 = (Graphics2D) g;
        token = new Ellipse2D.Double();
        // g2.setRenderingHints(Graphics2D.ANTIALIASING,Graphics2D.ANTIALIAS_ON);
        		
        for (int i = 0; i < rows + 1; i++) {
        	g2.setStroke(new BasicStroke(1));
        	g2.drawLine(x, y + i*squareSize, x + boardWidth, y + i*squareSize);
        	
        }
        for (int i = 0; i < cols + 1; i++) {
        	g2.setStroke(new BasicStroke(1));
        	g2.drawLine(x + i*squareSize, y , x + i*squareSize, y + boardHeight);
        	
        }
        //Point pt = this.getMousePosition();
        // draw discs
        for (int i = 0; i < cols; i++) {
            column = board.get(i);
            for (int j = 0; j < column.size(); j++) {

                token.setFrame(x + i*squareSize,
                            y + boardHeight - j*squareSize - squareSize,
                            squareSize,
                            squareSize);
                g2.setColor(column.get(j));

                g2.fill(token);
            }
            
        }
        int indicatorX = x;
        if (currentColor != null) {
                indicatorX = mouseX - (squareSize/2);
                token.setFrame(indicatorX,
                            y - squareSize,
                            squareSize,
                            squareSize);
                g2.setColor(currentColor);
                g2.fill(token);
        }

        // paint action
        if (action.equals("add")) {        
            column = board.get(actionColumn);
            int start = y - squareSize;
            int end = y + boardHeight - column.size()*squareSize - squareSize;
            int current = easeOutBounce(start, end);

            token.setFrame(x + actionColumn*squareSize, current,
                        squareSize,
                        squareSize);
            g2.setColor(actionColor);
            g2.fill(token);        

            if (time == duration) {
                time = 0;
                timer.stop();
                board.get(actionColumn).add(actionColor);
                action = "";
                repaint();
            }
        }


    }

    public boolean isProcessing() {
        return (!action.equals(""));
    }

    public int getColumnNumber(int x, int y) {
        int relX = x - this.x;
        int col = -1;
        if (relX > 0 && relX < boardWidth && currentColor != null) {
            col = relX / squareSize;
        }
        return col;
    }

    public void addToken(Token tok, int colNum) {
        currentColor = null;
        time = 0;
        action = "add";
        actionColumn = colNum;
        actionColor = tok.getOwner().getColor();
        timer.start();        
    }

    public void setPlayer(Player player) {
        currentColor = player.getColor();
    }

    public int easeOutBounce (int start, int end) {
        double p = (double)time/(double)duration;
        int change = end - start ;
        int current = start;
        
        if (p < 0.6) {
            current += change*(2.7778*p*p);
        } else if (p < 0.9) {
            p -= 0.75;
            current += change*(2.7778*p*p + 0.9375);
        } else {
            p -= 0.95;
            current += change*(2.7778*p*p + 0.9931);
        }
        return current;
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
    	mouseX = e.getX();
    	mouseY = e.getY();
    	repaint();
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// now repaint the board on the screen
		repaint();
	}  

}
