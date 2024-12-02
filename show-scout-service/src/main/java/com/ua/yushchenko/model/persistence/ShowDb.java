package com.ua.yushchenko.model.persistence;

import com.ua.yushchenko.enums.Genre;
import com.ua.yushchenko.enums.ShowStatus;


import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;


import java.util.List;
import java.util.UUID;

@Table(name = "show_scout_show")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowDb {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "show_id", nullable = false)
    UUID showID;
    @Column(name = "show_name")
    String showName;
    @Type(JsonBinaryType.class)
    @Column(name = "genres", columnDefinition = "jsonb")
    List<Genre> genres;
    @Column(name = "summary")
    String summary;
    @Column(name = "platform")
    String platform;
    @Column(name = "score")
    Float score;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ShowStatus showStatus;
    @Column(name = "img")
    String img;
}
