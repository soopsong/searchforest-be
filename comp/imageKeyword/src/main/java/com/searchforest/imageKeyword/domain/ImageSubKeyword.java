package com.searchforest.imageKeyword.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sf_imageSubKeyword")
public class ImageSubKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private URL url;

    @ManyToOne
    @JoinColumn(name = "image_keyword_id")
    @ToString.Exclude
    @JsonIgnore
    private ImageKeyword imageKeyword;

    @OneToMany(mappedBy = "imageSubKeyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageLeafKeyword> leafKeywords = new ArrayList<>();
}
