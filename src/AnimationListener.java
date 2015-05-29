
// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving animation events.
 * The class that is interested in processing a animation
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAnimationListener<code> method. When
 * the animation event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AnimationEvent
 */
public interface AnimationListener {

    /**
     * New frame.
     */
    public void newFrame();

    /**
     * Last frame.
     */
    public void lastFrame();
}
