package edu.cust.ddos.dao;

import org.springframework.stereotype.Component;

import edu.cust.ddos.domain.DDosConfig;
import edu.cust.util.DAOTemplate;
@Component
public class DDosConfigDAO extends DAOTemplate<DDosConfig> {

	public DDosConfigDAO() {
		clazz = DDosConfig.class;
		pkColumns = new String[0];
        comColumns = new String[]{"c_ip","c_port","c_startup_delay","c_interval","c_count","c_duration","c_video_url","c_scene_url"};
        tableName = "c_ddos_config";
        init();
	}
}
