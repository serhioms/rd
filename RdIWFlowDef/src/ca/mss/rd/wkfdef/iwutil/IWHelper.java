package ca.mss.rd.wkfdef.iwutil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.support.AbstractApplicationContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.prop.UtilProp;
import ca.mss.rd.flow.sp.SPActivity;
import ca.mss.rd.flow.sp.SPContext;
import ca.mss.rd.flow.sp.SPFlow;
import ca.mss.rd.flow.sp.SPParallel;
import ca.mss.rd.flow.sp.SPFlowProp;
import ca.mss.rd.flow.sp.SPSequential;
import ca.mss.rd.flow.sp.SPStage;
import ca.mss.rd.flow.sp.SPStart;
import ca.mss.rd.flow.sp.SPSubflow;
import ca.mss.rd.flow.tools.exec.RdChecksumValidation;
import ca.mss.rd.flow.tools.exec.RdDbFunction;
import ca.mss.rd.flow.tools.exec.RdDbProcedure;
import ca.mss.rd.flow.tools.exec.RdDeleteFile;
import ca.mss.rd.flow.tools.exec.RdExtProc;
import ca.mss.rd.flow.tools.exec.RdFileWatcher;
import ca.mss.rd.flow.tools.exec.RdJavaExt;
import ca.mss.rd.flow.tools.exec.RdNoOperation;
import ca.mss.rd.flow.tools.exec.RdSqlLoader;
import ca.mss.rd.flow.tools.exec.RdTransferFile;
import ca.mss.rd.flow.tools.prop.ChecksumAlgoritm;
import ca.mss.rd.flow.tools.prop.DbParam;
import ca.mss.rd.flow.tools.prop.UriResource;
import ca.mss.rd.job.AbstractJob;
import ca.mss.rd.job.JobPoolParallel;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilString;
import ca.mss.rd.util.io.UtilFile;
import ca.mss.rd.wkfdef.iw.CallActiviti;
import ca.mss.rd.wkfdef.iw.CallActiviti.Variables;
import ca.mss.rd.wkfdef.iw.CallExternalProcess;
import ca.mss.rd.wkfdef.iw.CdataProperties;
import ca.mss.rd.wkfdef.iw.ChecksumGeneration;
import ca.mss.rd.wkfdef.iw.ChecksumValidation;
import ca.mss.rd.wkfdef.iw.ComponentReference;
import ca.mss.rd.wkfdef.iw.DbFunction;
import ca.mss.rd.wkfdef.iw.DbProcedure;
import ca.mss.rd.wkfdef.iw.DeleteResource;
import ca.mss.rd.wkfdef.iw.ExecDefinition;
import ca.mss.rd.wkfdef.iw.ExternalWorkflowReference;
import ca.mss.rd.wkfdef.iw.FileTransfer;
import ca.mss.rd.wkfdef.iw.JavaExtension;
import ca.mss.rd.wkfdef.iw.JavaExtension.JavaExtensionOutputPropertyNames;
import ca.mss.rd.wkfdef.iw.Mapping;
import ca.mss.rd.wkfdef.iw.NoOpExecDefinition;
import ca.mss.rd.wkfdef.iw.Parallel;
import ca.mss.rd.wkfdef.iw.Param;
import ca.mss.rd.wkfdef.iw.PollingFileWatcher;
import ca.mss.rd.wkfdef.iw.PollingFileWatcher.WatchedFiles;
import ca.mss.rd.wkfdef.iw.ProcessDescriptor;
import ca.mss.rd.wkfdef.iw.PropertyMapping;
import ca.mss.rd.wkfdef.iw.Resource;
import ca.mss.rd.wkfdef.iw.Sequential;
import ca.mss.rd.wkfdef.iw.SimpleProperties;
import ca.mss.rd.wkfdef.iw.SimpleProperties.Properties;
import ca.mss.rd.wkfdef.iw.SimpleProperty;
import ca.mss.rd.wkfdef.iw.SqlLoader;
import ca.mss.rd.wkfdef.iw.Stage;
import ca.mss.rd.wkfdef.iw.StageFalure;
import ca.mss.rd.wkfdef.iw.Transfer;
import ca.mss.rd.wkfdef.iw.Variable;
import ca.mss.rd.wkfdef.iw.WorkflowDefinition;
import ca.mss.rd.wkfdef.iw.WorkflowProperties;
import ca.mss.rd.wkfdef.iw.WorkflowProperties.GeneralProperties;
import ca.mss.rd.wkfdef.iw.WorkflowProperties.InputProperties;
import ca.mss.rd.wkfdef.iw.WorkflowProperties.OutputProperties;

import com.scotiabank.maestro.exec.services.appconfig.ExecServicesAppConfig;
import com.scotiabank.maestro.orch.api.workflow.PropertyScopeTypeEnum;
import com.scotiabank.maestro.orch.services.workflow.definition.validation.WorkflowConditionValidator;
import com.scotiabank.maestro.orch.services.workflow.definition.validation.WorkflowPropertyValidator;
import com.scotiabank.maestro.utils.spring.MaestroSpringContextLoaderImpl;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

public class IWHelper {

    /*
	 * Spring Context
	 */
	static public AbstractApplicationContext ctxt;

