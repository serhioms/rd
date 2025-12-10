package ca.mss.rd.flow.tools.exec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mss.rd.flow.expr.Solver;
import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.tools.prop.DbParam;

import com.scotiabank.maestro.exec.services.db.DbService;
import com.scotiabank.maestro.msg.type.RdResult;

public class RdDbProcedure extends RdExecDefault {

	@Autowired
	private DbService dbService;

	private final List<DbParam> inParam = new ArrayList<DbParam>(0);
	private final List<DbParam> outParam = new ArrayList<DbParam>(0);
	private final List<RdProp> mapping = new ArrayList<RdProp>(0);

	public void addPropMap(List<RdProp> mapping) {
		this.mapping.addAll(mapping);
	}

	public List<DbParam> getInParam() {
		return inParam;
	}

	public List<DbParam> getOutParam() {
		return outParam;
	}

	public List<RdProp> getMapping() {
		return mapping;
	}

	public void addInParam(DbParam p) {
		inParam.add(p);
		getInSet().add(p.getName());
	}

	public void addOutParam(DbParam p) {
		outParam.add(p);
		getOutSet().add(p.getName());
		getValSet().add(p.getExpr());
	}

	@Override
	public String toString() {
		String str = String.format("DbProcedure[%s]", getName());

		String in = "";
		for(DbParam param: inParam){
			if( !in.isEmpty() )
				in += " | ";
			in += String.format("%s %s = %s", param.type, param.name, param.expr);
		}
		
		if( !in.isEmpty() )
			str += String.format(" In[%s]", in);

		String out = "";
		for(DbParam param: outParam){
			if( !out.isEmpty() )
				out += " | ";
			out += String.format("%s %s = %s", param.type, param.name, param.expr);
		}

		if( !out.isEmpty() )
			str += String.format(" Out[%s]", out);

		String map = "";
		for(Iterator<RdProp> iter = mapping.iterator(); iter.hasNext(); ){
			RdProp next = iter.next();
			if( !map.isEmpty() )
				map += " | ";
			map += String.format("%s => %s", next.getName(), next.getValue());
		}

		if( !map.isEmpty() )
			str += String.format(" Map[%s]", map);

		return str;
	}

	@Override
	public RdResult execute(Solver<String,RdProp> solver) {
		return dbService.executeCallToProcedure(this, solver);
	}
}
