package edu.cust.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.cust.Env;
import edu.cust.util.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : CzmlFileControl  //类名
 * @Description : czml文件的控制  //描述
 * @Author : 迷离岁月 //作者
 * @Date: 2023/1/19  0:35
 */

@Controller
@Slf4j
public class CzmlFileControl {

    @Resource
    private Env env;

    @RequestMapping(value = {"/czml"}, method = RequestMethod.POST)
    public String saveCzmlFile(@RequestParam("fileData") MultipartFile file, Model model) {
        //获取czml文件存储路径
        String path = env.getCzmlRoot();
        log.debug(saveFile(file, path));
        model.addAttribute("retCode", "ok");
        model.addAttribute("retMsg", "添加成功！");
        return "json";
    }

    @RequestMapping(value = {"/czml"}, method = RequestMethod.GET)
    public String getCzmlFile(String fileName, HttpServletResponse resp) {
        //fileName本事就附带.czml后缀
        File file = new File(env.getCzmlRoot(), fileName);
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = resp.getOutputStream();
        ) {
            resp.setContentType("application/octet-stream");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("czml:{}", fileName);
        return null;
    }

    public String saveFile(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        File dest = new File(path);
        log.debug("file---{}", dest);
        if (!dest.exists()) {
            if (!dest.mkdirs()) {
                throw new BusinessException("路径创建失败!");
            }
        }
        try {
            //把文件保存到磁盘下
            file.transferTo(new File(dest, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功！";
    }
}