	static {
		ctxt = null;
		
		try {
			Class<?> startingAppConfigClass = ExecServicesAppConfig.class;
			ctxt = MaestroSpringContextLoaderImpl.openApplicationContext(startingAppConfigClass);
			ctxt.registerShutdownHook();

			assert( Logger.SPRING.isOn ? Logger.SPRING.printf("Spring context initialized!"): true);
		} catch(Exception x) {
			throw new RuntimeException("Can not initialize spring context.", x);
		} finally {
			if( ctxt != null ) {
				ctxt.close();
			}
		}
	}
	
	static public WorkflowConditionValidator<SPFlowProp,SPContext> createWorkflowConditionValidator(){ 
		return ctxt.getBean(WorkflowConditionValidator.class);
	}

	static public WorkflowPropertyValidator createWorkflowPropertyValidator(){ 
		return ctxt.getBean(WorkflowPropertyValidator.class);
	}

	static public RdDbFunction createRdDbFunction(){ 
		return ctxt.getBean(RdDbFunction.class);
	}

	static public RdDbProcedure createRdDbProcedure(){ 
		return ctxt.getBean(RdDbProcedure.class);
	}

	/*
	 * Other services
	 */
	
	private static List<RdProp> toMapping(List<Mapping> mapping, PropertyScopeTypeEnum scope) {
		return toMapping(mapping, new ArrayList<RdProp>(0), scope);
	}

	private static List<RdProp> toMapping(List<Mapping> mapping, List<RdProp> result, PropertyScopeTypeEnum scope) {
		if (mapping != null) {
			for (Object item : mapping) {
				if (item != null) {
					if( item instanceof ElementNSImpl ){
						ElementNSImpl elem = (ElementNSImpl) item;
						result.add(toValue(toName(elem), elem, scope));
					} else if ( item instanceof Mapping ){
						Mapping map = (Mapping) item;
						result.add(UtilProp.toProp(map.getSource(), scope, map.getTarget()));
					} else {
						if( Logger.ERROR.isOn ) Logger.ERROR.printf("Unexpected class type %s", item.getClass().getName());
					}
				}
			}
		}
		return result;
	}

	private static List<RdProp> toProperty(List<Object> property, PropertyScopeTypeEnum scope) {
		return toProperty(property, new ArrayList<RdProp>(0), scope);
	}

	private static List<RdProp> toProperty(List<Object> property, List<RdProp> result, PropertyScopeTypeEnum scope) {
		if (property != null) {
			for (Object item : property) {
				if (item != null) {
					ElementNSImpl elem = (ElementNSImpl) item;
					result.add(toValue(toName(elem), elem, scope));
				}
			}
		}
		return result;
	}

	private static RdProp toValue(String nodeName, ElementNSImpl elem, PropertyScopeTypeEnum scope) {
		Node firstChild = elem.getFirstChild();
		if (firstChild != null) {
			String nodeValue = firstChild.getNodeValue();
			if (nodeValue != null) {
				return UtilProp.toProp(nodeName, nodeValue, scope);
			}
		} else {
			return UtilProp.toProp(nodeName, "", scope);
		}
		throw new RuntimeException("Can not find property value!");
	}

	private static String toName(ElementNSImpl elem) {
		NamedNodeMap attributes = elem.getAttributes();
		if (attributes != null) {
			Node namedItem = attributes.getNamedItem("name");
			if (namedItem != null) {
				String nodeValue = namedItem.getNodeValue();
				if (nodeValue != null)
					return nodeValue;
			}
		}
		throw new RuntimeException("Can not find property name!");
	}

	public static String toString(StageFalure stageFailure) {
		return stageFailure == null ? null : String.format("[FailureCondition=%s][FailureMessage=%s]", stageFailure.getFailureCondition(), stageFailure.getFailureMessage());
	}

	public static void traverser(int level, WorkflowDefinition wkf, IWFlowVisitor workflowVisitor) {

		List<RdProp> input = new ArrayList<RdProp>(0);
		List<RdProp> output = new ArrayList<RdProp>(0);
		List<RdProp> general = new ArrayList<RdProp>(0);

		WorkflowProperties properties = wkf.getProperties();
		if (properties != null) {

			InputProperties inputProperties = properties.getInputProperties();
			if (inputProperties != null) {
				input = toProperty(inputProperties.getProperty(), PropertyScopeTypeEnum.INPUT);
			}

			OutputProperties outputProperties = properties.getOutputProperties();
			if (outputProperties != null) {
				output = toProperty(outputProperties.getProperty(), PropertyScopeTypeEnum.OUTPUT);
			}

			GeneralProperties generalProperties = properties.getGeneralProperties();
			if (generalProperties != null) {
				general = toProperty(generalProperties.getProperty(), PropertyScopeTypeEnum.GENERAL);
			}
		}

		workflowVisitor.workflowDefinition(level, wkf, input, output, general);

		traverser(level + 1, wkf, wkf.getStage(), workflowVisitor);

		traverser(level + 1, wkf, wkf.getParallel(), workflowVisitor);

		traverser(level + 1, wkf, wkf.getSequential(), workflowVisitor);

		traverser(level + 1, wkf, wkf.getComponentReference(), workflowVisitor);

		traverser(level + 1, wkf, wkf.getExternalWorkflowReference(), workflowVisitor);
		
		workflowVisitor.workflowEnds(level, wkf);
	}

