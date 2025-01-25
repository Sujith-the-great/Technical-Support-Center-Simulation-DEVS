package Component.Project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Arrays;

import GenCol.doubleEnt;

//import org.assertj.core.util.Arrays;

import GenCol.entity;
import model.modeling.message;
import view.modeling.ViewableAtomic;

public class EscalationEngineer extends ViewableAtomic {
	protected entity caseDetails;
	protected double processing_time;
	protected double updating_db_time;
	protected double clock;
	protected HashMap<Integer, String> advancedErrorSolutions;
	protected List<String> productDocumentSectionSolutions;
	protected List<String> workaroundSolutions;
	protected List<String> knowledgeBaseArticles;
	protected int errorCode;
	protected String solution;
	protected Queue<entity> casesQueue;
	protected Map arrived;
	
	public EscalationEngineer() {
		this("EscalationEngineer", 10);
	}

	public EscalationEngineer(String name, double Processing_time) {
		super(name);
		addInport("inLevelII");
		addOutport("resolvedLevelII");
		addOutport("escalateLevelIII");
		addInport("none"); // allows testing for null input
							// which should cause only "continue"

		processing_time=10;
		processing_time = Processing_time;
		addTestInput("in", new entity("job1"));
		addTestInput("in", new entity("job2"), 20);
		addTestInput("none", new entity("job"));
		addTestInput("inLevelII",new entity("customerCase 3 5 Michael 117 Performance-problems Amazon.com false false false 0.0 0.0 0.0 none"));
		addTestInput("inLevelII",new entity("customerCase 1 2 Sujith 102 UI-Not-showing-up Amazon.com false false false 0.0 0.0 0.0 none"));
		addTestInput("inLevelII",new entity("customerCase 4 1 AxelLopez 119 Account-management issues AWS false false false 0.0 0.0 0.0 Solution102 EE"));
		addTestInput("inLevelII",new entity("customerCase 5 3 Shreyansh 121 Recommendation-failures Amazon.com false false false 0.0 0.0 0.0 Solution119 PSE"));
		addTestInput("inLevelII",new entity("customerCase 2 4 LockheedMartin 110 Browser-Compatibility-issues Amazon.com false false false 0.0 0.0 0.0 none"));
		
		advancedErrorSolutions = new HashMap<>();
		productDocumentSectionSolutions = new ArrayList<>();
		workaroundSolutions = new ArrayList<>();
		knowledgeBaseArticles = new ArrayList<>();
		casesQueue = new LinkedList<>();
		arrived = new HashMap();
		clock = 0;
		updating_db_time=3;
		setBackgroundColor(Color.MAGENTA);
	}

	public void initialize() {
		phase = "passive";
		sigma = INFINITY;
		caseDetails = new entity("job");
		preLoadAdvancedErrorSolutions();
		super.initialize();
	}
	
