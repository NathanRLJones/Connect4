/**
 * Implements AnimationListener class
 * 
 * @author      Alen Bou-Haidar z5019028
 *
 * @version     0.1
 * @since       2015-05-29
 */

/**
 * The listener interface for receiving animation events.
 * The class that is interested in processing a animation
 * event implements this interface, and the object created
 * with that class is registered with a component. When
 * the animation event occurs, that object's appropriate
 * method is invoked.
 */
public interface AnimationListener {

    /**
     * New frame event.
     * Occurs for each frame
     */
    public void newFrame();

    /**
     * Last frame event.
     * Occurs after the last frame
     */
    public void lastFrame();
}
