package dev.pdrotmz.LBM.domain.controller;

import dev.pdrotmz.LBM.domain.model.Teacher;
import dev.pdrotmz.LBM.domain.model.Video;
import dev.pdrotmz.LBM.repository.TeacherRepository;
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
    @Autowired
    private TeacherRepository teacherRepository;


    @PostMapping("upload-video")
    public ResponseEntity<Video> registerVideo(@RequestParam("video") MultipartFile videoFile,
                                               @RequestParam("title") String title,
                                               @RequestParam("description") String description,
                                               @RequestParam("teacherId") UUID teacherId) {
        if (videoFile.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            // Salvar o arquivo no diretório especificado
            byte[] bytes = videoFile.getBytes();
            Path path = Paths.get(uploadedFolder + videoFile.getOriginalFilename());
            Files.write(path, bytes);

            // Buscar o Teacher pelo ID considerando a herança
            Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
            if (!teacherOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Teacher teacher = teacherOpt.get();

            // Criar e salvar o vídeo
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setFilePath(path.toString());
            video.setTeacher(teacher); // Associar o Teacher ao vídeo
            Video savedVideo = videoService.registerVideo(video);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
        } catch (IOException e) {
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
    public ResponseEntity<Video> updateVideo(@PathVariable UUID id, @RequestBody Video video) {
        Video updateVideo = videoService.updateVideo(id, video);
        return ResponseEntity.ok(updateVideo);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok().build();
    }
}
