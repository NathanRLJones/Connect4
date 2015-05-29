import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


class DialogPanel extends JLayeredPane {

    private Component basePanel;
    private Component dialogPane;
    private Component glassPane;

    
    public DialogPanel () {
        super();
        setOpaque(false);
        addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e) {
                resizePanels();
            }
        });

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

    private void resizePanels() {
        basePanel.setSize(new Dimension(getWidth(), getHeight()));
        Dimension size = dialogPane.getPreferredSize();
        int x = (getWidth() - size.width)/2;
        dialogPane.setLocation(x, 0);
        dialogPane.setSize(size);

        glassPane.setLocation(0, 0);
        glassPane.setSize(getSize());
        validate();
    }
    
    public void showDialog(){
    	dialogPane.setVisible(true);
        glassPane.setVisible(true);
    }
    
    public void hideDialog(){
    	dialogPane.setVisible(false);
        glassPane.setVisible(false);
    }
}

