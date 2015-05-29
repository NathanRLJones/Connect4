import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


class PlayerListPanel extends JLayeredPane 
                      implements MouseListener, 
                                 MouseMotionListener, 
                                 AnimationListener {

    private LinkedList<PlayerOptionsPanel> panels; // list of player panels
    private PlayerOptionsPanel dragPanel;          // currently dragged panel
    private int yAdjustment;                // cursor offset
    private int panelWidth;                 // player panel width
    private int panelHeight;                // player panel height
    private int dragIndex;                  // last index of drag panel
    private ArrayList<Color> colors;
    private Animation animation;            // animation info

    public PlayerListPanel () {
        super();
        panels = new LinkedList<PlayerOptionsPanel>();

        addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e) {
                resizePanels();
            }
        });
        addMouseListener(this);
        addMouseMotionListener(this);
        
        colors = new ArrayList<Color>();
        colors.add(new Color(255, 41, 31));     // red
        colors.add(new Color(255, 238, 6));     // yellow
        colors.add(new Color(123, 211, 8));     // green
        colors.add(new Color(45, 185, 255));    // blue

        addPlayerPanel(new PlayerOptionsPanel("Player 1", "Human", 
                                              colors.get(0)));
        addPlayerPanel(new PlayerOptionsPanel("Player 2", "Easy", 
                                              colors.get(1)));

        animation = new Animation(this);
        animation.setDuration(200);
    }


    public void setNumberOfPlayers(int number) {
        while (panels.size() < number) {
            addPlayerPanel(new PlayerOptionsPanel(
                                "Player " + panels.size(), 
                                "Human", colors.get(panels.size())));
        }
        while (panels.size() > number) {
            removePlayerPanel(colors.get(panels.size()-1));
        }
        repaint();
    }


    public void addPlayerPanel(PlayerOptionsPanel pp) {
        Dimension size;
        if (panels.isEmpty()) {
            size = pp.getPreferredSize();
            panelWidth = size.width;
            panelHeight = size.height;
        } 
        pp.setLocation(0, panels.size() * panelHeight);
        pp.setSize(pp.getPreferredSize());
        panels.add(pp);
        add(pp, JLayeredPane.DEFAULT_LAYER);
        size = new Dimension(panelWidth, panelHeight * 4);
        setPreferredSize(size);
        if (getParent() != null)
            getParent().doLayout();

    }

    public void removePlayerPanel(Color c) {
        PlayerOptionsPanel pp;
        Dimension size;
        for (int i=0; i < panels.size(); i++) {
            pp = panels.get(i);
            if (pp.getColor().equals(c)) {
                panels.remove(pp);
                remove(pp);
                size = new Dimension(panelWidth, panelHeight * 4);
                setPreferredSize(size);
                setupAnimation();
                break;
            }
        }
        if (getParent() != null)
            getParent().doLayout();

    }

    private void resizePanels() {
        PlayerOptionsPanel pp;
        for (int i = 0; i < panels.size(); i++) {
            pp = panels.get(i);
            pp.setSize(new Dimension(getWidth(), panelHeight));
        }
        validate();
    }

    public void newFrame() {
        PlayerOptionsPanel pp;
        int start;
        int end;
        int current;

        for (int i = 0; i < panels.size(); i++) {
            pp = panels.get(i);
            if (pp == dragPanel) 
                continue;
            start = pp.getStartY();
            end = i*panelHeight;
            current = animation.easeLinear(start, end);
            pp.setLocation(0, current);
        }
    }

    public void lastFrame() {};

    private void setupAnimation() {
        PlayerOptionsPanel pp;
        for (int i = 0; i < panels.size(); i++) {
            pp = panels.get(i);
            pp.setStartY(pp.getLocation().y);
        }
        animation.start();
    }
    
    public ArrayList<Player> getPlayers(){
    	ArrayList<Player> players = new ArrayList<Player>();
    	for (PlayerOptionsPanel po : panels)
    		players.add(po.getPlayer());
    	return players;
    }

    public void mousePressed(MouseEvent e) {
        Point location;
        int index;
        if (e.getY() > panels.size() * panelHeight)
            return;

        index = e.getY()/panelHeight;
        dragPanel = panels.get(index);
        location = dragPanel.getLocation();
        yAdjustment = location.y - e.getY();
        dragIndex = panels.indexOf(dragPanel);
        setLayer(dragPanel, JLayeredPane.DRAG_LAYER);
    }

    public void mouseDragged(MouseEvent e){
        int minY;   // minimum y for drag panel
        int maxY;   // maxinum y for drag panel
        int panelY; // actual y for drag panel
        int index;  // new index for drag panel

        if (dragPanel == null) return;

        minY = 0;
        maxY = (panels.size() - 1) * panelHeight;

        panelY = e.getY() + yAdjustment;
        panelY = Math.max(panelY, minY);
        panelY = Math.min(panelY, maxY);
        dragPanel.setLocation(0, panelY);

        index = (panelY + (panelHeight/2)) / panelHeight;
        if (dragIndex != index) {
            dragIndex = index;
            panels.remove(dragPanel);
            panels.add(dragIndex, dragPanel);
            setupAnimation();
        }

    }

    public void mouseReleased(MouseEvent e)
    {
        if (dragPanel == null) 
            return;
        setLayer(dragPanel, JLayeredPane.DEFAULT_LAYER);
        dragPanel = null;
        setupAnimation();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
