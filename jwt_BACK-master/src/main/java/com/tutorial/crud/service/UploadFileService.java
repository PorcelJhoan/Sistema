package com.tutorial.crud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {
    private String folder = "C://Users//porcel//Documents//SISTEMA//tutorial_jwt_FRONT-master//src//assets//img";
    public String saveImage(MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            byte [] bytes = file.getBytes();
            Path path = Paths.get(folder+file.getOriginalFilename().replaceAll("\\s",""));
            System.out.println("nombre : "+file.getOriginalFilename().replaceAll("\\s",""));
            Files.write(path,bytes);
            return file.getOriginalFilename().replaceAll("\\s","");
        }
        return "default.jpg";
    }

    public void deleteImage(String nombre){
        String ruta ="C://Users//porcel//Documents//SISTEMA//tutorial_jwt_FRONT-master//src//assets//img";
        File file =new File(ruta+nombre.replaceAll("\\s",""));
        file.delete();
    }
}
