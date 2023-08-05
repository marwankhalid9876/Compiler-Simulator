# Compiler-Simulator

## Compiler Construction Java Simulation
This repository contains a Java simulation of sequential steps for building a compiler. The compiler construction process is modeled using ANTLR (ANother Tool for Language Recognition) and other supporting Java classes.

## Contents
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




