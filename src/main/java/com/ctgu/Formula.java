package com.ctgu;

import java.util.Map;

public class Formula {
	String formulaStatement;
	Map parameter;
	double result;
	public String getFormulaStatement() {
		return formulaStatement;
	}
	public void setFormulaStatement(String formulaStatement) {
		this.formulaStatement = formulaStatement;
	}
	public Map getParameter() {
		return parameter;
	}
	public void setParameter(Map parameter) {
		this.parameter = parameter;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	
}
