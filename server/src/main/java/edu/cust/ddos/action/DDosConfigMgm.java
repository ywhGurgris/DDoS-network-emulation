package edu.cust.ddos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cust.ddos.dao.DDosConfigDAO;
import edu.cust.ddos.domain.DDosConfig;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DDosConfigMgm {
	@Autowired
	private DDosConfigDAO ddosConfigDAO;
	
	@RequestMapping(value={"/adm/ddosConfigLoad"})
	public String load(Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		if(!result.isEmpty()) {
			model.addAttribute("result", result.get(0));
		}
		return "adm/ddosConfigMgm";
	}
	
	@RequestMapping(value={"/adm/ddosConfigUpdateAjax"})
	@Transactional
	public String update(DDosConfig dc, Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		if(result.isEmpty()) {
			ddosConfigDAO.insert(dc);
		}else {
			ddosConfigDAO.update(dc);
		}
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "更新成功！");
		return "json";
	}
	
	

}
