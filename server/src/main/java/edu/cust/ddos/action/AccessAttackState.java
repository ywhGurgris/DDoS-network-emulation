package edu.cust.ddos.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import edu.cust.ddos.dao.AccessAttackDAO;
import edu.cust.ddos.domain.AccessAttack;
import edu.cust.util.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AccessAttackState {
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private AccessAttackDAO accessAttackDAO;
	
	@RequestMapping(value={"/adm/receiveAccessAttackStateAjax"})
	public String receive(HttpServletRequest request, Model model) {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()))){
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = in.readLine()) != null) {
				sb.append(line);
			}
			String json = sb.toString();
			messagingTemplate.convertAndSend("/topic/aa", json);
			log.debug("send access attack msg complete: {}", json);
			model.addAttribute("retCode", "ok");
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		return "json";
	}
	
	@RequestMapping(value={"/adm/accessAttackProtectAjax"})
	public String accessAttackProtect(String protect, Model model) {
		List<AccessAttack> result = accessAttackDAO.loadMore(null, null);
		if(result.isEmpty()) {
			throw new BusinessException("请先配置攻击节点和靶机信息");
		}
		AccessAttack aa = result.get(0);
		HashMap<String, Object> map = new HashMap<>();
		map.put("protect", protect);
		map.put("threshold", aa.getThreshold() + "");
		try(DatagramSocket ds = new DatagramSocket()){
			ds.connect(InetAddress.getByName(aa.getTargetIp()), aa.getTargetPort());
			byte[] data = new Gson().toJson(map).getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ds.send(packet);
			ds.disconnect();
		}catch(IOException ex) {
			throw new BusinessException(ex.getMessage());
		}
		model.addAttribute("retCode", "ok");
		return "json";
	}

}