	private static void traverser(int level, WorkflowDefinition wkf, Parallel parallel, IWFlowVisitor workflowVisitor) {
		if (parallel != null) {
			workflowVisitor.parallel(level, parallel);
			traverser(level + 1, wkf, parallel.getStageOrComponentReferenceOrExternalWorkflowReference(), workflowVisitor);
			workflowVisitor.parallelEnds(level, parallel);
		}
	}

	private static void traverser(int level, WorkflowDefinition wkf, Sequential sequential, IWFlowVisitor workflowVisitor) {
		if (sequential != null) {
			workflowVisitor.sequential(level, sequential);
			traverser(level + 1, wkf, sequential.getStageOrComponentReferenceOrExternalWorkflowReference(), workflowVisitor);
			workflowVisitor.sequentialEnds(level, sequential);
		}
	}

	private static void traverser(int level, WorkflowDefinition wkf, Stage stage, IWFlowVisitor workflowVisitor) {
		if (stage != null) {
			workflowVisitor.stage(level, stage);
		}
	}

	private static void traverser(int level, WorkflowDefinition wkf, ComponentReference componentReference, IWFlowVisitor workflowVisitor) {
		if (componentReference != null) {
			workflowVisitor.componentReference(level, componentReference);
		}
	}

	private static void traverser(int level, WorkflowDefinition wkf, ExternalWorkflowReference externalWorkflowReference, IWFlowVisitor workflowVisitor) {
		if (externalWorkflowReference != null) {

			List<RdProp> input = new ArrayList<RdProp>(0);
			List<RdProp> output = new ArrayList<RdProp>(0);

			ca.mss.rd.wkfdef.iw.ExternalWorkflowReference.InputProperties inputProperties = externalWorkflowReference.getInputProperties();
			if (inputProperties != null) {
				if (inputProperties.getProperty() != null) {
					input = toProperty(inputProperties.getProperty(), PropertyScopeTypeEnum.INPUT);
				}
			}

			PropertyMapping outputPropertyMapping = externalWorkflowReference.getOutputPropertyMapping();
			if (outputPropertyMapping != null) {
				output = toMapping(outputPropertyMapping.getMapping(), PropertyScopeTypeEnum.OUTPUT);
			}

			workflowVisitor.externalWorkflowReference(level, externalWorkflowReference, input, output);
		}
	}

	private static void traverser(int level, WorkflowDefinition wkf, List<Object> stageOrComponentReferenceOrExternalWorkflowReference, IWFlowVisitor workflowVisitor) {
		for (Object item : stageOrComponentReferenceOrExternalWorkflowReference) {
			if (item != null) {
				if (item instanceof ExternalWorkflowReference) {
					traverser(level, wkf, (ExternalWorkflowReference) item, workflowVisitor);
				} else if (item instanceof ComponentReference) {
					traverser(level, wkf, (ComponentReference) item, workflowVisitor);
				} else if (item instanceof Stage) {
					traverser(level, wkf, (Stage) item, workflowVisitor);
				} else if (item instanceof Parallel) {
					traverser(level, wkf, (Parallel) item, workflowVisitor);
				} else if (item instanceof Sequential) {
					traverser(level, wkf, (Sequential) item, workflowVisitor);
				} else {
					throw new RuntimeException(String.format("Unexpected type: %s", item.getClass().getSimpleName()));
				}
			}
		}
	}

	static public WorkflowDefinition readFlowDefn(String wkfFile) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(WorkflowDefinition.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (WorkflowDefinition) jaxbUnmarshaller.unmarshal(new File(wkfFile));

		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}

	static public class IWFlowLoggerVisitor implements IWFlowVisitor {
		
		@Override
		public void workflowDefinition(int level, WorkflowDefinition wkf, List<RdProp> input, List<RdProp> output, List<RdProp> general) {
			Logger.TEST_VERBOSE.printf("%s%sWorkflow [Name=%s][Code=%s][isFinishOrchestrationASAPOnError=%b][Description=%s]", UtilString.space(level), UtilString.space(level),
					wkf.getName(), wkf.getExternalWorkflowCode(), wkf.isFinishOrchestrationASAPOnError(), wkf.getDescription());
			if (!general.isEmpty()) {
				Logger.TEST_VERBOSE.printf("%s%s         |-----General Prop %s", UtilString.space(level), UtilString.space(level), UtilMisc.toString(general));
			}
			if (!input.isEmpty()) {
				Logger.TEST_VERBOSE.printf("%s%s         |-----Input Prop %s", UtilString.space(level), UtilString.space(level), UtilMisc.toString(input));
			}
			if (!output.isEmpty()) {
				Logger.TEST_VERBOSE.printf("%s%s         |-----Output Prop %s", UtilString.space(level), UtilString.space(level), UtilMisc.toString(output));
			}
		}

		@Override
		public void componentReference(int level, ComponentReference componentReference) {
			Logger.TEST_VERBOSE.printf("%s%sComponentReference [Name=%s][Id=%s][SkipCondition=%s][StageFailure=%s][isEnabled=%b][Description=%s]", UtilString.space(level),
					UtilString.space(level), componentReference.getName(), componentReference.getId(), componentReference.getSkipCondition(),
					IWHelper.toString(componentReference.getStageFailure()), componentReference.isEnabled(), componentReference.getDescription());
		}

