package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}") // application.yml에 지정한 커스텀 저장 디렉토리
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @ResponseBody
    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName,
                           @RequestParam MultipartFile file, // 스프링이 제공하는 MultipartFile
                           HttpServletRequest request) throws IOException {
        log.info("request = {}", request);
        log.info("itemName = {}", itemName);
        log.info("file = {}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장! fullPath = {}", fullPath);

            file.transferTo(new File(fullPath));
        }

        return "successfully saved!";
    }

}
