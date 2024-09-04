package dev.pdrotmz.LBM.repository;

import dev.pdrotmz.LBM.domain.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
}