package Component.Project;

import java.awt.Color;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import GenCol.*;

import model.modeling.*;
import model.simulation.*;

import view.modeling.ViewableAtomic;
import view.simView.*;

public class CustomerCaseGenerator extends ViewableAtomic {

	protected double int_arr_time;
	protected int caseCount;
	static int c = 0;
//	protected CustomerCase customerCase;
	protected List<CustomerCase> customerCaseList = new ArrayList<>();
	protected int priority;
	protected int severity;
	protected int errorCode;
	protected String customerName;
	protected String productType;
	protected String problemDescription;
	protected CustomerCase customerCase;

	public CustomerCaseGenerator() {
		this("CustomerCaseGenerator", 10);
//		customerCase = new CustomerCase();
	}

	public CustomerCaseGenerator(String name, double Int_arr_time) {
		super(name);
		addInport("in");
		addOutport("out");
		addInport("stop");
		addInport("start");
		int_arr_time = Int_arr_time;

		addTestInput("start", new entity(""));
		addTestInput("stop", new entity(""));
		
		setBackgroundColor(Color.orange);
	}

	public void initialize() {
		holdIn("active", int_arr_time);

		// phase = "passive";
		// sigma = INFINITY;
		sigma = 0;
		caseCount = 0;
		customerCase = new CustomerCase();
//		generateDifferentCustomerCases();
		super.initialize();
	}

	public void deltext(double e, message x) {
		Continue(e);
//		System.out.println("******************** elapsed item for generator is " + e + "************************");
		if (phaseIs("passive")) {
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "start", i)) {

					holdIn("active", int_arr_time);
				}
		}
		if (phaseIs("active"))
			for (int i = 0; i < x.getLength(); i++)
				if (messageOnPort(x, "stop", i))
					phase = "finishing";
	}

	public void deltint() {
		/*
		 * System.out.println(name+" deltint count "+count);
		 * System.out.println(name+" deltint int_arr_time "+int_arr_time);
		 * System.out.println(name+" deltint tL "+tL);
		 * System.out.println(name+" deltint tN "+tN);
		 */

		// System.out.println("********generator**********" + c);
		if (phaseIs("active")) {
//			caseCount = caseCount + 1;
			CustomerCase customerCase = generateCase();
			customerCaseList.add(customerCase);
			holdIn("active", int_arr_time);
		} else
			passivate();
	}
	
	public CustomerCase generateCase() {
		priority = generateRandomPriority();
		severity = generateRandomSeverity();
		errorCode = generateRandomErrorCode();
		customerName = generateRandomCustomerName();
		productType = generateRandomProductType();
		problemDescription = generateRandomProblemDescription();
		
		customerCase.setPriority(priority);
		customerCase.setSeverity(severity);
		customerCase.setCustomerName(customerName);
		customerCase.setErrorCode(errorCode);
		customerCase.setProblemDescription(problemDescription);
		customerCase.setProductType(productType);
		customerCase.setTseProcessed(false);
		customerCase.setEeProcessed(false);
		customerCase.setPseProcessed(false);
		customerCase.setTseRstnTime(0);
		customerCase.setEeRstnTime(0);
		customerCase.setPseRstnTime(0);
		customerCase.setSolution("none");
		
		return customerCase;
		
	}

	public message out() {

		// System.out.println(name+" out count "+count);

		message m = new message();
		entity ent = new entity("INVALID");
		CustomerCase customerCase = customerCaseList.get(caseCount);
		System.out.println(caseCount);
//		System.out.println(customerCase.getSeverity() + " " +  customerCase.getPriority() + " " +
//						customerCase.getCustomerName() + " "+ customerCase.getErrorCode() + " " +
//						customerCase.getProblemDescription() + " " + customerCase.getProductType());
																	
		content con = makeContent("out", new entity("customerCase" + " " + customerCase.getSeverity()+ " " + 
																	 customerCase.getPriority()+ " " +
																	customerCase.getCustomerName()+ " " +
																	 customerCase.getErrorCode()+ " " +
																	customerCase.getProblemDescription()+ " " +
																	customerCase.getProductType()+ " " +
																	customerCase.getTseProcessed()+ " " +
																	customerCase.getEeProcessed()+ " " +
																	customerCase.getPseProcessed()+ " " +
																	customerCase.getTseRstnTime()+ " " +
																	customerCase.getEeRstnTime()+ " " +
																	customerCase.getPseRstnTime()+" "+
																	customerCase.getSolution()));
		caseCount = caseCount + 1;
		m.add(con);

		return m;
	}
	
	public int generateRandomPriority() {
		int randomPriority = ThreadLocalRandom.current().nextInt(1, 6);
		return randomPriority;
	}
	
	public int generateRandomSeverity() {
		int randomPriority = ThreadLocalRandom.current().nextInt(1, 6);
		return randomPriority;
	}
	
	public int generateRandomErrorCode() {
		int randomErrorCode = ThreadLocalRandom.current().nextInt(100, 150);
		return randomErrorCode;
	}
	
	public String generateRandomCustomerName() {
		
		ArrayList<String> customerNameList = new ArrayList<>();
		customerNameList.add("Shreyansh");
		customerNameList.add("LockheedMartin");
		customerNameList.add("JohnSmith");
		customerNameList.add("USDeptofAgriculture");
		customerNameList.add("Sujith");
		customerNameList.add("Nalini");
		customerNameList.add("BobWilson");
		customerNameList.add("JaneDoe");
		customerNameList.add("AxelLopez");
		customerNameList.add("Michael");
		
		Random rand = new Random();
		
		String randomCustomerName = customerNameList.get(rand.nextInt(customerNameList.size()));
		
		return randomCustomerName;
	}
	
	public String generateRandomProductType() {
		
		ArrayList<String> productTypeList = new ArrayList<>();
		productTypeList.add("Amazon.com");
		productTypeList.add("AWS");
		
		Random rand = new Random();
		
		String randomProductType = productTypeList.get(rand.nextInt(productTypeList.size()));
		
		return randomProductType;
	}
	
	public String generateRandomProblemDescription() {
		
		ArrayList<String> problemDescriptionList = new ArrayList<>();
		problemDescriptionList.add("UI-Not-showing-up");
		problemDescriptionList.add("Page-Load-Errors");
		problemDescriptionList.add("Search-Issues");
		problemDescriptionList.add("Shopping-Cart-Errors");
		problemDescriptionList.add("CheckOut-failures");
		problemDescriptionList.add("Account-management-issues");
		problemDescriptionList.add("Recommendation-failures");
		problemDescriptionList.add("UI-too-slow");
		problemDescriptionList.add("Performance-problems");
		problemDescriptionList.add("Cross-scripting-security-issues");
		problemDescriptionList.add("Browser-Compatibility-issues");
		
		Random rand = new Random();
		
		String randomProblemDescription = problemDescriptionList.get(rand.nextInt(problemDescriptionList.size()));
		
		return randomProblemDescription;
	}
	
	
	

	public void showState() {
		super.showState();
//		System.out.println("int_arr_t: " + int_arr_time);
	}

	public String getTooltipText() {
		return super.getTooltipText() + "\n" + " int_arr_time: " + int_arr_time + "\n" + " count: " + caseCount;
	}

}
