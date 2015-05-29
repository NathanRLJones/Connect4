/*
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * The Class DialogPanel.
 */
class DialogPanel extends JLayeredPane {

    /** The base background panel. */
    private Component basePanel;
    
    /** The dialog model pane. */
    private Component dialogPane;
    
    /** The glass pane to block mouse events. */
    private Component glassPane;

    
    /**
     * Instantiates a new dialog panel.
     */
    public DialogPanel () {
        super();
        setOpaque(false);
        addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e) {
                resizePanels();
            }
        });
        // create dark glass pane to block mouse events
        glassPane = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.8f));
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        glassPane.addMouseListener(new MouseAdapter(){});
        glassPane.addMouseMotionListener(new MouseMotionAdapter(){});
    }

    /**
     * Sets the base and dialog panels.
     *
     * @param basePanel the base panel
     * @param dialogPane the dialog panel
     */
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
        add(glassPane, JLayeredPane.MODAL_LAYER);
        dialogPane.setVisible(false);
        glassPane.setVisible(false);
        setPreferredSize(size);
    }

    /**
     * Resize the panels.
     */
    private void resizePanels() {
        basePanel.setSize(new Dimension(getWidth(), getHeight()));
        Dimension size = dialogPane.getPreferredSize();
        int x = (getWidth() - size.width)/2;
        int y = (getHeight() - size.height)/2;
        dialogPane.setLocation(x, y);
        dialogPane.setSize(size);

        glassPane.setLocation(0, 0);
        glassPane.setSize(getSize());
        validate();
    }
    
    /**
     * Show the dialog panel.
     */
    public void showDialog(){
    	dialogPane.setVisible(true);
        glassPane.setVisible(true);
    }
    
    /**
     * Hide the dialog panel.
     */
    public void hideDialog(){
    	dialogPane.setVisible(false);
        glassPane.setVisible(false);
    }
}

