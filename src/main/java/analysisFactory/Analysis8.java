package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drawStrategy.DoubleSeriesStrategy;

public class Analysis8 extends Analysis{
	DoubleSeriesStrategy ds = new DoubleSeriesStrategy();
	final String ind1 = "SE.XPD.TOTL.GD.ZS"; //Government Expenditure on Education 
	final String ind2 = "SH.XPD.CHEX.GD.ZS"; //Health Expenditure 
	
	public Analysis8(String country, int fromYear, int toYear) {
		this.data1 = df.getData(country, ind1, fromYear-1, toYear);
		this.data2 = df.getData(country, ind2, fromYear-1, toYear);
		this.setName1("Government expenditure on education, total (% of GDP)");
		this.setName2("Current health expenditure (% of GDP)");
		this.setCountry(country);
		this.setGraphName("Government expenditure on education, total (% of GDP) vs Current health expenditure (% of GDP)");
		this.toYear = toYear;
		this.fromYear = fromYear;
		//calc
		data1 = calculate(data1);
		data2 = calculate(data2);
	}
	
	public 	ArrayList<Double> calculate(ArrayList<Double> data){
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 0 ; i < toYear+1-fromYear ;i++) {
			if(data.get(i) != null && data.get(i+1)!= null && data.get(i) != 0 && data.get(i+1) != 0) {
				result.add(((data.get(i+1)-data.get(i))/data.get(i))*100);
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
		// TODO Auto-generated method stub
		return ds.draw(charts, viewType, this);
	}
	
}
