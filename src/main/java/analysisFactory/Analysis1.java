package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import drawStrategy.*;

public class Analysis1 extends Analysis{
	TripleSeriesStrategy ds = new TripleSeriesStrategy();
	final String ind1 = "EN.ATM.CO2E.PC";
	final String ind2 = "EG.USE.PCAP.KG.OE";
	final String ind3 = "EN.ATM.PM25.MC.M3";
	
	public Analysis1(String country, int fromYear, int toYear) {
		data1 = df.getData(country, ind1, fromYear-1, toYear);
		data2 = df.getData(country, ind2, fromYear-1, toYear);
		data3 = df.getData(country, ind3, fromYear-1, toYear);
		this.setName1("CO2 emissions (metric tons per capita)");
		this.setName2("Energy use (kg of oil equivalent per capita)");
		this.setName3("PM2.5 air pollution, mean annual exposure (micrograms per cubic meter)");
		this.setGraphName("Annual Percentage Change of CO2 Emissions, Energy Use, PM2.5 Air Pollution");
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		//calculations go here
		data1 = calculate(data1);
		data2 = calculate(data2);
		data3 = calculate(data3);
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
		//System.out.println(result.toString());
		return result;
		
	}
	@Override
	public JComponent draw(JPanel charts, int viewType) {
		return ds.draw(charts, viewType, this);
	}
}
