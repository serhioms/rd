package ca.mss.rd.workflow.reader.xpdl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


import ca.mss.rd.util.UtilValidate;

import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfActivityInstantiation;
import ca.mss.rd.workflow.def.WkfActivityMode;
import ca.mss.rd.workflow.def.WkfActivityToolType;
import ca.mss.rd.workflow.def.WkfTool;
import ca.mss.rd.workflow.def.WkfTransitionType;
import ca.mss.rd.workflow.impl.WkfToolDefinition;
import ca.mss.rd.workflow.impl.WkfToolImpl;
import ca.mss.rd.workflow.parser.xpdl.Activity;
import ca.mss.rd.workflow.parser.xpdl.ArrayType;
import ca.mss.rd.workflow.parser.xpdl.Condition;
import ca.mss.rd.workflow.parser.xpdl.DataField;
import ca.mss.rd.workflow.parser.xpdl.DataType;
import ca.mss.rd.workflow.parser.xpdl.ExtendedAttribute;
import ca.mss.rd.workflow.parser.xpdl.FinishMode;
import ca.mss.rd.workflow.parser.xpdl.FormalParameter;
import ca.mss.rd.workflow.parser.xpdl.SimulationInformation;
import ca.mss.rd.workflow.parser.xpdl.StartMode;
import ca.mss.rd.workflow.parser.xpdl.Tool;
import ca.mss.rd.workflow.parser.xpdl.Transition;
import ca.mss.rd.workflow.parser.xpdl.TransitionRestriction;
import ca.mss.rd.workflow.parser.xpdl.WorkflowProcess;
import ca.mss.rd.workflow.reader.WkfReader;

public class WkfReaderXPDL implements WkfReader {

	final public static String module = WkfReaderXPDL.class.getName();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final private static String WKF_PACKAGE = "ca.mss.rd.workflow.parser.xpdl";
	
	private JAXBContext jaxbContext;
	private Unmarshaller unmarshaller;
	
	private ca.mss.rd.workflow.parser.xpdl.Package xpdlPackage;
	private List<WorkflowProcess> processes;
	
	
	/**
	 * @return the jaxbContext
	 */
	final public JAXBContext getJaxbContext() {
		if( jaxbContext == null ){
			try {
				setJaxbContext(JAXBContext.newInstance( WKF_PACKAGE ));
			}catch(JAXBException e){
				throw new RuntimeException(e);
			}
		}
		return jaxbContext;
	}



	/**
	 * @param jaxbContext the jAXBContext to set
	 */
	final public void setJaxbContext(JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}



	/**
	 * @return the unmarshaller
	 */
	final public Unmarshaller getUnmarshaller() {
		if( unmarshaller == null ){
			try {
				setUnmarshaller(getJaxbContext().createUnmarshaller());
			}catch(JAXBException e){
				throw new RuntimeException(e);
			}
		}
		return unmarshaller;
	}



