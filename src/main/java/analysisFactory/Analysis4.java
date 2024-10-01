package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drawStrategy.SingleSeriesStrategy;

public class Analysis4 extends Analysis{
	SingleSeriesStrategy ds = new SingleSeriesStrategy();
	final String ind = "AG.LND.FRST.ZS";
	
	public Analysis4(String country, int fromYear, int toYear) {
		data1 = df.getData(country, ind, fromYear, toYear);
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		this.setName1("Average Forest area (% of land area)");
		//calculations go here
		double sum = 0;
		double size = data1.size();
		for(int i = 0; i < data1.size(); i++) {
			sum += data1.get(i);
		}
		ArrayList<Double> result = new ArrayList<Double>();
		//index 0 would be the avg, index 1 would be The rest percentage which is land for all other uses
		result.add(sum/size);
		result.add(100-sum/size);
		System.out.println(result.toString());
		data1 = result;
	}

	@Override
	public JComponent draw(JPanel charts, int viewType) {
		return ds.draw(charts, 0, this);
	}

}
