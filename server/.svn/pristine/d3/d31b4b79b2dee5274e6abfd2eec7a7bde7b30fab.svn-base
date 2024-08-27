package edu.cust.ddos.action;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import edu.cust.ddos.dao.BhrNodeDAO;
import edu.cust.ddos.domain.BhrNode;
import edu.cust.util.BusinessException;
import edu.cust.util.page.Page;
import edu.cust.util.page.PageFactory;
import edu.cust.util.search.Search;
import lombok.extern.slf4j.Slf4j;

@Controller
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class BhrNodeMgm {

	@Autowired
	private BhrNodeDAO bhrNodeDAO;
	
//	@Autowired
//	private BhrConfigDAO bhrConfigDAO;
	
	@Autowired
	private JdbcTemplate outerJt;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	//bhrAttackState
//	@RequestMapping(value={"/adm/bhrAttackState"})
//	public String bhrAttackState(Model model) {
//		List<BhrConfig> result = bhrConfigDAO.loadMore(null, null);
//		if(!result.isEmpty()) {
//			model.addAttribute("result", result.get(0));
//		}
//		return "adm/bhrAttackState";
//	}
//	
//	@RequestMapping(value={"/adm/bhrConfigLoad"})
//	public String configLoad(Model model) {
//		List<BhrConfig> result = bhrConfigDAO.loadMore(null, null);
//		if(!result.isEmpty()) {
//			model.addAttribute("result", result.get(0));
//		}
//		return "adm/blackholeRouteMgm";
//	}
//	
//	@RequestMapping(value={"/adm/bhrConfigUpdateAjax"})
//	@Transactional
//	public String configUpdate(BhrConfig bc, Model model) {
//		int n = bhrConfigDAO.update(bc);
//		if(n == 0) {
//			bhrConfigDAO.insert(bc);
//		}
//		model.addAttribute("retCode", "ok");
//		model.addAttribute("retMsg", "修改成功！");
//		return "json";
//	}
	
	@RequestMapping(value={"/adm/bhrNodesListAjax"})
	public String list(int page, int rows, Search search, Model model) {
		Page mlpage = PageFactory.getPage();
		mlpage.setPageNum(page);
		mlpage.setRecordNum(rows);
		String sql = search.buildSQL(bhrNodeDAO);
		log.debug("sql:{}", sql);
		
		List<?> result = mlpage.getOnePage(sql, search.getParams(), bhrNodeDAO);
		model.addAttribute("pages", mlpage);
		model.addAttribute("result", result);
		return "json";
	}
	
	@RequestMapping(value={"/adm/bhrNodeAddAjax"})
	@Transactional
	public String add(BhrNode dn, Model model) {
		log.debug("id:{}", dn.getId());
		dn.setId(UUID.randomUUID().toString());
		bhrNodeDAO.insert(dn);
		updateAddresses(dn);
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "添加成功！");
		return "json";
	}
	
	@RequestMapping(value={"/adm/bhrNodeUpdateAjax"})
	@Transactional
	public String update(BhrNode dn, Model model) {
		log.debug("id:{}", dn.getId());
		BhrNode old = bhrNodeDAO.loadOne(dn.getId());
		dn.setStatus(old.getStatus());
		//bhrNodeDAO.update(dn);
		updateAddresses(dn);
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "更新成功！");
		return "json";
	}
	
	@RequestMapping(value={"/adm/bhrNodeDeleteAjax"})
	@Transactional
	public String del(String id, Model model) {
		BhrNode old = bhrNodeDAO.loadOne(id);
		old.setTargetIp("");
		updateAddresses(old);
		bhrNodeDAO.delete(id);
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "删除成功！");
		return "json";
	}
	
	private void updateAddresses(BhrNode bn) {
		try(DatagramSocket ds = new DatagramSocket()){
			HashMap<String, Object> map = new HashMap<>();
			map.put("event", "attack");
			map.put("targetIp", bn.getTargetIp());
			ds.connect(InetAddress.getByName(bn.getIp()), bn.getPort());
			byte[] data = new Gson().toJson(map).getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ds.send(packet);
			ds.disconnect();
		}catch(IOException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@RequestMapping(value={"/adm/bhrNodeStatusNotifyAjax"})
	public String notify(String midIp, String srcIps, String sums, Model model) {
		Date d = new Date();
		if(srcIps != null && !srcIps.equals("")) {
			String[] srcIpArr = srcIps.split(",");
			String[] sumArr = sums.split(",");
			int i = 0;
			for (String srcIp : srcIpArr) {
				int n = outerJt.update("update c_attack_path set c_start_time=?,c_action=? where c_src_ip=? and c_mid_ip=?", d, "blackhole:" + sumArr[i], srcIp, midIp);
				if(n == 0) {
					outerJt.update("insert into c_attack_path (c_src_ip,c_mid_ip,c_start_time,c_action)values(?,?,?,?)", srcIp, midIp, d, "blackhole:" + sumArr[i]);
				}
				i++;
			}
		}
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "通知成功！");
		return "json";
	}
	
	@RequestMapping(value={"/adm/receiveRouterStateAjax"})
	public String receive(String rid, String recv, String send, Model model) {
		HashMap<String, String> data = new HashMap<>();
		data.put("rid", rid);
		data.put("recv", recv);
		data.put("send", send);
		messagingTemplate.convertAndSend("/topic/bhr", new Gson().toJson(data));
		log.debug("rid:{}, r:{}, s:{}", rid, recv, send);
		model.addAttribute("retCode", "ok");
		return "json";
	}
}
