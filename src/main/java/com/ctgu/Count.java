package com.ctgu;
import java.util.ArrayList;
import java.util.List;
public class Count {
	private List<CenterPoint> centerPoints;//中心线
	private List<Formula> formulas;//公式组
	private double[] landslidePoint;//滑坡点	
	private double[] countPoint;//计算点
	private double countRange;//计算范围	
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	public Count() {}
	 //初始化中心线，公式组，滑坡点，计算范围
	public Count(List<CenterPoint> centerPoints,List<Formula> formulas,double[] landslidePoint,double countRange){
		this.centerPoints = centerPoints;
		this.formulas = formulas;
		this.landslidePoint = landslidePoint;
		this.countRange = countRange;	
	}
	
	//确认计算范围
	public void CountRangecount() {
		//JepUtil jepUtil = new JepUtil();
	    // 得出结果
	    //double countRangeNo = Math.round((int)jepUtil.trigonmetricFunction(countRange));
		//System.out.println(Math.round(jep.getValue()));
	        minX = landslidePoint[0]-countRange;
	        minY = landslidePoint[1]-countRange;
	        maxX = landslidePoint[0]+countRange;
	        maxY = landslidePoint[1]+countRange;
		
			/*minX = -100;
	        minY = -30;
	        maxX = 100;
	        maxY = 30;*/
	        
	    //限制计算范围，根据中心线普及情况
	    if(minX<=centerPoints.get(0).getPosition()[0]
	         ||maxX>centerPoints.get(centerPoints.size()-1).getPosition()[0]) {
        	minX = 0;
        	minY = 0;
        	maxX = 0;
	        maxY = 0;
		   System.out.println("计算范围超过所中心线有效范围");
	        }
		        
		}
	
