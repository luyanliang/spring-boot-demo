package com.fhmou.controller;

import com.fhmou.dao.UserRepository;
import com.fhmou.dto.StatusResult;
import com.fhmou.dto.WebConstantMsg;
import com.fhmou.model.User;
import com.fhmou.utils.poi.POIConfigure;
import com.fhmou.utils.poi.POIExcelObjectReader;
import com.fhmou.utils.poi.POIExcelObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/11/1
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/import/excel")
    public StatusResult importFromExcel(@RequestParam("checkFile") MultipartFile file) {
        StatusResult result = new StatusResult();
        logger.info("接受到批量上传数据，文件大小为：" + file.getSize());
        try {
            POIConfigure poiConfigure = new POIConfigure("classpath:user.properties");
            POIExcelObjectReader reader = new POIExcelObjectReader(poiConfigure);
            List<User> userList = reader.read(file.getInputStream());
            logger.info("上传用户数量为" + userList.size());
            userRepository.save(userList);
            result.setResult(WebConstantMsg.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(WebConstantMsg.INTERNAL_SERVER_ERROR);
            result.setData(e);
        }
        return result;
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) {
        List<User> userList = userRepository.findAll();
        OutputStream stream = null;
        try {
            stream = response.getOutputStream();
            if (null != userList && !userList.isEmpty()) {
                String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                fileName = "用户信息_" + fileName;
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1"); // 处理中文文件名
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

                POIConfigure configure = new POIConfigure("classpath:user.properties");
                POIExcelObjectWriter writer = new POIExcelObjectWriter(configure);
                writer.write(stream, userList, "用户列表");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流错误! 错误信息为: {}", e);
                }
            }
        }
    }
}
