import Repo.Repository;
import Repo.User;
import domain.MyMap;
import domain.Student;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("alex", 5);
        Student student2 = new Student("marian", 10);
        Student student3 = new Student("alex", 5);

        HashSet<Student> students = new HashSet<Student>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        for (Student st: students) {
            System.out.println(st.toString());
        }

        System.out.println(student1.equals(student3));

        TreeSet<Student> treeSet = new TreeSet<Student>();
        treeSet.add(student1);
        treeSet.add(student2);
        treeSet.add(student3);

        for (Student st: treeSet) {
            System.out.println(st.toString());
        }

        HashMap<String, Student> studentMap = new HashMap<>();
        studentMap.put("alex", student1);
        studentMap.put("marian", student2);
        studentMap.put("alex", student3);

        for (String key : studentMap.keySet()) {
            System.out.println(key);
        }

        //TreeMap<String, Student> treeMap2 = new TreeMap<String, Student>((o1, o2)->{return o1.getName().compareTo(o2.getName());});
        //treeMap2.put("alex", student1);
        //treeMap2.put("marian", student2);
        //treeMap2.put("alex", student3);

        //for (Map.Entry<String, Student>  entry: treeMap2.entrySet()) {
        //    System.out.println(entry);
        //}

        MyMap studentMyMap = new MyMap();
        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);

        for (Student st : list) {
            studentMyMap.add(st);
        }
        for (Map.Entry<Integer, List<Student>> entry : studentMyMap.getEntries()) {
            System.out.println(entry.getKey());
            List<Student> list2 = entry.getValue();
            Collections.sort(list2);
            for (Student st : list2) {
                System.out.println(st.toString());
            }
        }



    }
}