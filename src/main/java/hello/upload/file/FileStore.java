package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public UploadFile storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String uploadFileName = file.getOriginalFilename(); // 업로드한 파일명(ex.image.png)
        String storeFileName = createStoreFileName(uploadFileName); // 고유한 파일명(ex.12345678-abcd-~~~.png)

        file.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(uploadFileName, storeFileName);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> files) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                UploadFile uploadFile = storeFile(file);
                storeFileResult.add(uploadFile);
            }
        }

        return storeFileResult;
    }

    // 고유한 파일명 만들기(ex.abcd1234-5668-~~~.png)
    private String createStoreFileName(String filename) {
        String uuid = UUID.randomUUID().toString();
        String fileExtension = extractExtension(filename);

        return uuid + "." + fileExtension;
    }

    // image.png -> png
    private String extractExtension(String fileName) {
        int idxOfPeriod = fileName.lastIndexOf(".");
        return fileName.substring(idxOfPeriod + 1);
    }

}
