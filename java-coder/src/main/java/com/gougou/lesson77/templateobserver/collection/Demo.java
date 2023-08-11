package com.gougou.lesson77.templateobserver.collection;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<Student2> student2s = new ArrayList<>();
        student2s.add(new Student2("Alice", 19, 89.0f));
        student2s.add(new Student2("Peter", 20, 78.0f));
        student2s.add(new Student2("Leo", 18, 99.0f));

        Collections.sort(student2s, new AgeAscComparator());
        print(student2s);

        Collections.sort(student2s, new NameAscComparator());
        print(student2s);

        Collections.sort(student2s, new ScoreDescComparator());
        print(student2s);
    }

    public static void print(List<Student2> student2s) {
        for (Student2 s : student2s) {
            System.out.println(s.getName() + " " + s.getAge() + " " + s.getScore());
        }
    }

    public static class AgeAscComparator implements Comparator<Student2> {
        @Override
        public int compare(Student2 o1, Student2 o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    public static class NameAscComparator implements Comparator<Student2> {
        @Override
        public int compare(Student2 o1, Student2 o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public static class ScoreDescComparator implements Comparator<Student2> {
        @Override
        public int compare(Student2 o1, Student2 o2) {
            if (Math.abs(o1.getScore() - o2.getScore()) < 0.001) {
                return 0;
            } else if (o1.getScore() < o2.getScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
