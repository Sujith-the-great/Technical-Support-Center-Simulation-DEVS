package Component.Project;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import GenCol.doubleEnt;
import GenCol.entity;


import model.modeling.content;
import model.modeling.message;

import view.modeling.ViewableAtomic;

public class Transducer extends ViewableAtomic {
	 protected Map arrived, solved;
	 protected double clock,total_ta,observation_time;
	 public Double count=0.00;
	 public String resolvedLevel = "none";
	 protected int n_TSE_received=0;
	 protected int n_EE_received=0;
	 protected int n_PSE_received=0;
	 protected int n_TSE_resolved=0;
	 protected int n_EE_resolved=0;
	 protected int n_PSE_resolved=0;
	 protected int n_AWS=0;
	 protected int n_Amazon=0;
	 protected double total_TSE_rsln_time=0;
	 protected double total_EE_rsln_time=0;
	 protected double total_PSE_rsln_time=0;
	 protected double total_TSE_escalation_time=0;
	 protected double total_EE_escalation_time=0;
	 protected double total_PSE_escalation_time=0;
	 

	 public Transducer(String  name,double Observation_time){
	  super(name);
	  addInport("in");
	  addOutport("out");
	  addOutport("TA");
	  addOutport("Thru");
	  addOutport("n_TSE_received");
	  addOutport("n_EE_received");
	  addOutport("n_PSE_received");
	  addOutport("Avg_TSE_Escalation_time");
	  addOutport("Avg_EE_Escalation_time");
	  addOutport("n_TSE_resolved");
	  addOutport("n_EE_resolved");
	  addOutport("n_PSE_resolved");
	  addOutport("Avg_TSE_Rsln_time");
	  addOutport("Avg_EE_Rsln_time");
	  addOutport("Avg_PSE_Rsln_time");
	  addOutport("n_AWS");
	  addOutport("n_Amazon");
	  addInport("ariv");
	  addInport("solved");
	  //addOutport("out");
	  arrived = new HashMap();
	  solved = new HashMap();
	  observation_time = Observation_time;
	  addTestInput("ariv",new entity("caseDetails"));
	  addTestInput("solved",new entity("caseDetails"));
	  addTestInput("ariv",new entity("customerCase 3 5 Michael 117 Performance-problems Amazon.com"));
	  addTestInput("ariv",new entity("customerCase 1 2 Sujith 102 UI-Not-showing-up Amazon.com"));
	  addTestInput("ariv",new entity("customerCase 4 1 AxelLopez 119 Account-management issues AWS"));
	  addTestInput("ariv",new entity("customerCase 5 3 Shreyansh 121 Recommendation-failures Amazon.com"));
	  addTestInput("ariv",new entity("customerCase 2 4 LockheedMartin 110 Browser-Compatibility-issues Amazon.com"));
	  addTestInput("solved",new entity("customerCase 3 5 Michael 117 Performance-problems Amazon.com"));
	  addTestInput("solved",new entity("customerCase 1 2 Sujith 102 UI-Not-showing-up Amazon.com"));
	  addTestInput("solved",new entity("customerCase 4 1 AxelLopez 119 Account-management issues AWS"));
	  addTestInput("solved",new entity("customerCase 5 3 Shreyansh 121 Recommendation-failures Amazon.com"));
	  addTestInput("solved",new entity("customerCase 2 4 LockheedMartin 110 Browser-Compatibility-issues Amazon.com"));

	  initialize();
	  
	  setBackgroundColor(Color.PINK);
	 }

	 public Transducer() {this("TransducerPerformanceAnalyzer", 200);}

	 public void initialize(){
	  phase = "active";
	  sigma = observation_time;
	  clock = 0;
	  total_ta = 0;
	  resolvedLevel = "none";
	  n_TSE_received=0;
	  n_EE_received=0;
	  n_PSE_received=0;
	  n_TSE_resolved=0;
	  n_EE_resolved=0;
	  n_PSE_resolved=0; 
	  total_TSE_rsln_time=0;
	  total_EE_rsln_time=0;
	  total_PSE_rsln_time=0;
	  total_TSE_escalation_time=0;
	  total_EE_escalation_time=0;
	  n_AWS=0;
	  n_Amazon=0;
	  super.initialize();
	 }

	 public void showState(){
	  super.showState();
	  System.out.println("arrived: " + arrived.size());
	  System.out.println("solved: " + solved.size());
	  System.out.println("TA: "+compute_TA());
	  System.out.println("Thruput: "+compute_Thru());
	 }

