package turing.machine;

import turing.machine.instruction.Movement;

import java.text.MessageFormat;

import static turing.machine.instruction.Movement.Left;
import static turing.machine.instruction.Movement.Right;

public class Tape {
    private char current;
    private final char base;
    private String left;
    private String right;

    public  Tape() {
        this.current = ' ';
        this.base = ' ';
        this.left = "";
        this.right = "";
    }

    public  Tape(char base_init) {
        this.current = base_init;
        this.base = base_init;
        this.left = "";
        this.right = "";
    }

    public  Tape(String right_init) {
        this.current = right_init.charAt(0);
        this.base = ' ';
        this.left = "";
        this.right = right_init;
    }

    public  Tape(String right_init, char base_init) {
        this.current = base_init;
        this.base = base_init;
        this.left = "";
        this.right = right_init;
    }

    public  Tape(String left_init, char current_init, String right_init) {
        this.current = current_init;
        this.base = ' ';
        this.left = left_init;
        this.right = right_init;
    }

    public  Tape(String left_init, char current_init, String right_init, char base_init) {
        this.current = current_init;
        this.base = base_init;
        this.left = left_init;
        this.right = right_init;
    }

    public char read() {
        return current;
    }

    public void write(char cell) {
        if (cell == '*') {
            return;
        }
        current = cell;
    }

    public void move(Movement move) {
        System.out.println(this);

        if (move == Right) {
            this.moveRight();
        } else if (move == Left) {
            this.moveLeft();
        }

        System.out.println(this);
    }

    public String toString() {
        String leftCopy = left;
        String rightCopy = right;

/*
        leftCopy = leftCopy.replace("", " ");
        rightCopy = rightCopy.replace("", " ");
*/
        return MessageFormat.format("{0}<{1}>{2}", leftCopy, current, rightCopy);
    }

    private void moveLeft() {
        if (left.length() == 0) {
            left = Character.toString(base);
        }
        right = current + right;
        current = left.charAt(left.length() - 1);
        left = left.substring(0, left.length() - 1);
    }

    private void moveRight() {
        if (right.length() == 0) {
            right = Character.toString(base);
        }
        left = left + current;
        current = right.charAt(0);
        right = right.substring(1);
    }

    public String format() {
        String leftCopy = left;
        String rightCopy = right;
        return MessageFormat.format("{0}{1}{2}", leftCopy, current, rightCopy);
    }
}
