package com.phanlop.khoahoc.Service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.phanlop.khoahoc.Entity.File;

public interface FileServices {
    public File addFile(MultipartFile multipartFile);
    public File getFileByUUID(UUID fileUUID);
}
