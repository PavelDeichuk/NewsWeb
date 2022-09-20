package com.pavel.newsweb.Dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    private Long id;

    private String title;

    private String description;

    private LocalDateTime updateby;

    private LocalDateTime createby;
}