	public void preLoadAdvancedErrorSolutions() {
//		List<List<String>> solutions111 = new ArrayList<>();
//		
//		List<String> docs111 = Arrays.asList("Section 1.1, Page 42");
//		List<String> workaround111 =  Arrays.asList("Clear the cache");
//		List<String> kbArticle111 = Arrays.asList("KB1234");
//		solutions111.add(docs111);
//		solutions111.add(workaround111);
//		solutions111.add(kbArticle111);
//		advancedErrorSolutions.put(111, solutions111.toString());
		String solutions111 = "Section1.1,page45_Clear_the_cache_KB1234";
		advancedErrorSolutions.put(111, solutions111);
		
		String solutions112 ="Section1.2,Page44_Reboot_the_computer/server_KB1235";
//		
//		List<String> docs112 = Arrays.asList("Section 1.2, Page 44");
//		List<String> workaround112 = Arrays.asList("Reboot the computer/server");
//		List<String> kbArticle112 = Arrays.asList("KB1235");
//		solutions112.add(docs112);
//		solutions112.add(workaround112);
//		solutions112.add(kbArticle112);
		
		advancedErrorSolutions.put(112, solutions112);
		
		
		
		String solutions113 = "Section1.3,Page46_Reboot_the_computer/server_KB1236";
//		List<String> docs113 = Arrays.asList("Section 1.3, Page 46");
//		List<String> workaround113 = Arrays.asList("Reboot the computer/server");
//		List<String> kbArticle113 = Arrays.asList("KB1236");
//		solutions113.add(docs113);
//		solutions113.add(workaround113);
//		solutions113.add(kbArticle113);
		advancedErrorSolutions.put(113, solutions113);
		
		String solutions114 = "Section1.4,Page55_Clear_website_cookies_and_data,Check_for_software/app_updates_KB1237";
//		List<String> docs114 = Arrays.asList("Section 1.4, Page 55");
//		List<String> workaround114 = Arrays.asList("Clear website cookies and data", "Check for software/app updates");
//		List<String> kbArticle114 = Arrays.asList("KB1237");
//		solutions114.add(docs114);
//		solutions114.add(workaround114);
//		solutions114.add(kbArticle114);
		advancedErrorSolutions.put(114, solutions114.toString());
		
		String solutions115 = "Section1.5,Page60_Uninstall_and_reinstall_the_software_KB1238";
//		List<String> docs115 = Arrays.asList("Section 1.5, Page 60");
//		List<String> workaround115 = Arrays.asList("Uninstall and reinstall the software");
//		List<String> kbArticle115 = Arrays.asList("KB1238");
//		solutions115.add(docs115);
//		solutions115.add(workaround115);
//		solutions115.add(kbArticle115);
		advancedErrorSolutions.put(115, solutions115);
		
		
		String solutions116 = "Section2.1,Page70_Restart_the application_KB1512";
//		List<String> docs116 = Arrays.asList("Section 2.1, Page 70");
//		List<String> workaround116 = Arrays.asList("Restart the application");
//		List<String> kbArticle116 = Arrays.asList("KB1512");
//		solutions116.add(docs116);
//		solutions116.add(workaround116);
//		solutions116.add(kbArticle116);
		advancedErrorSolutions.put(116, solutions116);
		
		String solutions117 = "Section2.2,Page80_Disable_antivirus_temporarily_KB1322";
//		List<String> docs117 = Arrays.asList("Section 2.2, Page 80");
//		List<String> workaround117 = Arrays.asList("Disable antivirus temporarily");
//		List<String> kbArticle117 = Arrays.asList("KB1322");
//		solutions117.add(docs117);
//		solutions117.add(workaround117);
//		solutions117.add(kbArticle117);
		advancedErrorSolutions.put(117, solutions117);
		
		String solutions118 = "Section2.3,Page90_Whitelist_website/app_in_firewall_KB1444";
		List<String> docs118 = Arrays.asList("Section 2.3, Page 90");
		List<String> workaround118 = Arrays.asList("Whitelist website/app in firewall");
		List<String> kbArticle118 = Arrays.asList("KB1444");
//		solutions118.add(docs118);
//		solutions118.add(workaround118);
//		solutions118.add(kbArticle118);
		advancedErrorSolutions.put(118, solutions118);
		
		String solutions119 = "Section2.4,Page100_Switch_to_guest_or_incognito_mode_KB1567";
		List<String> docs119 = Arrays.asList("Section 2.4, Page 100");
		List<String> workaround119 = Arrays.asList("Switch to guest or incognito mode");
		List<String> kbArticle119 = Arrays.asList("KB1567");
//		solutions119.add(docs119);
//		solutions119.add(workaround119);
//		solutions119.add(kbArticle119);
		advancedErrorSolutions.put(119, solutions119);
		
		
		String solutions120 = "Section2.5,Page101_Try_wired_network_connection_instead_of_wifi_KB9876";
		List<String> docs120 = Arrays.asList("Section 2.5, Page 101");
		List<String> workaround120 = Arrays.asList("Try wired network connection instead of wifi");
		List<String> kbArticle120 = Arrays.asList("KB9876");
//		solutions120.add(docs120);
//		solutions120.add(workaround120);
//		solutions120.add(kbArticle120);
		advancedErrorSolutions.put(120, solutions120);
		
		String solutions121 = "Section3.1,Page_120_Flush_DNS_and_renew_IP_address_KB9234";
		List<String> docs121 = Arrays.asList("Section 3.1, Page 120");
		List<String> workaround121 = Arrays.asList("Flush DNS and renew IP address");
		List<String> kbArticle121 = Arrays.asList("KB9234");
//		solutions121.add(docs121);
//		solutions121.add(workaround121);
//		solutions121.add(kbArticle121);
		advancedErrorSolutions.put(121, solutions121);
		
		String solutions122 = "Section3.2,Page144_Disable_browser_extensions/plugins_KB4534";
		List<String> docs122 = Arrays.asList("Section 3.2, Page 144");
		List<String> workaround122 = Arrays.asList("Disable browser extensions/plugins");
		List<String> kbArticle122 = Arrays.asList("KB4534");
//		solutions122.add(docs122);
//		solutions122.add(workaround122);
//		solutions122.add(kbArticle122);
		advancedErrorSolutions.put(122, solutions122);
		
		String solutions123 = "Section3.3,Page179_Reset_browser_settings_to_default_KB1514";
		List<String> docs123 = Arrays.asList("Section 3.3, Page 179");
		List<String> workaround123 = Arrays.asList("Reset browser settings to default");
		List<String> kbArticle123 = Arrays.asList("KB1514");
//		solutions123.add(docs123);
//		solutions123.add(workaround123);
//		solutions123.add(kbArticle123);
		advancedErrorSolutions.put(123, solutions123);
		
		String solutions124 = "Section_3.4,Page_190_Try_compatibility_mode_if_available_KB1700";
		List<String> docs124 = Arrays.asList("Section 3.4, Page 190");
		List<String> workaround124 = Arrays.asList("Try compatibility mode if available");
		List<String> kbArticle124 = Arrays.asList("KB1700");
//		solutions124.add(docs124);
//		solutions124.add(workaround124);
//		solutions124.add(kbArticle124);
		advancedErrorSolutions.put(124, solutions124);
		
		
		String solutions125 = "Section3.5,Page200_Update_network/ethernet_drivers_KB1711";
		List<String> docs125 = Arrays.asList("Section 3.5, Page 200");
		List<String> workaround125 = Arrays.asList("Update network/ethernet drivers");
		List<String> kbArticle125 = Arrays.asList("KB1711");
//		solutions125.add(docs125);
//		solutions125.add(workaround125);
//		solutions125.add(kbArticle125);
		advancedErrorSolutions.put(125, solutions125);
		
		String solutions126 = "Section4.1,Page244_Use_IP_address_instead_of_hostname/URL_KB1800";
		List<String> docs126 = Arrays.asList("Section 4.1, Page 244");
		List<String> workaround126 = Arrays.asList("Use IP address instead of hostname/URL");
		List<String> kbArticle126 = Arrays.asList("KB1800");
//		solutions126.add(docs126);
//		solutions126.add(workaround126);
//		solutions126.add(kbArticle126);
		advancedErrorSolutions.put(126, solutions126);
		
		
		String solutions127 = "Section4.2,Page288_Verify_file_permissions_and_ownership_KB1991";
		List<String> docs127 = Arrays.asList("Section 4.2, Page 288");
		List<String> workaround127 = Arrays.asList("Verify file permissions and ownership");
		List<String> kbArticle127 = Arrays.asList("KB1991");
//		solutions127.add(docs127);
//		solutions127.add(workaround127);
//		solutions127.add(kbArticle127);
		advancedErrorSolutions.put(127, solutions127);
		
		String solutions128 = "Section4.3,Page300_Check_for_disk_space,cleanup_if_needed_KB2000";
		List<String> docs128 = Arrays.asList("Section 4.3, Page 300");
		List<String> workaround128 = Arrays.asList("Check for disk space, cleanup if needed");
		List<String> kbArticle128 = Arrays.asList("KB2000");
//		solutions128.add(docs128);
//		solutions128.add(workaround128);
//		solutions128.add(kbArticle128);
		advancedErrorSolutions.put(128, solutions128);
		
		String solutions129 = "Section4.4,Page310_Verify_database_connection_settings_KB2212";
//		List<String> docs129 = Arrays.asList("Section 4.4, Page 310");
//		List<String> workaround129 = Arrays.asList("Verify database connection settings");
//		List<String> kbArticle129 = Arrays.asList("KB2212");
//		solutions129.add(docs129);
//		solutions129.add(workaround129);
//		solutions129.add(kbArticle129);
		advancedErrorSolutions.put(129, solutions129);
		
		String solutions130 = "Section4.5,Page350_Retry_operation_at_off-peak_time_KB2333";
//		List<String> docs130 = Arrays.asList("Section 4.5, Page 350");
//		List<String> workaround130 = Arrays.asList("Retry operation at off-peak time");
//		List<String> kbArticle130 = Arrays.asList("KB2333");
//		solutions130.add(docs130);
//		solutions130.add(workaround130);
//		solutions130.add(kbArticle130);
		advancedErrorSolutions.put(130, solutions130);
//		Section4.5, Page 350 Retry operation at off-peak time KB2333
		
	}

