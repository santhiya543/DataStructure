// package com.lift;

// import com.lift.Lift;

// import java.util.concurrent.locks.*;

// public class Main{
//     public static void main(String[] args) {
//         Lift lift = new Lift();

//         // Simulating simultaneous lift requests
//         Thread[] threads = new Thread[4];
//         int[][] requests = {
//             {0, 1}, 
//             {1, -1},
//             {2, -1}, 
//             {2, 1}  
//         };

//         for (int i = 0; i < requests.length; i++) {
//             final int floor = requests[i][0];
//             final String direction = requests[i][1] == 1 ? "UP" : "DOWN";
//             threads[i] = new Thread(() -> lift.requestLift(floor, direction));
//             threads[i].start();
//         }

//         for (Thread thread : threads) {
//             try {
//                 thread.join();
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// }

// class Lift{
//     private int currentFloor;
//     private String direction;
//     private final Lock lock = new ReentrantLock();

//     public Lift() {
//         currentFloor = 0;
//         direction = "STOP";
//     }

//     public void move(String direction) {
//         lock.lock();
//         try {
//             System.out.println("Lift moving " + direction + " from floor " + currentFloor);
//             // Simulating lift movement
//             try {
//                 Thread.sleep(1000);
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//             if (direction.equals("UP")) {
//                 currentFloor++;
//             } else if (direction.equals("DOWN")) {
//                 currentFloor--;
//             }
//             System.out.println("Lift reached floor " + currentFloor);
//             this.direction = "STOP";
//         } finally {
//             lock.unlock();
//         }
//     }

//     public void requestLift(int floor, String direction) {
//         System.out.println("Requesting lift from floor " + floor + " to go " + direction);
//         while (true) {
//             lock.lock();
//             try {
//                 if (this.direction.equals("STOP") || (this.direction.equals(direction) && this.currentFloor == floor)) {
//                     this.direction = direction;
//                     break;
//                 }
//             } finally {
//                 lock.unlock();
//             }
//         }
//         move(direction);
//     }
// }



import java.util.Scanner;
import java.util.concurrent.locks.*;

public class Main {
    public static void main(String[] args) {
        Lift lift = new Lift();
        Scanner scanner = new Scanner(System.in);

        // Simulating lift requests from user input
        while (true) {
            System.out.println("Enter floor number (-1 to exit):");
            int floor = scanner.nextInt();
            if (floor == -1) // Exit condition
                break;
            scanner.nextLine(); // Consume newline character
            System.out.println("Enter direction (UP/DOWN):");
            String direction = scanner.nextLine().toUpperCase();
            if (!direction.equals("UP") && !direction.equals("DOWN")) {
                System.out.println("Invalid direction. Please enter UP or DOWN.");
                continue; // Go back to start of loop for valid input
            }
            lift.requestLift(floor, direction);
        }

        scanner.close();
    }
}

class Lift {
    private int currentFloor;
    private String direction;
    private final Lock lock = new ReentrantLock();

    public Lift() {
        currentFloor = 0;
        direction = "STOP";
    }

    public void move(String direction) {
        lock.lock();
        try {
            System.out.println("Lift moving " + direction + " from floor " + currentFloor);
            // Simulating lift movement
            try {
                Thread.sleep(1000); // Simulate lift moving between floors
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (direction.equals("UP")) {
                currentFloor++;
            } else if (direction.equals("DOWN")) {
                currentFloor--;
            }
            System.out.println("Lift reached floor " + currentFloor);
            this.direction = "STOP";
        } finally {
            lock.unlock();
        }
    }

    public void requestLift(int floor, String direction) {
        System.out.println("Requesting lift from floor " + floor + " to go " + direction);
        while (true) {
            lock.lock();
            try {
                if (this.direction.equals("STOP") || (this.direction.equals(direction) && this.currentFloor == floor)) {
                    this.direction = direction;
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
        move(direction);
    }
}
