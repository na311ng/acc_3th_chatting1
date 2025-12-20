package com.example.chatting1.domain.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto {
    private int size;
    private int number;
    private long totalElements;
    private int totalPages;
}
