package edu.cust.ddos.dao;

import org.springframework.stereotype.Component;

import edu.cust.ddos.domain.DosNode;
import edu.cust.util.DAOTemplate;
@Component
public class DosNodeDAO extends DAOTemplate<DosNode> {

	public DosNodeDAO() {
		clazz = DosNode.class;
		pkColumns = new String[] {"c_id"};
        comColumns = new String[]{"c_name","c_ip","c_port","c_status"};
        tableName = "c_dos_node";
        init();
	}
}
