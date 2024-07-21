package com.deepl.api.test.service;

import com.deepl.api.*;
import com.deepl.api.test.model.MyTranslator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class TranslatorService {
    Translator translator;
    File csvFile = new File("src\\main\\resources\\pl_glossary1.csv");

    //DEEPL AUTHORIZATION
    public TranslatorService() throws Exception {
        String authKey = null;
        try {
            File file = new File("src\\main\\resources\\static\\key.txt");
            FileInputStream fis = new FileInputStream(file);
            Scanner scanner = new Scanner(fis);
            if (scanner.hasNextLine()) {
                authKey = scanner.nextLine();
                translator = new Translator(authKey);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //BASIC ENG ROW TRANSLATION
    public String output_en(String input) throws DeepLException, InterruptedException {
        TextResult result_en = translator.translateText(input, null, "en-US");
        return result_en.getText();
    }

    //BASIC POL ROW TRANSLATION
    public String output_pl(String input) throws DeepLException, InterruptedException {
        TextResult result_pl = translator.translateText(input, null, "pl");
        return result_pl.getText();
    }

    //BASIC UKR ROW TRANSLATION
    public String output_ua(String input) throws DeepLException, InterruptedException {
        TextResult result_ua = translator.translateText(input, null, "uk");
        return result_ua.getText();
    }

    //GLOSSARY CREATION
    public GlossaryInfo glossaryFromCsv() throws Exception {
        GlossaryInfo scvGlossary =
                translator.createGlossaryFromCsv("pl_csv_glossary", "en", "pl", csvFile);
        return scvGlossary;
    }

    //TRANSLATION WITH GLOSSARY
    public String usingGlossaryOutput(String input) throws Exception {
        input = output_en(input);                                                                                         // TextTranslationOptions options = new TextTranslationOptions().setGlossary(glossaryPl()); TextResult glos_result_pl = translator.translateText(output, "en", "pl", options);
        TextTranslationOptions options = new TextTranslationOptions().setGlossary(glossaryFromCsv());
        TextResult glos_result_pl = translator.translateText(input, "en", "pl", options);
        return glos_result_pl.getText();
    }

    //FILE TRANSLATION
    public File documentTranslation(File inputFile) throws Exception {
        Path filePath = Path.of("src/main/resources/static/output/", inputFile.getName());
        File outputFile = filePath.toFile();
        try {
            translator.translateDocument(inputFile, outputFile, null, "pl");
        } catch (DocumentTranslationException exception) {
            DocumentHandle handle = exception.getHandle();
            System.out.printf(
                    "Error after uploading %s, document handle: id: %s key: %s",
                    exception.getMessage(),
                    handle.getDocumentId(),
                    handle.getDocumentKey());
        }
        return outputFile;
    }
}


//    public GlossaryInfo glossaryPl() throws Exception {
//        GlossaryEntries entries = new GlossaryEntries() {{
//            put("Water turbine-generator unit", "Hydrozespół");
//        }};
//        GlossaryInfo plGlossary = translator.createGlossary("pl_glossary", "en", "pl", entries);
//
//        System.out.printf("Created '%s' (%s) %s->%s containing %d entries\n",
//                plGlossary.getName(),
//                plGlossary.getGlossaryId(),
//                plGlossary.getSourceLang(),
//                plGlossary.getTargetLang(),
//                plGlossary.getEntryCount());
//        return plGlossary;
//    }