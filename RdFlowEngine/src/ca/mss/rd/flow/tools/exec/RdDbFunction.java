package ca.mss.rd.flow.tools.exec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mss.rd.flow.expr.Solver;
import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.tools.prop.DbParam;

import com.scotiabank.maestro.exec.services.db.DbService;
import com.scotiabank.maestro.msg.type.RdResult;

public class RdDbFunction extends RdExecDefault {

	@Autowired
	private DbService dbService;

	public final List<DbParam> inParam = new ArrayList<DbParam>(0);
	private DbParam outParam;

	public RdDbFunction() {
		super();
	}

	public void addInParam(DbParam p) {
		inParam.add(p);
		getInSet().add(p.getName());
		getValSet().add(p.getExpr());
	}

	public DbParam getOutParam() {
		return outParam;
	}

	public void setOutParam(DbParam p) {
		this.outParam = p;
		getOutSet().add( p.getName() );
		getValSet().add(p.getExpr());
	}

	
	public List<DbParam> getInParam() {
		return inParam;
	}

	@Override
	public String toString() {
		String str = String.format("DbFunction[%s]", getName());
		
		if( outParam != null ){
			str += String.format(" Res[name=%s,type=%s,value=%s]", outParam.name, outParam.type, outParam.expr);
		}
		
		String in = "";
		for(DbParam param: inParam){
			if( !in.isEmpty() )
				in += " | ";
			in += String.format("[%s %s = %s]", param.type, param.name, param.expr);
		}

		if( !in.isEmpty() )
			str += String.format(" In[%s]", in);
		
		return str;
	}

	@Override
	public RdResult execute(Solver<String,RdProp> solver) {
		return dbService.executeCallToFunction(this, solver);
	}
	
}
