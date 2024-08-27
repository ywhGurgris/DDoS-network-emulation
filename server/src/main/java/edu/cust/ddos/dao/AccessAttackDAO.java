package edu.cust.ddos.dao;

import org.springframework.stereotype.Component;

import edu.cust.ddos.domain.AccessAttack;
import edu.cust.util.DAOTemplate;
@Component
public class AccessAttackDAO extends DAOTemplate<AccessAttack> {
	public AccessAttackDAO () {
		clazz = AccessAttack.class;
		pkColumns = new String[0];
        comColumns = new String[]{"c_target_ip","c_target_port","c_threshold"};
        tableName = "c_access_attack_config";
        init();
	}
}
