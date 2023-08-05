package csen1002.main.task7;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 07
 */

public class CfgLl1Parser {

	/**
	 * Constructs a Context Free Grammar
	 * 
	 * @param cfg A formatted string representation of the CFG, the First sets of
	 *            each right-hand side, and the Follow sets of each variable. The
	 *            string representation follows the one in the task description
	 */
	HashMap<String, String> parsingTable;
	HashMap<Character, ArrayList<String>> rules;
	HashMap<Character, ArrayList<String>> firsts;
	HashMap<Character, ArrayList<String>> follows;
	String[] terminals;
	public CfgLl1Parser(String input) {
		// TODO Auto-generated constructor stub
		parsingTable = new HashMap<>();
		rules = new HashMap<>();
		firsts = new HashMap<>();
		follows = new HashMap<>();

		String[] input_tokenized = input.split("#");
		String variablesWithSemicolons = input_tokenized[0];
		String terminalsWithSemicolons = input_tokenized[1];
		String rulesWithSemicolons = input_tokenized[2];
		String firstsWithSemicolons = input_tokenized[3];
		String followsWithSemicolons = input_tokenized[4];

		String[] variables = variablesWithSemicolons.split(";");
		terminals = (terminalsWithSemicolons + ";$").split(";");
		String[] rulesString = rulesWithSemicolons.split(";");
		String[] firstsString = firstsWithSemicolons.split(";");
		String[] followsString = followsWithSemicolons.split(";");

		//initialize firsts and follows and rules of variables
		for(int i=0; i<variables.length; i++)
		{
			firsts.put(variables[i].charAt(0), new ArrayList<>());
			follows.put(variables[i].charAt(0), new ArrayList<>());
			rules.put(variables[i].charAt(0), new ArrayList<>());
		}

		for(int i=0; i<terminals.length; i++) {
			ArrayList<String> terminalFirsts = new ArrayList<>();
			terminalFirsts.add(terminals[i]);
			firsts.put(terminals[i].charAt(0), terminalFirsts);
		}
		//fill rules of all variables
		for(int i=0; i<rulesString.length; i++)
		{
			char variable = rulesString[i].charAt(0);
			String rulesWithCommas = rulesString[i].substring(2);
			String[] rulesOfVariable = rulesWithCommas.split(",");
			for (int j=0; j<rulesOfVariable.length; j++)
				rules.get(variable).add(rulesOfVariable[j]);
		}

		//fill firsts of all variables
		for(int i=0; i<firstsString.length; i++)
		{
			char variable = firstsString[i].charAt(0);
			String firstsWithCommas = firstsString[i].substring(2);
			String[] firstsOfVariable = firstsWithCommas.split(",");
			for (int j=0; j<firstsOfVariable.length; j++)
			{
				String variableFirsts = firstsOfVariable[j];
				for(int k=0; k<variableFirsts.length(); k++)
					firsts.get(variable).add(variableFirsts.charAt(k)+"");
			}
		}
		//fill follows of all variables
		for(int i=0; i<followsString.length; i++)
		{
			char variable = followsString[i].charAt(0);
			String followsOfVariable = followsString[i].substring(2);
			for (int j=0; j<followsOfVariable.length(); j++)
				follows.get(variable).add(followsOfVariable.charAt(j)+"");
		}

		//initialize content of the parsing table
		//from firsts
		for(int i=0; i<variables.length; i++)
			for(int j=0; j<terminals.length; j++)
			{
				char variable = variables[i].charAt(0);
				char terminal = terminals[j].charAt(0);
				parsingTable.put(variable+","+terminal, getRuleOfFirst(variable, terminal));
			}

		//from follows
		//loop over all variables
		for(int i=0; i<variables.length; i++) {
			//if variables has epsilon in its firsts
			char variable = variables[i].charAt(0);
			if (firsts.get(variable).contains("e"))
				//put epislon in the cells of all its follows
				for (String followOfVariable : follows.get(variable))
					parsingTable.put(variable + "," + followOfVariable, "e");
		}


	}

