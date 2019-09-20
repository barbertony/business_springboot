package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@CrossOrigin
public class UploadController {
    @Autowired
    IUploadService uploadService;


    @Value("${img.local.path}")
    private String imgPath;
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public  String upload(){
        return "upload";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@RequestParam("picfile")MultipartFile uploadFile , HttpServletRequest request){
        File newFile=null;
        if (uploadFile!=null&&!uploadFile.getOriginalFilename().equals("")&&uploadFile.getOriginalFilename()!=null)
        {
            String uuid= UUID.randomUUID().toString();
            String fileName=uploadFile.getOriginalFilename();
            String fileextendname=fileName.substring(fileName.lastIndexOf("."));
            String newFileName=uuid+fileextendname;
            File file=new File(imgPath);
            newFile=new File(file,newFileName);
            try {
                uploadFile.transferTo(newFile);
                return uploadService.uploadFile(newFile);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return ServerResponse.createServerResponseByFail(1,"上传失败");
    }

}
