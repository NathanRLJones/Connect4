import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.util.*;
import javax.swing.*;


public class BoardPanel extends JPanel implements MouseMotionListener,
                                                  MouseListener,
                                                  ComponentListener,
                                                  AnimationListener {

    private LinkedList<BoardAction> actions;    // Actions queue
    private BoardListener listener;             // Board listener
    private Animation animation;                // Animation controller
    private Board board;                        // Board state
    private Token input;                        // Token to be slotted
    private int inputX;                         // X coordinate of input
    private int cols;                           // Number of columns
    private int rows;                           // Number of rows
    private int tokenSize;                      // Token size pixels
    private int width;                          // Board width pixel
    private int height;                         // Board height pixels
    private int x;                              // Board X coordinate
    private int y;                              // Board Y coordinate


	/**
	 * Constructor for a panel
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
	
    public void setBoardSize(int width, int height) {
        animation.stop();
        actions = new LinkedList<BoardAction>();
        input = null;
        board = new Board(width, height);
        calculateMetrics();
        repaint();
    }

    /**
     * Set the size of the panel
     */
	public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    public void newFrame() {
        repaint();
    }

    public void lastFrame() {
        BoardAction action = actions.poll();
        String name = action.getName();
        int column = action.getColumn();
        Token token = action.getToken();

        if (name.equals("place")) {
            board.placeToken(column, token);
        } else if (name.equals("remove")) {
            board.removeToken(column);
        }

        if (!actions.isEmpty())
            animation.start();

        repaint();
    }

	/**
	 * Method to paint the board on the screen
	 */
    public void paintComponent(Graphics g) {
        Graphics2D g2;          // 2D graphics context
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        paintBoardLines(g2);
        paintPlacedTokens(g2);
        paintInputToken(g2);
        paintActionToken(g2);
        g2.translate(-x, -y);

    }

    public void calculateMetrics() {
        cols = board.getWidth();
        rows = board.getHeight();
        tokenSize = Math.min(getWidth()/cols, getHeight()/(rows+1));
        width = tokenSize * cols;
        height = tokenSize * rows;
        x = (getWidth() - width)/2;
        y = ((getHeight() - height - tokenSize)/2) + tokenSize;
    }

    public int getColumnNumber() {
        int column = -1;        // selected column
        int relX = inputX - x;  // calculate relative input x coordinate
        if (relX > 0 && relX <  width)
            column = relX / tokenSize;
        return column;
    }

    public void placeToken(int column, Token token) {
        actions.add(new BoardAction("place", column, token));
        if (actions.size() == 1)
            animation.start();
    }

    public void removeToken(int column) {
        actions.add(new BoardAction("remove", column, null));
        if (actions.size() == 1)
            animation.start();
    }

    public void setInput(Token token) {
        input = token;
    }

    public void paintBoardLines(Graphics2D g2) {
        Point temp;
        temp = new Point();
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1));
               
        for (int i = 0; i < rows + 1; i++){
            temp.y = i*tokenSize;
            g2.drawLine(0, temp.y, width,  temp.y);
        }
        for (int i = 0; i < cols + 1; i++) {
            temp.x = i*tokenSize;
            g2.drawLine(temp.x, 0,  temp.x, height);
        }
    }

    public void paintPlacedTokens(Graphics2D g2) {
        Token token;
        Point temp;
        int level;
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
    }

    public void paintInputToken(Graphics2D g2) {
        if (input != null && actions.isEmpty())
            paintToken(g2, input, inputX-x-(tokenSize/2), -tokenSize);
    }

    public void paintActionToken(Graphics2D g2) {
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

    public void paintToken(Graphics2D g2, Token token, int x, int y) {
        Ellipse2D circle = new Ellipse2D.Double();
        int diff = (int)(tokenSize * 0.15);
        int size = tokenSize - diff*2;
        circle.setFrame(x+diff, y+diff, size, size);
        g2.setColor(token.getColor());
        g2.fill(circle);
    }

    public void mouseMoved(MouseEvent e) {
        inputX = e.getX();
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        // non interactive state
        if (!actions.isEmpty() || input == null) 
            return;

        // interactive state
        int column = getColumnNumber();    
        if (!board.isColumnFull(column))
            listener.columnSelected(column);
    };

    public void componentResized(ComponentEvent e) {
        calculateMetrics();
    };

    public void mouseDragged(MouseEvent e) {};
    public void mouseEntered(MouseEvent e) {};
    public void mouseExited(MouseEvent e) {};
    public void mousePressed(MouseEvent e) {};
    public void mouseReleased(MouseEvent e) {};
    public void componentHidden(ComponentEvent e) {};
    public void componentMoved(ComponentEvent e) {};
    public void componentShown(ComponentEvent e) {};
}