	public String getRuleOfFirst(char variable, char terminal)
	{
		//if the variable does not even have the terminal in its first, then no point in searching for the sentential that
		//has it as first
		if(!firsts.get(variable).contains(terminal+""))
			return "EMPTY";

		ArrayList<String> variableRules = rules.get(variable);
		for(String sentential: variableRules)
		{
			if(sententialHasTerminalAsFirst(sentential, terminal))
				return sentential;
		}
		return "EMPTY";
	}
	public boolean sententialHasTerminalAsFirst(String sentential, char terminal)
	{
		if(sentential.charAt(0)==terminal)
			return true;
		ArrayList<String> firstsOfSentential = new ArrayList<>();
		//if current (first) character is not a variable return false
		if(!rules.keySet().contains(sentential.charAt(0)))
			return false;

		boolean epsilonPresent = true;
		for(int i=0; i<sentential.length(); i++)
		{
			if(!firsts.get(sentential.charAt(i)).contains("e"))
				epsilonPresent=false;
		}
		if(epsilonPresent) firstsOfSentential.add("e");
		for(int i=0; i<sentential.length(); i++)
		{
			firstsOfSentential.addAll(firsts.get(sentential.charAt(i)));
			if(!firsts.get(sentential.charAt(i)).contains("e"))
				break;
		}
		return firstsOfSentential.contains(terminal+"");
	}


	public static void main(String[] args) {
		String g = "S;T#a;c;i#S/iST,e;T/cS,a#S/i,e;T/c,a#S/$ca;T/$ca";
		CfgLl1Parser cfg = new CfgLl1Parser(g);
		System.out.println(cfg.parse("iia"));

	}

	/**
	 * @param input The string to be parsed by the LL(1) CFG.
	 * 
	 * @return A string encoding a left-most derivation.
	 */
	public String parse(String input) {
		// TODO Auto-generated method stub
		PDA PDA = new PDA(rules, parsingTable, terminals);
		input+="$";
		String out = PDA.parseInput(input);

		return out;
	}

}

class PDA{
	Stack<String> stack;
	final String x = "";
	HashMap<String, String> parsingTable;

	TreeSet<String> terminals;
	public PDA(HashMap<Character, ArrayList<String>> rules, HashMap<String, String> parsingTable, String[] terminalsArray)
	{
		this.parsingTable = parsingTable;
		this.stack = new Stack<>();
		stack.push("$"); stack.push("S");
		List<String> list = Arrays.asList(terminalsArray);
		terminals = new TreeSet<String>(list);
	}
	public String contentOfStackWithout$()
	{
		StringBuilder stringBuildervarible = new StringBuilder();
		stringBuildervarible.append( stack.stream().map(Object::toString).collect(Collectors.joining("")));
		String out = stringBuildervarible.reverse().toString();
		return out.substring(0,out.length()-1);//ignore $
	}
	public String parseInput(String input)
	{
		String out = "S";
		String trimmedInput = "";
		while(input.length()>0)
		{
			char curr = input.charAt(0);
			if(terminals.contains(stack.peek()+"")) {//if top of stack is a terminal, try to match
				boolean matched = Match(curr);
				if (matched) { //if successfully matched, remove first character of input
					trimmedInput += input.charAt(0);
					input = input.substring(1);
				}
				else
				{//if could not match print an error and return
					out += ";ERROR";
					break;
				}
			}
			else
			{//if input is not a terminal try to replace
				boolean replaced = Replace(curr);
				if(replaced) //
					out += ";"+trimmedInput + contentOfStackWithout$();
				else
				{
					out += ";ERROR";
					break;
				}
			}

		}
		return out;
	}

	public boolean Replace(char input)
	{
		String stackTop = stack.pop();
		//if no entry in parsing table
		if  (parsingTable.get(stackTop+","+input).equals("EMPTY"))
			return false;
		String sentential = parsingTable.get(stackTop+","+input);
		if(!sentential.equals("e"))
		{
			for(int i=sentential.length()-1; i>=0; i--)
				stack.push(sentential.charAt(i)+"");
		}
		return true;
	}
	public boolean Match(char input)
	{
		if(input == stack.peek().charAt(0))
		{
			stack.pop();
			return true;
		}
		else
			return false;
	}
}