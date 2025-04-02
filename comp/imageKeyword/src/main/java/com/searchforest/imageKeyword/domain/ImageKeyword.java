package com.searchforest.imageKeyword.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sf_ImageKeyword")
public class ImageKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private URL url;

    @OneToMany(mappedBy = "imageKeyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageSubKeyword> imageSubKeywords;
}
