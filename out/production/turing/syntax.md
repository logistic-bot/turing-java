*Inspiration: http://morphett.info/turing/turing.html*

Syntax:
=======
 - Each line should contain one tuple of the form 
   '<current state> <current symbol> <new symbol> <direction> <new state>'.
 - You can use any number or word for <current state> and <new state>, eg. 10, a, state1. 
   State labels are case-sensitive.
 - You can use any character for <current symbol> and <new symbol>, or '_' to represent blank (space). 
   Symbols are case-sensitive.
 - <direction> should be 'l', 'r' or '*', denoting 'move left', 'move right' or 'do not move', respectively.
 - Anything after a ';' is a comment and is ignored.
 - The machine halts when it reaches any state starting with 'halt', eg. halt, halt-accept.
    
Also:
=====
 - '*' can be used as a wildcard in <current symbol> or <current state> to match any character or state.
 - '*' can be used in <new symbol> or <new state> to mean 'no change'.
 - The machine starts in state '0'
 - '!' can be used in <new state> to halt the machine.
