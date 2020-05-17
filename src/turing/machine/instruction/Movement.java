package turing.machine.instruction;

@SuppressWarnings("unused")
public enum Movement {
    Left,
    Right,
    None,
    Halt,
    HaltSuccess,
    HaltFailure,
    HaltError,
    HaltNoMatchingRule,
}
