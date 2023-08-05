# Compiler-Simulator

Compiler Construction Java Simulation
This repository contains a Java simulation of sequential steps for building a compiler. The compiler construction process is modeled using ANTLR (ANother Tool for Language Recognition) and other supporting Java classes.

Contents
The repository includes the following files:

ANTLRLexicalAnalysis.g4: This file represents the lexer grammar written in ANTLR's grammar format. It defines the lexical analysis rules to tokenize the input source code.

ANTLRParsing.g4: This file contains the parser grammar written in ANTLR's grammar format. It defines the syntax rules to parse the tokenized input source code.

CfgEpsUnitElim.java: This Java class implements the elimination of epsilon (ε) productions from the context-free grammar. Epsilon productions are non-terminal rules that can generate an empty string.

CfgFirstFollow.java: This Java class calculates the first and follow sets for the non-terminal symbols in the context-free grammar.

CfgLeftRecElim.java: This Java class handles left-recursion elimination from the context-free grammar. Left recursion can lead to infinite loops in the parsing process.

CfgLl1Parser.java: This Java class constructs an LL(1) parser using the calculated first and follow sets to perform top-down parsing for the given grammar.

FallbackDfa.java: This Java class represents a fallback DFA (Deterministic Finite Automaton) implementation to support efficient parsing.

NfaToDfa.java: This Java class converts a Non-deterministic Finite Automaton (NFA) to a Deterministic Finite Automaton (DFA) to improve parsing performance.

RegExToNfa.java: This Java class converts a Regular Expression (RegEx) into an NFA to facilitate pattern matching in the lexical analysis phase.

Task8Lexer.java: This Java class implements the Lexer, which is responsible for tokenizing the input source code based on the lexer grammar (ANTLRLexicalAnalysis.g4).

How to Use
To use this compiler construction simulation, follow the steps below:

Clone or download the repository to your local machine.

Make sure you have ANTLR installed and set up properly in your development environment.

Run the ANTLR tool on the ANTLRLexicalAnalysis.g4 and ANTLRParsing.g4 files to generate lexer and parser classes.

Compile all the Java files in the repository using a Java compiler.

Execute the main class, CfgLl1Parser.java, to start the simulation.

Note
This simulation is intended for educational purposes and to demonstrate the step-by-step construction of a compiler. It may not be a complete and production-ready compiler implementation.

Feel free to explore, modify, and enhance the code to suit your learning or project requirements.

License
[Include the appropriate license information here. If you're using an open-source license, link to the full license file in the repository.]

Acknowledgments
[Optional: Acknowledge any external resources or contributors that inspired or assisted in the development of this simulation.]

[You can include any additional sections or information that you think would be relevant to the users of this repository.]
