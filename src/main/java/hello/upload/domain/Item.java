package hello.upload.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;
    private String name;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;

    public Item(String name, UploadFile attachFile, List<UploadFile> imageFiles) {
        this.name = name;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }

}