		@Override
		public void externalWorkflowReference(int level, ExternalWorkflowReference externalWorkflowReference, List<RdProp> input, List<RdProp> output) {
			Logger.TEST_VERBOSE.printf("%s%sExternalWorkflowReference [Name=%s][Code=%s][Id=%s][SkipCondition=%s][StageFailure=%s][isEnabled=%b]", UtilString.space(level),
					UtilString.space(level), externalWorkflowReference.getName(), externalWorkflowReference.getExternalWorkflowCode(), externalWorkflowReference.getId(),
					externalWorkflowReference.getSkipCondition(), IWHelper.toString(externalWorkflowReference.getStageFailure()), externalWorkflowReference.isEnabled());
			if (!input.isEmpty()) {
				Logger.TEST_VERBOSE.printf("%s%s                          |-----Input Prop %s", UtilString.space(level), UtilString.space(level), UtilMisc.toString(input));
			}
			if (!output.isEmpty()) {
				Logger.TEST_VERBOSE.printf("%s%s                          |-----Output Prop %s", UtilString.space(level), UtilString.space(level), UtilMisc.toString(output));
			}
		}

		@Override
		public void stage(int level, Stage stage) {
			Logger.TEST_VERBOSE.printf("%s%sStage [Name=%s][Id=%s][SkipCondition=%s][StageFailure=%s][isEnabled=%b][Description=%s]", UtilString.space(level), UtilString.space(level),
					stage.getName(), stage.getId(), stage.getSkipCondition(), IWHelper.toString(stage.getStageFailure()), stage.isEnabled(), stage.getDescription());
		}

		@Override
		public void parallel(int level, Parallel parallel) {
			Logger.TEST_VERBOSE.printf("%s%sParallel [Name=%s][Id=%s][SkipCondition=%s][StageFailure=%s][isEnabled=%b]", UtilString.space(level), UtilString.space(level),
					parallel.getName(), parallel.getId(), parallel.getSkipCondition(), IWHelper.toString(parallel.getStageFailure()), parallel.isEnabled());
		}

		@Override
		public void sequential(int level, Sequential sequential) {
			Logger.TEST_VERBOSE.printf("%s%sSequential [Name=%s][Id=%s][SkipCondition=%s][StageFailure=%s][isEnabled=%b]", UtilString.space(level), UtilString.space(level),
					sequential.getName(), sequential.getId(), sequential.getSkipCondition(), IWHelper.toString(sequential.getStageFailure()), sequential.isEnabled());
		}

		@Override
		public void workflowEnds(int level, WorkflowDefinition wkf) {
			Logger.TEST_VERBOSE.printf("%s%sWorkflowEnds [Name=%s]",  UtilString.space(level), UtilString.space(level), wkf.getName());
		}

		@Override
		public void parallelEnds(int level, Parallel parallel) {
			Logger.TEST_VERBOSE.printf("%s%sParallelEnds [Name=%s]",  UtilString.space(level), UtilString.space(level), parallel.getName());
		}

		@Override
		public void sequentialEnds(int level, Sequential sequential) {
			Logger.TEST_VERBOSE.printf("%s%sSequentialEnds [Name=%s]",  UtilString.space(level), UtilString.space(level), sequential.getName());
		}

		
	}
	
	static public Map<String,WorkflowDefinition> readFlowRepository(String testWorkflowDir) throws InterruptedException {
		Map<String,WorkflowDefinition> repo = new ConcurrentHashMap<String,WorkflowDefinition>(0);
		
		List<String> files = new ArrayList<String>(0);
		UtilFile.scanDirectory(testWorkflowDir, "^.*\\.xml$", true, files, null);
		int size = files.size();
		

		JobPoolParallel<IWFlowFile> pool = new JobPoolParallel<IWFlowFile>(size, size);
		pool.startPool();
		for(int i=0; i<size; i++) {
			pool.queueJob(new IWFlowFile(files.get(i), repo));
		}
		while( repo.size() != size ){
			Thread.sleep(10L);
		}
		pool.shutdownPool(new IWFlowFile(AbstractJob.SHUTDOWN));

		return repo;
	}

	private static Stack<SPActivity> flowBuildSet = new Stack<SPActivity>();

	/*
	 * Workflow builder
	 */
	public static SPActivity buildIWFlow(SPContext context, WorkflowDefinition wkfDefn, Map<String, WorkflowDefinition> repo, int level) {
		// Here is main workflow initialization
		SPStart startSet = new SPStart(context, level);
		startSet.setName(wkfDefn.getName());

		try {
			IWHelper.traverser(level, repo.get(wkfDefn.getName()), new IWFlowVisitor() {

				public void workflowDefinition(int level, WorkflowDefinition wkf, List<RdProp> input, List<RdProp> output, List<RdProp> general) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("WORKFLOW[%d][%d] = %s", level, flowBuildSet.size(), wkf.getName()): true);
					flowBuildSet.push(startSet);
				}


				public void parallel(int level, Parallel parallel) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("PARALLEL[%d][%d] = %s", level, flowBuildSet.size(), parallel.getName()): true);

					SPParallel spparallel = new SPParallel(context, level);
					IWHelper.populateParallel(spparallel, parallel);

					flowBuildSet.peek().add(spparallel);
					flowBuildSet.push(spparallel);
				}


