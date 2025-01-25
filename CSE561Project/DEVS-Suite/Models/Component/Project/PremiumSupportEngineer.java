package Component.Project;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import GenCol.doubleEnt;
import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class PremiumSupportEngineer extends ViewableAtomic {
	// graphics capability
	protected entity caseDetails;
	protected double processing_time;
	protected Queue<entity> casesQueue;
	protected Map arrived;
	protected double clock;
	protected int currentDefectNo;

	public PremiumSupportEngineer() {
		this("PremiumSupportEngineer", 10);
	}

	public PremiumSupportEngineer(String name, double Processing_time) {
		super(name);
		addInport("inLevelIII");
		addOutport("resolvedLevelIII");
		addInport("none"); // allows testing for null input
							// which should cause only "continue"
		processing_time=10;
		processing_time = Processing_time;
		addTestInput("in", new entity("job1"));
		addTestInput("in", new entity("job2"), 20);
		addTestInput("none", new entity("job"));
		addTestInput("inLevelIII",new entity("customerCase 3 5 Michael 117 Performance-problems Amazon.com"));
		addTestInput("inLevelIII",new entity("customerCase 1 2 Sujith 102 UI-Not-showing-up Amazon.com"));
		addTestInput("inLevelIII",new entity("customerCase 4 1 AxelLopez 119 Account-management issues AWS"));
		addTestInput("inLevelIII",new entity("customerCase 5 3 Shreyansh 121 Recommendation-failures Amazon.com"));
		addTestInput("inLevelIII",new entity("customerCase 2 4 LockheedMartin 110 Browser-Compatibility-issues Amazon.com"));

		casesQueue = new LinkedList<>();
		arrived = new HashMap();
		clock = 0;
		currentDefectNo = 1000;
		
		
		setBackgroundColor(Color.gray);
	}

	public void initialize() {
		phase = "passive";
		sigma = INFINITY;
		caseDetails = new entity("job");
		super.initialize();
	}

	public void deltext(double e, message x) {
		Continue(e);
		clock = clock + e;
//		It is because of all the simulators has to be executed. What is the minimum of nextTN of these three atomic models
//		

//		System.out.println("The elapsed time of the processor is" + e);
//		System.out.println("*****************************************");
//		System.out.println("external-Phase before: "+phase);
		System.out.println("PSE Ext Transiton fnction");
		for (int i=0; i < x.getLength(); i++) {
			if (phaseIs("passive")) {
				if (messageOnPort(x, "inLevelIII", i)) {
					caseDetails = x.getValOnPort("inLevelIII", i);
					arrived.put(caseDetails.getName(),new doubleEnt(clock));
					holdIn("busy", processing_time);
					System.out.println(phase);
					System.out.println(caseDetails);
				}
			}
			else if (phaseIs("busy")) {
				if (messageOnPort(x, "inLevelIII", i)) {
					casesQueue.add(x.getValOnPort("inLevelIII",i));
					arrived.put(x.getValOnPort("inLevelIII",i).getName(),new doubleEnt(clock));
					
				}
			}
		}
		/*
		if (phaseIs("passive"))
			for (int i=0; i < x.getLength(); i++) {
				if (messageOnPort(x, "inLevelIII", i)) {
					caseDetails = x.getValOnPort("inLevelIII", i);
					arrived.put(caseDetails.getName(),new doubleEnt(clock));
					holdIn("busy", 10);
					System.out.println(phase);
					System.out.println(caseDetails);
				}
			}
		else if (phaseIs("busy"))
			for (int i=0; i < x.getLength(); i++) {
				if (messageOnPort(x, "inLevelIII", i)) {
					casesQueue.add(x.getValOnPort("inLevelIII",i));
					arrived.put(x.getValOnPort("inLevelIII",i).getName(),new doubleEnt(clock));
					
				}
			}
			*/
		/*
		if (phaseIs("passive"))
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "inLevelIII", i)) {
					caseDetails = x.getValOnPort("inLevelIII", i);
					holdIn("busy", 10);
//					System.out.println("processing tiem of proc is"
//							+ processing_time);
				}
		*/
//		System.out.println("external-Phase after: "+phase);
	}

	public void deltint() {
//		System.out.println("Internal-Phase before: "+phase);
		clock = clock + sigma;
		
		if (!casesQueue.isEmpty()) {
			caseDetails = casesQueue.remove();
			holdIn("busy", processing_time);
		}
		else
		{
			passivate();
			caseDetails = new entity("none");
		}
		/*
		passivate();
		caseDetails = new entity("none");
		*/
//		System.out.println("Internal-Phase after: "+phase);
	}

	public void deltcon(double e, message x) {
//		System.out.println("confluent");
		deltint();
		deltext(0, x);
	}

	public message out() {
		message m = new message();
		currentDefectNo++;
		String resultantSolution = "Defect_No_is:"+currentDefectNo+":";
		String[] parts = caseDetails.toString().trim().split(" ");
		double case_ta_time = clock-((doubleEnt)((entity)arrived.get(caseDetails.getName()))).getv()+processing_time;
		caseDetails = new entity(parts[0].trim()+" "+parts[1].trim()+" "+parts[2].trim()+" "+
                                 parts[3].trim()+" "+parts[4].trim()+" "+parts[5].trim()+" "+
	                             parts[6].trim()+" "+parts[7].trim()+" "+parts[8].trim()+" "+"true"+" "+
	                             parts[10].trim()+" "+parts[11].trim()+" "+case_ta_time+" "+resultantSolution+parts[4].toString());
		if (phaseIs("busy")) {
			m.add(makeContent("resolvedLevelIII", new entity(caseDetails.toString()+" "+"PSE")));
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