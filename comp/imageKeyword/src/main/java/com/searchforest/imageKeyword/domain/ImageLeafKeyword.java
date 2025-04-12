package com.searchforest.imageKeyword.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sf_imageLeafKeyword")
public class ImageLeafKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String text;
    private URL url;

    @ManyToOne
    @JoinColumn(name = "image_sub_keyword_id")
    @JsonIgnore
    private ImageSubKeyword imageSubKeyword;
}
