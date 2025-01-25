package Component.Project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//import GenCol.Queue;
import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class SupportCenterRouter extends ViewableAtomic {
	
	protected entity caseDetails;
	protected CustomerCase customerCase;
	protected double processing_time;
	protected int severity;
	protected int priority;
	protected String customerName;
	protected String errorCode;
	protected String problemDescription;
	protected String productType;
	protected Queue<entity> casesRoutedToLevelI;
	protected Queue<entity> casesRoutedToLevelII;
	protected Queue<entity> casesRoutedToLevelIII;
	protected int routedToLevelI;
	protected int routedToLevelII;
	protected int routedToLevelIII;
	

	public SupportCenterRouter() {
		this("SupportCenterRouter", 10);
		
	}

	public SupportCenterRouter(String name, double Processing_time) {
		super(name);
		addInport("in");
		addOutport("outTSE");
		addOutport("outEE");
		addOutport("outPSE");
		addInport("none"); // allows testing for null input
							// which should cause only "continue"
		processing_time = Processing_time;
		addTestInput("in", new entity("job1"));
		addTestInput("in", new entity("job2"), 20);
		addTestInput("none", new entity("job"));
		casesRoutedToLevelI = new LinkedList<>();
		casesRoutedToLevelII = new LinkedList<>();
		casesRoutedToLevelIII = new LinkedList<>();
		routedToLevelI = -1;
		routedToLevelII = -1;
		routedToLevelIII = -1;
		
		setBackgroundColor(Color.cyan);
	}

	public void initialize() {
		phase = "passive";
		sigma = INFINITY;
		caseDetails = new entity("caseDetails");
		super.initialize();
	}

	public void deltext(double e, message x) {
		Continue(e);
//		It is because of all the simulators has to be executed. What is the minimum of nextTN of these three atomic models
//		

//		System.out.println("The elapsed time of the processor is" + e);
//		System.out.println("*****************************************");
//		System.out.println("external-Phase before: "+phase);
		
		
			
		if (phaseIs("passive"))
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "in", i)) {
					caseDetails = x.getValOnPort("in", i);
//					System.out.println(caseDetails.toString());
					String[] parts = caseDetails.toString().trim().split(" ");
					severity = Integer.parseInt(parts[1].trim());
				    priority = Integer.parseInt(parts[2].trim());
					String customerName = parts[3].trim();
//					System.out.println(severity);
//					System.out.println(customerName);
					if((severity == 1) || (priority == 1) || (customerName.equalsIgnoreCase("LockheedMartin"))) {
						casesRoutedToLevelIII.add(caseDetails);
						System.out.println("*** Cases Routed to Level III ***");
						System.out.println(caseDetails);
						routedToLevelIII++;
					}
					else if((severity == 2) || (priority == 2) || (severity == 3) || (priority == 3)) {
						casesRoutedToLevelII.add(caseDetails);
						System.out.println("*** Cases Routed to Level II ***");
						System.out.println(caseDetails);
						routedToLevelII++;
					}
					else {
						casesRoutedToLevelI.add(caseDetails);
						System.out.println("** Cases Routed to Level I ***");
						System.out.println(caseDetails);
						routedToLevelI++;
					}
					holdIn("busy", processing_time);
//					System.out.println("processing tiem of proc is"
//							+ processing_time);
				}
		
//		System.out.println("external-Phase after: "+phase);
	}

	public void deltint() {
//		System.out.println("Internal-Phase before: "+phase);
		passivate();
		caseDetails = new entity("none");
//		System.out.println("Internal-Phase after: "+phase);
	}

	public void deltcon(double e, message x) {
//		System.out.println("confluent");
		deltint();
		deltext(0, x);
	}

	public message out() {
		message m = new message();
		if (phaseIs("busy")) {
			if(!casesRoutedToLevelI.isEmpty()) {
			m.add(makeContent("outTSE", casesRoutedToLevelI.remove()));
			}
			if(!casesRoutedToLevelII.isEmpty()) {
				m.add(makeContent("outEE", casesRoutedToLevelII.remove()));
				}
			if(!casesRoutedToLevelIII.isEmpty()) {
				m.add(makeContent("outPSE", casesRoutedToLevelIII.remove()));
			}
		
		}
		return m;
	}

	public void showState() {
		super.showState();
		// System.out.println("job: " + job.getName());
	}

	public String getTooltipText() {
		return super.getTooltipText() + "\n" + "job: " + caseDetails.getName();
	}

}
