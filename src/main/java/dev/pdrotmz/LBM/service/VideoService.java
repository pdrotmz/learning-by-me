package dev.pdrotmz.LBM.service;

import dev.pdrotmz.LBM.domain.model.VideoModel;
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

    public VideoModel registerVideo(VideoModel video) {
        return videoRepository.save(video);
    }

    public List<VideoModel> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<VideoModel> getVideoById(UUID idVideo) {
        return videoRepository.findById(idVideo);
    }

    public VideoModel updateVideo(UUID idVideo, VideoModel updateVideo) {
        return videoRepository.findById(idVideo).map(VideoModel ->{
            VideoModel.setTitle(updateVideo.getTitle());
            VideoModel.setDescription(updateVideo.getDescription());
            VideoModel.setFilePath(updateVideo.getFilePath());
            return videoRepository.save(VideoModel);
        }).orElseThrow(() -> new EntityNotFoundException("Video was not found"));
    }

    public void deleteVideo(UUID idVideo) {
        videoRepository.deleteById(idVideo);
    }
}
