package turing.machine;

import turing.machine.instruction.Movement;
import turing.machine.instruction.Rule;
import turing.machine.instruction.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TuringMachine {
    private Tape tape;
/*
    private List<State> states;
*/
    private Map<String, State> states;
    private String statePointer;
    private Movement lastMovement = null;
    private Rule lastRule;
    private State lastState;

    public TuringMachine(Map<String,State> states, Tape tape) {
        this.tape = tape;
        this.states = states;
    }

    public TuringMachine(Map<String, List<Rule>> stateNameToRule) {
        this.statePointer = "0";
        this.states = new HashMap<String, State>(); // prevents null pointer exception

        for (String key : stateNameToRule.keySet()) {
            List<Rule> ruleList = stateNameToRule.get(key);
            State state = new State(ruleList, key);
            this.states.put(key, state);
        }

        State test = this.states.get("0");
        if (test == null) {
            System.out.println("No starting state was found.");
            System.out.println("Hint: the machine starts in state 0.");
            throw new IllegalArgumentException("No starting state was found");
        }
    }

    public static TuringMachine fromFile(String path) throws IOException {
        String everything;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }

        String[] lines = everything.split("\n");
        Map<String, List<Rule>> stateNameToRule = new HashMap<String, List<Rule>>();

        for (String line : lines) {
            line = line.trim(); // trim whitespace

            if (line.equals("")) { // ignore empty lines
                continue;
            }

            if (line.charAt(0) == ';') { // ignore comments
                continue;
            }

            String[] tokens = line.split(" "); // split line by space

            // get current cell
            char currentCell;
            String currentCellString = tokens[1];
            if (currentCellString.length() > 1) {
                System.out.println("Current symbol needs to be a single character, but multiple characters were detected");
                System.out.println("Line: " + line);
                System.out.println("Detected current Symbol: " + currentCellString);
                throw new IllegalArgumentException("Current symbol needs to be a single character, but multiple characters were detected");
            } else {
                currentCell = currentCellString.charAt(0);
                if (currentCell == '_') {
                    currentCell = ' '; // '_' is used to denote a space
                }
            }

            // get new cell
            char newCell;
            String newCellString = tokens[2];
            if (newCellString.length() > 1) {
                System.out.println("New symbol needs to be a single character, but multiple characters were detected");
                System.out.println("Line: " + line);
                System.out.println("Detected new Symbol: " + currentCellString);
                throw new IllegalArgumentException("New symbol needs to be a single character, but multiple characters were detected");
            } else {
                newCell = newCellString.charAt(0);
                if (newCell == '_') {
                    newCell = ' '; // '_' is used to denote a space (' ')
                }
            }

            // get movement
            Movement movement;
            String movementString = tokens[3];

            switch (movementString) {
                case "l":
                    movement = Movement.Left;
                    break;
                case "r":
                    movement = Movement.Right;
                    break;
                case "*":
                    movement = Movement.None;
                    break;
                default:
                    System.out.print("Unknown movement ");
                    System.out.print(movementString);
                    System.out.print(" in line ");
                    System.out.println(line);
                    System.out.println("Setting movement to None.");
                    System.out.println("Hint: Use '*' to hide this warning.");

                    movement = Movement.None;
                    break;
            }

            String newState = tokens[4];

            String state = tokens[0];

            Rule rule = new Rule(currentCell, newCell, movement, newState);

            if (stateNameToRule.containsKey(state)) {
                List<Rule> stateRules = stateNameToRule.get(state);
                stateRules.add(rule);
            } else {
                List<Rule> stateRules = new ArrayList<>();
                stateRules.add(rule);
                stateNameToRule.put(state, stateRules);
            }
        }

        return new TuringMachine(stateNameToRule);
    }

    public void start() {
        do {
            this.step();
        } while (lastMovement == Movement.Left || lastMovement == Movement.Right || lastMovement == Movement.None);
        System.out.println(tape.format());
        System.out.println(lastMovement);
    }

    public void step() {
        try {
            // find rule
            char symbol = tape.read(); // get current cell
            Rule rule = findRule(symbol); // find rule

            lastRule = rule;
            lastState = states.get(statePointer);

            // write symbol
            tape.write(rule.getNewCell());

            // move tape
            Movement move = rule.getMovement();
            tape.move(move);
            this.lastMovement = move;

            // new state
            String pointer = rule.getNewState();
            if (!pointer.equals("*")) {
                this.statePointer = rule.getNewState();
            }
            if (pointer.equals("!")) {
                lastMovement = Movement.Halt;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch (NullPointerException e) {
            System.out.print("Last state: ");
            System.out.println(lastState.getName());

            System.out.print("Current state: ");
            System.out.println(statePointer);

            System.out.print("Last Rule: ");
            System.out.println(lastRule);

            System.out.println("Availible rules for current state:");
            for (Rule r : states.get(statePointer).getRules()) {
                System.out.println(r);
            }

            System.out.println();

            System.out.println("It may be that you reached a halt function, by copy-pasting a programm.");
            System.out.println("Please replace the next state with `!` if this is the case.");

            System.out.println();

            System.out.println("No matching rule. EXIT.");
            System.out.println("(Above: Last rule tracback)");

            System.exit(1);
        }
    }

    private Rule findRule(char symbol) {
        State state = states.get(statePointer);
        return state.getRule(symbol);
    }

    public void loadTape(String s, char base) {
        this.tape = new Tape(s, base);
    }
}
