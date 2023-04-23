package com.example.ebookservice.controller;

import com.example.ebookdatamodel.entity.Book;
import com.example.ebookdatamodel.entity.Image;
import com.example.ebookservice.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/image")
public class ImageController {
    @Autowired
    private EntityLinks entityLinks;
    @Autowired
    private ResourceLoader resourceLoader;
    private ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer imageId){
        boolean jpgCheck = false;
        String imgdir ="D:/image/anya.png";
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isPresent()){
            imgdir = imgdir.substring(0,9)+image.get().getImg();
            if(image.get().getImg().split("\\.")[1].equals("jpg")){
                jpgCheck = true;
            }
        }

        byte[] imageData = new byte[0];
        try {
            File file = new File(imgdir);
            BufferedImage i = ImageIO.read(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (jpgCheck){
                ImageIO.write(i, "jpg", outputStream);
            }else {
                ImageIO.write(i, "png", outputStream);
            }

            imageData = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
        HttpHeaders headers = new HttpHeaders();
        if (jpgCheck){
            headers.setContentType(MediaType.IMAGE_JPEG);
        }else{
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
    @GetMapping("/")
    public Iterable<Image> getAll(){
        return imageRepository.findAll();
    }
    //add image
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public String addImage(@RequestParam("file") MultipartFile file,@RequestParam("name") String name
            ,@RequestParam("bid") Integer bid){
        String fileName = file.getOriginalFilename();
        try {
            // Save the file to the server
            File destinationFile = new File("D:/image/" + fileName);
            file.transferTo(destinationFile);
            Image i = new Image();
            i.setImg(fileName);
            i.setName(name);
            Book b = new Book();
            b.setId(bid);
            i.setBook(b);
            imageRepository.save(i);
            return "File uploaded successfully: " + fileName;
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }

    }
    //update image
    @PutMapping("/{imageId}")
    public Image updateImage(@RequestBody Image image){
        return imageRepository.save(image);
    }
    //delete image
    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable Integer imageId){
        try{
            imageRepository.deleteById(imageId);
        }catch (EmptyResultDataAccessException e){
            log.info(e.getMessage());
        }
    }
}
