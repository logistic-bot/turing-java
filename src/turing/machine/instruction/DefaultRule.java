package turing.machine.instruction;

import static turing.machine.instruction.Movement.HaltNoMatchingRule;

public class DefaultRule extends Rule {
    public DefaultRule() {
        super(' ', ' ', HaltNoMatchingRule, "HALT_NO_MATCHING_RULE");
    }
}
