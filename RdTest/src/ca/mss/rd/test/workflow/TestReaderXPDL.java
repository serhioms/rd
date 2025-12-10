package ca.mss.rd.test.workflow;

import junit.framework.TestCase;

import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfTool;
import ca.mss.rd.workflow.def.WkfTransitionType;
import ca.mss.rd.workflow.reader.xpdl.WkfReaderXPDL;

public class TestReaderXPDL extends TestCase {

	public static final String module = TestReaderXPDL.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	private WkfReaderXPDL xpdlReader; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		xpdlReader = new WkfReaderXPDL();
	}

	/**
	 * 
	 */
	public void testReadXPDL(){

		xpdlReader.parseWorkflow("repository/triplechess1.xpdl");
		
		if( logger.isDebugEnabled()) logger.debug("Package   ID = "+xpdlReader.getPackageId());
		if( logger.isDebugEnabled()) logger.debug("Package Name = "+xpdlReader.getPackageName());
		
		for(int process=0, max1=xpdlReader.processSize(); process < max1; process++ ){

			if( logger.isDebugEnabled()) logger.debug("Process   ID = "+xpdlReader.getProcessId(process));
			if( logger.isDebugEnabled()) logger.debug("Process Name = "+xpdlReader.getProcessName(process));
			
			if( logger.isDebugEnabled()) logger.debug("\n");
			if( logger.isDebugEnabled()) logger.debug("Process Formal Parameters\n");
			
			for(int parameter=0, max2=xpdlReader.getFormalParametersSize(process); parameter <max2; parameter++){
				if( logger.isDebugEnabled()) logger.debug("\n");
				if( logger.isDebugEnabled()) logger.debug("Parameter                    ID = "+xpdlReader.getFormalParameterId(process, parameter));
				if( logger.isDebugEnabled()) logger.debug("Parameter           Description = "+xpdlReader.getFormalParameterDescr(process, parameter));
				if( logger.isDebugEnabled()) logger.debug("Parameter                  Mode = "+xpdlReader.getFormalParameterMode(process, parameter));
				if( logger.isDebugEnabled()) logger.debug("Parameter                 Index = "+xpdlReader.getFormalParameterIndex(process, parameter));
				if( logger.isDebugEnabled()) logger.debug("Parameter                  Type = "+xpdlReader.getFormalParameterType(process, parameter));
			}

			if( logger.isDebugEnabled()) logger.debug("\n");
			if( logger.isDebugEnabled()) logger.debug("Process Data Fields\n");
			
			for(int field=0, max5=xpdlReader.getDataFieldSize(process); field <max5; field++){
				if( logger.isDebugEnabled()) logger.debug("\n");
				if( logger.isDebugEnabled()) logger.debug("Data                         ID = "+xpdlReader.getDataFieldId(process, field));
				if( logger.isDebugEnabled()) logger.debug("Data                Description = "+xpdlReader.getDataFieldDescr(process, field));
				if( logger.isDebugEnabled()) logger.debug("Data                       Name = "+xpdlReader.getDataFieldName(process, field));
				if( logger.isDebugEnabled()) logger.debug("Data                       Type = "+xpdlReader.getDataFieldType(process, field));
				if( logger.isDebugEnabled()) logger.debug("Data               InitialValue = "+xpdlReader.getDataFieldInitialValue(process, field));
			}

			if( logger.isDebugEnabled()) logger.debug("\n");
			if( logger.isDebugEnabled()) logger.debug("Process Activities\n");
		
			for(int activity=0, max3=xpdlReader.getActivitySize(process); activity <max3; activity++){
				if( logger.isDebugEnabled()) logger.debug("\n");
				if( logger.isDebugEnabled()) logger.debug("Activity                     ID = "+xpdlReader.getActivityId(process, activity));
				if( logger.isDebugEnabled()) logger.debug("Activity                   Name = "+xpdlReader.getActivityName(process, activity));
				if( logger.isDebugEnabled()) logger.debug("Activity            Description = "+xpdlReader.getActivityDesc(process, activity));
				if( logger.isDebugEnabled()) logger.debug("Activity               Priority = "+xpdlReader.getActivityPriority(process, activity));
				if( logger.isDebugEnabled()) logger.debug("Activity          Instantiation = "+xpdlReader.getActivityInstantiation(process, activity));
				if( logger.isDebugEnabled()) logger.debug("Activity              StartMode = "+xpdlReader.getActivityStartMode(process, activity));
				if( logger.isDebugEnabled()) logger.debug("Activity             FinishMode = "+xpdlReader.getActivityFinishMode(process, activity));

				WkfActivityImplementation activityImplementation = xpdlReader.getActivityImplementation(process, activity);
				if( logger.isDebugEnabled()) logger.debug("Activity         Implementation = "+activityImplementation);
				
				if( activityImplementation == WkfActivityImplementation.Tool ){
					WkfTool[] tools = xpdlReader.getActivityTool(process, activity);
					for(int i=0; i<tools.length; i++ ){
						if( logger.isDebugEnabled()) logger.debug("Activity              Tool Body = "+tools[i]+"}");
					}
				}
			}
		
			if( logger.isDebugEnabled()) logger.debug("\n");
			if( logger.isDebugEnabled()) logger.debug("Process Transitions\n");
			
			for(int transition=0, max4=xpdlReader.getTransitionSize(process); transition <max4; transition++){
				if( logger.isDebugEnabled()) logger.debug("\n");
				if( logger.isDebugEnabled()) logger.debug("Transition                     ID = "+xpdlReader.getTransitionId(process, transition));
				if( logger.isDebugEnabled()) logger.debug("Transition                   Name = "+xpdlReader.getTransitionName(process, transition));
				if( logger.isDebugEnabled()) logger.debug("Transition            Description = "+xpdlReader.getTransitionDescr(process, transition));
				if( logger.isDebugEnabled()) logger.debug("Transition                   From = "+xpdlReader.getTransitionFrom(process, transition));
				if( logger.isDebugEnabled()) logger.debug("Transition                     To = "+xpdlReader.getTransitionTo(process, transition));

				WkfTransitionType type = xpdlReader.getTransitionType(process, transition);
				if( logger.isDebugEnabled()) logger.debug("Transition                   Type = "+type);
				if( type == WkfTransitionType.Condition ){
				if( logger.isDebugEnabled()) logger.debug("Transition             Expression = "+xpdlReader.getTransitionExpression(process, transition));
				}
			}
		
		}
	}
}
