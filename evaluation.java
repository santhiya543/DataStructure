import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class evaluation {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter the number of teachers: ");
        int teacherCount = scanner.nextInt();

        // Read the number of students
        System.out.print("Enter the number of students: ");
        int studentCount = scanner.nextInt();
        
        
        CircularLinkedList teacherList = new CircularLinkedList();
        for (int i = 1; i <= teacherCount; i++) {
            teacherList.addTeacher(new Teacher("Teacher" + i));
        }


        Student[] students = new Student[studentCount];
        for (int i = 0; i < studentCount; i++) {
            students[i] = new Student("Student" + (i + 1));
        }

        Random random = new Random();
        for (int round = 1; round <= 3; round++) {
            for (Student student : students) {
                Teacher teacher = teacherList.getNextTeacher(student);
                System.out.println(student.getName() + " meets " + teacher.getName());
            }
            System.out.println("Round " + round + " complete.\n");
        }
    }
}

class Teacher {
    private final String name;

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Node {
    Teacher teacher;
    Node next;

    public Node(Teacher teacher) {
        this.teacher = teacher;
    }
}

class CircularLinkedList {
    private Node head;

    public CircularLinkedList() {
        head = null;
    }

    public void addTeacher(Teacher teacher) {
        Node newNode = new Node(teacher);
        if (head == null) {
            head = newNode;
            newNode.next = head;
        } else {
            Node temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = head;
        }
    }

    public Teacher getNextTeacher(Student student) {
        Node current = head;
        Node prev = null;
        do {
            prev = current;
            current = current.next;
        } while (current.teacher.getName().equals(student.getName()) || (prev != null && current != head));

        head = current.next;
        return current.teacher;
    }
}