				public void sequential(int level, Sequential sequential) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("SEQUENTIAL[%d][%d] = %s", level, flowBuildSet.size(), sequential.getName()): true);
					
					SPSequential spsequential = new SPSequential(context, level);
					
					IWHelper.populateSequential(spsequential, sequential);
					
					
					flowBuildSet.peek().add(spsequential);
					flowBuildSet.push(spsequential);
				}


				public void stage(int level, Stage stage) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("STAGE[%d][%d] = %s", level, flowBuildSet.size(), stage.getName()): true);
					
					SPStage spstage = new SPStage(context, level);
					
					IWHelper.populateStage(spstage, stage, context.wkfProp);

					flowBuildSet.peek().add(spstage);
				}


				public void externalWorkflowReference(int level, ExternalWorkflowReference externalWorkflowReference, List<RdProp> input, List<RdProp> output) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("SUBFLOW[%d][%d] = %s", level, flowBuildSet.size(), externalWorkflowReference.getName()): true);
					
					// Here are subflow initialization
					WorkflowDefinition wkfDefn = repo.get(externalWorkflowReference.getExternalWorkflowCode());
					if( wkfDefn != null ){
						SPContext context2 = new SPContext();
						context2.setExprContext(context.exprContext);
	
						SPFlow flow = new SPFlow(context2, IWHelper.buildIWFlow(context2, wkfDefn, repo, level+1));
	
						IWHelper.populateWorkflow(flow, wkfDefn, context2.wkfProp);
						
						SPSubflow subflow = new SPSubflow(context, externalWorkflowReference, flow, level); // externalWorkflowReference.getExternalWorkflowCode();
						IWHelper.populateSubflow(subflow, externalWorkflowReference, context.getWkfProp());
						
						flowBuildSet.peek().add(subflow);
					}
				}
				

				public void componentReference(int level, ComponentReference componentReference) {
					throw new RuntimeException(String.format("Component[%d] = %s not found", componentReference.getName()));
				}


				public void workflowEnds(int level, WorkflowDefinition wkf) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("WORKFLOW.ENDS[%d][%d] = %s", level, flowBuildSet.size(), wkf.getName()): true);
					SPActivity pop = flowBuildSet.pop();
					if( !pop.getName().contains(wkf.getName()) ) throw new RuntimeException(String.format("Stack flow [%s] <> Recursive flow [%s]", pop.getName(), wkf.getName()));
					pop.finalizeSet();
				}


				public void parallelEnds(int level, Parallel parallel) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("PARALLEL.ENDS[%d][%d] = %s", level, flowBuildSet.size(), parallel.getName()): true);
					SPActivity pop = flowBuildSet.pop();
					if( !pop.getName().contains(parallel.getName()) ) throw new RuntimeException(String.format("Stack flow [%s] <> Recursive flow [%s]", pop.getName(), parallel.getName()));
					pop.finalizeSet();
				}


				public void sequentialEnds(int level, Sequential sequential) {
					assert( Logger.WORKFLOW_DEFN.isOn ? Logger.WORKFLOW_DEFN.printf("SEQUENTIAL.ENDS[%d][%d] = %s", level, flowBuildSet.size(), sequential.getName()): true);
					SPActivity pop = flowBuildSet.pop();
					if( !pop.getName().contains(sequential.getName()) ) throw new RuntimeException(String.format("Stack flow [%s] <> Recursive flow [%s]", pop.getName(), sequential.getName()));
					pop.finalizeSet();
				}
				
				
			});
			
		} catch(Throwable t){
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Faild instantiate workflow [%s]", wkfDefn.getName());
		}
		
		return startSet;
	}

	public static void populateSubflow(SPSubflow subflow, ExternalWorkflowReference subDefn, SPFlowProp prop) {
		subflow.setName(subDefn.getName());
		subflow.setCode(subDefn.getId());
		subflow.setDisable(!subDefn.isEnabled());
		if( subDefn.getSkipCondition() != null ){
			subflow.setSkipCondition(subDefn.getSkipCondition());
		}
		if( subDefn.getStageFailure() != null ){
			subflow.setFailCondition(subDefn.getStageFailure().getFailureCondition());
			subflow.setFailConditionMessage(subDefn.getStageFailure().getFailureMessage());
		}
		
		// Populate subflow properties here
		if( subDefn.getInputProperties() != null ){
			prop.getSubPropInput().put(subDefn.getId(), IWHelper.toProperty(subDefn.getInputProperties().getProperty(), PropertyScopeTypeEnum.INPUT));
		}
		if( subDefn.getOutputPropertyMapping() != null ){
			prop.getSubPropOutput().put(subDefn.getId(), IWHelper.toMapping(subDefn.getOutputPropertyMapping().getMapping(), PropertyScopeTypeEnum.OUTPUT));
		}
	}

	public static void populateParallel(SPParallel parallel, Parallel parDefn) {
		parallel.setName(parDefn.getName());
		parallel.setCode(parDefn.getId());
		parallel.setDisable(!parDefn.isEnabled());
		if( parDefn.getSkipCondition() != null ){
			parallel.setSkipCondition(parDefn.getSkipCondition());
		}
		if( parDefn.getStageFailure() != null ){
			parallel.setFailCondition(parDefn.getStageFailure().getFailureCondition());
			parallel.setFailConditionMessage(parDefn.getStageFailure().getFailureMessage());
		}
	}

	public static void populateSequential(SPSequential sequential, Sequential seqDefn) {
		sequential.setName(seqDefn.getName());
		sequential.setCode(seqDefn.getId());
		sequential.setDisable(!seqDefn.isEnabled());
		if( seqDefn.getSkipCondition() != null ){
			sequential.setSkipCondition(seqDefn.getSkipCondition());
		}
		if( seqDefn.getStageFailure() != null ){
			sequential.setFailCondition(seqDefn.getStageFailure().getFailureCondition());
			sequential.setFailConditionMessage(seqDefn.getStageFailure().getFailureMessage());
		}
	}

	public static void populateWorkflow(SPFlow wkf, WorkflowDefinition wkfDefn, SPFlowProp prop) {
		wkf.setName(wkfDefn.getName());
		wkf.setExtCode(wkfDefn.getExternalWorkflowCode());
		wkf.setDescription(wkfDefn.getDescription());

		// Populate workflow properties here
		WorkflowProperties dprop = wkfDefn.getProperties();
		if( dprop != null ){
			if( dprop.getGeneralProperties() != null ){
				prop.getWkfPropGeneral().put(wkfDefn.getExternalWorkflowCode(), IWHelper.toProperty(dprop.getGeneralProperties().getProperty(), PropertyScopeTypeEnum.GENERAL));
			}
			if( dprop.getInputProperties() != null ){
				prop.getWkfPropInput().put(wkfDefn.getExternalWorkflowCode(), IWHelper.toProperty(dprop.getInputProperties().getProperty(), PropertyScopeTypeEnum.INPUT));
			}
			if( dprop.getOutputProperties() != null ){
				prop.getWkfPropOutput().put(wkfDefn.getExternalWorkflowCode(), IWHelper.toProperty(dprop.getOutputProperties().getProperty(), PropertyScopeTypeEnum.OUTPUT));
			}
		}
	}

	public static void populateStage(SPStage stage, Stage stageDefn, SPFlowProp prop) {
		stage.setName(stageDefn.getName());
		stage.setCode(stageDefn.getId());
		stage.setDisable(!stageDefn.isEnabled());
		if( stageDefn.getSkipCondition() != null ){
			stage.setSkipCondition(stageDefn.getSkipCondition());
		}
		if( stageDefn.getStageFailure() != null ){
			stage.setFailCondition(stageDefn.getStageFailure().getFailureCondition());
			stage.setFailConditionMessage(stageDefn.getStageFailure().getFailureMessage());
		}
		if( stageDefn.getAutoRetryConfiguration() != null ){
			stage.setDelayBetweenRetry(stageDefn.getAutoRetryConfiguration().getDelayBetweenAttemptsInMs());
			stage.setMaxRetryIfFail(stageDefn.getAutoRetryConfiguration().getMaximumNumberOfRetryAttempts());
			stage.setMaxRetryDuration(stageDefn.getAutoRetryConfiguration().getMaximumRetryDurationInMs());
		}

		// Initiate stage execution
		ExecDefinition exe = stageDefn.getExecDefinition();
		if( exe != null ){
			IWHelper.populateExe(exe, prop, stage.getCode());
		}
	}

	/*
	 * Properties 
	 */

	@SuppressWarnings("unused")
	public static void populateExe(ExecDefinition exe, SPFlowProp prop, String stageCode) {
		if( exe.getCallActiviti() != null ){
			CallActiviti callActiviti = exe.getCallActiviti();
			
			callActiviti.getBusinessKey();
			callActiviti.getProcessDefinitionId();
			callActiviti.getTenantId();
			
			ca.mss.rd.wkfdef.iw.CallActiviti.OutputProperties outputProperties = callActiviti.getOutputProperties();
			if( outputProperties != null ){
				for(Object obj: outputProperties.getProperty() ){
					new RuntimeException("Activiti prop not implemented!");
				}
			}
			
			PropertyMapping outputPropertyMapping = callActiviti.getOutputPropertyMapping();
			if( outputPropertyMapping != null ){
				if( outputPropertyMapping.getMapping() != null ){
					List<RdProp> mapping = toMapping(outputPropertyMapping.getMapping(), PropertyScopeTypeEnum.OUTPUT);
					new RuntimeException("Activiti prop not implemented!");
				}
			}
			
			ProcessDescriptor processDescriptor = callActiviti.getProcessDescriptor();
			if( processDescriptor != null ){
				processDescriptor.getName();
				processDescriptor.getProcessDefinitonKey();
				processDescriptor.getVersion();
			}
			
			Variables variables = callActiviti.getVariables();
			if( variables != null ){
				if( variables.getVariable() != null ){
					for(Variable var: variables.getVariable() ){
//						var.getName();
//						var.getValue();
//						var.getScope();
//						var.getType();
						new RuntimeException("Activiti prop not implemented!");
					}
				}
			}

		} else if( exe.getCallExternalProcess() != null ){
			CallExternalProcess callExternalProcess = exe.getCallExternalProcess();
			
			RdExtProc extProc = new RdExtProc();
			
			prop.getStageExec().put(stageCode, extProc);
			
			extProc.setCommandLine( callExternalProcess.getCommandLine());
			
			extProc.setExitCode(callExternalProcess.getExitCodeProperty());
			 
			extProc.setWorkingDir(callExternalProcess.getWorkingDirectory());

		} else if( exe.getChecksumGeneration() != null ){
			ChecksumGeneration checksumGeneration = exe.getChecksumGeneration();
			
			RdChecksumValidation chsgenerator = new RdChecksumValidation();
			prop.getStageExec().put(stageCode, chsgenerator);

			chsgenerator.setAlgoritm(ChecksumAlgoritm.valueOf(checksumGeneration.getChecksumAlgorithm().toString()));
			
			Resource markerResource = checksumGeneration.getMarkerResource();
			if( markerResource != null ){
				chsgenerator.setMarkerResource(new UriResource( markerResource.getSearchPattern(), markerResource.getMatchedUriOutputProperty(),markerResource.getUri()));
			}
			
			Resource sourceResource = checksumGeneration.getSourceResource();
			if( sourceResource != null ){
				chsgenerator.setSourceResource(new UriResource(sourceResource.getSearchPattern(), sourceResource.getMatchedUriOutputProperty(), sourceResource.getUri()));
			}

		} else if( exe.getChecksumValidation() != null ){
			ChecksumValidation checksumValidation = exe.getChecksumValidation();

			RdChecksumValidation chsvalidator = new RdChecksumValidation();
			prop.getStageExec().put(stageCode, chsvalidator);

			chsvalidator.setAlgoritm(ChecksumAlgoritm.valueOf(checksumValidation.getChecksumAlgorithm().toString()));
			
			Resource markerResource = checksumValidation.getMarkerResource();
			if( markerResource != null ){
				chsvalidator.setMarkerResource(new UriResource(markerResource.getSearchPattern(), markerResource.getMatchedUriOutputProperty(), markerResource.getUri()));
			}
			
			Resource sourceResource = checksumValidation.getSourceResource();
			if( sourceResource != null ){
				chsvalidator.setSourceResource(new UriResource(sourceResource.getSearchPattern(), sourceResource.getMatchedUriOutputProperty(), sourceResource.getUri()));
			}
		} else if( exe.getDeleteResource() != null ){
			DeleteResource deleteResource = exe.getDeleteResource();
			
			RdDeleteFile delRes = new RdDeleteFile();
			prop.getStageExec().put(stageCode, delRes);

			delRes.setCount(deleteResource.getDeleteCountProperty());
			
			if( deleteResource.getResource() != null ){
				List<Resource> resources = deleteResource.getResource();
				for(Resource resource: resources ){
					delRes.addResource(new UriResource(resource.getSearchPattern(), resource.getMatchedUriOutputProperty(), resource.getUri()));
				}
			}

		} else if( exe.getFileTransfer() != null ){
			FileTransfer fileTransfer = exe.getFileTransfer();

			RdTransferFile transRes = new RdTransferFile();
			prop.getStageExec().put(stageCode, transRes);

			transRes.setCount(fileTransfer.getTransferCountProperty());
			
			if( fileTransfer.getTransfer() != null ){
				for(Transfer file: fileTransfer.getTransfer()){
					Resource sourceResource = file.getSourceResource();
					if( sourceResource != null ){
						transRes.addSource(new UriResource(sourceResource.getSearchPattern(), sourceResource.getMatchedUriOutputProperty(), sourceResource.getUri()));
					}
					
					Resource targetResource = file.getTargetResource();
					if( targetResource != null ){
						transRes.addTarget(new UriResource(targetResource.getSearchPattern(), targetResource.getMatchedUriOutputProperty(), targetResource.getUri()));
					}
				}
			}
		
		} else if( exe.getJavaExtension() != null ){
			JavaExtension javaExt = exe.getJavaExtension();
			
			RdJavaExt java = new RdJavaExt();
			prop.getStageExec().put(stageCode, java);

			java.setClassName( javaExt.getJavaExtensionExecutorClassName());

			CdataProperties cdataProperties = javaExt.getCdataProperties();
			if( cdataProperties != null ){
				PropertyMapping outputPropertyMapping = javaExt.getOutputPropertyMapping();
				if( outputPropertyMapping != null ){
					List<RdProp> outputPro = toMapping(outputPropertyMapping.getMapping(), PropertyScopeTypeEnum.OUTPUT);
				}
				
				throw new RuntimeException("Depricated...");
			}
			
			SimpleProperties simpleProperties = javaExt.getSimpleProperties();
			if( simpleProperties != null ){
				Properties properties = simpleProperties.getProperties();
				if( properties != null ){
					for(SimpleProperty pro: properties.getSimpleProperty()){
						java.addInMap(pro.getName(), pro.getValue());
					}
				}
			}

			JavaExtensionOutputPropertyNames javaExtensionOutputPropertyNames = javaExt.getJavaExtensionOutputPropertyNames();
			if( javaExtensionOutputPropertyNames != null ){
				for(String name: javaExtensionOutputPropertyNames.getPropertyName() ){
					java.addOutSet(name);
				}
			}
			
		} else if( exe.getDbFunction() != null ){
			DbFunction dbFunction = exe.getDbFunction();

			RdDbFunction dbf = createRdDbFunction();
			prop.getStageExec().put(stageCode, dbf);
			
			dbf.setName(dbFunction.getName());

			Param result = dbFunction.getResult();
			if( result != null ){
				dbf.setOutParam(new DbParam(result.getName(), result.getType().toString(), result.getValue()));
			}
			
			DbFunction.In in = dbFunction.getIn();
			if( in != null ){
				if( in.getParam() != null ){
					for(Param param: in.getParam()){
						dbf.addInParam(new DbParam(param.getName(), param.getType().toString(), param.getValue()));
					}
				}
			}
		} else if( exe.getDbProcedure() != null ){
			DbProcedure dbProcedure = exe.getDbProcedure();

			RdDbProcedure dbp = createRdDbProcedure();
			prop.getStageExec().put(stageCode, dbp);
			
			dbp.setName(dbProcedure.getName());

			DbProcedure.In in = dbProcedure.getIn();
			if( in != null ){
				if( in.getParam() != null ){
					for(Param param: in.getParam()){
						dbp.addInParam(new DbParam(param.getName(), param.getType().toString(), param.getValue()));
					}
				}
			}
			
			DbProcedure.Out out = dbProcedure.getOut();
			if( out != null ){
				if( out.getParam() != null ){
					for(Param param: out.getParam()){
						dbp.addOutParam(new DbParam(param.getName(), param.getType().toString(), param.getValue()));
					}
				}
			}
			
			PropertyMapping outputPropertyMapping = dbProcedure.getOutputPropertyMapping();
			if( outputPropertyMapping != null ){
				if( outputPropertyMapping.getMapping() != null ){
					dbp.addPropMap(toMapping( outputPropertyMapping.getMapping(), PropertyScopeTypeEnum.OUTPUT));
				}
			}
			
			System.currentTimeMillis();
			
		} else if( exe.getNoOpExecDefinition() != null ){
			NoOpExecDefinition noOpExecDefinition = exe.getNoOpExecDefinition();
			
			RdNoOperation noop = new RdNoOperation();
			prop.getStageExec().put(stageCode, noop);

			noop.setInput(noOpExecDefinition.getInput());
			noop.setNtasks(noOpExecDefinition.getNumberOfTasks());
			noop.setNinterimStatusResponses(noOpExecDefinition.getNumberOfInterimStatusResponses());
			noop.setDurationBetweenEventsMls(noOpExecDefinition.getDurationBetweenEventsInMs());
			noop.setOutPropName(noOpExecDefinition.getOutputPropertyName());
			noop.setDescr(noOpExecDefinition.getDescription());
		
		} else if( exe.getPollingFileWatcher() != null ){
			PollingFileWatcher pollingFileWatcher = exe.getPollingFileWatcher();

			RdFileWatcher watcher = new RdFileWatcher();
			prop.getStageExec().put(stageCode, watcher);

			watcher.setCount(pollingFileWatcher.getMatchedCountProperty());
			
			watcher.setAllowFileNotFound(pollingFileWatcher.isAllowFileNotFound());

			WatchedFiles watchedFiles = pollingFileWatcher.getWatchedFiles();
			if( watchedFiles != null ){
				for(Resource resource: watchedFiles.getResource()){
					watcher.addResource(new UriResource(resource.getSearchPattern(), resource.getMatchedUriOutputProperty(), resource.getUri()));
				}
			}
			
		} else if( exe.getSqlLoader() != null ){
			SqlLoader sqlLoader = exe.getSqlLoader();
			
			RdSqlLoader sqlldr = new RdSqlLoader();
			prop.getStageExec().put(stageCode, sqlldr);

			sqlldr.setControlFileUri(sqlLoader.getControlFileUri());
			sqlldr.setDataFileUri(sqlLoader.getDataFileUri());
			sqlldr.setRejectFileUri(sqlLoader.getRejectFileUri());
			sqlldr.setDiscardFileUri(sqlLoader.getDiscardFileUri());
			sqlldr.setExitCodeProp(sqlLoader.getExitCodeProperty());
			sqlldr.setLogFileUri(sqlLoader.getLogFileUri());
			sqlldr.setnDiscardRowsProp(sqlLoader.getNumberOfDiscardedRowsProperty());
			sqlldr.setnRejected(sqlLoader.getNumberOfRejectedRowsProperty());
			
		} else
			throw new RuntimeException("Can not find stage implementation");
	}

	public static List<RdProp> getWkfPropInput(IWorkflow<SPFlowProp,SPContext> defn) {
		List<RdProp> props = null;
		
		if( defn.getContext().getWkfProp().getWkfPropInput() != null ){
			props = defn.getContext().getWkfProp().getWkfPropInput().get(defn.getExtCode());
		}
	
		return props == null? new ArrayList<RdProp>(0): props;
	}

	public static List<RdProp>  getWkfPropOutput(IWorkflow<SPFlowProp,SPContext> defn) {
		List<RdProp> props = null;
		
		if( defn.getContext().getWkfProp().getWkfPropOutput() != null ){
			props = defn.getContext().getWkfProp().getWkfPropOutput().get(defn.getExtCode());
		}
	
		return props == null? new ArrayList<RdProp>(0): props;
	}

	public static List<RdProp> getWkfPropGeneral(IWorkflow<SPFlowProp,SPContext> defn) {
		List<RdProp> props = null;
		
		if( defn.getContext().getWkfProp().getWkfPropGeneral() != null ){
			props = defn.getContext().getWkfProp().getWkfPropGeneral().get(defn.getExtCode());
		}
	
		return props == null? new ArrayList<RdProp>(0): props;
	}

}
