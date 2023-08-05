package csen1002.main.task6;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

public class CfgFirstFollow {

	/**
	 * Constructs a Context Free Grammar
	 * 
	 * @param cfg A formatted string representation of the CFG. The string
	 *            representation follows the one in the task description
	 */
	String variablesWithSemicolon;
	String terminalsWithSemicolon;
	HashMap<Character, Variable> variablesMap;
	LinkedHashMap<Character, TreeSet<Character>> firstMap;
	LinkedHashMap<Character, TreeSet<Character>> followMap;

	public CfgFirstFollow(String cfg) {
		// TODO Auto-generated constructor stub
		String cfgArr[] = cfg.split("#");
		variablesWithSemicolon = cfgArr[0]; //A;B;C
		terminalsWithSemicolon = cfgArr[1];	//a;b;c
		String[] rules = cfgArr[2].split(";");
		firstMap = new LinkedHashMap<Character, TreeSet<Character>>();
		followMap = new LinkedHashMap<Character, TreeSet<Character>>();
		variablesMap = new HashMap<>();
		//fill the variable map variable character ---> Variable object
		for(int i=0; i<rules.length; i++)
		{
			char variableChar = rules[i].charAt(0);
			String toSententials = rules[i].substring(2);
			Variable variableObject = new Variable(variableChar);
			variablesMap.put(variableChar, variableObject);
			String[] sententials = toSententials.split(",");
			//fill the rules "to" of the variable
			for(int j=0; j<sententials.length; j++)
			{
				variablesMap.get(variableChar).to.add(sententials[j]);
			}
			//fill the firsts and follows with empty treesets
			firstMap.put(variableChar, new TreeSet<>());
			followMap.put(variableChar, new TreeSet<>());
		}

		//put firsts of terminals as themselves
		String terminalsStrings[] = terminalsWithSemicolon.split(";");
		for(int i=0; i<terminalsStrings.length; i++)
		{
			TreeSet<Character> first = new TreeSet<>();
			first.add(terminalsStrings[i].charAt(0));
			firstMap.put(terminalsStrings[i].charAt(0), first);
		}
		//add the first of epsilon as epsilon
		TreeSet<Character> firstOfEpsilon = new TreeSet<>();
		firstOfEpsilon.add('e');
		firstMap.put('e', firstOfEpsilon);

		//set follow of S as $
		TreeSet<Character> SFollow = new TreeSet<>();
		SFollow.add('$');
		followMap.put('S', SFollow);
	}

