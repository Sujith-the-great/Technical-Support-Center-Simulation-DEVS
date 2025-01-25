package Component.Project;

import java.util.Objects;

public class CustomerCase {
	
	protected int severity;
	protected int priority;
	protected String customerName;
	protected String problemDescription;
	protected int errorCode;
	protected String productType;
	protected boolean tseProcessed;
	protected boolean eeProcessed;
	protected boolean pseProcessed;
	protected double tseRstnTime;
	protected double eeRstnTime;
	protected double pseRstnTime;
	protected String solution;
	
	public CustomerCase() {
		
	}
	
	public CustomerCase(int severity, int priority, String customerName, String problemDescription, int errorCode,
			String productType) {
		super();
		this.severity = severity;
		this.priority = priority;
		this.customerName = customerName;
		this.problemDescription = problemDescription;
		this.errorCode = errorCode;
		this.productType = productType;
		this.tseProcessed = false;
		this.eeProcessed = false;
		this.pseProcessed = false;
		this.tseRstnTime = 0;
		this.eeRstnTime = 0;
		this.pseRstnTime = 0;
		this.solution = "none";
	}

//	Severity of the product can be in a range of 1-5
	
	@Override
	public int hashCode() {
		return Objects.hash(customerName, errorCode, priority, problemDescription, productType, severity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerCase other = (CustomerCase) obj;
		return Objects.equals(customerName, other.customerName) && errorCode == other.errorCode
				&& priority == other.priority && Objects.equals(problemDescription, other.problemDescription)
				&& Objects.equals(productType, other.productType) && severity == other.severity;
	}

	public int getSeverity() {
		return severity;
	}
	
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
//	Priority of the product can be in a range of 1-5
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
//	Possible customers who have raised this case
	
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
//	Problem Description regarding the case
	
	public String getProblemDescription() {
		return problemDescription;
	}
	
	
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	
//	Possible error codes
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
//	ProductType against whom the case has been registered
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}

// boolean value indicating whether case is processed by TSE
	
	public boolean getTseProcessed() {
		return this.tseProcessed;
	}
	public void setTseProcessed(boolean tseProcessed) {
		this.tseProcessed = tseProcessed;
	}
	
// boolean value indicating whether case is processed by EE	
	
	public boolean getEeProcessed() {
		return this.eeProcessed;
	}
	public void setEeProcessed(boolean eeProcessed) {
		this.eeProcessed = eeProcessed;
	}

// boolean value indicating whether case is processed by PSE	
		
    public boolean getPseProcessed() {
		return this.pseProcessed;
	}
	public void setPseProcessed(boolean pseProcessed) {
		this.pseProcessed = pseProcessed;	
	}



//double value indicating time taken by TSE to resolve the case

    public double getTseRstnTime() {
	    return this.tseRstnTime;
    }
    public void setTseRstnTime(double tseRstnTime) {
    	this.tseRstnTime = tseRstnTime;
    }
    
//double value indicating time taken by EE to resolve the case

    public double getEeRstnTime() {
	    return this.eeRstnTime;
    }
    public void setEeRstnTime(double eeRstnTime) {
    	this.eeRstnTime = eeRstnTime;
    }  
    
//double value indicating time taken by PSE to resolve the case

    public double getPseRstnTime() {
	    return this.pseRstnTime;
    }
    public void setPseRstnTime(double pseRstnTime) {
    	this.pseRstnTime = pseRstnTime;
    }    
    
//String value indicating the solution. If "none", the case is unresolved.

    public String getSolution() {
	    return this.solution;
    }
    public void setSolution(String solution) {
    	this.solution = solution;
    }        
}