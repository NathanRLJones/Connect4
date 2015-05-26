import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


class DialogPanel extends JLayeredPane 
                      implements MouseListener, 
                                 MouseMotionListener, 
                                 AnimationListener {

    private Component basePanel;
    private Component dialogPane;
    private Animation animation;
    public DialogPanel () {
        super();
        setOpaque(false);
        addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e) {
                resizePanels();
            }
        });
        addMouseListener(this);
        addMouseMotionListener(this);
        animation = new Animation(this);
        animation.setDuration(150);
    }

    public void setPanels(Component basePanel, Component dialogPane) {
        this.basePanel = basePanel;
        this.dialogPane = dialogPane;        
        Dimension size = basePanel.getPreferredSize();
        Dimension dialogSize = dialogPane.getPreferredSize();
        size.width = Math.max(size.width, dialogSize.width + 40);
        size.height = Math.max(size.height, dialogSize.height + 40);
        basePanel.setLocation(0, 0);
        add(basePanel, JLayeredPane.DEFAULT_LAYER);
        add(dialogPane, JLayeredPane.MODAL_LAYER);    
        dialogPane.setVisible(false);
        setPreferredSize(size);
    }

    private void resizePanels() {
        basePanel.setSize(new Dimension(getWidth(), getHeight()));
        Dimension size = dialogPane.getPreferredSize();
        int x = (getWidth() - size.width)/2;
        dialogPane.setLocation(x, 0);
        dialogPane.setSize(size);
    }
    
    public void showDialog(){
    	dialogPane.setVisible(true);
    }
    
    public void hideDialog(){
    	dialogPane.setVisible(false);
    }

    public void newFrame() {}
    public void lastFrame() {};
    public void mousePressed(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}