
// TODO: Auto-generated Javadoc
/**
 * The Class BoardAction.
 */
public class BoardAction {

    /** The name. */
    private String name;
    
    /** The token. */
    private Token token;
    
    /** The column. */
    private int column;

    /**
     * Instantiates a new board action.
     *
     * @param name the name
     * @param column the column
     * @param token the token
     */
    public BoardAction(String name, int column, Token token) {
        this.name = name;
        this.token = token;
        this.column = column;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }
}
