package edu.cust.ddos.action;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cust.ddos.dao.DDosConfigDAO;
import edu.cust.ddos.dao.DosNodeDAO;
import edu.cust.ddos.domain.DDosConfig;
import edu.cust.ddos.domain.DosNode;
import edu.cust.util.page.Page;
import edu.cust.util.page.PageFactory;
import edu.cust.util.search.Search;
import lombok.extern.slf4j.Slf4j;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class DosNodeMgm {
	
	@Autowired
	private DDosConfigDAO ddosConfigDAO;
	
	@Autowired
	private DosNodeDAO dosNodeDAO;
	
	@Autowired
	private JdbcTemplate outerJt;
	
	@RequestMapping(value={"/adm/dosNodesListAjax"})
	public String list(int page, int rows, Search search, Model model) {
		Page mlpage = PageFactory.getPage();
		mlpage.setPageNum(page);
		mlpage.setRecordNum(rows);
		String sql = search.buildSQL(dosNodeDAO);
		log.debug("sql:{}", sql);
		
		List<?> result = mlpage.getOnePage(sql, search.getParams(), dosNodeDAO);
		model.addAttribute("pages", mlpage);
		model.addAttribute("result", result);
		return "json";
	}
	
	@RequestMapping(value={"/adm/dosNodeAddAjax"})
	@Transactional
	public String add(DosNode dn, Model model) {
		log.debug("id:{}", dn.getId());
		dn.setId(UUID.randomUUID().toString());
		dosNodeDAO.insert(dn);
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "添加成功！");
		return "json";
	}
	
	@RequestMapping(value={"/adm/dosNodeUpdateAjax"})
	@Transactional
	public String update(DosNode dn, Model model) {
		log.debug("id:{}", dn.getId());
		DosNode old = dosNodeDAO.loadOne(dn.getId());
		dn.setStatus(old.getStatus());
		dosNodeDAO.update(dn);
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "更新成功！");
		return "json";
	}
	
	@RequestMapping(value={"/adm/dosNodeDeleteAjax"})
	@Transactional
	public String del(String id, Model model) {
		dosNodeDAO.delete(id);
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "删除成功！");
		return "json";
	}
	
	@RequestMapping(value={"/adm/dosNodeStatusNotifyAjax"})
	public String notify(String id, String msg, Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		String dstIp = result.isEmpty() ? null : result.get(0).getIp();
		DosNode dn = dosNodeDAO.loadOne(id);
		dn.setStatus(msg);
		dosNodeDAO.update(dn);
		if("已开始".equals(msg)) {
			outerJt.update("insert into c_attack_path (c_src_ip,c_dst_ip,c_start_time,c_action)values(?,?,?,?)", dn.getIp(), dstIp, new Date(), "ddos");
		} else {
			outerJt.update("delete from c_attack_path where c_src_ip=? and c_dst_ip=? and c_action=?", dn.getIp(), dstIp, "ddos");
		}
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "通知成功！");
		return "json";
	}

}
