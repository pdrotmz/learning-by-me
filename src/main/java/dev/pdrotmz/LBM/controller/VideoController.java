package dev.pdrotmz.LBM.controller;

import dev.pdrotmz.LBM.model.VideoModel;
import dev.pdrotmz.LBM.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("video-area")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("upload-video")
    public ResponseEntity<VideoModel> registerVideo(@RequestBody VideoModel video) {
        VideoModel videoModel = videoService.registerVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(videoModel);
    }

    @GetMapping
    public ResponseEntity<List<VideoModel>> getAllVideos(){
        List<VideoModel> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoModel> getVideoById(@PathVariable UUID id) {
        Optional<VideoModel> video = videoService.getVideoById(id);
        return video.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoModel> updateVideo(@PathVariable UUID id,
                                                  @RequestBody VideoModel video) {
        try {
            VideoModel updateVideo = videoService.updateVideo(id, video);
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
