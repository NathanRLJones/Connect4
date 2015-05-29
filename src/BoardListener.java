
/**
 * The listener interface for receiving board events.
 * The class that is interested in processing a board
 * event implements this interface, and the object created
 * with that class is registered with a component. When
 * the board event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BoardEvent
 */
public interface BoardListener {
	
    /**
     * A Column was selected.
     *
     * @param column the index of the selected column
     */
    public void columnSelected(int column);
    
    /**
     * Placed animation is complete.
     */
    public void placedAnimationComplete();
    
    /**
     * Animation queue is emptied.
     */
    public void animationQueueEmptied();
    
}