package com.kappa.resources.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class File {
    private String name;
    private String userId;
    private MultipartFile file;
}
