package edu.cust.ddos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cust.ddos.dao.AccessAttackDAO;
import edu.cust.ddos.domain.AccessAttack;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccessAttackConfigMgm {
	@Autowired
	private AccessAttackDAO accessAttackDAO;
	
	@RequestMapping(value={"/adm/accessAttackConfigLoad"})
	public String load(Model model) {
		List<AccessAttack> result = accessAttackDAO.loadMore(null, null);
		if(!result.isEmpty()) {
			model.addAttribute("result", result.get(0));
		}
		return "adm/ddosConfigMgm";
	}
	
	@RequestMapping(value={"/adm/accessAttackConfigUpdateAjax"})
	@Transactional
	public String update(AccessAttack aa, Model model) {
		List<AccessAttack> result = accessAttackDAO.loadMore(null, null);
		if(result.isEmpty()) {
			accessAttackDAO.insert(aa);
		}else {
			accessAttackDAO.update(aa);
		}
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "更新成功！");
		return "json";
	}

}
