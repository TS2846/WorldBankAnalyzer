package drawStrategy;

import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import analysisFactory.Analysis;

public class TripleSeriesStrategy implements DrawStrategy{
	public JComponent draw(JPanel charts, int viewType, Analysis a) {
		switch(viewType) {
		case 0:
			return pieChart(charts, a);
		case 1:
			return lineChart(charts, a);
		case 2:
			return barChart(charts, a);
		case 3:
			return scatterChart(charts, a);
		case 4:
			return report(charts, a);
		case 5:
			return timeSeriesChart(charts, a);
		default:
			return null;
		}
	}
	
	public JComponent lineChart(JPanel charts, Analysis a) {
		XYSeries series1 = new XYSeries(a.getName1());
		
		XYSeries series2 = new XYSeries(a.getName2());
		
		XYSeries series3 = new XYSeries(a.getName3());
		for (int i = 0; i < a.toYear+1-a.fromYear; i++) {
			series1.add(a.fromYear+i, a.data1.get(i));
			series2.add(a.fromYear+i, a.data2.get(i));
			series3.add(a.fromYear+i, a.data3.get(i));
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		JFreeChart chart = ChartFactory.createXYLineChart(a.getGraphName(), "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = chart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.getLegend().setFrame(BlockBorder.NONE);

		chart.setTitle(
				new TextTitle(a.getCountry()+" "+a.getGraphName(), new Font("Serif", java.awt.Font.BOLD, 18)));

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		charts.add(chartPanel);
		return chartPanel;
		
	}

	public JComponent barChart(JPanel charts, Analysis a) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		for (int i = 0; i < a.toYear+1-a.fromYear; i++) {
			dataset.setValue(a.data1.get(i), a.getName1(), String.valueOf(a.fromYear+i));
			dataset.setValue(a.data2.get(i), a.getName2(), String.valueOf(a.fromYear+i));
			dataset2.setValue(a.data3.get(i), a.getName3(), String.valueOf(a.fromYear+i));
		}
		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();
		BarRenderer barrenderer2 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.setDataset(1, dataset2);
		plot.setRenderer(1, barrenderer2);

		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 0); // 2nd dataset to 2nd y-axis

		JFreeChart barChart = new JFreeChart(a.getCountry()+" "+a.getGraphName(),
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		// Different way to create bar chart
		/*
		 * dataset = new DefaultCategoryDataset();
		 * 
		 * dataset.addValue(3.946, "Unemployed", "Men"); dataset.addValue(96.054,
		 * "Employed", "Men"); dataset.addValue(3.837, "Unemployed", "Women");
		 * dataset.addValue(96.163, "Employed", "Women"); barChart =
		 * ChartFactory.createBarChart("Unemployment: Men vs Women", "Gender",
		 * "Percentage", dataset, PlotOrientation.VERTICAL, true, true, false);
		 */

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		charts.add(chartPanel);
		return chartPanel;
	}

	public JComponent pieChart(JPanel charts, Analysis a) {
		System.out.println("reached draw");
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(3.946, "Unemployed", "Men");
		dataset.addValue(96.054, "Employed", "Men");
		dataset.addValue(3.837, "Unemployed", "Women");
		dataset.addValue(96.163, "Employed", "Women");

		JFreeChart pieChart = ChartFactory.createMultiplePieChart("Unemployment: Men vs Women", dataset,
				TableOrder.BY_COLUMN, true, true, false);

		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		charts.add(chartPanel);
		return chartPanel;
	}

	public JComponent report(JPanel charts, Analysis a) {
		JTextArea report = new JTextArea();
		report.setEditable(false);
		report.setPreferredSize(null);
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		String reportMessage = a.getCountry() + " " + a.getGraphName() + "\n==============================\n";
		for (int i = 0; i < a.toYear+1-a.fromYear; i++) {
			reportMessage += "Year: " + String.valueOf(a.fromYear+i) +"\n";
			reportMessage += "\t" + a.getName1() + " => " + String.valueOf(a.data1.get(i))+"\n";
			reportMessage += "\t" + a.getName2() + " => " + String.valueOf(a.data2.get(i))+"\n";
			reportMessage += "\t" + a.getName3() + " => " + String.valueOf(a.data3.get(i))+"\n";
			reportMessage += "\n";
		}

		report.setText(reportMessage);
		JScrollPane outputScrollPane = new JScrollPane(report);
		charts.add(outputScrollPane);
		return outputScrollPane;
		
	}

	public JComponent timeSeriesChart(JPanel charts, Analysis a) {
		TimeSeries series1 = new TimeSeries(a.getName1());
		TimeSeries series2 = new TimeSeries(a.getName2());
		TimeSeries series3 = new TimeSeries(a.getName3());
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		for(int i = 0; i < a.toYear-a.fromYear; i++) {
			series1.add(new Year(a.fromYear+i), a.data1.get(i));
			series2.add(new Year(a.fromYear+i), a.data2.get(i));
			series3.add(new Year(a.fromYear+i), a.data3.get(i));
			
		}
		dataset2.addSeries(series2);
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series3);

		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.setDataset(1, dataset2);
		plot.setRenderer(1, splinerenderer2);
		plot.setRangeAxis(1, new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart chart = new JFreeChart(a.getCountry()+" "+a.getGraphName(),
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		charts.add(chartPanel);
		return chartPanel;
		
	}

	public JComponent scatterChart(JPanel charts, Analysis a) {
		TimeSeries series1 = new TimeSeries(a.getName1());
		TimeSeries series2 = new TimeSeries(a.getName2());
		TimeSeries series3 = new TimeSeries(a.getName3());
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		for(int i = 0; i < a.toYear-a.fromYear; i++) {
			series1.add(new Year(a.fromYear+i), a.data1.get(i));
			series2.add(new Year(a.fromYear+i), a.data2.get(i));
			series3.add(new Year(a.fromYear+i), a.data3.get(i));
			
		}
		dataset2.addSeries(series2);
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series3);

		XYPlot plot = new XYPlot();
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
		XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.setDataset(1, dataset2);
		plot.setRenderer(1, itemrenderer2);
		plot.setRangeAxis(1, new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart scatterChart = new JFreeChart(a.getCountry()+" "+a.getGraphName(),
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		charts.add(chartPanel);
		return chartPanel;
		
	}
}
