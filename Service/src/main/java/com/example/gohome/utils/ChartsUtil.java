package com.example.gohome.utils;

import java.util.List;

/** 
* 报表工具类
* @author chencaihui 
* @datetime 创建时间：2018年1月4日 上午9:25:07 
*/
public class ChartsUtil {
	
	public static final String Column2D = "Column2D";
	public static final String Column3D = "Column3D";
	public static final String Line = "Line";
	public static final String Bar2D = "Bar2D";
	public static final String Area2D = "Area2D";
	public static final String Pie2D = "Pie2D";
	public static final String Pie3D = "Pie3D";
	public static final String Doughnut2D = "Doughnut2D";
	public static final String Doughnut3D = "Doughnut3D";

	/**
	 * 构建报表
	 *@author chencaihui 
	 *@datetime 创建时间：2018年1月4日 上午9:35:13 
	 * @param dataMap 数据（key：x轴数据，value：y轴数据）
	 * @param style 报表类型
	 * @param titile 标题
	 * @param xName x轴名称
	 * @param yName y轴名称
	 * @return
	 */
	public static String getChartXml(List<String[]> dataList, String style, String titile, String xName, String yName){
		StringBuilder xml = new StringBuilder();
		xml.append("<chart showCanvasBg='1' exportEnabled='1' bgColor='#DDFFDE' baseFontSize='12' caption='"+titile+"' palette='4'");
		if ("Column2D".equals(style) || "Column3D".equals(style)|| "Line".equals(style)|| "Bar2D".equals(style)|| "Area2D".equals(style)) {
			xml.append(" xAxisName='"+xName+"' yAxisName='"+yName+"'  showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1' rotateYAxisName='0'");   
		}
		xml.append(">");
		if(NullUtil.isNotNull(dataList)){
			for (String[] data : dataList) {
				xml.append("<set label='"+data[0]+"' value='" + data[1] + "'/>");
			}
		}
		xml.append("</chart>");
		return xml.toString();
	}
}
