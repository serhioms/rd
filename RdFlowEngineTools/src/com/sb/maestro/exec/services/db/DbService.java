package com.scotiabank.maestro.exec.services.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ca.mss.rd.flow.expr.Solver;
import ca.mss.rd.flow.tools.exec.RdDbFunction;
import ca.mss.rd.flow.tools.exec.RdDbProcedure;
import ca.mss.rd.flow.tools.prop.DbParam;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.Timing;

import com.scotiabank.maestro.exec.UtilExec;
import com.scotiabank.maestro.exec.services.constants.IBeanIdConstants;
import com.scotiabank.maestro.msg.type.RdResult;
import com.scotiabank.maestro.msg.type.StageTaskStatusTypeEnum;

@Service("DbService")
public class DbService {

	public DbService() {
		super();
	}

	@Autowired
	@Qualifier(IBeanIdConstants.BEAN_ID_JDBC_TEMPLATE_EXEC)
	private JdbcTemplate jdbcTemplate;
	
	public RdResult executeCallToFunction(RdDbFunction fn, Solver solver) {
		return getJdbcTemplate().execute(
		    new CallableStatementCreator() {
		        @Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException{
		            String sql = createCall(fn);
		            
					CallableStatement cs = con.prepareCall( sql);
		            setParametersByIndex(cs, fn.getInParam(), Arrays.asList(fn.getOutParam()), solver);
		            return cs;
		        }
		    },
		    new CallableStatementCallback<RdResult>() {
		        @Override
				public RdResult doInCallableStatement(CallableStatement cs) throws SQLException{
		        	
		        	RdResult result = new RdResult();
		        	boolean execute = false;
		        	
		        	try {

		        		result.setStart(System.currentTimeMillis());
		        		execute = cs.execute();
		        		result.setStop(System.currentTimeMillis());
			        	
			            //if( execute ) {
			            	Object value = cs.getObject(1);
			            	
			            	DbParam param = fn.getOutParam();
			            	DbParam r = new DbParam(param.getName(), param.getType(), value);
			            	
			            	result.getResults().add(r);
			            	
			            	assert( Logger.DB.isOn ? Logger.DB.printf("%s OUT %s = '%s'", r.getType(), r.getName(), r.getValue()): true);
//			            } else {
//			            	result.setMessage("No exceptions.. Input parameters are not in domain???");
//			            }
		        	} catch( Throwable t){
		        		result.setStop(System.currentTimeMillis());
			        	result.setMessage(t.getMessage());
		        	} finally {
//        				if( execute ){ 				            
//			    			result.setCompletedStatus(StageTaskStatusTypeEnum.COMPLETED);
//        					assert( Logger.DB.isOn ? Logger.DB.printf("%s: %s took %s", result.getCompletedStatus(), fn.getName(), Timing.formatMls(result.getDuration())): true);
//        				} else {
//			    			result.setCompletedStatus(StageTaskStatusTypeEnum.FAILED);
//        					assert( Logger.DB.isOn ? Logger.DB.printf("%s: %s took %s", result.getCompletedStatus(), fn.getName(), Timing.formatMls(result.getDuration())): true);
//        				}
		        	}
		        	
        			return result;
		        }
		    }
		);
	}

	public RdResult executeCallToProcedure(RdDbProcedure proc, Solver solver) {
		
		return jdbcTemplate.execute(
		    new CallableStatementCreator() {
		        @Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException{
		            CallableStatement cs = con.prepareCall( createCall(proc));
		            setParametersByName(cs, proc.getInParam(), proc.getOutParam(), solver);
		            return cs;
		        }
		    },
		    new CallableStatementCallback<RdResult>() {
		        @Override
				public RdResult doInCallableStatement(CallableStatement cs) throws SQLException{

		        	RdResult result = new RdResult();
		        	boolean execute = false;

		        	try {
		        		result.setStart(System.currentTimeMillis());
		        		execute = cs.execute();
		        		result.setStop(System.currentTimeMillis());
	
//			            if( execute ){
			            	for (DbParam param : proc.getOutParam()) {
				            
				            	String value = UtilExec.parameterToString(cs.getObject(param.getName()), param.getType());
					            
					            DbParam r = new DbParam(param.getName(), param.getType(), value);
					            result.getResults().add(r);
								
				            	assert( Logger.DB.isOn ? Logger.DB.printf("%s OUT %s = '%s'", r.getName(), r.getName(), r.getValue()): true);
			            	}
//			            } else {
//			            	result.setMessage("No exceptions.. Input parameters are not in domain???");
//			            }
		        	} catch( Throwable t){
		        		result.setStop(System.currentTimeMillis());
			        	result.setMessage(t.getMessage());
		        	} finally {
//        				if( execute ){ 				            
//			    			result.setCompletedStatus(StageTaskStatusTypeEnum.COMPLETED);
//        					assert( Logger.DB.isOn ? Logger.DB.printf("%s: %s took %s", result.getCompletedStatus(), proc.getName(), Timing.formatMls(result.getDuration())): true);
//        				} else {
//			    			result.setCompletedStatus(StageTaskStatusTypeEnum.FAILED);
//        					assert( Logger.DB.isOn ? Logger.DB.printf("%s: %s took %s", result.getCompletedStatus(), proc.getName(), Timing.formatMls(result.getDuration())): true);
//        				}
		        	}
		        	return result;
		        }
		    }
		);
	}

