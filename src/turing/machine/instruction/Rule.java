package turing.machine.instruction;

public class Rule {
    private final Character currentCell;
    private final Character newCell;
    private final Movement movement;
    private final String newState;

    /**
     * @param currentCell The current cell of the tape that needs to be matched. If the current cell matches this value, the newCell will replace it.
     * @param newCell The cell with which the currentCell is replaced on the tape if the rule is executed.
     * @param movement Which way the tape should move. Can be Left, Right or None.
     * @param newState The state that the turing machine enters after the rule is executed.
     */
    public Rule(Character currentCell, Character newCell, Movement movement, String newState) {
        this.currentCell = currentCell;
        this.newCell = newCell;
        this.movement = movement;
        this.newState = newState;
    }

    public String toString() {
        return "" + currentCell + "-" + newCell + "-" + movement + "-" + newState + "";
    }

    public boolean matches(char symbol) {
        Boolean match = false;
        if (this.currentCell == symbol) {
            match = true;
        } if (this.currentCell == '*') { // '*' matches any character
            match = true;
        }
        return match;
    }

    public char getNewCell() {
        return newCell;
    }

    public Movement getMovement() {
        return movement;
    }

    public String getNewState() {
        return newState;
    }
}
