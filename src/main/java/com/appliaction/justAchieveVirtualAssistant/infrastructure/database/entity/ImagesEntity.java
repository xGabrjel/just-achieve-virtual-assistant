package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@ToString(of = {"id", "name", "type"})
@EqualsAndHashCode(of = {"id", "name"})
public class ImagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "image_data", length = 1000, nullable = false)
    private byte[] imageData;

    @JoinColumn(name = "profile_id", nullable = true)
    @OneToOne(fetch = FetchType.EAGER)
    private UserProfileEntity profileId;
}
