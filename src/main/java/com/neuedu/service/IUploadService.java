package com.neuedu.service;

import com.neuedu.common.ServerResponse;

import java.io.File;

public interface IUploadService {
    ServerResponse uploadFile(File uploadFile);
}
