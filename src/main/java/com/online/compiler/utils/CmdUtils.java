package com.online.compiler.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtils {

    public static int compileFile(String filename, Runtime runtime, File dir) {
        try {
            Process processToExecuteCode = runtime.exec("gcc " + filename, null, dir);
            Process processToGetRS = runtime.exec(dir.getAbsolutePath() + "\\a.exe", null, dir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(processToGetRS.getInputStream()));
            String result = reader.readLine();
            processToGetRS.destroy();
            processToExecuteCode.destroy();
            if (!result.isEmpty()) {
                return Integer.parseInt(result);
            }
        } catch (IOException io) {
            System.out.println(io);
        }
        return 0;
    }
}
