package com.task.webnotes;

import com.task.webnotes.domain.Note;
import com.task.webnotes.repo.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;


@Controller
public class NotesController {
    @Autowired
    private NotesRepo notesRepo;

    @GetMapping("/notedata")
    public String notedata(@RequestParam int id,Map<String,Object> model) {
        Iterable<Note> notes = notesRepo.findById(id);
        model.put("notes", notes);
        return "notedata";
    }

    @GetMapping
    public String main(Map<String,Object> model) {
        Iterable<Note> notes = notesRepo.findAll();
        model.put("notes", notes);
        return "main";
    }

    @GetMapping("all")
    public String all(Map<String,Object> model) {
        Iterable<Note> notes = notesRepo.findAll();
        model.put("notes", notes);
        return "redirect:main";
    }

    @PostMapping
    public String add(@RequestParam String title, @RequestParam String memo, Map<String,Object> model) {
        if (memo == null || memo.isEmpty()) {
            throw new RuntimeException("Memo field should not be empty!");
        }
        if (title == null || title.isEmpty()) {
            title = memo;
        }
        Note note = new Note(title,memo);
        notesRepo.save(note);

        Iterable<Note> notes = notesRepo.findAll();
        model.put("notes", notes);

        return "redirect:main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String,Object> model) {
        Iterable<Note> notes;
        if (filter != null && !filter.isEmpty()) {
            notes = notesRepo.findByMemoContainingOrTitleContaining(filter, filter);
        } else {
            notes = notesRepo.findAll();
        }
        model.put("notes", notes);
        return "main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam int id, Map<String,Object> model) {
        Iterable<Note> notes;
        if (id != 0) {
            notes = notesRepo.deleteById(id);
        }
        notes = notesRepo.findAll();
        model.put("notes", notes);
        return "redirect:main";
    }

}