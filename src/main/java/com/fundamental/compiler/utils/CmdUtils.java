package com.fundamental.compiler.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtils {

    public static void compileFile(String filename, Runtime runtime, File dir) {
        try {
            String fileToExecute = filename.replace("http://localhost:9999/upload/files/", "");
            Process processToExecuteCode = runtime.exec("gcc " + fileToExecute, null, dir);
            processToExecuteCode.destroy();
        } catch (
                IOException io) {
            System.out.println(io);
        }
    }

    public static int getResult(Runtime runtime, File dir) {
        try {
            Process processToGetRS = runtime.exec(dir.getAbsolutePath() + "\\a.exe", null, dir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(processToGetRS.getInputStream()));
            String saveRs = reader.readLine();
            processToGetRS.destroy();
            if (!saveRs.isEmpty()) {
                return Integer.parseInt(saveRs);
            }
        } catch (IOException io) {
            System.out.println(io);
        }
        return 0;
    }
}
