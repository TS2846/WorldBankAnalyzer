package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drawStrategy.DrawStrategy;
import drawStrategy.SingleSeriesStrategy;

public class Analysis3 extends Analysis{
	DrawStrategy ds = new SingleSeriesStrategy();
	final String ind1 = "EN.ATM.CO2E.PC"; //CO2 emissions (metric tons per capita) 
	final String ind2 = "NY.GDP.PCAP.CD"; //GDP per capita (current US$) 
	
	public Analysis3(String country, int fromYear, int toYear) {
		data1 = df.getData(country, ind1, fromYear, toYear);
		data2 = df.getData(country, ind2, fromYear, toYear);
		
		this.setName1("Ratio of CO2 emissions (metric tons per capita) to GDP per capita (current US$)");
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		//calculations go here
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 0 ; i < data1.size();i++) {
			if(data1.get(i) != null && data2.get(i)!= null && data2.get(i) != 0) {
				result.add((data1.get(i)/data2.get(i)));
			}
			else {
				result.add(0.0);
			}
		}
		data1 = result;
	}

	@Override
	public JComponent draw(JPanel charts, int viewType) {
		return ds.draw(charts, viewType, this);
	}

}
