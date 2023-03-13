package com.codeup.deramussweedenvillegascapstone.controllers;

import com.codeup.deramussweedenvillegascapstone.models.Note;
import com.codeup.deramussweedenvillegascapstone.models.Property;
import com.codeup.deramussweedenvillegascapstone.repositories.NoteRepository;
import com.codeup.deramussweedenvillegascapstone.repositories.PropertyRepository;
import com.codeup.deramussweedenvillegascapstone.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    private final UserRepository userDao;

    private final PropertyRepository propDao;

    private final NoteRepository noteDao;


    public NoteController(UserRepository userDao, PropertyRepository propDao, NoteRepository noteDao) {
        this.userDao = userDao;
        this.propDao = propDao;
        this.noteDao = noteDao;
    }

    @GetMapping("/notes")
    public String showAllNotes(Model model) {
        model.addAttribute("notes", noteDao.findAll());
        model.addAttribute("props", propDao.findAll());
        return "notes/index";
    }

//    @GetMapping("/notes/create")
//    public String showCreateNoteForm(Model model) {
//        model.addAttribute("note", new Note());
//        return "notes/create";
//    }


    @GetMapping("notes/create/{id}")
    public String createNoteForm(@PathVariable long id, Model model){
        Property property = propDao.findById(id);
        Note note = new Note();
        note.setProperty(property);
        model.addAttribute("note", note);
        model.addAttribute("propId", id);
        return "notes/create";
    }

    @PostMapping("/notes/create")
    public String createNote(@RequestParam(name = "propId") long id, @ModelAttribute Note note) {
//        Property property = propDao.findById(propId);
//        System.out.println("id = " + propId);
//        System.out.println("property.getCategory() = " + property.getCategory());
//        Property property = propDao.findPropertiesById(id);
//        note.setProperty(property);
//        noteDao.save(note);
//        Property property = propDao.findById(5);
      Property property = propDao.findById(id);
        note.setProperty(property);
        noteDao.save(note);
        return "redirect:/notes";
    }


    @GetMapping("/notes/search")
    public String showAllProps(@RequestParam(name="q") String query, Model model) {
//        model.addAttribute("notes", noteDao.searchByNoteLike(query));
        model.addAttribute("notes", noteDao.searchByTitleLike(query));
        return "notes/index";
    }

//        /search/properties?q=600+navarro
//@RequestParam(name="q") String Query






    @GetMapping("/notes/{id}")
    public String indNote(@PathVariable long id, Model model) {
        model.addAttribute("notes", noteDao.findNotesById(id));
        return "notes/show";
    }


    @GetMapping("/notes/{id}/edit")
    public String editNoteForm(Model model, @PathVariable long id) {
        Note note = noteDao.findNotesById(id);
        Property prop = new Property();
        model.addAttribute("notes", note);
        model.addAttribute("props", prop);
        return "notes/edit-note";
    }


    @PostMapping("/notes/edit")
    public String editNote(@ModelAttribute Note note) {
        noteDao.save(note);
        return "redirect:/notes";
    }



}
