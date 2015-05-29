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

    /**
<<<<<<< HEAD
     * Sets the duration of animation
     *
     * @param duration the new duration in ms
=======
     * Sets the duration given the integer duration
     *
     * @param duration integer duration value
>>>>>>> 17397c58e95ebc140ca7954e11132355163a64cb
     */
    public void setDuration(int duration) {
        this.duration = Math.max(1, duration);
    }

    /**
<<<<<<< HEAD
     * Start the animation
=======
     * Method to start the timer
>>>>>>> 17397c58e95ebc140ca7954e11132355163a64cb
     */
    public void start() {
        time = 0;
        timer.start();
    }

    /**
<<<<<<< HEAD
     * Stop the animation
=======
     * Method to stop the timer
>>>>>>> 17397c58e95ebc140ca7954e11132355163a64cb
     */
    public void stop() {
        timer.stop();
    }

    /**
<<<<<<< HEAD
     * Ease out bounce function.
     *
     * @param start the start value
     * @param end the end value
     * @return the current value based on animation time/duration
=======
     * Method to ease out the bounce
     *
     * @param start integer start position
     * @param end integer end position
     * @return current integer position
>>>>>>> 17397c58e95ebc140ca7954e11132355163a64cb
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
<<<<<<< HEAD
     * Ease linear function.
     *
     * @param start the start value
     * @param end the end value
     * @return the current value based on animation time/duration
=======
     * Method to linearly change a value
     *
     * @param start integer start value
     * @param end integer end value
     * @return current integer value
>>>>>>> 17397c58e95ebc140ca7954e11132355163a64cb
     */
    public int easeLinear (int start, int end) {
        double p = (double)time/(double)duration;
        int change = end - start ;
        int current = (int)(start + change*p);
        return current;
    }

}