	/**
	 * Calculates the First Set of each variable in the CFG.
	 * 
	 * @return A string representation of the First of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String first() {
		// TODO Auto-generated method stub
		boolean change = true;
		while(change)
		{
			change = false;
			//loop on all variables
			for(Character variableCharacter: variablesMap.keySet())
			{
				Variable variable = variablesMap.get(variableCharacter);
				//loop on all rules of the variable
				for(String sentential: variable.to)
				{
					//if epsilon is in the first of all characters of the sentential B1,B2,B3,..
					//A --> B1B2B3B4...
					if(epsilonInAll(sentential))
					{
						//if the first of epsilon is not already in the first of A, add it
						if(!firstMap.get(variableCharacter).contains('e'))
						{
							firstMap.get(variableCharacter).add('e');
							change = true;
						}
					}
					//for every prefix of sentential B1B2B3B4
					for(int i=0; i<sentential.length();i++)
					{
						//if epsilon is in the first of all the prefix till Bi-1
						if(epsilonInAll(sentential.substring(0,i))){
							char bi = sentential.charAt(i);
							//if the first of Bi is not subset of the first of A, then add all Bi's firsts to A's firsts
							if(!isSubset(firstMap.get(bi), firstMap.get(variableCharacter)))
							{
								//remove epsilon when adding to A
								TreeSet<Character> bWithoutEpsilon = (TreeSet<Character>) firstMap.get(bi).clone();
								bWithoutEpsilon.remove('e');
								firstMap.get(variableCharacter).addAll(bWithoutEpsilon);
								change = true;
							}
						}
					}
				}
			}
		}

		//formatting the output string
		String out = "";
		for(Character varOrTerminal: firstMap.keySet())
		{
			if(variablesMap.containsKey(varOrTerminal))
			{
				out+= varOrTerminal+"/";
				out+=firstMap.get(varOrTerminal).stream().map(Object::toString).collect(Collectors.joining(""))
				+ ";";
			}
		}

		return out.substring(0, out.length()-1);
	}

	public boolean epsilonInAll(String sentential)
	{
		//A method that checks whether epsilon is in the first of all characters of sentential
		if(sentential.equals("e"))
			return true;
		for(int i=0; i<sentential.length();i++)
		{
			char B = sentential.charAt(i);
			if (!firstMap.get(B).contains('e'))
				return false;
		}
		return true;
	}
	public boolean isSubset(TreeSet<Character> b, TreeSet<Character> a)
	{
		//checks whether Set b is subset of A, ignoring epsilon
		TreeSet<Character> bWithoutEpsilon = (TreeSet<Character>) b.clone();
		bWithoutEpsilon.remove('e');
		for(Character c: bWithoutEpsilon)
		{
			if(!a.contains(c))
				return false;
		}
		return true;
	}

	/**
	 * Calculates the Follow Set of each variable in the CFG.
	 * 
	 * @return A string representation of the Follow of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String follow() {
		// TODO Auto-generated method stub
		this.first();

		boolean change = true;
		while(change)
		{
			change=false;
			//for every variable A
			for(Character variableCharacter: variablesMap.keySet())
			{
				//for every rule in that variable A --> alpha B Beta
				for(String sentential: variablesMap.get(variableCharacter).to)
				{
					for(int i=0; i<sentential.length(); i++)
					{
						//if this is a variable B in the middle of the rule A --> alpha B Beta
						if(variablesMap.keySet().contains(sentential.charAt(i)))
						{
							//case 1: it is the last character of the sentential, add follow of A to follow of B
							if(i==sentential.length()-1)
							{
								change |=addFollowOfAToFollowB(variableCharacter, sentential.charAt(i));
							}
							//case2: there is another character after B, say beta,
							// add firsts of Beta of Follow of B
							//If first of Beta has epsilon,
							//add follow of A to follow of B
							else
							{
								change |= addFirstOfBeta(sentential.charAt(i), sentential.substring(i+1));
								//if epsilon is in the first of all those after me,
								//add follow of A to follow of B if not already exists
								if(epsilonInAll(sentential.substring(i+1)))
									change |= addFollowOfAToFollowB(variableCharacter,sentential.charAt(i));

							}
						}

					}
				}
			}
		}

		//formatting the output string
		String out = "";
		for(Character varOrTerminal: followMap.keySet())
		{
			if(variablesMap.containsKey(varOrTerminal))
			{
				out+= varOrTerminal+"/";
				out+=followMap.get(varOrTerminal).stream().map(Object::toString).collect(Collectors.joining(""))
						+ ";";
			}
		}

		return out.substring(0, out.length()-1);

	}
	public boolean addFirstOfBeta(char variable, String beta)
	{
		boolean change = false;
		for(int i=0; i<beta.length(); i++)
		{
			char betai = beta.charAt(i);
			TreeSet<Character> firstOfBeta = (TreeSet<Character>) firstMap.get(betai).clone();
			firstOfBeta.remove('e');
			// add firsts of Beta of Follow of B, if they do not already exist
			if(!isSubset(firstOfBeta, followMap.get(variable)))
			{
				followMap.get(variable).addAll(firstOfBeta);
				change=true;
			}
			//if betai does not have epsilon, no need to check the remaining of the sentential
			if(!firstMap.get(betai).contains('e'))
				break;
		}
		return change;
	}
	public boolean addFollowOfAToFollowB(char A, char B)
	{
		//if follow of A is not already in follow of B
		if(!isSubset(followMap.get(A), followMap.get(B)))
		{
			followMap.get(B).addAll(followMap.get(A));
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		CfgFirstFollow cfgFirstFollow= new CfgFirstFollow("S;T;L#a;b;c;d;i#S/ScT,T;T/aSb,iaLb,e;L/SdL,S");
		System.out.println(cfgFirstFollow.first());
		System.out.println(cfgFirstFollow.follow());
		int $1 = 2;
	}

}
class Variable {

	char variable;
	ArrayList<String> to;//rules of the variable


	public Variable(char variable) {
		this.variable = variable;
		this.to = new ArrayList<>();
	}

	@Override
	public String toString() {
		String out = variable + ", To: " + to.toString();
		return out;
	}
}

