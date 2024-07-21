package com.deepl.api.test.controller;

import com.deepl.api.test.service.TranslatorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.FileInputStream;


@Controller
public class MyTranslatorController {
    @Autowired
    TranslatorService translatorService;

    @GetMapping("/translator")
    public String myTranslator() {
        return "main";
    }

//    @ExceptionHandler
//    @PostMapping("/translator")
//    public String input(@ModelAttribute("textToTranslate") String input, @RequestParam String selectedLanguage, Model model) throws Exception {
//        String output_pl = translatorService.output_pl(input);
//        String output_en = translatorService.output_en(input);
//        model.addAttribute("previousText", input);
////        model.addAttribute("output_pl", output_pl);
////        model.addAttribute("output_en", output_en);
//        String glos_output_pl = translatorService.usingGlossaryOutput(input);
//        model.addAttribute("glos_output_pl", glos_output_pl);
//        if ("en".equals(selectedLanguage)) {
//            model.addAttribute("output_en", output_en);
//        } else if ("pl".equals(selectedLanguage)) {
//            model.addAttribute("output_pl", output_pl);
//        }
//        return "main";
//    }

    @ExceptionHandler
    @PostMapping("/translator")
    public String input(@ModelAttribute("textToTranslate") String input, @RequestParam String selectedLanguage, Model model){
        try{
            model.addAttribute("previousText", input);
            String glos_output_pl = translatorService.usingGlossaryOutput(input);
            model.addAttribute("glos_output_pl", glos_output_pl);
            if ("en".equals(selectedLanguage)) {
                String output = translatorService.output_en(input);
                model.addAttribute("output", output);
            } else if ("pl".equals(selectedLanguage)) {
                String output = translatorService.output_pl(input);
                model.addAttribute("output", output);
            } else if ("uk".equals(selectedLanguage)) {
                String output = translatorService.output_ua(input);
                model.addAttribute("output", output);
            }
            return "main";
        }
        catch (Exception e){
            return "main";
        }
    }

    @PostMapping("/setLanguage")
    public String setLanguage(@RequestParam("language") String language, HttpSession session) {
        session.setAttribute("selectedLanguage", language);
        return "main";
    }

//    @PostMapping("/translator/glossary")
//    public String input_glossary(@ModelAttribute("glossaryText") String output, Model model) throws Exception {
//        String glos_output_pl = translatorService.usingGlossaryOutput(output);
//        model.addAttribute("glos_output_pl", glos_output_pl);
//        return "main";
//    }
}
