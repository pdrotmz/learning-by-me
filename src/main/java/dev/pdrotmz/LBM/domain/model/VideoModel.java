package dev.pdrotmz.LBM.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_video_aulas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoModel {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID IdVideo;
    private String title;
    private String description;
    private String filePath;
}
