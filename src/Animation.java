/**
 * Implements Animation class
 * 
 * @author      Alen Bou-Haidar z5019028
 *
 * @version     0.1
 * @since       2015-05-29
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

/**
 * The Class Animation.
 * Contains mechanism in which to time animations
 * and query easing values for animation frames
 */
public class Animation {

    /** The animation listener. */
    private AnimationListener listener;
    
    /** The current duration. */
    private int duration;
    
    /** The current delay. */
    private int delay;
    
    /** The current time. */
    private int time;
    
    /** The timer of animation. */
    private Timer timer; 

    /**
     * Instantiates a new animation.
     *
     * @param al the animation listener
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

     * Sets the duration given the integer duration
     *
     * @param duration integer duration value
     */
    public void setDuration(int duration) {
        this.duration = Math.max(1, duration);
    }

    /**
     * Method to start the timer
     */
    public void start() {
        time = 0;
        timer.start();
    }

    /**
     * Method to stop the timer
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Method to ease out the bounce
     *
     * @param start integer start position
     * @param end integer end position
     * @return current integer position
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
     * Method to linearly change a value
     *
     * @param start integer start value
     * @param end integer end value
     * @return current integer value
     */
    public int easeLinear (int start, int end) {
        double p = (double)time/(double)duration;
        int change = end - start ;
        int current = (int)(start + change*p);
        return current;
    }

}
