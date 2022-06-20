package com.example.todo.util;

import android.os.Build;

import com.example.todo.dto.Todo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {
    public static long getCompletedTask(ArrayList<Todo.Response> todos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return todos.stream().filter(todo -> todo.getCompleted()).count();
        }
        return 0;
    }

    public static boolean writeLocalStorageList(FileOutputStream file, ArrayList<String> data) {
        try {
            for(String str: data) file.write(str.getBytes());
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean writeLocalStorage(FileOutputStream file, String data) {
        try {
            file.write(data.getBytes());
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> readLocalStorageList(FileInputStream file) {
        try {
            ArrayList<String> data = new ArrayList<>();
            Scanner reader = new Scanner(new InputStreamReader(file));

            if(reader.hasNext()) data.add(reader.next());
            file.close();

            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readLocalStorage(FileInputStream file) {
        try {
            Scanner reader = new Scanner(new InputStreamReader(file));
            if(!reader.hasNext()) return null;

            String res = reader.next();
            file.close();

            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
