package edu.cust.ddos.action;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import edu.cust.ddos.dao.DDosConfigDAO;
import edu.cust.ddos.dao.DosNodeDAO;
import edu.cust.ddos.domain.DDosConfig;
import edu.cust.ddos.domain.DosNode;
import edu.cust.util.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DDosTargetState {
	
//	private List<Object> cpuList = Collections.synchronizedList(new LinkedList<Object>());
//	private List<Object> memoryList = Collections.synchronizedList(new LinkedList<Object>());
//	private List<Object> networkList = Collections.synchronizedList(new LinkedList<Object>());
//	private List<Object> backlogList = Collections.synchronizedList(new LinkedList<Object>());
	
	@Autowired
	private DDosConfigDAO ddosConfigDAO;
	
	@Autowired
	private DosNodeDAO dosNodeDAO;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@RequestMapping(value={"/adm/receiveDdosTargetStateAjax"})
	public String receive(String cpu, String memory, String recv, String send, String backlog, Model model) {
		HashMap<String, String> data = new HashMap<>();
		data.put("cpu", cpu);
		data.put("memory", memory);
		data.put("recv", recv);
		data.put("send", send);
		data.put("backlog", backlog);
		log.debug("c:{}, m:{}, r:{}, s:{}, b:{}", cpu, memory, recv, send, backlog);
//		log.debug("send msg");
		messagingTemplate.convertAndSend("/topic/ddos", new Gson().toJson(data));
		log.debug("send ddos msg complete");
		model.addAttribute("retCode", "ok");
		return "json";
	}
	
	@RequestMapping(value={"/adm/receiveDdosTargetProtectInfoAjax"})
	public String receiveProtectInfo(String detectedTime, Model model) {
		HashMap<String, String> data = new HashMap<>();
		data.put("detectedTime", detectedTime);
//		log.debug("send msg");
		messagingTemplate.convertAndSend("/topic/ddos", new Gson().toJson(data));
		model.addAttribute("retCode", "ok");
		return "json";
	}
	
	@RequestMapping(value={"/adm/dosAdState"})
	public String dosAdState(Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		if(!result.isEmpty()) {
			model.addAttribute("result", result.get(0));
		}
		return "adm/dosAdState";
	}
	
	@RequestMapping(value={"/adm/obtainDdosTargetState"})
	public String obtain(Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		if(!result.isEmpty()) {
			model.addAttribute("result", result.get(0));
		}
//		model.addAttribute("cpu", cpuList);
//		model.addAttribute("memory", memoryList);
//		model.addAttribute("network", networkList);
//		model.addAttribute("backlog", backlogList);
		return "adm/dosAdState";
	}
	
	@RequestMapping(value={"/adm/ddosProtectAjax"})
	public String protect(boolean protect, Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		if(result.isEmpty()) {
			throw new BusinessException("请先完成DDoS配置");
		}
		DDosConfig dc = result.get(0);
		HashMap<String, Object> map = new HashMap<>();
		map.put("protect", protect);
		map.put("backlogDuration", dc.getDuration());
		try(DatagramSocket ds = new DatagramSocket()){
			ds.connect(InetAddress.getByName(dc.getIp()), 8888);
			byte[] data = new Gson().toJson(map).getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ds.send(packet);
			ds.disconnect();
			
		}catch(IOException ex) {
			throw new BusinessException(ex.getMessage());
		}
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "防护命令已发送！");
//		model.addAttribute("cpu", cpuList);
//		model.addAttribute("memory", memoryList);
//		model.addAttribute("network", networkList);
//		model.addAttribute("backlog", backlogList);
		return "json";
	}
	
	@RequestMapping(value={"/adm/ddosAttackStartAjax2"})
	public String start(Model model) {
		List<DDosConfig> result = ddosConfigDAO.loadMore(null, null);
		if(result.isEmpty()) {
			throw new BusinessException("请先完成DDoS配置");
		}
		DDosConfig dc = result.get(0);
		List<DosNode> nodes = dosNodeDAO.loadMore(null, null);
		HashMap<String, Object> map = new HashMap<>();
		map.put("command", "synflood");
		map.put("targetIp", dc.getIp());
		map.put("targetPort", dc.getPort());
		map.put("startupDelay", dc.getStartupDelay());
		map.put("interval", dc.getInterval());
		map.put("count", dc.getCount());
		try(DatagramSocket ds = new DatagramSocket()){
			for(DosNode dn : nodes) {
				map.put("nodeId", dn.getId());
				map.put("localIp", dn.getIp());
				ds.connect(InetAddress.getByName(dn.getIp()), dn.getPort());
				byte[] data = new Gson().toJson(map).getBytes();
				DatagramPacket packet = new DatagramPacket(data, data.length);
				ds.send(packet);
				ds.disconnect();
			}
		}catch(IOException ex) {
			throw new BusinessException(ex.getMessage());
		}
		model.addAttribute("retCode", "ok");
		model.addAttribute("retMsg", "已将启动命令发送所有节点！");
		return "json";
	}

}
