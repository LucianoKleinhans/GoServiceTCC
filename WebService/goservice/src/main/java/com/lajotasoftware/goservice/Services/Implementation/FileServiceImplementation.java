package com.lajotasoftware.goservice.Services.Implementation;

import com.lajotasoftware.goservice.Services.FileService;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
public class FileServiceImplementation implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file, Long idUsuario) throws IOException {

        //File name
        String name= file.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf('.')));

        //Fullpath
        String filePath=path+ File.separator+fileName;

        //Create Folder if not created
        File f = new File(path);
        if (!f.exists()){
            f.mkdir();
        }

        //File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);

        return is;
    }
}
