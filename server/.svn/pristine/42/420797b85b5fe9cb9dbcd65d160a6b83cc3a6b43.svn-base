package edu.cust.ddos.dao;

import org.springframework.stereotype.Component;

import edu.cust.ddos.domain.BhrConfig;
import edu.cust.util.DAOTemplate;
@Component
public class BhrConfigDAO extends DAOTemplate<BhrConfig> {

	public BhrConfigDAO () {
		clazz = BhrConfig.class;
		pkColumns = new String[0];
        comColumns = new String[]{"c_scene_url"};
        tableName = "c_bhr_config";
        init();
	}
}