	 public void  deltext(double e,message  x){
//		 System.out.println("--------Transduceer elapsed time ="+e);
//		 System.out.println("-------------------------------------");
	  clock = clock + e;
	  Continue(e);
	  entity  val;
	  for(int i=0; i< x.size();i++){
	    if(messageOnPort(x,"ariv",i)){
	       val = x.getValOnPort("ariv",i);
	           arrived.put(val.getName(),new doubleEnt(clock));
	    }
	    if(messageOnPort(x,"solved",i)){
	       String[] parts = x.getValOnPort("solved",i).toString().trim().split(" ");
	       val = new entity(parts[0].trim()+" "+parts[1].trim()+" "+parts[2].trim()+" "+
	                        parts[3].trim()+" "+parts[4].trim()+" "+parts[5].trim()+" "+
	    		            parts[6].trim()+" "+"false"+" "+"false"+" "+"false"+" 0.0 0.0 0.0 none");
	       resolvedLevel = parts[14].trim();
	       if(parts[6].trim().equals("AWS")) {n_AWS++;}
	       else if (parts[6].trim().equals("Amazon.com")) {n_Amazon++;}
	       boolean TSE_processed = Boolean.parseBoolean(parts[7].trim());
	       boolean EE_processed = Boolean.parseBoolean(parts[8].trim());
	       boolean PSE_processed = Boolean.parseBoolean(parts[9].trim());
	       System.out.println(resolvedLevel);
	       if (resolvedLevel.equals("TSE")) {
	    	       n_TSE_resolved++; 
	    	       total_TSE_rsln_time+=Double.parseDouble(parts[10].trim()); 
	    	       total_EE_escalation_time+= Double.parseDouble(parts[11].trim());
	    	   }
	       else if (resolvedLevel.equals("EE")) {
	    	       n_EE_resolved++; 
	    	       total_EE_rsln_time+=Double.parseDouble(parts[11].trim()); 
	    	       total_TSE_escalation_time+= Double.parseDouble(parts[10].trim());
	    	   }
	       else if (resolvedLevel.equals("PSE")) { 
	    	       n_PSE_resolved++; 
	    	       total_PSE_rsln_time+=Double.parseDouble(parts[12].trim());
	    	       total_TSE_escalation_time+= Double.parseDouble(parts[10].trim());
	    	       total_EE_escalation_time+= Double.parseDouble(parts[11].trim());
	    	   }
	       if (TSE_processed) { n_TSE_received++; }
	       if (EE_processed) { n_EE_received++; }
	       if (PSE_processed) { n_PSE_received++; }
	       count++;
	       /*
	       if(arrived.contains(val)){
	    	   System.out.println("Debug: val="+val);
	         entity  ent = (entity)arrived.assoc(val.getName());

	         doubleEnt  num = (doubleEnt)ent;
	         double arrival_time = num.getv();

	         double turn_around_time = clock - arrival_time;
	         total_ta = total_ta + turn_around_time;
	         solved.put(val, new doubleEnt(clock));
	       }
	       */
	       if(arrived.containsKey(val.getName())){
	         //entity  ent = (entity)arrived.assoc(val.getName());
	         entity  ent = (entity)arrived.get(val.getName());

	         doubleEnt  num = (doubleEnt)ent;
	         double arrival_time = num.getv();

	         double turn_around_time = clock - arrival_time;
	         total_ta = total_ta + turn_around_time;
	         solved.put(val.getName(), new doubleEnt(clock));
	       }
	    }
	  }
	show_state();
	 }

	 public void  deltint(){
	  clock = clock + sigma;
	  passivate();
	  show_state();
	 }

	 public  message    out( ){
	  message  m = new message();
	  content  con1 = makeContent("TA",new entity(" "+compute_TA()));
	  content  con2 = makeContent("out",new entity(count.toString()));
	  content  con3 = makeContent("Thru",new entity(" "+compute_Thru()));
	  content  con4 = makeContent("n_TSE_resolved",new doubleEnt(n_TSE_resolved));
	  content  con5 = makeContent("n_EE_resolved",new doubleEnt(n_EE_resolved));
	  content  con6 = makeContent("n_PSE_resolved",new doubleEnt(n_PSE_resolved));
	  content  con7 = makeContent("n_TSE_received",new doubleEnt(n_TSE_received));
	  content  con8 = makeContent("n_EE_received",new doubleEnt(n_EE_received));
	  content  con9 = makeContent("n_PSE_received",new doubleEnt(n_PSE_received));
	  content  con10 = makeContent("Avg_TSE_Rsln_time",new doubleEnt(total_TSE_rsln_time/n_TSE_resolved));
	  content  con11 = makeContent("Avg_EE_Rsln_time",new doubleEnt(total_EE_rsln_time/n_EE_resolved));
	  content  con12 = makeContent("Avg_PSE_Rsln_time",new doubleEnt(total_PSE_rsln_time/n_PSE_resolved));
	  content  con13 = makeContent("Avg_TSE_Escalation_time",new doubleEnt(total_TSE_escalation_time/(n_TSE_received-n_TSE_resolved)));
	  content  con14 = makeContent("Avg_EE_Escalation_time",new doubleEnt(total_EE_escalation_time/(n_EE_received-n_EE_resolved)));
	  content  con15 = makeContent("n_AWS",new doubleEnt(n_AWS));
	  content  con16 = makeContent("n_Amazon",new doubleEnt(n_Amazon));
	  m.add(con1);
	  m.add(con2);
	  m.add(con3);
	  m.add(con4);
	  m.add(con5);
	  m.add(con6);
	  m.add(con7);
	  m.add(con8);
	  m.add(con9);
	  m.add(con10);
	  m.add(con11);
	  m.add(con12);
	  m.add(con13);
	  m.add(con14);
	  m.add(con15);
	  m.add(con16);
	  return m;
	 }

	 public double compute_TA(){
	  double avg_ta_time = 0;
	  if(!solved.isEmpty())
	    avg_ta_time = ((double)total_ta)/solved.size();
	  return avg_ta_time;
	 }

	 
	 public double compute_Thru(){
	  double thruput = 0;
	  if(clock > 0)
	    thruput = solved.size()/(double)clock;
	  return thruput;
	 }

	 public void  show_state(){

	//  System.out.println("state of  "  +  name  +  ": " );
	// System.out.println("phase, sigma : "
//	          + phase  +  " "  +  sigma  +  " "   );

	 if (arrived != null && solved != null){
	//   System.out.println(" jobs arrived :" );
	  //          arrived.print_all();;

	//  System.out.println("total :"  +  arrived.size()  );


	//  System.out.println("jobs solved :" );
	  //          solved.print_all();
	//  System.out.println("total :"  +  solved.size()  );
	//  System.out.println("AVG TA = "  +  compute_TA()   );
	//  System.out.println("THRUPUT = "  +  compute_Thru()  );
	  }
	 }
}

