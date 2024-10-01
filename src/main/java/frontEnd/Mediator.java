package frontEnd;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import analysisFactory.AnalysisFactory;

public class Mediator implements Observer{
	private Subject main;
	public AnalysisFactory af = new AnalysisFactory();
	private ArrayList<Integer> yearList = new JSonReader("resources/years.json").parseYears(); 
	private HashMap<String, ArrayList<Integer>> aList = new JSonReader("resources/analyses.json").parseAnalysis();
	private HashMap<String, String> countryList = new JSonReader("resources/country.json").parseCountry();
	private HashMap<String, JComponent> viewList = new HashMap<String, JComponent>();
	private int viewType = 0; //determines the type of analysis used - default value 0
	private int fromYear = 2020; //start year - default value 2021
	private int toYear = 2020; //end year - default value 2021
	private int analysisType = 0; //type of analysis - default value 0
	private String country = "Brazil"; //country - default value brazil
	private String aType = "Annual Percentage Change of CO2 Emissions, Energy Use, PM2.5 Air Pollution";
	private JPanel charts;
	private String key;
	
	public void setView(int viewType) {
		this.viewType = viewType;
	}
	
	public void setFromYear(int fromYear) {
		this.fromYear = fromYear;
	}
	
	public void setToYear(int toYear) {
		this.toYear = toYear;
	}

	public void setAnalysisType(int analysisType) {
		this.analysisType = analysisType;
	}

	public void setCountry(String string) {
		this.country = string;
	}

	public void setCharts(JPanel charts) {
		this.charts = charts;
	}
	
	public void setAType(String aType) {
		this.aType = aType;
	}
	
	public Boolean canAdd() {
		if (aList.get(aType).contains(viewType)) {
			return true;
		}
		else
		return false;
	}
	
	public void add() {
		if (!viewList.containsKey(key) && this.canAdd()) {
			viewList.put(key, af.make(analysisType, countryList.get(country), fromYear, toYear).draw(charts, viewType));
		}
	}
	
	public void remove() {
		if (viewList.containsKey(key)) {
			charts.remove(viewList.get(key));
			viewList.remove(key);	
		}
	}
	
	public void recalculate() {
		charts.revalidate();
		charts.repaint();
	}

	public ArrayList<Integer> getYearList() {
		return yearList;
	}
	public HashMap<String, ArrayList<Integer>> getAList() {
		return aList;
	}
	public HashMap<String, String> getCountryList() {
		return countryList;
	}

	@Override
	public void setSubject(Subject sub) {
		this.main = MainUI.getInstance();		
	}

	@Override
	public void update() {
		key = String.valueOf(country) + String.valueOf(analysisType) + String.valueOf(viewType) + String.valueOf(fromYear) + String.valueOf(toYear);		
	}
	
}