	public void deltext(double e, message x) {
		Continue(e);
		clock = clock + e;
//		It is because of all the simulators has to be executed. What is the minimum of nextTN of these three atomic models
//		

//		System.out.println("The elapsed time of the processor is" + e);
//		System.out.println("*****************************************");
//		System.out.println("external-Phase before: "+phase);
		
		for (int i = 0; i < x.getLength(); i++) {
			if (phaseIs("passive")) {
				if (messageOnPort(x, "inLevelII", i)) {
					caseDetails = x.getValOnPort("inLevelII", i);
					arrived.put(caseDetails.getName(),new doubleEnt(clock));
					String[] parts = caseDetails.toString().trim().split(" ");
					solution = parts[13].trim();
					errorCode = Integer.parseInt(parts[4].toString());
					if(solution.equals("none")) {
						holdIn("busy", processing_time);
					}
					else {
						advancedErrorSolutions.put(errorCode,solution);
						holdIn("updating_db",updating_db_time);
					}
				}
			}
			else if (phaseIs("busy")|| phaseIs("updating_db")) {
				if (messageOnPort(x,"inLevelII",i)) {
					casesQueue.add(x.getValOnPort("inLevelII",i));
					arrived.put(x.getValOnPort("inLevelII",i).getName(),new doubleEnt(clock));
					
				}
			}
		}
		/*
		if (phaseIs("passive")) {
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "inLevelII", i)) {
					caseDetails = x.getValOnPort("inLevelII", i);
					arrived.put(caseDetails.getName(),new doubleEnt(clock));
					String[] parts = caseDetails.toString().trim().split(" ");
					errorCode = Integer.parseInt(parts[4].toString());
					holdIn("busy", 10);
				}
		}
		else if (phaseIs("busy")) {
			for (int i=0; i < x.getLength(); i++) {
				if (messageOnPort(x,"inLevelII",i)) {
					casesQueue.add(x.getValOnPort("inLevelII",i));
					arrived.put(x.getValOnPort("inLevelII",i).getName(),new doubleEnt(clock));
					
				}
			}
		}
		*/
		/*	
		if (phaseIs("passive"))
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "inLevelII", i)) {
					caseDetails = x.getValOnPort("inLevelII", i);
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
				advancedErrorSolutions.put(errorCode,solution);
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
	                             parts[6].trim()+" "+parts[7].trim()+" "+"true"+" "+parts[9].trim()+" "+
	                             parts[10].trim()+" "+case_ta_time+" "+parts[12].trim());
		if (phaseIs("busy") || phaseIs("updating_db")) {
			if ( !solution.equals("none") ) {
				System.out.println("The resolved case is directed from EE to TSE");
				m.add(makeContent("resolvedLevelII",caseDetails));
			}
			else if(advancedErrorSolutions.containsKey(errorCode)) {
				System.out.println("The case has been resolved by Level- II");
				m.add(makeContent("resolvedLevelII", new entity(parts[0].trim()+" "+parts[1].trim()+" "+parts[2].trim()+" "+
                        parts[3].trim()+" "+parts[4].trim()+" "+parts[5].trim()+" "+
                        parts[6].trim()+" "+parts[7].trim()+" "+"true"+" "+parts[9].trim()+" "+
                        parts[10].trim()+" "+case_ta_time+" "+parts[12].trim()+" "+advancedErrorSolutions.get(errorCode)+" "+"EE"))); // this is fine
				/*advancedErrorSolutions.get(errorCode))*/
			} else {
				m.add(makeContent("escalateLevelIII", caseDetails_out));
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