	protected String createCall(RdDbFunction fn) {
		if( fn.getOutParam() == null) {
			throw new IllegalArgumentException(String.format("Function '%s' has not defined a return value", fn.getName()));
		}
		
		StringBuilder buf = new StringBuilder("{? = call ");
		buf.append(fn.getName());
		buf.append("(");
		int params = fn.getInParam().size();
		for( int i = 0; i < params; i++) {
			buf.append("?,");
		}
		if( params > 0) {
			buf.deleteCharAt( buf.length()-1);
		}
		buf.append(")}");
		
		assert( Logger.DB.isOn ? Logger.DB.printf(buf.toString()): true);
		
		return buf.toString();
	}

	protected String createCall(RdDbProcedure proc) {
		StringBuilder buf = new StringBuilder("{call ");
		buf.append(proc.getName());
		buf.append("(");
		int params = proc.getInParam().size() + proc.getOutParam().size();
		for( int i = 0; i < params; i++) {
			buf.append("?,");
		}
		if( params > 0) {
			buf.deleteCharAt( buf.length()-1);
		}
		buf.append(")}");
		
		assert( Logger.DB.isOn ? Logger.DB.printf(buf.toString()): true);
		
		return buf.toString();
	}
	
	protected void setParametersByName(CallableStatement stmt, List<DbParam> inParams, List<DbParam> outParams, Solver solver) throws SQLException {
		if (inParams != null) {
			for (DbParam param : inParams) {
				assert( Logger.DB.isOn ? Logger.DB.printf(String.format("IN %s = '%s' %s", param.getName(), solver.evalExpression(param.getName(), param.getExpr()), param.getType())): true);
				Object value = UtilExec.getParameterValue(solver.evalExpression(param.getName(), param.getExpr()), param.getType());			
				if (value == null) {
					stmt.setNull(param.getName(), param.getType().sqlType());				
				} else {
					stmt.setObject(param.getName(), value, param.getType().sqlType());				
				}
			}			
		}
		
		if (outParams != null) {
			for (DbParam param : outParams) {
				assert( Logger.DB.isOn ? Logger.DB.printf(String.format("OUT %s %s", param.getName(), param.getType())): true);
				stmt.registerOutParameter(param.getName(), param.getType().sqlType());
			}					
		}
	}
	
	protected void setParametersByIndex(CallableStatement stmt, List<DbParam> inParams, List<DbParam> outParams, Solver solver) throws SQLException {
		int idx = 1;
		
		if (outParams != null) {
			for (DbParam param : outParams) {
				assert( Logger.DB.isOn ? Logger.DB.printf(String.format("OUT %s %s", solver.detokenizedExpression(param.getName()), param.getType())): true);
				stmt.registerOutParameter(idx++, param.getType().sqlType());
			}					
		}
		
		if (inParams != null) {
			for (DbParam param : inParams) {
				assert( Logger.DB.isOn ? Logger.DB.printf(String.format("IN %s %s", param.getName(), param.getExpr()), param.getType()): true);
				Object value = UtilExec.getParameterValue(solver.evalExpression(param.getName(), param.getExpr()), param.getType());			
				if (value == null) {
					stmt.setNull(idx++, param.getType().sqlType());				
				} else {
					stmt.setObject(idx++, value, param.getType().sqlType());				
				}
			}			
		}
	}

	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}		
}
