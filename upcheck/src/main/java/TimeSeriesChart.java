import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TimeSeriesChart extends ApplicationFrame
{
	public TimeSeriesChart(String title)
	{
		super(title);
		XYDataset dataset =  createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
	}

	private static JFreeChart createChart(XYDataset dataset)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart("UPI-Transactions for last 5 Month",
			"Month's",
			"Value in Cr",
			dataset,
			true,true,false);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setDefaultShapesVisible(true);
			renderer.setDefaultShapesFilled(true);
		}
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		return chart;
	}

	private static XYDataset createDataset()
	{
		Map<String,Float> r1 = new LinkedHashMap<>();
		Map<String,Float> r2 = new LinkedHashMap<>();
		Map<String,Float> r3 = new LinkedHashMap<>();
		Map<String,Float> r4 = new LinkedHashMap<>();
		Map<String,Float> r5 = new LinkedHashMap<>();
		String url = "https://www.npci.org.in/what-we-do/upi/upi-ecosystem-statistics";
		Document document = null;
		try
		{
			document = Jsoup.connect(url).maxBodySize(Integer.MAX_VALUE).get();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		ArrayList<String> b1 = new ArrayList<>();
		generateMonthsforFetchingData(b1);
		fetchData(document,r1,r2,r3,r4,r5,b1);
		String s1 = "",s2="",s3="",s4="",s5="";
		int i = 0;
		for(String s:r5.keySet())
		{
			if(i==0)
				s1 = s;
			if(i==1)
				s2=s;
			if(i==2)
				s3=s;
			if(i==3)
				s4 = s;
			if(i==4)
				s5=s;
			if(i==5)
				break;

			i++;
		}
		TimeSeries se1 = new TimeSeries(s1);
		se1.add(new Month(3,2022),r1.get(s1));
		se1.add(new Month(4,2022),r2.get(s1));
		se1.add(new Month(5,2022),r3.get(s1));
		se1.add(new Month(6,2022),r4.get(s1));
		se1.add(new Month(7,2022),r5.get(s1));

		TimeSeries se2 = new TimeSeries(s2);
		se2.add(new Month(3,2022),r1.get(s2));
		se2.add(new Month(4,2022),r2.get(s2));
		se2.add(new Month(5,2022),r3.get(s2));
		se2.add(new Month(6,2022),r4.get(s2));
		se2.add(new Month(7,2022),r5.get(s2));

		TimeSeries se3 = new TimeSeries(s3);
		se3.add(new Month(3,2022),r1.get(s3));
		se3.add(new Month(4,2022),r2.get(s3));
		se3.add(new Month(5,2022),r3.get(s3));
		se3.add(new Month(6,2022),r4.get(s3));
		se3.add(new Month(7,2022),r5.get(s3));

		TimeSeries se4 = new TimeSeries(s4);
		se4.add(new Month(3,2022),r1.get(s4));
		se4.add(new Month(4,2022),r2.get(s4));
		se4.add(new Month(5,2022),r3.get(s4));
		se4.add(new Month(6,2022),r4.get(s4));
		se4.add(new Month(7,2022),r5.get(s4));

		TimeSeries se5 = new TimeSeries(s5);
		se5.add(new Month(3,2022),r1.get(s5));
		se5.add(new Month(4,2022),r2.get(s5));
		se5.add(new Month(5,2022),r3.get(s5));
		se5.add(new Month(6,2022),r4.get(s5));
		se5.add(new Month(7,2022),r5.get(s5));

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(se1);
		dataset.addSeries(se2);
		dataset.addSeries(se3);
		dataset.addSeries(se4);
		dataset.addSeries(se5);
		return dataset;
	}
	static void generateMonthsforFetchingData(ArrayList<String> b1)
	{
		String m = String.valueOf(LocalDate.now().getMonth());
		int y = LocalDate.now().getYear();
		int yp = y-1;
		String ys = String.valueOf(y);
		String ysp = String.valueOf(yp);
		String ysx = ys.substring(ys.length()-2);
		String yspx = ysp.substring(ysp.length()-2);
		switch(m)
		{
			case "JANUARY":
				b1.add("innerTabTwoAug"+yspx);
				b1.add("innerTabTwoSep"+yspx);
				b1.add("innerTabTwoOct"+yspx);
				b1.add("innerTabTwoNov"+yspx);
				b1.add("innerTabTwoDec"+yspx);
				break;
			case "FEBRUARY":
				b1.add("innerTabTwoSep"+yspx);
				b1.add("innerTabTwoOct"+yspx);
				b1.add("innerTabTwoNov"+yspx);
				b1.add("innerTabTwoDec"+yspx);
				b1.add("innerTabTwoJan"+ysx);
				break;
			case "MARCH":
				b1.add("innerTabTwoOct"+yspx);
				b1.add("innerTabTwoNov"+yspx);
				b1.add("innerTabTwoDec"+yspx);
				b1.add("innerTabTwoJan"+ysx);
				b1.add("innerTabTwoFeb"+ysx);
				break;
			case "APRIL":
				b1.add("innerTabTwoNov"+yspx);
				b1.add("innerTabTwoDec"+yspx);
				b1.add("innerTabTwoJan"+ysx);
				b1.add("innerTabTwoFeb"+ysx);
				b1.add("innerTabTwoMar"+ysx);
				break;
			case "MAY":
				b1.add("innerTabTwoDec"+yspx);
				b1.add("innerTabTwoJan"+ysx);
				b1.add("innerTabTwoFeb"+ysx);
				b1.add("innerTabTwoMar"+ysx);
				b1.add("innerTabTwoApr"+ysx);
				break;
			case "JUNE":
				b1.add("innerTabTwoJan"+ysx);
				b1.add("innerTabTwoFeb"+ysx);
				b1.add("innerTabTwoMar"+ysx);
				b1.add("innerTabTwoApr"+ysx);
				b1.add("innerTabTwoMay"+ysx);
				break;
			case "JULY":
				b1.add("innerTabTwoFeb"+ysx);
				b1.add("innerTabTwoMar"+ysx);
				b1.add("innerTabTwoApr"+ysx);
				b1.add("innerTabTwoMay"+ysx);
				b1.add("innerTabTwoJun"+ysx);
				break;
			case "AUGUST":
				b1.add("innerTabTwoMar"+ysx);
				b1.add("innerTabTwoApr"+ysx);
				b1.add("innerTabTwoMay"+ysx);
				b1.add("innerTabTwoJun"+ysx);
				b1.add("innerTabTwoJul"+ysx);
				break;
			case "SEPTEMBER":
				b1.add("innerTabTwoApr"+ysx);
				b1.add("innerTabTwoMay"+ysx);
				b1.add("innerTabTwoJun"+ysx);
				b1.add("innerTabTwoJul"+ysx);
				b1.add("innerTabTwoAug"+ysx);
				break;
			case "OCTOBER":
				b1.add("innerTabTwoMay"+ysx);
				b1.add("innerTabTwoJun"+ysx);
				b1.add("innerTabTwoJul"+ysx);
				b1.add("innerTabTwoAug"+ysx);
				b1.add("innerTabTwoSep"+ysx);
				break;
			case "NOVEMBER":
				b1.add("innerTabTwoJun"+ysx);
				b1.add("innerTabTwoJul"+ysx);
				b1.add("innerTabTwoAug"+ysx);
				b1.add("innerTabTwoSep"+ysx);
				b1.add("innerTabTwoOct"+ysx);
				break;
			case "DECEMBER":
				b1.add("innerTabTwoJul"+ysx);
				b1.add("innerTabTwoAug"+ysx);
				b1.add("innerTabTwoSep"+ysx);
				b1.add("innerTabTwoOct"+ysx);
				b1.add("innerTabTwoNov"+ysx);
				break;
			default:
				b1.add("innerTabTwoMar22");
				b1.add("innerTabTwoApr22");
				b1.add("innerTabTwoMay22");
				b1.add("innerTabTwoJun22");
				b1.add("innerTabTwoJul22");
				break;
		}

	}
	public static void fetchData(Document document,Map<String,Float> r1,Map<String,Float> r2,
		Map<String,Float> r3,Map<String,Float> r4,Map<String,Float> r5,ArrayList<String> b)
	{
		Map<String,Float> m = new HashMap<>();
		for(int j = 0; j<b.size(); j++)
		{
			Element element = document.getElementById(b.get(j));
			Element table = element.select("table").get(0);
			Elements tbody = table.select("tbody");
			Elements rows = tbody.select("tr");
			int i =0;
			while(i<rows.size())
			{
				Element row = rows.get(i);
				Elements col = row.select("td");
				String l = col.get(11).text().replaceAll(",", "");
				Float f = null;
				try
				{
					f = Float.parseFloat(l);
				}
				catch(Exception e)
				{
					i++;
					continue;
				}
				m.put(col.get(1).text(), f);

				i++;
			}
			if(j==0)
				m.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(d -> r1.put(d.getKey(), d.getValue()));
			if(j==1)
				m.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(d -> r2.put(d.getKey(), d.getValue()));
			if(j==2)
				m.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(d -> r3.put(d.getKey(), d.getValue()));
			if(j==3)
				m.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(d -> r4.put(d.getKey(), d.getValue()));
			if(j==4)
				m.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(d -> r5.put(d.getKey(), d.getValue()));

			m.clear();
		}
	}

	public static void main(String[] args)
	{
		TimeSeriesChart demo = new TimeSeriesChart("UPI Transactions Line chart");
		demo.pack();
		demo.setVisible(true);

	}
}
