package com.example.tasknotes.tasks;

import com.example.tasksnotes.common.BaseEntity;
import com.example.tasknotes.notes.NotesEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaskEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name ="due_date", nullable = false)
    private Date dueDate;

    @Column(name = "done", nullable = false)
    private boolean done;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    List<NotesEntity> notes;
}