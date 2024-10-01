package analysisFactory;

public class AnalysisFactory {
	public Analysis make(int AnalysisType, String country, int fromYear, int toYear) {
		switch (AnalysisType) {
		case 0:
			return new Analysis1(country, fromYear, toYear);
		case 1:
			return new Analysis2(country, fromYear, toYear);
		case 2:
			return new Analysis3(country, fromYear, toYear);
		case 3:
			return new Analysis4(country, fromYear, toYear);
		case 4:
			return new Analysis5(country, fromYear, toYear);
		case 5:
			return new Analysis6(country, fromYear, toYear);
		case 6:
			return new Analysis7(country, fromYear, toYear);
		case 7:
			return new Analysis8(country, fromYear, toYear);
		default:
			return null;
		}	
	}
}