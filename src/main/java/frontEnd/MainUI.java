package frontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainUI extends JFrame implements Subject {
	private static final long serialVersionUID = 1L;
	private static MainUI instance;
	private List<Observer> observers;
	private Mediator mediator = new Mediator();
	private final Object MUTEX= new Object();
	private boolean changed;
	
	//public HashSet<Integer> chartSet = new HashSet<Integer>();	
	
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();
		return instance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MainUI() {
		// Set window title
		super("Country Statistics");
		// Set top bar
		JLabel chooseCountryLabel = new JLabel("Choose a country: ");
		
		Vector<String> countriesNames = new Vector<String>(mediator.getCountryList().keySet());
		countriesNames.sort(null);
		final JComboBox<String> countriesList = new JComboBox<String>(countriesNames);
		
		countriesList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mediator.setCountry((String) countriesList.getSelectedItem());
				notifyObservers();
			}
		});
		
		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		final JComboBox<Integer> fromList = new JComboBox(mediator.getYearList().toArray());
		final JComboBox<Integer> toList = new JComboBox(mediator.getYearList().toArray());
		fromList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mediator.setFromYear((int) fromList.getSelectedItem());
				notifyObservers();
			}
		});
		toList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mediator.setToYear((int) toList.getSelectedItem());
				notifyObservers();
			}
		});
		
		JPanel north = new JPanel();
		north.add(chooseCountryLabel);
		north.add(countriesList);
		north.add(from);
		north.add(fromList);
		north.add(to);
		north.add(toList);

		// Set bottom bar
		JButton recalculate = new JButton("Recalculate");
		recalculate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mediator.recalculate();
				
			}
			
		});

		JLabel viewsLabel = new JLabel("Available Views: ");

		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("Pie Chart");
		viewsNames.add("Line Chart");
		viewsNames.add("Bar Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Report");
		viewsNames.add("TimeSeries");
		final JComboBox<String> viewsList = new JComboBox<String>(viewsNames);
		viewsList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mediator.setView(viewsList.getSelectedIndex());
				notifyObservers();
			}
		});
		
		JPanel charts = new JPanel();
		charts.setLayout(new GridLayout(2, 0));
		mediator.setCharts(charts);
		
		//CURRENTLY WORKING ON THIS
		JButton addView = new JButton("+");
		addView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediator.add();
			}
		});
		
		//CURRENTLY WORKING ON THIS 
		JButton removeView = new JButton("-");
		removeView.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mediator.remove();
			}
			
		});

		JLabel methodLabel = new JLabel("        Choose analysis method: ");

		Vector<String> methodsNames = new Vector<String>(mediator.getAList().keySet());

		final JComboBox<String> methodsList = new JComboBox<String>(methodsNames);
		methodsList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mediator.setAnalysisType(methodsList.getSelectedIndex());
				mediator.setAType((String) methodsList.getSelectedItem());
			}
			
		});

		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);

		south.add(methodLabel);
		south.add(methodsList);
		south.add(recalculate);

		JPanel east = new JPanel();

		// Set charts region
		
		//createScatter(charts);
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(charts, BorderLayout.WEST);
		charts.setPreferredSize(new Dimension(900, 700));
	}

	@Override
	public void register(Observer obs) {
		if(obs == null) throw new NullPointerException("Null Observer");
		synchronized (MUTEX) {
		if(!observers.contains(obs)) observers.add(obs);
		}
	}

	@Override
	public void unregister(Observer obs) {
		synchronized (MUTEX) {
			observers.remove(obs);
			}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observersLocal = null;
		//synchronization is used to make sure any observer registered after message is received is not notified
		synchronized (MUTEX) {
			if (!changed)
				return;
			observersLocal = new ArrayList<>(this.observers);
			this.changed=false;
		}
		for (Observer obj : observersLocal) {
			obj.update();
		}
	}
}