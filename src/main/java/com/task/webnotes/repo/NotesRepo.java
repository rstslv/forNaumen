package com.task.webnotes.repo;

import com.task.webnotes.domain.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotesRepo extends CrudRepository<Note, Integer> {
    List<Note> findById(int id);
    List<Note> findByMemoContainingOrTitleContaining(String filterMemo, String filterTitle);
    List<Note> deleteById(int id);
}
