
/**
 * The Class BoardAction.
 * Represents an action to be performed on a board
 * example actions are place, and remove.
 */
public class BoardAction {

    /** The action name. */
    private String name;
    
    /** The action token. */
    private Token token;
    
    /** The action column. */
    private int column;

    /**
     * Instantiates a new board action.
     *
     * @param name the action name
     * @param column the action column
     * @param token the action token
     */
    public BoardAction(String name, int column, Token token) {
        this.name = name;
        this.token = token;
        this.column = column;
    }

    /**
     * Gets the action name.
     *
     * @return the action name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the action token.
     *
     * @return the action token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Gets the action column.
     *
     * @return the action column
     */
    public int getColumn() {
        return column;
    }
}
