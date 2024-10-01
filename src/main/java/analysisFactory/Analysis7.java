package analysisFactory;

import javax.swing.JComponent;
import javax.swing.JPanel;

import drawStrategy.DoubleSeriesStrategy;

public class Analysis7 extends Analysis{
	DoubleSeriesStrategy ds = new DoubleSeriesStrategy();
	final String ind1 = "SH.ACS.MONY.Q1.ZS"; //Problems in accessing health care (% of women): Q1 (lowest)  
	final String ind2 = "SP.DYN.IMRT.IN"; //Mortality rate, infant (per 1,000 live births)
	
	public Analysis7(String country, int fromYear, int toYear) {
		data1 = df.getData(country, ind1, fromYear, toYear);
		data2 = df.getData(country, ind2, fromYear, toYear);
		this.setCountry(country);
		this.fromYear = fromYear;
		this.toYear = toYear;
		this.setName1("Problems in accessing health care (% of women): Q1 (lowest)");
		this.setName2("Mortality rate, infant (per 1,000 live births)");
		this.setGraphName("Problems in accessing healthcare (% of women) vs Mortality rate, infant (per 1000 live births)");
		System.out.println(data1.toString());

		System.out.println(data2.toString());
}

	@Override
	public JComponent draw(JPanel charts, int viewType) {
		// TODO Auto-generated method stub
		return ds.draw(charts, viewType, this);
	}

}
