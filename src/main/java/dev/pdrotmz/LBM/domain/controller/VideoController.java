package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.model.Video;
import dev.pdrotmz.LBM.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("video-area")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Value("${video.upload.dir}")
    private String uploadedFolder;


    @PostMapping("upload-video")
    public ResponseEntity<Video> registerVideo(@RequestParam("video") MultipartFile videoFile,
                                               @RequestParam("title") String title,
                                               @RequestParam("description") String description) {
        if(videoFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            byte[] bytes = videoFile.getBytes();
            Path path = Paths.get(uploadedFolder + videoFile.getOriginalFilename());
            Files.write(path, bytes);

            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setFilePath(path.toString());
            Video savedVideo = videoService.registerVideo(video);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
        } catch(IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos(){
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable UUID id) {
        Optional<Video> video = videoService.getVideoById(id);
        return video.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable UUID id,
                                             @RequestBody Video video) {
        try {
            Video updateVideo = videoService.updateVideo(id, video);
            return ResponseEntity.ok(updateVideo);

        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.ok().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
