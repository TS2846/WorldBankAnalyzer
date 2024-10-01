package analysisFactory;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class Analysis {
	private String country;
	private String graphName;
	public DataFetcher df = new DataFetcher();
	public int fromYear;
	public int toYear;
	public int viewType;
	private String name1;
	private String name2;
	private String name3;
	public ArrayList<Double> data1;
	public ArrayList<Double> data2;
	public ArrayList<Double> data3;
	public abstract JComponent draw(JPanel charts, int viewType);
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
}
