package com.scotiabank.maestro.exec.services.scn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scotiabank.maestro.exec.services.constants.IBeanIdConstants;
import com.scotiabank.maestro.exec.services.message.executor.ISystemChangeNumberSelectCallback;


public class SystemChangeNumberFinder {
	private static final String SQL_SELECT_SCN = "select dbms_flashback.get_system_change_number scn from dual";

	@Autowired
	@Qualifier(IBeanIdConstants.BEAN_ID_JDBC_TEMPLATE_EXEC)
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ISystemChangeNumberSelectCallback systemChangeNumberSelectCallback;
	
	public Long findSystemChangeNumber() {
		return jdbcTemplate.execute(SQL_SELECT_SCN, systemChangeNumberSelectCallback);
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public ISystemChangeNumberSelectCallback getSystemChangeNumberSelectCallback() {
		return systemChangeNumberSelectCallback;
	}


	public void setSystemChangeNumberSelectCallback(ISystemChangeNumberSelectCallback systemChangeNumberSelectCallback) {
		this.systemChangeNumberSelectCallback = systemChangeNumberSelectCallback;
	}


}
