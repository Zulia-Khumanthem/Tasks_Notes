package com.example.tasknotes.notes;

import com.example.tasksnotes.common.BaseEntity;
import com.example.tasknotes.tasks.TaskEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "notes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class NotesEntity extends BaseEntity {

    @Column(name= "title", nullable = false)
    private String title;

    @Column(name= "body", nullable = false)
    private String body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private TaskEntity task;
}