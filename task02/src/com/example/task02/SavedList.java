package com.example.task02;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SavedList<E extends Serializable> extends AbstractList<E> {

    private final File file;
    private List<E> list;

    public SavedList(File file) {
        this.file = file;
        if (file.exists()) {
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(file))) {
                list = (ArrayList<E>) is.readObject();
            } catch (Exception e) {
                System.err.println("Ошибка при чтении данных из файла: " + file.getAbsolutePath());
                e.printStackTrace();}
        } else {
            list = new ArrayList<>();
        }
    }

    private void writeFile() {
        try (ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(file))) {
            obj.writeObject(list);
        } catch (IOException ignored) {
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E old = list.set(index, element);
        this.writeFile();
        return old;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        this.writeFile();
    }

    @Override
    public E remove(int index) {
        E old = list.remove(index);
        this.writeFile();
        return old;
    }
}
