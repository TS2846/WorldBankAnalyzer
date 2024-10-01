package drawStrategy;

import javax.swing.JComponent;
import javax.swing.JPanel;

import analysisFactory.Analysis;

public interface DrawStrategy {
	public JComponent draw(JPanel charts, int viewType, Analysis a);
	public JComponent lineChart(JPanel charts, Analysis a);
	public JComponent barChart(JPanel charts, Analysis a);
	public JComponent pieChart(JPanel charts, Analysis a);
	public JComponent report(JPanel charts, Analysis a);
	public JComponent timeSeriesChart(JPanel charts, Analysis a);
	public JComponent scatterChart(JPanel charts, Analysis a);
}
