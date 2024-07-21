package com.deepl.api.test.model;
import java.io.File;
import java.io.FileNotFoundException;


public class MyTranslator {
    private int id;
    //String translator
    private String input_string;
    private String output_en;
    private String output_pl;

    //File Transaltor
    private File input_file;
    private File outputFile;

    public MyTranslator() throws FileNotFoundException {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInput_string() {
        return input_string;
    }

    public void setInput_string(String input_string) {
        this.input_string = input_string;
    }

    public String getOutput_en() {
        return output_en;
    }

    public void setOutput_en(String output_en) {
        this.output_en = output_en;
    }

    public String getOutput_pl() {
        return output_pl;
    }

    public void setOutput_pl(String output_pl) {
        this.output_pl = output_pl;
    }

    public File getInput_file() {
        return input_file;
    }

    public void setInput_file(File input_file) {
        this.input_file = input_file;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public String toString() {
        return "MyTranslator{" +
                "id=" + id +
                ", input_string='" + input_string + '\'' +
                ", input_file=" + input_file +
                ", output_pl_file=" + outputFile +
                ", output_en='" + output_en + '\'' +
                ", output_pl='" + output_pl + '\'' +
                '}';
    }
}
