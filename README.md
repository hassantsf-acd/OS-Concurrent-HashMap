## Concurrent HashMap
- #### Course: Operating System Project
- #### Semester: Spring 2023
- #### Instructor: Dr. Hamed Khan Mirza
ConcurrentHashMap is a thread-safe implementation of the Map interface in Java that allows multiple threads to access it simultaneously without any synchronization issues.
Requirement is to design a concurrent hashmap that is thread-safe and allows multiple threads to read and write data simultaneously.

The program should create **N** workers for each file and assign them to read a portion of the file simultaneously.
The workers should use the implemented functions to store the read data in the concurrent hashmap.
The program should print a message indicating the completion of the reading process and the execution time. Afterward, the program should allow the user to search for a word and display its meaning until the user enters the "exit" command.

### Constraints
- The program should manage access to files as a shared resource using locks
- The implementation should be done in Java without using any thread-safe libraries or classes
