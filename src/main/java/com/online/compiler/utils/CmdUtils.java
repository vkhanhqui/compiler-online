package com.online.compiler.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CmdUtils {

    public static int compileFile(String id, String filename, Runtime runtime, File dir) {
        try {
            String command = "gcc -o " + id + " " + filename;
            Process processToCompileCode = runtime.exec(command, null, dir);
            System.out.println("compile gcc successfully");
            processToCompileCode.waitFor(1, TimeUnit.SECONDS);
            processToCompileCode.destroy();
            String exeFile = id + ".exe";
            Process processToGetRS = runtime.exec(dir.getAbsolutePath() + "\\" + exeFile, null, dir);
            System.out.println("execute .exe successfully");
            BufferedReader reader = new BufferedReader(new InputStreamReader(processToGetRS.getInputStream()));
            String result = reader.readLine();
            processToGetRS.destroy();
            if (!result.isEmpty()) {
                return Integer.parseInt(result);
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
