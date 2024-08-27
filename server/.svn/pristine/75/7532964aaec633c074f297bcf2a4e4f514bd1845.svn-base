package edu.cust.ddos.dao;

import org.springframework.stereotype.Component;

import edu.cust.ddos.domain.BhrNode;
import edu.cust.util.DAOTemplate;
@Component
public class BhrNodeDAO extends DAOTemplate<BhrNode> {
	
	public BhrNodeDAO() {
		clazz = BhrNode.class;
		pkColumns = new String[] {"c_id"};
        comColumns = new String[]{"c_name","c_ip","c_port","c_target_ip","c_status"};
        tableName = "c_bhr_node";
        init();
	}

}
