package com.example.tasknotes.notes;

import com.example.tasknotes.tasks.TaskEntity;
import com.example.tasknotes.tasks.TasksRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotesService {
    private final NotesRepository notesRepository;
    private final TasksRepository tasksRepository;
    private final ModelMapper modelMapper;

    public NotesService(NotesRepository notesRepository, TasksRepository tasksRepository, ModelMapper modelMapper) {
        this.notesRepository = notesRepository;
        this.tasksRepository = tasksRepository;
        this.modelMapper = modelMapper;
    }

    List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream()

                .map(note -> {
                    NotesDto dto = modelMapper.map(note, NotesDto.class);
                    dto.setTaskId(note.getTask().getId());
                    return dto;
                })
                .toList();
    }

    NotesDto getNoteById(Long id) {
        NotesEntity note = notesRepository.findById(id)
                .orElseThrow(() -> new NotesNotFoundException("Note not found with id: " + id));
        NotesDto dto = modelMapper.map(note, NotesDto.class);
        dto.setTaskId(note.getTask().getId());
        return dto;
    }

    NotesDto createNote(NotesDto dto) {
        if (dto.getTaskId() == null) {
            throw new NotesInvalidException("Task ID is required for a note.");
        }

        TaskEntity task = tasksRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotesInvalidException("Associated task not found."));

        NotesEntity note = modelMapper.map(dto, NotesEntity.class);
        note.setTask(task);

        NotesEntity saved = notesRepository.save(note);
        NotesDto savedDto = modelMapper.map(saved, NotesDto.class);
        savedDto.setTaskId(task.getId());

        return savedDto;
    }

    static class NotesNotFoundException extends IllegalArgumentException {
        public NotesNotFoundException(String message) {
            super(message);
        }
    }

    static class NotesInvalidException extends IllegalArgumentException {
        public NotesInvalidException(String message) {
            super(message);
        }
    }
}