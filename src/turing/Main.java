package turing;

import turing.machine.TuringMachine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TuringMachine machine = TuringMachine.fromFile("/home/khais/src/java/turing/src/rpli.tur");
        machine.loadTape("01~1|&", ' ');
        machine.start();
    }
}

