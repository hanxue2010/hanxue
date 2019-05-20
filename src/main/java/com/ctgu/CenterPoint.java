package com.ctgu;

public class CenterPoint {
	double[] position;//位置，
	public double[] getPosition() {
		return position;
	}
	int width;//河宽
	//夹角
	//局部水头损失率
	public CenterPoint(double x,double y) {
		position = new double[] {x,y};
	}

}
