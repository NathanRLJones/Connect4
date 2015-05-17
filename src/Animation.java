import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Animation {

    private AnimationListener listener; // widget to repaint
    private int duration;               // current duration
    private int delay;                  // current delay
    private int time;                   // current time
    private Timer timer;                // timer of animation

    public Animation (AnimationListener al) {
        listener = al;
        duration = 500;
        delay = 40;
        time = 0;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time += delay;
                time = Math.min(time, duration);
                listener.newFrame();
                if (time == duration) {
                    timer.stop();
                    listener.lastFrame();
                }
            }
        });
        timer.setRepeats(true);
    }

    public void setDuration(int duration) {
        this.duration = Math.max(1, duration);
    }

    public void start() {
        time = 0;
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

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

    public int easeLinear (int start, int end) {
        double p = (double)time/(double)duration;
        int change = end - start ;
        int current = (int)(start + change*p);
        return current;
    }

}
