package com.example.tasknotes.notes;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesDto {

    @Nullable private Long id;
    @Nullable private String title;
    @Nullable private String body;
    @Nullable private Long taskId;
}