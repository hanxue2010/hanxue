package com.ctgu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountBefore {
	//中心线
	 private List<CenterPoint> centerPoints;
	//公式组
	 private List<Formula> formulas;
	//滑坡点
	 private double[] landslidePoint;
	//计算范围
	 private double countRange;
	
	 
	 public void setParameter() {
		 //初始化中心线
		 centerPoints = new ArrayList<CenterPoint>();
		 centerPoints.add(new CenterPoint(-160,-10));
		 centerPoints.add(new CenterPoint(-120,20));
		 centerPoints.add(new CenterPoint(-80,10));
		 centerPoints.add(new CenterPoint(-40,15));
		 centerPoints.add(new CenterPoint(0,10));
		 centerPoints.add(new CenterPoint(40,5));
		 centerPoints.add(new CenterPoint(80,0));
		 centerPoints.add(new CenterPoint(120,-10));
		 centerPoints.add(new CenterPoint(160,-20));
		 
		//初始化公式组 
		 formulas = new ArrayList<Formula>();
		 Formula formula1 = new Formula();
			formula1.setFormulaStatement("a+b+c");
			Map map =  new HashMap();
			map.put("a", 1);
			map.put("b", 2);
			map.put("c", 3);
			formula1.setParameter(map);
			formulas.add(formula1);
		
		//设置滑坡点
		landslidePoint = new double[2];
		landslidePoint[0]=10;
		landslidePoint[1]=40;
		
		//设置计算范围
		countRange = 100;
	 
	 }
	 
	//调计算类进行计算
	public void count() {
		setParameter();
		Count c = new Count(centerPoints,formulas,landslidePoint,countRange);
		//设置计算范围
		c.CountRangecount();
		//计算
		c.counting();		
		}
	 
	 
	 
	 
	 
	 public List<CenterPoint> getCenterPoints() {
		return centerPoints;
	}
	public void setCenterPoints(List<CenterPoint> centerPoints) {
		this.centerPoints = centerPoints;
	}
	public List<Formula> getFormulas() {
		return formulas;
	}
	public void setFormulas(List<Formula> formulas) {
		this.formulas = formulas;
	}
	public double[] getLandslidePoint() {
		return landslidePoint;
	}
	public void setLandslidePoint(double[] landslidePoint) {
		this.landslidePoint = landslidePoint;
	}
	public double getCountRange() {
		return countRange;
	}
	public void setCountRange(double countRange) {
		this.countRange = countRange;
	}	
}



