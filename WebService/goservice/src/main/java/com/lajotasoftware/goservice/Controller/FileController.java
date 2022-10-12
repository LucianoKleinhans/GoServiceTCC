package com.lajotasoftware.goservice.Controller;

import com.lajotasoftware.goservice.DAO.DAOUsuario;
import com.lajotasoftware.goservice.Entity.FileResponse;
import com.lajotasoftware.goservice.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private DAOUsuario daoUsuario;

    @Value("${project.image}")
    private String path;

    @PostMapping("/image/upload")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam("image")MultipartFile image,
            @RequestParam("user")Long idUser
    ) {
        String fileName = null;
        Long user = 0L;
        try {
            fileName = this.fileService.uploadImage(path, image, idUser);
            user = idUser;
            daoUsuario.saveImageUrl(user,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null, "Imagem não foi salva!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new FileResponse(fileName, "Imagem salva com sucesso!"), HttpStatus.OK);
    }

    @GetMapping(value = "/image/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void fileDownload(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
