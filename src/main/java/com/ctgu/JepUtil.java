package com.ctgu;

import org.nfunk.jep.JEP;

public class JepUtil {
	/*
	 * 等腰直角三角形，已知斜边，求邻边
	 *hypotenuse:斜边
	 */
	public double trigonmetricFunction(double hypotenuse) {
		JEP jep = new JEP();
        // 设置公式
		String f = "((hypotenuse^2)/2)^0.5";
        // 给变量赋值
        jep.addVariable("hypotenuse",hypotenuse);
        // 运算
        jep.parseExpression(f);
        // 得出结果	
		return  jep.getValue();		
	}
	/*直角三角形，已知两直角边，求斜边
	* rightFacingEdge:右邻边
	 *leftFacingEdge:左邻边
	 */
	public double trigonmetricFunction(double rightFacingEdge,double leftFacingEdge) {
		JEP jep = new JEP();
        // 设置公式
		String f = "(rightFacingEdge^2+leftFacingEdge^2)^0.5";
        // 给变量赋值
        jep.addVariable("rightFacingEdge",rightFacingEdge);
        jep.addVariable("leftFacingEdge",leftFacingEdge);
        // 运算
        jep.parseExpression(f);
        // 得出结果	
		return  jep.getValue();			
		}
	/*任意三角形，已知三条边，求投影距离
	 * 
	 * hypotenuse:斜边
	* rightFacingEdge:右邻边
	 * leftFacingEdge:左邻边
	 * ((b^+c^-a^)/2bc)*b
	 */
	public double trigonmetricFunction(double hypotenuse,double rightFacingEdge,double leftFacingEdge) {
		JEP jep = new JEP();
        // 设置公式
		String f = "((rightFacingEdge^2+leftFacingEdge^2-hypotenuse^2)/(2*rightFacingEdge*leftFacingEdge))*rightFacingEdge";
        // 给变量赋值
		jep.addVariable("hypotenuse",hypotenuse);
        jep.addVariable("rightFacingEdge",rightFacingEdge);
        jep.addVariable("leftFacingEdge",leftFacingEdge);
        // 运算
        jep.parseExpression(f);
        // 得出结果
		return jep.getValue();		
	}
	/*任意三角形，已知三条边，求投影距离
	 * 
	 * hypotenuse:斜边
	* rightFacingEdge:右邻边
	 * leftFacingEdge:左邻边
	 * (1-((b^+c^-a^)/2bc)^2)^0.5
	 */
	public double trigonmetricFunction(double hypotenuse,double rightFacingEdge,double leftFacingEdge,int x) {
		JEP jep = new JEP();
        // 设置公式
		String f = "((1-((rightFacingEdge^2+leftFacingEdge^2-hypotenuse^2)/(2*rightFacingEdge*leftFacingEdge))^2)^0.5)*rightFacingEdge";
        // 给变量赋值
		jep.addVariable("hypotenuse",hypotenuse);
        jep.addVariable("rightFacingEdge",rightFacingEdge);
        jep.addVariable("leftFacingEdge",leftFacingEdge);
        // 运算
        jep.parseExpression(f);
        // 得出结果
		return jep.getValue();		
	}
	
}
