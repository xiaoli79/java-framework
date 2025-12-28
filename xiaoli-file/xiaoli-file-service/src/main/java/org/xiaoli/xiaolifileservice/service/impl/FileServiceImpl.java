package org.xiaoli.xiaolifileservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoli.xiaolifileservice.domain.dto.FileDTO;
import org.xiaoli.xiaolifileservice.domain.dto.SignDTO;
import org.xiaoli.xiaolifileservice.service.IFileService;



@Service
public class FileServiceImpl implements IFileService {


    @Override
    public FileDTO upload(MultipartFile file) {
        return null;
    }

    @Override
    public SignDTO getSign() {
        return null;
    }


}
