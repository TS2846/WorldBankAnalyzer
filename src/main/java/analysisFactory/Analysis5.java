package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drawStrategy.SingleSeriesStrategy;

public class Analysis5 extends Analysis{
	SingleSeriesStrategy ds = new SingleSeriesStrategy();
	final String ind = "SE.XPD.TOTL.GD.ZS"; //Government expenditure on education, total (% of GDP)
	
	public Analysis5(String country, int fromYear, int toYear) {
		data1 = df.getData(country, ind, fromYear, toYear);
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		this.setName1("Average Government expenditure on education, total (% of GDP)");
		//calculations go here
		ArrayList<Double> result = new ArrayList<Double>();
		double sum = 0;
		double size = data1.size();
		for(int i = 0; i < data1.size(); i++) {
			sum += data1.get(i);
		}
		//index 0 would be the avg, index 1 would be The rest percentage which is expenditures for all other purposes
		result.add(sum/size);
		result.add(100-sum/size);
		data1 = result;
	}

	@Override
	public JComponent draw(JPanel charts, int viewType) {
		return ds.draw(charts, 0, this);
	}

}