	/**
	 * @param unmarshaller the unmarshaller to set
	 */
	final public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}



	/**
	 * @return the xpdlPackage
	 */
	final public ca.mss.rd.workflow.parser.xpdl.Package getXpdlPackage() {
		if( xpdlPackage == null ){
			throw new RuntimeException("Parse XPDL file first like parseXpdl(path)");
		}
		return xpdlPackage;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#parse(java.lang.String)
	 */
	final public void parseWorkflow(String xpdlFile) {
		try {
			InputStream in = new FileInputStream(xpdlFile);
			setXpdlPackage((ca.mss.rd.workflow.parser.xpdl.Package)getUnmarshaller().unmarshal(in));
			setProcesses(getXpdlPackage().getWorkflowProcesses().getWorkflowProcess());
			in.close();
		}catch(JAXBException e){
			throw new RuntimeException("Can't parse [file="+xpdlFile+"]", e);
		}catch(FileNotFoundException e){
			throw new RuntimeException("Can't find [file="+xpdlFile+"]", e);
		}catch(IOException e){
			throw new RuntimeException("Can't read [file="+xpdlFile+"]", e);
		}
	}



	/**
	 * @param xpdlPackage the xpdlPackage to set
	 */
	final public void setXpdlPackage(ca.mss.rd.workflow.parser.xpdl.Package xpdlPackage) {
		this.xpdlPackage = xpdlPackage;
	}


	final public String getPackageId(){
		return getXpdlPackage().getId();
	}
	final public String getPackageName(){
		return getXpdlPackage().getName();
	}

	/**
	 * @return the workflowProcess
	 */
	final public List<WorkflowProcess> getProcesses() {
		return processes;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getProcessSize()
	 */
	@Override
	public int getProcessSize() {
		return getProcesses().size();
	}

	/**
	 * @param processes the processes to set
	 */
	final public void setProcesses(List<WorkflowProcess> processes) {
		this.processes = processes;
	}

	final public int processSize() {
		return getProcesses().size();
	}

	final public WorkflowProcess getProcess(int i) {
		return getProcesses().get(i);
	}

	final public String getProcessId(int i) {
		return getProcess(i).getId();
	}

	final public String getProcessName(int i) {
		return getProcess(i).getName();
	}

	final public List<FormalParameter> getFormalParameters(int i) {
		return getProcess(i).getFormalParameters().getFormalParameter();
	}

	final public List<DataField> getDataField(int i) {
		return getProcess(i).getDataFields().getDataField();
	}

	final public int getDataFieldSize(int i) {
		return getProcess(i).getDataFields().getDataField().size();
	}

	final public String getDataFieldId(int process, int field) {
		return getDataField(process).get(field).getId();
	}

	final public String getDataFieldDescr(int process, int field) {
		return getDataField(process).get(field).getDescription();
	}

	final public String getDataFieldName(int process, int field) {
		return getDataField(process).get(field).getName();
	}

	final public String getDataFieldInitialValue(int process, int field) {
		return getDataField(process).get(field).getInitialValue();
	}

	final public String getDataFieldType(int process, int field) {
		return getType(getDataField(process).get(field).getDataType());
	}

	final public int getFormalParametersSize(int process) {
		return getFormalParameters(process).size();
	}

	final public String getFormalParameterId(int process, int parameter) {
		return getFormalParameters(process).get(parameter).getId();
	}

	final public String getFormalParameterDescr(int process, int parameter) {
		return notNull(getFormalParameters(process).get(parameter).getDescription());
	}

	final public String getFormalParameterIndex(int process, int parameter) {
		return getFormalParameters(process).get(parameter).getIndex();
	}

	final public String getFormalParameterMode(int process, int parameter) {
		return getFormalParameters(process).get(parameter).getMode();
	}

	final public String getFormalParameterType(int process, int parameter) {
		return getType(getFormalParameters(process).get(parameter).getDataType());
	}
	
	final private String getType(DataType type){
		if( type.getArrayType() != null )
			return "ArrayType["+getType(type.getArrayType())+"]";
		else if( type.getBasicType() != null )
			return type.getBasicType().getType();
		else if( type.getDeclaredType() != null )
			return "DeclaredType";
		else if( type.getEnumerationType() != null )
			return "EnumerationType"+type.getEnumerationType().getEnumerationValue();
		else if( type.getExternalReference() != null )
			return "ExternalReference["+type.getExternalReference().getLocation()+","+type.getExternalReference().getNamespace()+"]";
		else if( type.getListType() != null )
			return "ListType["+getType(type.getArrayType())+"]";
		else if( type.getRecordType() != null )
			return "RecordType"+type.getRecordType().getMember();
		else if( type.getSchemaType() != null )
			return "SchemaType"+type.getSchemaType().getAny();
		else if( type.getUnionType() != null )
			return "UnionType"+type.getUnionType().getMember();
		else
			return "";
	}

	final private String getType(ArrayType type){
		if( type.getArrayType() != null )
			return "ArrayType["+getType(type.getArrayType())+"]";
		else if( type.getBasicType() != null )
			return type.getBasicType().getType();
		else if( type.getDeclaredType() != null )
			return "DeclaredType";
		else if( type.getEnumerationType() != null )
			return "EnumerationType"+type.getEnumerationType().getEnumerationValue();
		else if( type.getExternalReference() != null )
			return "ExternalReference["+type.getExternalReference().getLocation()+","+type.getExternalReference().getNamespace()+"]";
		else if( type.getListType() != null )
			return "ListType["+getType(type.getArrayType())+"]";
		else if( type.getRecordType() != null )
			return "RecordType"+type.getRecordType().getMember();
		else if( type.getSchemaType() != null )
			return "SchemaType"+type.getSchemaType().getAny();
		else if( type.getUnionType() != null )
			return "UnionType"+type.getUnionType().getMember();
		else
			return "";
	}

	/**
	 * @return the getWorkflowActivities
	 */
	final public List<Activity> getActivities(int process) {
		return getProcess(process).getActivities().getActivity();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivitySize(int)
	 */
	final public int getActivitySize(int process) {
		return getActivities(process).size();
	}


	final public Activity getActivity(int process, int activity) {
		return getActivities(process).get(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityId(int, int)
	 */
	final public String getActivityId(int process, int activity) {
		return getActivity(process, activity).getId();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityName(int, int)
	 */
	final public String getActivityName(int process, int activity) {
		return getActivity(process, activity).getName();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityDesc(int, int)
	 */
	final public String getActivityDesc(int process, int activity) {
		return notNull(getActivity(process, activity).getDescription());
	}
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityStartMode(int, int)
	 */
	final public WkfActivityMode getActivityStartMode(int process, int activity) {
		return getTransitionRestrictions(getActivity(process, activity), getStartMode(getActivity(process, activity).getStartMode()))[0];
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityPriority(int, int)
	 */
	final public int getActivityPriority(int process, int activity) {
		try {
			return Integer.parseInt(getActivity(process, activity).getPriority());
		} catch (NumberFormatException e){
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityInstantiation(int, int)
	 */
	final public WkfActivityInstantiation getActivityInstantiation(int process, int activity) {
		SimulationInformation simulation = getActivity(process, activity).getSimulationInformation();
		if( simulation == null )
			return WkfActivityInstantiation.Once;
		
		String instantiation = simulation.getInstantiation();
		if( "ONCE".equals(instantiation) )
			return WkfActivityInstantiation.Once;
		else if( "MULTIPLE".equals(instantiation) )
			return WkfActivityInstantiation.Multiple;
		else 
			throw new RuntimeException("Unknown activity instantiation ["+instantiation+"]");
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityCanStart(int, int)
	 */
	final public boolean getActivityCanStart(int process, int activity) {
		List<ExtendedAttribute> extattrs = getActivity(process, activity).getExtendedAttributes().getExtendedAttribute();
		for(int i=0, max=extattrs.size(); i<max; i++ ){
			ExtendedAttribute extattr = extattrs.get(i);
			if( "canStart".equals(extattr.getName()) )
				return "Y".equals(extattr.getValue());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityFinishMode(int, int)
	 */
	final public WkfActivityMode getActivityFinishMode(int process, int activity) {
		return getTransitionRestrictions(getActivity(process, activity), getFinishMode(getActivity(process, activity).getFinishMode()))[1];
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getActivityImplementation(int, int)
	 */
	final public WkfActivityImplementation getActivityImplementation(int process, int activity) {
		return getImplementation(getActivity(process, activity));
	}

	
	final public WkfTool[] getActivityTool(int process, int activity) {
		WkfToolImpl[] wkftools = new WkfToolImpl[0]; // empty by default
		
		List<Tool> tools = getActivity(process, activity).getImplementation().getTool();

		if( tools.size() > 0 ){
			
			wkftools = new WkfToolImpl[tools.size()];
	
			for(int i=0, maxi=tools.size(); i<maxi; i++){
				
				WkfToolImpl wkftool = new WkfToolImpl();
				wkftools[i] = wkftool; 
				
				Tool tool = tools.get(i);
				List<String> params = tool.getActualParameters().getActualParameter();
				String type = tool.getType();
				
				if( "PROCEDURE".equals(type) )
					wkftool.setType(WkfActivityToolType.Procedure);
				else
					throw new RuntimeException("Unknown activity implementation type ["+type+"]");
				
				wkftool.setId(notNull(tool.getId()));
				wkftool.setDescr(tool.getDescription());
				wkftool.setParam(params); 				
				List<ExtendedAttribute> attrs = tool.getExtendedAttributes().getExtendedAttribute();
				if( attrs.size() > 0){
					
					Map<String, WkfToolDefinition> definitions = new HashMap<String, WkfToolDefinition>();
					
					for(int j=0, maxj=attrs.size(); j<maxj; j++){
						
						WkfToolDefinition definition = new WkfToolDefinition();

						ExtendedAttribute attr = attrs.get(j);
						definition.name = attr.getName();
						definition.value = attr.getValue();
						if( attr.getContent() != null && attr.getContent().size() > 0 ){
							definition.content = attr.getContent().toString();
							if( "[]".equals(definition.content) )
								definition.content = null;
						} else {
							definition.content = null;
						}
						
						definitions.put(definition.name, definition); 
					}
					
					wkftool.setDefinition(definitions);
				}
			}
		}
		return wkftools;
	}

	
	final public List<Transition> getTransitions(int process) {
		return getProcess(process).getTransitions().getTransition();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getTransitionSize(int)
	 */
	final public int getTransitionSize(int process) {
		return getTransitions(process).size();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getTransitionId(int, int)
	 */
	final public String getTransitionId(int process, int transition) {
		return getTransitions(process).get(transition).getId();
	}

	final public String getTransitionName(int process, int transition) {
		return notNull(getTransitions(process).get(transition).getName());
	}

	final public String getTransitionDescr(int process, int transition) {
		return notNull(getTransitions(process).get(transition).getDescription());
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getTransitionFrom(int, int)
	 */
	final public String getTransitionFrom(int process, int transition) {
		return getTransitions(process).get(transition).getFrom();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getTransitionTo(int, int)
	 */
	final public String getTransitionTo(int process, int transition) {
		return getTransitions(process).get(transition).getTo();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getTransitionType(int, int)
	 */
	final public WkfTransitionType getTransitionType(int process, int transition) {
		Condition condition = getTransitions(process).get(transition).getCondition();
		
		if( condition == null )
			return WkfTransitionType.Goto;
		
		String type = condition.getType();
		
		if( type == null )
			return WkfTransitionType.Goto;
		else if ("CONDITION".equals(type) )
			return WkfTransitionType.Condition;
		else if ("OTHERWISE".equals(type) )
			return WkfTransitionType.Otherwise;
		else {
			throw new RuntimeException("Unknown transition type ["+type+"]");
		}	
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.reader.WkfReader#getTransitionExpression(int, int)
	 */
	final public String getTransitionExpression(int process, int transition) {
		Condition condition = getTransitions(process).get(transition).getCondition();
		List<Object> content = condition.getContent();
		String expr = "";
		for(int i=0, max=content.size(); i<max; i++){
			expr = expr + content.get(i).toString().trim();
		}
		return notNull(expr);
	}

	final private String notNull(String str){
		return str != null && !"".equals(str) && !"[]".equals(str)? str: "";
	}
	
	final private WkfActivityMode[] getTransitionRestrictions(Activity a, WkfActivityMode mode){
		WkfActivityMode[] result = new WkfActivityMode[]{mode, mode}; // by default
		
		if( a.getTransitionRestrictions() == null )
			return result; 
		
		List<TransitionRestriction> rl = a.getTransitionRestrictions().getTransitionRestriction();
		for(int i=0, maxi=rl.size(); i<maxi; i++){
			TransitionRestriction r = rl.get(i);
			if( r.getJoin() != null ){
				result[0] = getActivityMode(r.getJoin().getType(), mode);
			} else if( r.getSplit() != null ){
				result[1] = getActivityMode(r.getSplit().getType(), mode);
			}
		}
		
		return result;
	}
	
	final private WkfActivityMode getActivityMode(String type, WkfActivityMode mode){
		if( "AND".equals(type) )
			if( mode == WkfActivityMode.AutoXor || mode == WkfActivityMode.AutoAnd )
				return  WkfActivityMode.AutoAnd;
			else
				return  WkfActivityMode.ManualAnd;
		if( "XOR".equals(type) )
			if( mode == WkfActivityMode.AutoXor || mode == WkfActivityMode.AutoAnd )
				return  WkfActivityMode.AutoXor;
			else
				return  WkfActivityMode.ManualXor;
		return WkfActivityMode.AutoXor; // by default
	}
	
	
	final private WkfActivityMode getStartMode(StartMode m){
		if( m.getAutomatic() != null )
			return WkfActivityMode.AutoXor;
		else if( m.getManual() != null )
			return WkfActivityMode.ManualXor;
		else
			return WkfActivityMode.AutoXor; // by default
	}
	
	final private WkfActivityMode getFinishMode(FinishMode m){
		if( m.getAutomatic() != null )
			return WkfActivityMode.AutoXor;
		else if( m.getManual() != null )
			return WkfActivityMode.ManualXor;
		else
			return WkfActivityMode.AutoXor; // by default
	}
	
	final private WkfActivityImplementation getImplementation(Activity a){
		if( a.getRoute() != null )
			return WkfActivityImplementation.Root;
		else if( a.getImplementation() == null )
			return WkfActivityImplementation.Root; // by default
		else if( a.getImplementation().getNo() != null )
			return WkfActivityImplementation.NoImplementation;
		else if( a.getImplementation().getSubFlow() != null )
			return WkfActivityImplementation.SubFlow;
		else if( a.getImplementation().getTool() != null )
			return WkfActivityImplementation.Tool;
		else	
			return WkfActivityImplementation.Root; // by default
	}



	@Override
	final public Map<String, Object> getInitialData(int process) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DataField> dataField = getProcess(process).getDataFields().getDataField();
		for(int field=0, max=dataField.size(); field <max; field++){
			String key = dataField.get(field).getId();
			Object value = dataField.get(field).getInitialValue();
			if( UtilValidate.isNotEmpty(key) )
				map.put(key, value);
		}
		if( logger.isDebugEnabled()) logger.debug("READ INITIAL DATA "+map);
		return map;
	}

	
}
