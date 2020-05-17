package turing.machine.instruction;

import java.util.List;

public class State {
    private final List<Rule> rules;
    private final String name;

    public State(List<Rule> rules, String name) {
        this.rules = rules;
        this.name = name;
    }

    public Rule getRule(char symbol) {
        for (Rule rule : this.rules) {
            if (rule.matches(symbol)) {
                return rule;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<Rule> getRules() {
        return rules;
    }
}

