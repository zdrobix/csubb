package domain;

import java.util.*;

public class MyMap {
    private TreeMap<Integer, List<Student>> students;

    public MyMap() {
        this.students = new TreeMap<>();
    }


    public static class StudentGradeComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer i1, Integer i2) {
            return i2 - i1;
        }
    }

    public void add (Student student) {
        Integer grade = (Integer)Math.round(student.getMedia());
        List<Student> studentList = students.get(grade);
        if (studentList == null) {
            studentList = new ArrayList<>();
            studentList.add(student);
            this.students.put(grade, studentList);
        } else studentList.add(student);
    }

    public Set<Map.Entry<Integer, List<Student>>> getEntries() {
        return this.students.entrySet();
    }


}

