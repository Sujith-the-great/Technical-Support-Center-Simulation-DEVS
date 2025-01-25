package Component.Project;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import GenCol.*;

//import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class TechnicalSupportEngineer extends ViewableAtomic {
	// of atomic due to its
	// graphics capability
	protected entity caseDetails;
	protected double processing_time;
	protected double updating_db_time;
	protected double clock;
	protected HashMap<Integer, String> errorSolutions;
	protected int errorCode;
	protected String solution; 
	protected Queue<entity> casesQueue;
	protected Map arrived;
	
	public TechnicalSupportEngineer() {
		this("TechnicalSupportEngineer", 10);
	}

	public TechnicalSupportEngineer(String name, double Processing_time) {
		super(name);
		addInport("inLevelI");
		addOutport("escalateLevelI");
		addOutport("resolvedLevelI");
		addInport("none"); // allows testing for null input
							// which should cause only "continue"
		processing_time=10;
		processing_time = Processing_time;
		//addTestInput("in", new entity("job1"));
		//addTestInput("in", new entity("job2"), 20);
		//addTestInput("none", new entity("job"));
		addTestInput("inLevelI",new entity("customerCase 3 5 Michael 117 Performance-problems Amazon.com false false false 0.0 0.0 0.0 none"));
		addTestInput("inLevelI",new entity("customerCase 1 2 Sujith 102 UI-Not-showing-up Amazon.com false false false 0.0 0.0 0.0 none"));
		addTestInput("inLevelI",new entity("customerCase 4 1 AxelLopez 119 Account-management issues AWS false false false 0.0 0.0 0.0 Solution102 EE"));
		addTestInput("inLevelI",new entity("customerCase 5 3 Shreyansh 121 Recommendation-failures Amazon.com false false false 0.0 0.0 0.0 Solution119 PSE"));
		addTestInput("inLevelI",new entity("customerCase 2 4 LockheedMartin 110 Browser-Compatibility-issues Amazon.com false false false 0.0 0.0 0.0 none"));
		errorSolutions = new HashMap<>();
		casesQueue = new LinkedList<>();
		arrived = new HashMap();
		clock = 0;
		
		updating_db_time=3;
		
		setBackgroundColor(Color.GREEN);
	}

	public void initialize() {
		phase = "passive";
		sigma = INFINITY;
		caseDetails = new entity("caseDetails");
		preLoadTheInternalDatabase();
		super.initialize();
	}
	
	public void preLoadTheInternalDatabase() {
		errorSolutions.put(100, "Reboot_your_computer_to_fix_error_100");  
		errorSolutions.put(101, "Reinstall_the_application_to_fix_error_101");
		errorSolutions.put(102, "Clear_cache_and_cookies_to_fix_error_102");
		errorSolutions.put(103, "Check_internet_connectivity_to_fix_error_103");
		errorSolutions.put(104, "Update_network_drivers_to_fix_error_104");
		errorSolutions.put(105, "Error_150_requires_a_software_update");
		errorSolutions.put(106, "Free_up_disk_space_to_fix_error_160");
		errorSolutions.put(107, "Check_file_permissions_to_allow_access");  
		errorSolutions.put(108, "Contact_administrator_to_fix_permission_issue");
		errorSolutions.put(109, "Rollback_recent_changes_to_fix_error_190");
		errorSolutions.put(110, "Critical_error,_contact_tech_support");
		
	}

	public void deltext(double e, message x) {
		Continue(e);
		clock = clock + e;
//		It is because of all the simulators has to be executed. What is the minimum of nextTN of these three atomic models
//		

//		System.out.println("The elapsed time of the processor is" + e);
//		System.out.println("*****************************************");
//		System.out.println("external-Phase before: "+phase);
		if (phaseIs("passive"))
			for (int i=0; i < x.getLength(); i++) {
				if (messageOnPort(x,"inLevelI",i)) {
					caseDetails = x.getValOnPort("inLevelI",i);
					String[] parts = caseDetails.toString().trim().split(" ");
					solution = parts[13].trim();
					arrived.put(caseDetails.getName(),new doubleEnt(clock));
					errorCode = Integer.parseInt(parts[4].toString());
					if(solution.equals("none")) {
						holdIn("busy", processing_time);
					}
					else {
						errorSolutions.put(errorCode,solution);
						holdIn("updating_db",updating_db_time);
					}
					
				}
			}
		else if (phaseIs("busy")|| phaseIs("updating_db"))
			for (int i=0; i < x.getLength(); i++) {
				if (messageOnPort(x,"inLevelI",i)) {
					arrived.put(x.getValOnPort("inLevelI",i).getName(),new doubleEnt(clock));
					casesQueue.add(x.getValOnPort("inLevelI",i));
					
				}
			}
		/*
		else if (phaseIs("updating_db"))
			for (int i=0; i < x.getLength(); i++) {
				if (messageOnPort(x,"inLevelI",i)) {
					arrived.put(x.getValOnPort("inLevelI",i).getName(),new doubleEnt(clock));
					casesQueue.add(x.getValOnPort("inLevelI",i));
					
				}
			}
		*/
		
		
		/*
			
		if (phaseIs("passive"))
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "inLevelI", i)) {
					caseDetails = x.getValOnPort("inLevelI", i);
					String[] parts = caseDetails.toString().trim().split(" ");
					errorCode = Integer.parseInt(parts[4].toString());
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
			String[] parts = caseDetails.toString().trim().split(" ");
			solution = parts[13].trim();
			errorCode = Integer.parseInt(parts[4].toString());
			if(solution.equals("none")) {
				holdIn("busy", processing_time);
			}
			else {
				errorSolutions.put(errorCode,solution);
				holdIn("updating_db",updating_db_time);
			}
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
		String[] parts = caseDetails.toString().trim().split(" ");
		double case_ta_time = clock-((doubleEnt)((entity)arrived.get(caseDetails.getName()))).getv()+processing_time;
		entity caseDetails_out = new entity(parts[0].trim()+" "+parts[1].trim()+" "+parts[2].trim()+" "+
                                 parts[3].trim()+" "+parts[4].trim()+" "+parts[5].trim()+" "+
	                             parts[6].trim()+" "+"true"+" "+parts[8].trim()+" "+parts[9].trim()+" "+
	                             case_ta_time+" "+parts[11].trim()+" "+parts[12].trim()+" "+parts[13].trim());
		if (phaseIs("busy") || phaseIs("updating_db")) {
			if ( !solution.equals("none") ) {
				System.out.println("The resolved case is directed from TSE to transducer+"+parts[11].trim()+" "+parts[14].trim());
				m.add(makeContent("resolvedLevelI",caseDetails));
			}
			else if(errorSolutions.containsKey(errorCode)) {
				System.out.println("The case has been resolved by Level- I");
				m.add(makeContent("resolvedLevelI", new entity(parts[0].trim()+" "+parts[1].trim()+" "+parts[2].trim()+" "+
                        parts[3].trim()+" "+parts[4].trim()+" "+parts[5].trim()+" "+
                        parts[6].trim()+" "+"true"+" "+parts[8].trim()+" "+parts[9].trim()+" "+
                        case_ta_time+" "+parts[11].trim()+" "+parts[12].trim()+" "+errorSolutions.get(errorCode)+" "+"TSE")));
			}
			else {
				System.out.println("The case has been escalated to Level- II");
				m.add(makeContent("escalateLevelI",caseDetails_out));
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
