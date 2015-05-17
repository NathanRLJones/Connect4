
public class BoardAction {

    private String name;
    private Token token;
    private int column;

    public BoardAction(String name, int column, Token token) {
        this.name = name;
        this.token = token;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }

    public int getColumn() {
        return column;
    }
}
