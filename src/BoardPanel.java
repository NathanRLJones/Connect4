/**
 * Implements BoardPanel class
 * 
 * @author      Alen Bou-Haidar z5019028
 *              Nathan Jones z5021296
 *              Lawrence z5018371
 *              Nicholas Yuwono z5016198
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.image.BufferedImage;
import java.awt.GradientPaint;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


/**
 * The Class BoardPanel.
 */
public class BoardPanel extends JPanel implements MouseMotionListener,
                                                  MouseListener,
                                                  ComponentListener,
                                                  AnimationListener {

    /** The list of actions queue. */
    private LinkedList<BoardAction> actions;    // Actions queue
    
    /** The board listener. */
    private BoardListener listener;             // Board listener
    
    /** The animation controller. */
    private Animation animation;                // Animation controller
    
    /** The board state. */
    private Board board;                        // Board state
    
    /** The token to be placed. */
    private Token input;                        // Token to be slotted
    
    /** The coordinate of input x. */
    private int inputX;                         // X coordinate of input
    
    /** The number of cols. */
    private int cols;                           // Number of columns
    
    /** The number of rows. */
    private int rows;                           // Number of rows
    
    /** The token size pixels. */
    private int tokenSize;                      // Token size pixels
    
    /** The width of the board. */
    private int width;                          // Board width pixel
    
    /** The height of the board. */
    private int height;                         // Board height pixels
    
    /** The x coordinate of the board. */
    private int x;                              // Board X coordinate
    
    /** The y coordinate of the board. */
    private int y;                              // Board Y coordinate
    
    /** The highlight of col1. */
    private int hlCol1;                         // Hightlight col1
    
    /** The highlight of row1. */
    private int hlRow1;                         // Hightlight row1
    
    /** The highlight of col2. */
    private int hlCol2;                         // Hightlight col2
    
    /** The highlight of row2. */
    private int hlRow2;                         // Hightlight row2
    
    /** The number of tokens to win. */
    private int tokensToWin;					// Number of winning tokens
    
    /** The visible highlight. */
    private boolean hasHighlight;               // Hightlight visible
    
    /** The visible hint. */
    private boolean hasHint;					// Hinted Token visible
    
    /** The coordinate of the hint . */
    private int hintCol, hintRow;				// Coordinates of hint token
    
    /** The hint token. */
    private Token hintToken;
    
	/**
	 * Constructor for a panel.
	 *
	 * @param listener the listener
	 */
	public BoardPanel(BoardListener listener) {
        super();
        setOpaque(true);
        setBackground(Color.WHITE);
        this.listener = listener;
        animation = new Animation(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        addComponentListener(this);
        setBoardSize(7, 6);
    }

    /**
     * Setup the initial board size.
     *
     * @param width width of the board
     * @param height height of the board
     */
    public void setBoardSize(int width, int height) {
        animation.stop();
        hasHighlight = false;
        hasHint = false;
        actions = new LinkedList<BoardAction>();
        input = null;
        board = new Board(width, height);
        calculateMetrics();
        repaint();
    }

    /**
     * Set the default size of the panel.
     *
     * @return the preferred size
     */
	public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    /* (non-Javadoc)
     * @see AnimationListener#newFrame()
     */
    public void newFrame() {
        repaint();
    }

    /* (non-Javadoc)
     * @see AnimationListener#lastFrame()
     */
    public void lastFrame() {
        BoardAction action = actions.poll();
        String name = action.getName();
        int column = action.getColumn();
        Token token = action.getToken();

        if (name.equals("place")) {
            board.placeToken(column, token);
            repaint();
            listener.placedAnimationComplete();
        } else if (name.equals("remove")) {
           
        }
        
        if (!actions.isEmpty()) {
            action = actions.peek();
            if(action.getName().equals("remove"))
                board.removeToken(action.getColumn());
            animation.start();
        } else {
            listener.animationQueueEmptied();
        }

    }

	/**
	 * Method to paint the board on the screen.
	 *
	 * @param g the g
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2;          // 2D graphics 
        g2 = (Graphics2D) g;
        g2.translate(x,y);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        paintInputToken(g2);
        paintColumnHighlight(g2);
        paintPlacedTokens(g2);
        paintActionToken(g2);
        paintBoard(g2);

    }

    /**
     * Calculate metrics.
     */
    public void calculateMetrics() {
        cols = board.getWidth();
        rows = board.getHeight();
        tokenSize = Math.min(getWidth()/cols, getHeight()/(rows+1));
        width = tokenSize * cols;
        height = tokenSize * rows;
        x = (getWidth() - width)/2;
        y = ((getHeight() - height - tokenSize)/2) + tokenSize;
    }

    /**
     * Gets the column number.
     *
     * @return the column number
     */
    public int getColumnNumber() {
        int column = -1;        // selected column
        int relX = inputX - x;  // calculate relative input x coordinate
        if (relX > 0 && relX <  width)
            column = relX / tokenSize;
        return column;
    }

    /**
     * Place token on the board.
     *
     * @param column column selected by player
     * @param token token to be placed on the board
     */
    public void placeToken(int column, Token token) {
    	hasHint = false;
        actions.add(new BoardAction("place", column, token));
        if (actions.size() == 1)
            animation.start();
    }

    /**
     * Place a hint on the board.
     *
     * @param column column selected by player
     * @param token token to be placed on the board
     */
    public void placeHint(int column, Token token) {
    	//actions.add(new BoardAction("hint"))
    	hasHint = true;
    	hintCol = column;
    	hintRow = board.getColumnLevel(column);
    	hintToken = token;
        repaint();
    }

    /**
     * Remove token from the board.
     *
     * @param column column selected by player
     * @param token token to be placed on the board
     */
    public void removeToken(int column, Token token) {
        hasHint = false;
    	actions.add(new BoardAction("remove", column, token));
        if (actions.size() == 1) {
        	board.removeToken(column);
            animation.start();
        }
    }

    /**
     * Highlight connected.
     *
     * @param col1 the col1
     * @param row1 the row1
     * @param col2 the col2
     * @param row2 the row2
     * @param numTokens the num tokens
     */
    public void highlightConnected(int col1, int row1, 
                                   int col2, int row2, int numTokens) {

        hlCol1 = col1;
        hlRow1 = row1;
        hlCol2 = col2;
        hlRow2 = row2;
        actions.add(new BoardAction("highlight", 0, null));
        tokensToWin = numTokens;
        hasHighlight = true;
    }


    /**
     * Sets the input.
     *
     * @param token the new input
     */
    public void setInput(Token token) {
        input = token;
    }

    /**
     * Paint the board on screen.
     *
     * @param g2 graphic 2d object
     */
    private void paintBoard(Graphics2D g2) {
        BufferedImage bufferedImage;
        AlphaComposite ac;
        Graphics2D bg2;         // buffered 2D graphics
        Point temp;
        bufferedImage = new BufferedImage(width, height, 
                                          BufferedImage.TYPE_INT_ARGB);
        bg2 = bufferedImage.createGraphics();
        bg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        

        Color c1 = new Color(30,75,143);
        Color c2 = new Color(15,29,74);

        GradientPaint gp1 = new GradientPaint(0, 0, c1, 0, height, c2 , false);
        bg2.setPaint(gp1);
        bg2.fillRoundRect(0, 0, width, height, 20, 20);
        ac = AlphaComposite.getInstance(AlphaComposite.CLEAR, 1.0f);
        bg2.setComposite(ac);
        temp = new Point();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {                
                temp.x = i*tokenSize;
                temp.y = j*tokenSize;
                bg2.fill(getTokenShape(temp.x, temp.y, 0.15));
            }
        }
        g2.drawImage(bufferedImage, null, 0, 0);
    }

    /**
     * Paint the token placed on the board.
     *
     * @param g2 graphic 2d object
     */
    private void paintPlacedTokens(Graphics2D g2) {
        Token token;
        Point temp;
        int level;
        float alpha;
        int value;
        AlphaComposite alcom ;

        if (hasHighlight) {


            if (!actions.isEmpty() && 
                actions.peek().getName().equals("highlight")) {
                value = animation.easeLinear (1000, 200);
                alpha = (float)(value / 1000.0);
            } else {
                value = animation.easeLinear (1000, 200);
                alpha = (float)(value / 1000.0);
            }

            alcom = AlphaComposite.getInstance(
                                        AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(alcom);
        }

        temp = new Point();
        for (int i = 0; i < cols; i++) {
            level = board.getColumnLevel(i);
            for (int j = 0; j < level; j++) {
                token = board.getToken(i, j);
                temp.x = i*tokenSize;
                temp.y = height - (j+1)*tokenSize;
                paintToken(g2, token, temp.x, temp.y);
            }
        }
        
        if (hasHint && !hasHighlight) {
        	alpha = 0.5f;
        	alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
        	
        	g2.setComposite(alcom);
        	
        	temp.x = hintCol * tokenSize;
        	temp.y = height - (hintRow+1)*tokenSize;
        	paintToken(g2, hintToken, temp.x, temp.y);
        	
        	alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1f);
        	
        	g2.setComposite(alcom);
        }

        if (hasHighlight) {
            alpha = 1;
            alcom = AlphaComposite.getInstance(
                                        AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(alcom);
            int dcol = (hlCol2 - hlCol1 )/(tokensToWin-1);
            int drow = (hlRow2 - hlRow1 )/(tokensToWin-1);
            int col =  hlCol1;
            int row = hlRow1;
            for (int i = 0; i < tokensToWin; i++) {
                temp.x = col*tokenSize;
                temp.y = height - (row+1)*tokenSize;
                token = board.getToken(col, row);
                paintToken(g2, token, temp.x, temp.y);
                col += dcol;
                row += drow;
            }
        }
    }


    /**
     * Paint input token.
     *
     * @param g2 the g2
     */
    private void paintInputToken(Graphics2D g2) {
        if (input != null && actions.isEmpty())
            paintToken(g2, input, inputX-x-(tokenSize/2), -tokenSize);
    }

    /**
     * Highlight selected column.
     *
     * @param g2 graphic 2d object
     */
    private void paintColumnHighlight(Graphics2D g2) {
        int col = getColumnNumber();
        if (input != null && actions.isEmpty() && col >= 0) {
            g2.setPaint(new Color(209, 240, 255));
            g2.fillRoundRect(col*tokenSize, 0, tokenSize, 
                             rows*tokenSize, 20, 20);
        }
    }

    /**
     * Paint action token.
     *
     * @param g2 the g2
     */
    private void paintActionToken(Graphics2D g2) {
        BoardAction action;     // A polled action to draw
        Token token;            // Token
        Point temp;             // Coordinate
        int level;              // Column level
        int column;             // Column

        temp = new Point();
        if (!actions.isEmpty()) {
            action = actions.peek();
            token = action.getToken();
            column = action.getColumn();
            level = board.getColumnLevel(column) + 1;
            temp.x = column*tokenSize;
            temp.y = (rows - level) * tokenSize;
            if (action.getName().equals("place")) {
                temp.y = animation.easeOutBounce(-tokenSize, temp.y);
                paintToken(g2, token, temp.x, temp.y);
            } else if (action.getName().equals("remove")) {
                temp.y = animation.easeLinear(temp.y, -tokenSize*2);
                paintToken(g2, token, temp.x, temp.y);
            }
        }
    }

    /**
     * Set the colour of the tokens.
     *
     * @param g2 graphic 2d object
     * @param token selected token
     * @param x x size of the token
     * @param y y size of the token
     */
    private void paintToken(Graphics2D g2, Token token, int x, int y) {
        g2.setColor(token.getColor().darker());
        g2.fill(getTokenShape(x, y, 0.15));
        g2.setColor(token.getColor());
        g2.fill(getTokenShape(x, y, 0.20));
    }

    /**
     * Get the token shape.
     *
     * @param x the x
     * @param y the y
     * @param pad the pad
     * @return the token shape
     */
    private Ellipse2D getTokenShape(int x, int y, double pad) {
        Ellipse2D circle = new Ellipse2D.Double();
        int diff = (int)(tokenSize * pad);
        int size = tokenSize - diff*2;
        circle.setFrame(x+diff, y+diff, size, size);
        return circle;
    } 

    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
     */
    public void componentResized(ComponentEvent e) {
    	calculateMetrics();
    	repaint();
    };
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        inputX = e.getX();
        repaint();
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        // non interactive state
        if (!actions.isEmpty() || input == null) 
            return;

        // interactive state
        int column = getColumnNumber();
        if (board.isValidColumn(column) && !board.isColumnFull(column)){
            input = null;
            listener.columnSelected(column);
        }
    };

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
     */
    public void componentHidden(ComponentEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
     */
    public void componentMoved(ComponentEvent e) {};
    
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
     */
    public void componentShown(ComponentEvent e) {};
}
