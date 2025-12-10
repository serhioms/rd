package com.scotiabank.maestro.msg;

import ca.mss.rd.flow.prop.RdExec;
import ca.mss.rd.flow.tools.exec.RdChecksumGeneration;
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

public enum ExecDefinitionType  {
	NO_OP(RdNoOperation.class),
	DB_FUNCTION(RdDbFunction.class),
	DB_PROCEDURE(RdDbProcedure.class),
	POLLING_FILE_WATCHER(RdFileWatcher.class),
	FILE_TRANSFER(RdTransferFile.class),
	DELETE_RESOURCE(RdDeleteFile.class),
	CHECKSUM_VALIDATION(RdChecksumValidation.class),
	CHECKSUM_GENERATION(RdChecksumGeneration.class),
	CALL_EXTERNAL_PROCESS(RdExtProc.class),
	SQL_LOADER(RdSqlLoader.class),	
	JAVA_EXTENSION(RdJavaExt.class);
	//CALL_ACTIVITI(RdCallActiviti.class); // TODO: Not implemented
	
	
	private Class<? extends RdExec> execDefinitionClass;
	
	private ExecDefinitionType(Class<? extends RdExec> execDefinitionClass) {
		this.execDefinitionClass=execDefinitionClass;
	}
	public Class<? extends RdExec> getExecDefinitionClass() {
		return execDefinitionClass;
	}
	
	public static ExecDefinitionType getTypeByExecDefinitionClass(Class<? extends RdExec> execDefinitionClass) {
		ExecDefinitionType result = null;
		for (ExecDefinitionType type : ExecDefinitionType.values()) {
			if(type.getExecDefinitionClass().isAssignableFrom(execDefinitionClass)) {
				result=type;
				break;
			}
		}
		return result;
	}
}
