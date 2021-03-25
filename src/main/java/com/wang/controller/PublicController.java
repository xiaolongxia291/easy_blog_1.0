package com.wang.controller;

import com.wang.util.Constants;
import com.wang.util.MyBlogUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.jfunc.json.impl.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Api(tags="文件上传")
@Controller
public class PublicController {

    @ApiOperation("点击上传博客图片")
    @PostMapping("/uploadImg")
    @ResponseBody
    //参数名必须为editormd-image-file
    public String uploadImg(
            @RequestParam(value = "editormd-image-file",required = false) MultipartFile file,
            HttpServletRequest request,HttpServletResponse response) throws URISyntaxException,IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC + "upload" + File.separator + newFileName);
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/easyblog/" + newFileName;
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC+"upload");
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            response.getWriter().write( "{\"success\": 1, \"message\":\"上传成功\",\"url\":\"" + fileUrl + "\"}");
            response.getWriter().close();
            return fileUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":0}");
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\":0}");
            response.getWriter().close();
        }finally {
            return "failed";
        }
    }

    @ApiOperation("点击上传博客封面")
    @PostMapping("/uploadCover")
    @ResponseBody
    public String uploadCover(HttpServletRequest request,
            @RequestParam(value = "file",required = false) MultipartFile file)
            throws URISyntaxException,IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC + "upload" + File.separator + newFileName);
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/easyblog/" + newFileName;
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC+"upload");
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "failed";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return fileUrl;
    }

    @ApiOperation("点击上传用户头像")
    @PostMapping("/uploadAdminImg")
    @ResponseBody
    public String uploadAdminImg(HttpServletRequest request,
                              @RequestParam(value = "file",required = false) MultipartFile file)
            throws URISyntaxException,IOException {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        //创建文件
        File destFile = new File(Constants.FILE_UPLOAD_DIC + "upload" + File.separator + newFileName);
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/easyblog/" + newFileName;
        File fileDirectory = new File(Constants.FILE_UPLOAD_DIC+"upload");
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "failed";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
        return fileUrl;
    }
}
