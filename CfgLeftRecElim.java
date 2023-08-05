package csen1002.main.task5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Write your info here
 * 
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

public class CfgLeftRecElim {

	/**
	 * Constructs a Context Free Grammar
	 * 
	 * @param cfg A formatted string representation of the CFG. The string
	 *            representation follows the one in the task description
	 */
	String variablesWithSemicolon;
	String terminalsWithSemicolon;
	HashMap<String, Variable> variablesMap;

	public CfgLeftRecElim(String cfg) {
		// TODO Auto-generated constructor stub
		String cfgArr[] = cfg.split("#");
		variablesWithSemicolon = cfgArr[0]; //A;B;C
		terminalsWithSemicolon = cfgArr[1];	//a;b;c
		String[] rules = cfgArr[2].split(";");
		variablesMap = new HashMap<>();
		ArrayList<Variable> beforeMe = new ArrayList<>();
		for(int i=0; i<rules.length; i++)
		{
			String variableString = rules[i].substring(0,1);
			String toSententials = rules[i].substring(2);
			Variable variableObject = new Variable(variableString, beforeMe);
			variablesMap.put(variableString, variableObject);
			String[] sententials = toSententials.split(",");
			for(int j=0; j<sententials.length; j++)
			{
				variablesMap.get(variableString).to.add(sententials[j]);
			}
			beforeMe.add(variableObject);//add this variable to the before me of the previous variable
		}
	}

	/**
	 * @return Returns a formatted string representation of the CFG. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String output =  variablesWithSemicolon + "#" +
				terminalsWithSemicolon + "#" +
				"S/" + variablesMap.get("S").to.stream().map(Object::toString).collect(Collectors.joining(",")) + ";";
		variablesMap.remove('S');

		String[] variables = variablesWithSemicolon.split(";");
		for(int i=1; i<variables.length; i++) {
			String variable = variables[i];
			output+=variable+"/";
			output+=variablesMap.get(variable).to.stream().map(Object::toString).collect(Collectors.joining(","));
			output+=";";
		}
		return output.substring(0,output.length()-1);//to avoid the last semicolon
	}

	/**
	 * Eliminates Left Recursion from the grammar
	 */
	public void eliminateLeftRecursion() {
		// TODO Auto-generated method stub
		String[] variables = variablesWithSemicolon.split(";");

		ArrayList<Variable> newVariablesToAdd = new ArrayList<>();
		for(int i=0; i<variables.length; i++)
		{
			String variableString = variables[i];
			Variable variableObject = variablesMap.get(variableString);
			variableObject.removeProductionRules();
			Variable variableToAdd = variableObject.removeImmediateLeftRecursion();
			if(variableToAdd!=null)
				newVariablesToAdd.add(variableToAdd);
		}
		for(Variable variable : newVariablesToAdd)
		{
			variablesMap.put(variable.variable, variable);
			variablesWithSemicolon+=";"+variable.variable;
		}
	}

	public static void main(String[] args) {
		CfgLeftRecElim cfgLeftRecElim= new CfgLeftRecElim("S;B;X;R;D#d;q;s#S/DX,DdDSq,dS,sBR;B/BSsXS,RdBD,sR;X/BdX,SqS,XS,XdSsB;R/SsBBX,dRSR,dXRsD,s;D/DRB,DqDdR,RDXs,RsS,XR,qDRD");
		cfgLeftRecElim.eliminateLeftRecursion();
		}

}
class Variable{

	String variable;
	ArrayList<String> to;

	ArrayList<Variable> beforeMe;


	public Variable(String variable, ArrayList<Variable>beforeMe)
	{
		this.variable = variable;
		this.to = new ArrayList<>();
		this.beforeMe = (ArrayList<Variable>) beforeMe.clone();

	}

	@Override
	public String toString() {
		String out = variable + ", To: " + to.toString() + ", Before Me: ";
		for(Variable v: beforeMe)
			out+=v.variable + ", ";
		return out;
	}
	public void removeProductionRules()
	{
		while (containsProductionRules())
		{
			ArrayList<String> newTo = new ArrayList<>();
			for(int i=0; i<to.size(); i++)//loop on the sententials that I go to
			{
				String sentential = to.get(i);
				//check if the sentential has a leftmost variable which is before me
				Variable beforeMeAndLeftmost = variableBeforeMeInSentential(sentential);
				if(beforeMeAndLeftmost!=null)//if indeed there is such variable
				{
					//remove the variable from the beginning of the sentential
					String sententialWithoutVariable = sentential.substring(beforeMeAndLeftmost.variable.length());
					for(String removedVariableSentential : beforeMeAndLeftmost.to)
					{
						newTo.add(removedVariableSentential + sententialWithoutVariable);
					}
				}
				else{
					newTo.add(sentential);
				}
			}
			to = newTo;
		}

	}
	public Variable removeImmediateLeftRecursion()
	{
		//this method returns the new added variable, if any. if no variable, returns null;

		ArrayList<String> alphas = new ArrayList<>();
		ArrayList<String> betas = new ArrayList<>();
		Variable variableDash = null;
		if(containsImmediateLeftRecursion())
		{
			for(String sentential: to)
			{
				if(sentential.startsWith(variable))
					alphas.add(sentential.substring(variable.length()));
				else
					betas.add(sentential);
			}
			//initialize new variable Dash
			ArrayList<Variable> beforeVariableDash = new ArrayList<>();
			beforeVariableDash = (ArrayList<Variable>) beforeMe.clone();
			beforeVariableDash.add(this);
			variableDash = new Variable(variable+"\'", beforeVariableDash);
			//reinitalize to
			to = new ArrayList<>();
			for(String beta: betas)
				to.add(beta + variableDash.variable);
			for(String alpha: alphas)
				variableDash.to.add(alpha+variableDash.variable);
			variableDash.to.add("e");
		}
		return variableDash;
	}
	public boolean containsProductionRules()
	{
		for(String sentential: to)
		{
			for(Variable variableBeforeMe: beforeMe)
			{
				if(sentential.startsWith(variableBeforeMe.variable))
					return true;
			}
		}
		return false;
	}
	public boolean containsImmediateLeftRecursion()
	{
		for(String sentential: to)
			if(sentential.startsWith(this.variable))
				return true;
		return false;
	}
	public Variable variableBeforeMeInSentential(String sentential)
	{
		for(Variable v : beforeMe)
		{
			if(sentential.startsWith(v.variable))
				return v;
		}
		return null;
	}

}
