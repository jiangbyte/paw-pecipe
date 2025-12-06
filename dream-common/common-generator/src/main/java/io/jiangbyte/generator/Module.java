package io.jiangbyte.generator;

import lombok.Data;

import java.util.List;

@Data
public class Module {
    private String gModule;
    private String moduleName;
    private List<String> tables;

    public Module(String gModule, String moduleName, List<String> tables) {
        this.gModule = gModule;
        this.moduleName = moduleName;
        this.tables = tables;
    }
}