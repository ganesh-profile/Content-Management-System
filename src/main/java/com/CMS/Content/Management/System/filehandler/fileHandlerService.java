package com.CMS.Content.Management.System.filehandler;

import org.springframework.web.multipart.MultipartFile;

public interface fileHandlerService {
    public String saveFile(MultipartFile multipartFile);
}