	//计算河宽
	public double countRiverWidth() {
		/*
		 * 滑坡点左侧
		*/
		List<CenterPoint> leftCenterPoints = new ArrayList<CenterPoint>();
		/*
		 * 滑坡点右侧
		*/
		List<CenterPoint> rightCenterPoints = new ArrayList<CenterPoint>();
		/*
		 * 计算三角形参数定义，以固定脚r为参考
		 * */
		double a = 0;//对边hypotenuse
		double b = 0;//右邻边
		double c = 0;//左邻边
	
		double di = 0;//投影高度
		JepUtil jepUtil = new JepUtil();
		
		//分组
		for(int i=0;i<centerPoints.size();i++) {		
			/*
			 将中线线分成两段，1，在滑坡点的左侧，2，在滑坡点右侧。
			*/
				if((centerPoints.get(i).getPosition())[0] < landslidePoint[0]) {			
					leftCenterPoints.add(centerPoints.get(i));						
				}else if((centerPoints.get(i).getPosition())[0] >= landslidePoint[0]) {			 
					rightCenterPoints.add(centerPoints.get(i));
				}else {
						System.out.println("出现异常点");
					}
				}
		//调jep计算,di
		//一组头与滑坡点 a=hypotenuse
		a = jepUtil.trigonmetricFunction(
			(landslidePoint[0] -				
				leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0]), 
			(landslidePoint[1] -				
				leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1]));
		//二组头与滑坡点c=rightFacingEdge
		b = jepUtil.trigonmetricFunction(
			(landslidePoint[0] -				
				rightCenterPoints.get(0).getPosition()[0]), 
			(landslidePoint[1] -				
				rightCenterPoints.get(0).getPosition()[1]));
		//一组头与二组尾b=leftFacingEdge
		c = jepUtil.trigonmetricFunction(
			(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
				rightCenterPoints.get(0).getPosition()[0]), 
			(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
				rightCenterPoints.get(0).getPosition()[1]));			
		di = jepUtil.trigonmetricFunction(a,b,c,1);
			//System.out.println("xi="+xi);
		
		
		return di*2;
		
	}
	//遍历所有计算点
	public void counting() {
		/*int[] countPoint= {-40,40};		
		double x =countX(countPoint);
		System.out.println("最终x="+x);*/
		
		for(double j=maxY,a=0;j>=minY&&a<((maxY-minY)/10);j-=10,a++) {
			for(double i=minX,b=0;i<maxX&&b<((maxX-minX)/10);i+=10,b++) {
				//传计算点去求x和r；
				countPoint= new double[2];
				countPoint[0]=i;
				countPoint[1]=j;
				//System.out.print("计算点"+i+";"+j);
				//使用计算
				double[] xAndR = countXAndR(countPoint);
				double r = xAndR[0];
				double x = xAndR[1];
				//System.out.println("x="+Math.round(x)+"r="+Math.round(r));
				//求河宽
				double d = countRiverWidth();
				//加入公式和x,获取公示结果储存
				if(r<d) {
					System.out.print(" ");
				}else {
					System.out.print("x");
				}
				//List[a][b] = .....;
		}
		System.out.println();
		}
	//取公式组的数据			
	}
		
		
		
	//求x和r
	public double[] countXAndR(double[] countPoint) {
		double[] xAndR;
		double x = 0;//投影距离
		double r = 0;//空间直线距离
		JepUtil jepUtil = new JepUtil();
		/*
		 先求需要参与计算的中心点数组,分左右计算方式不同		
		所有参数不为空的情况下才进行计算
		*/
		if(countPoint !=null && landslidePoint != null && centerPoints != null) {
			/*
			 * 计算点左边时：计算点左边有效中心点集合
			 * 计算点右边时：滑坡点左边有效中心点集合
			*/
			List<CenterPoint> leftCenterPoints = new ArrayList<CenterPoint>();
			/*
			 * 计算点左边时：滑坡点右边有效中心点集合
			 * 计算点右边时：计算点点左边有效中心点集合
			*/
			List<CenterPoint> rightCenterPoints = new ArrayList<CenterPoint>();
			/*
			 * 计算点左边时：计算点与滑坡点之间有效中心点集合
			 * 计算点右边时：滑坡点与计算点之间有效中心点集合
			*/
			List<CenterPoint> centerCenterPoints = new ArrayList<CenterPoint>();
			/*
			 * 计算三角形参数定义，以固定脚r为参考
			 * */
			double a = 0;//对边hypotenuse
			double b = 0;//右邻边
			double c = 0;//左邻边
			/*
			 * 计算x时的三段长度
			 * */
			double xi = 0;//左部分距离
			double xj = 0;//中间部分距离
			double xk = 0;//右部分距离
			//计算r
			r = jepUtil.trigonmetricFunction(
					countPoint[0]-landslidePoint[0], 
					countPoint[1]-landslidePoint[1]);
			
			
			//计算x
			//如果计算点在滑坡点左侧
			if(countPoint[0]<=landslidePoint[0]) {
				
				
				//遍历中心点并分三组
				for(int i=0;i<centerPoints.size();i++) {		
				/*
				 将中线线分成三段，1，在计算点的左侧，2，在计算点和滑坡点中间 ，3，在滑坡点右侧。
				*/
					if((centerPoints.get(i).getPosition())[0] < countPoint[0]) {			
						leftCenterPoints.add(centerPoints.get(i));						
					}else if((centerPoints.get(i).getPosition())[0] >= countPoint[0]
							&&(centerPoints.get(i).getPosition())[0] < landslidePoint[0]) {
						centerCenterPoints.add(centerPoints.get(i));
					}else if((centerPoints.get(i).getPosition())[0] >= landslidePoint[0]) {			 
						rightCenterPoints.add(centerPoints.get(i));
					}else {
							System.out.println("出现异常点");
						}
					}
				//System.out.print("在左侧");
				//System.out.println("点"+countPoint[0]+";"+countPoint[1]+"在左侧");
				/*System.out.println("左侧点&&左边共有"+leftCenterPoints.size()+"个点"+
						"中间共有"+centerCenterPoints.size()+"个点"	+
						"右边共有"+rightCenterPoints.size()+"个点"	);	*/
				
				//调jep计算,xi/xj/xk
				//如果中间集合为空
				if(centerCenterPoints.size() == 0){
					//三组头与计算点 a=hypotenuse
					a = jepUtil.trigonmetricFunction(
						(countPoint[0] -				
							rightCenterPoints.get(0).getPosition()[0]), 
						(countPoint[1] -				
							rightCenterPoints.get(0).getPosition()[1]));
					//一组尾与三组头c=rightFacingEdge
					b = jepUtil.trigonmetricFunction(
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
							rightCenterPoints.get(0).getPosition()[0]), 
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
							rightCenterPoints.get(0).getPosition()[1]));
					//一组尾与计算点b=leftFacingEdge
					c = jepUtil.trigonmetricFunction(
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
							countPoint[0]), 
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
							countPoint[1]));			
					xi = jepUtil.trigonmetricFunction(a,b,c);
					//System.out.println("xi="+xi);
					
					//三组头与滑坡点 a=hypotenuses
					a = jepUtil.trigonmetricFunction(
							(landslidePoint[0] -				
								rightCenterPoints.get(0).getPosition()[0]), 
							(landslidePoint[1] -				
								rightCenterPoints.get(1).getPosition()[1]));
					//一组尾与三组头 c=rightFacingEdge
					b = jepUtil.trigonmetricFunction(
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
							rightCenterPoints.get(0).getPosition()[0]), 
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
							rightCenterPoints.get(0).getPosition()[1]));
					//一组尾与滑坡点 c=leftFacingEdge
					c = jepUtil.trigonmetricFunction(
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
							landslidePoint[0]), 
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
							landslidePoint[1]));
					xk = jepUtil.trigonmetricFunction(a,b,c);
					//System.out.println("xi="+xi);
					x = xk-xi;
					
				}else{
					//一组尾与计算点 a=hypotenuse
					a = jepUtil.trigonmetricFunction(
							(countPoint[0] -				
								leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0]), 
							(countPoint[1] -				
								leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1]));				
					//二组头与计算点b=rightFacingEdge
					b = jepUtil.trigonmetricFunction(
						(centerCenterPoints.get(0).getPosition()[0] -				
							countPoint[0]), 
						(centerCenterPoints.get(0).getPosition()[1] -				
								countPoint[1]));
					//一组尾与二组头 c=leftFacingEdge
					c = jepUtil.trigonmetricFunction(
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
							centerCenterPoints.get(0).getPosition()[0]), 
						(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
							centerCenterPoints.get(0).getPosition()[1]));
					xi = jepUtil.trigonmetricFunction(a,b,c);
					//System.out.println("xi="+xi);
					//二组头到二组尾
					for(int i = 0;i+1<centerCenterPoints.size();i++) {
						xj += jepUtil.trigonmetricFunction(
							centerCenterPoints.get(i).getPosition()[0] - 
								centerCenterPoints.get(i+1).getPosition()[0],
							centerCenterPoints.get(i).getPosition()[1] - 
								centerCenterPoints.get(i+1).getPosition()[1]
								);
					}
					//System.out.println("xj="+xj);
					//xk算法与xi相同
					//三组头与滑坡点 a=hypotenuse
					a = jepUtil.trigonmetricFunction(
						(landslidePoint[0] -				
							rightCenterPoints.get(0).getPosition()[0]), 
						(landslidePoint[1] -				
							rightCenterPoints.get(0).getPosition()[1]));
					//二组尾与三组头c=rightFacingEdge
					b = jepUtil.trigonmetricFunction(
						(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[0] -				
							rightCenterPoints.get(0).getPosition()[0]), 
						(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[1] -				
							rightCenterPoints.get(0).getPosition()[1]));
					//二组尾与滑坡点b=leftFacingEdge
					c = jepUtil.trigonmetricFunction(
						(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[0] -				
							landslidePoint[0]), 
						(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[1] -				
							landslidePoint[1]));			
					xk = jepUtil.trigonmetricFunction(a,b,c);
					//System.out.println("xk="+xk);
				}
					x = xi+xj+xk;
				
				//否则在右边
		}else {						
			//遍历中心点分组
			for(int i=0;i<centerPoints.size();i++) {		
				/*
				 将中线线分成三段，1，在滑坡点的左侧，2，在滑坡点和计算点中间 ，3，在计算点右侧。
				*/
				if((centerPoints.get(i).getPosition())[0] <landslidePoint[0]) {			
					leftCenterPoints.add(centerPoints.get(i));		
					//System.out.println((leftCenterPoints.getPosition())[0]);				
				}else if((centerPoints.get(i).getPosition())[0] >= landslidePoint[0]
						&&(centerPoints.get(i).getPosition())[0] < countPoint[0]) {
					centerCenterPoints.add(centerPoints.get(i));
				}else if((centerPoints.get(i).getPosition())[0] >= countPoint[0]) {			 
					rightCenterPoints.add(centerPoints.get(i));
				}else {
						System.out.println("出现异常点");
					}
				}
			//System.out.print("在右侧");
			//调jep计算,xi/xj/xk
			//如果中间集合为空
			if(centerCenterPoints.size() == 0){
				//一组尾与滑坡点 a=hypotenuse
				a = jepUtil.trigonmetricFunction(
					(landslidePoint[0] -				
						leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0]), 
					(landslidePoint[1] -				
						leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1]));
				//三组头与滑坡点 c=rightFacingEdge
				b = jepUtil.trigonmetricFunction(
					(rightCenterPoints.get(0).getPosition()[0] -				
						landslidePoint[0]), 
					(rightCenterPoints.get(0).getPosition()[1] -				
						landslidePoint[1]));
				//一组尾与三组头b=leftFacingEdge
				c = jepUtil.trigonmetricFunction(
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
						rightCenterPoints.get(0).getPosition()[0]), 
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
						rightCenterPoints.get(0).getPosition()[1]));			
				xi = jepUtil.trigonmetricFunction(a,b,c);
				//System.out.println("xi="+xi);
				
				//一组尾与计算点 a=hypotenuses
				a = jepUtil.trigonmetricFunction(
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
						countPoint[0]), 
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
						countPoint[1]));
				//三组头与计算点 c=rightFacingEdge
				b = jepUtil.trigonmetricFunction(
					(rightCenterPoints.get(0).getPosition()[0] -				
						countPoint[0]), 
					(rightCenterPoints.get(0).getPosition()[1] -				
						countPoint[1]));
				//一组尾与三组头 c=leftFacingEdge
				c = jepUtil.trigonmetricFunction(
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
						rightCenterPoints.get(0).getPosition()[0]), 
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
						rightCenterPoints.get(0).getPosition()[1]));
				xk = jepUtil.trigonmetricFunction(a,b,c);
				//System.out.println("xi="+xi);
				x = xi-xk;
				
			}else {
			//一组尾与滑坡点 a=hypotenuse
			a = jepUtil.trigonmetricFunction(
					(landslidePoint[0] -				
						leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0]), 
					(landslidePoint[1] -				
						leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1]));				
			//二组头与滑坡点b=rightFacingEdge
			b = jepUtil.trigonmetricFunction(
					(centerCenterPoints.get(0).getPosition()[0] -				
						landslidePoint[0]), 
					(centerCenterPoints.get(0).getPosition()[1] -				
						landslidePoint[1]));
			//一组尾与二组头 c=leftFacingEdge
			c = jepUtil.trigonmetricFunction(
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[0] -				
						centerCenterPoints.get(0).getPosition()[0]), 
					(leftCenterPoints.get(leftCenterPoints.size()-1).getPosition()[1] -				
						centerCenterPoints.get(0).getPosition()[1]));
			xi = jepUtil.trigonmetricFunction(a,b,c);
			//System.out.println("xi="+xi);
			//二组头到二组尾
			for(int i = 0;i+1<centerCenterPoints.size();i++) {
				xj += jepUtil.trigonmetricFunction(
						centerCenterPoints.get(i).getPosition()[0] - 
							centerCenterPoints.get(i+1).getPosition()[0],
						centerCenterPoints.get(i).getPosition()[1] - 
							centerCenterPoints.get(i+1).getPosition()[1]
						);
			}
			//System.out.println("xj="+xj);
			//xk算法与xi相同
			//三组头与计算点 a=hypotenuse
			a = jepUtil.trigonmetricFunction(
					(countPoint[0] -				
						rightCenterPoints.get(0).getPosition()[0]), 
					(countPoint[1] -				
						rightCenterPoints.get(0).getPosition()[1]));
			//二组尾与三组头 c=rightFacingEdge
			b = jepUtil.trigonmetricFunction(
					(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[0] -				
						rightCenterPoints.get(0).getPosition()[0]), 
					(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[1] -				
						rightCenterPoints.get(0).getPosition()[1]));
			//二组尾与计算点b=leftFacingEdge
			c = jepUtil.trigonmetricFunction(
					(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[0] -				
						countPoint[0]), 
					(centerCenterPoints.get(centerCenterPoints.size()-1).getPosition()[1] -				
						countPoint[1]));			
			xk = jepUtil.trigonmetricFunction(a,b,c);
			//System.out.println("xk="+xk);
			x = xi+xj+xk;
			}
		}
		}
			xAndR = new double[2]; 
			xAndR[0] = r;
			xAndR[1] = x;
			return xAndR;
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
	public double[] getCountPoint() {
		return countPoint;
	}
	public void setCountPoint(double[] countPoint) {
		this.countPoint = countPoint;
	}
	public double getCountRange() {
		return countRange;
	}
	public void setCountRange(double countRange) {
		this.countRange = countRange;
	}
	public double getMinX() {
		return minX;
	}
	public void setMinX(double minX) {
		this.minX = minX;
	}
	public double getMinY() {
		return minY;
	}
	public void setMinY(double minY) {
		this.minY = minY;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
}
