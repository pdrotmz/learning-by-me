package dev.pdrotmz.LBM.service;

import dev.pdrotmz.LBM.domain.model.Video;
import dev.pdrotmz.LBM.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video registerVideo(Video video) {
        return videoRepository.save(video);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideoById(UUID idVideo) {
        return videoRepository.findById(idVideo);
    }

    public Video updateVideo(UUID idVideo, Video updateVideo) {
        return videoRepository.findById(idVideo).map(Video ->{
            Video.setTitle(updateVideo.getTitle());
            Video.setDescription(updateVideo.getDescription());
            Video.setFilePath(updateVideo.getFilePath());
            return videoRepository.save(Video);
        }).orElseThrow(() -> new EntityNotFoundException("Video was not found"));
    }

    public void deleteVideo(UUID idVideo) {
        videoRepository.deleteById(idVideo);
    }
}