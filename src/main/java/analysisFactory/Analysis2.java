package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import drawStrategy.DoubleSeriesStrategy;

public class Analysis2 extends Analysis{
	DoubleSeriesStrategy ds = new DoubleSeriesStrategy();
	final String ind1 = "EN.ATM.PM25.MC.M3"; //PM2.5 air pollution, mean annual exposure (micrograms per cubic meter) 
	final String ind2 = "AG.LND.FRST.ZS"; //Forest area (% of land area) 

	public Analysis2(String country, int fromYear, int toYear) {
		this.setName1("PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)");
		this.setName2("Forest area (% of land area)");
		this.setGraphName("Annual Percentage Change of PM2.5 air pollution, mean annual exposure (micrograms per cubic meter) vs Forest area (% of land area)");
		data1 = df.getData(country, ind1, fromYear-1, toYear);
		data2 = df.getData(country, ind2, fromYear-1, toYear);
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		//calculations go here
		data1 = calculate(data1);
		data2 = calculate(data2);
	}
	
	public 	ArrayList<Double> calculate(ArrayList<Double> data){
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 1 ; i < data.size();i++) {
			if(data.get(i) != null && data.get(i-1) != null && data.get(i) != 0 && data.get(i-1) != 0) {
				result.add((data.get(i)-data.get(i-1))/data.get(i-1)*100);
			}
			else {
				result.add(0.0);
			}
		}
		System.out.println(result.toString());
		return result;
	}
	
	@Override
	public JComponent draw(JPanel charts, int viewType) {
		return ds.draw(charts, viewType, this);
	}

}
