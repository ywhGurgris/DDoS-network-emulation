package edu.cust.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cust.util.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SceneControl {
	
	@Autowired
    private SimpMessagingTemplate smt;
	
	@RequestMapping(value={"/topologyControlAjax"})
	public String topologyControl(HttpServletRequest req, Model model) {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream()))){
			String s = in.readLine();
			log.debug("s:{}", s);
			smt.convertAndSend("/topic/topology", s);
			model.addAttribute("retCode", "ok");
			return "json";
		}catch(IOException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@RequestMapping(value={"/pathControlAjax"})
	public String pathControl(HttpServletRequest req, boolean update, Model model) {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream()))){
			String s = in.readLine();
			log.debug("s:{}", s);
			smt.convertAndSend(update ? "/topic/updatePath" : "/topic/path", s);
			model.addAttribute("retCode", "ok");
			return "json";
		}catch(IOException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}

}
