package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drawStrategy.SingleSeriesStrategy;

public class Analysis6 extends Analysis{
	SingleSeriesStrategy ds = new SingleSeriesStrategy();
	final String ind1 = "SH.XPD.CHEX.PC.CD"; //Current health expenditure per capita (current US$) NEEDS TO BE MULTIPLIED BY 1000 
	final String ind2 = "SH.MED.BEDS.ZS"; //Hospital beds (per 1,000 people
	
	public Analysis6(String country, int fromYear, int toYear) {
		data1 = df.getData(country, ind1, fromYear, toYear);
		data2 = df.getData(country, ind2, fromYear, toYear);
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		this.setName1("Ratio of Current health expenditure per 1000 people to Hospital beds per 1000 people");
		//calculations go here
		ArrayList<Double> result = new ArrayList<Double>();
		for (int i = 0; i < data1.size(); i++) {
			if(data2.get(i) != 0 && data1.get(i) != null && data2.get(i) != null) {
				result.add(data1.get(i)*1000/data2.get(i));
			}
			else result.add(0.0);
		}
		data1 = result;
	}
	@Override
	public JComponent draw(JPanel charts, int viewType) {
		return ds.draw(charts, viewType, this);
	}

}
