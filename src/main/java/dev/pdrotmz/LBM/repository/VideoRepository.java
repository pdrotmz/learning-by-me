package dev.pdrotmz.LBM.repository;

import dev.pdrotmz.LBM.model.VideoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<VideoModel, UUID> {
}
