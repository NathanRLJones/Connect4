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
 * The Class Animation contains mechanism in which to time animations
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


    /**
     * Sets the duration of animation
     * 
     * @param duration the new duration in ms
     */
    public void setDuration(int duration) {
        this.duration = Math.max(1, duration);
    }

    /**
     * Method to start the timer for the animation
     */
    public void start() {
        time = 0;
        timer.start();
    }

    /**
     * Method to stop the timer for the animation
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Gives the current position for an ease out bounce animation
     *
     * @param start the start value
     * @param end the end value
     * @return the current value based on animation time/duration
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
     * Gives the current position for an ease linear animation
     *
     * @param start the start value
     * @param end the end value
     * @return the current value based on animation time/duration
     */
    public int easeLinear (int start, int end) {
        double p = (double)time/(double)duration;
        int change = end - start ;
        int current = (int)(start + change*p);
        return current;
    }

}
