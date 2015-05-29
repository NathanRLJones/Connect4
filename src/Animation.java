import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Animation.
 */
public class Animation {

    /** The listener. */
    private AnimationListener listener; // widget to repaint
    
    /** The duration. */
    private int duration;               // current duration
    
    /** The delay. */
    private int delay;                  // current delay
    
    /** The time. */
    private int time;                   // current time
    
    /** The timer. */
    private Timer timer;                // timer of animation

    /**
     * Instantiates a new animation.
     *
     * @param al the al
     */
    public Animation (AnimationListener al) {
        listener = al;
        duration = 800;
        delay = 40;
        time = 0;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.newFrame();
                time += delay;
                if (time > duration) {
                    timer.stop();
                    listener.lastFrame();
                }

            }
        });
        timer.setRepeats(true);
    }

    /**
     * Sets the duration.
     *
     * @param duration the new duration
     */
    public void setDuration(int duration) {
        this.duration = Math.max(1, duration);
    }

    /**
     * Start.
     */
    public void start() {
        time = 0;
        timer.start();
    }

    /**
     * Stop.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Ease out bounce.
     *
     * @param start the start
     * @param end the end
     * @return the int
     */
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

    /**
     * Ease linear.
     *
     * @param start the start
     * @param end the end
     * @return the int
     */
    public int easeLinear (int start, int end) {
        double p = (double)time/(double)duration;
        int change = end - start ;
        int current = (int)(start + change*p);
        return current;
    }

}
