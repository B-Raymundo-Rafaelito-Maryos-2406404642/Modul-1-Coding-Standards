## Refleksi 1
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code.

Jawaban:

Pada saat tulisan ini dibuat, kode pada repositori GitHub ini sudah mengimplementasikan _clean code_ dan _secure code_, walaupun masih beberapa bagiannya saja. Oleh karena itu, masih perlu dilakukan beberapa perbaikan. Rinciannya adalah sbb:
- Untuk _clean code_: Sudah mengimplementasikan _meaningful names_, _function_, serta _objects and data structure_. Masih kurang pengimplementasian _comments_ dan _error handling_.
- Untuk _secure code_: Sudah mengimplementasikan _input data validation_, tetapi masih kurang pengimplementasian _authentication_ dan _authorization_.

## Refleksi 2

After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

Answer:

I am answering these questions based mainly on my opinion so they're unlikely to be true. I think I'm burning out right now. As many as possible. Check for as many scenarios as possible, just like what I just said. No, that does not. There may be other scenarios that we don't know exist. Imo, it is just like the halting problem, we never know whether or not a program is secure and good until some errors or bugs occur.

Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Answer: 

Again, I am answering these questions based mainly on my opinion. Just like what the questions stated, The new Java class similar to the prior functional test suites have the same setup procedures and instance variables, so i think the code would not be clean because of massive redundancy. In my opinion, yes, it will. The solutions I'm gonna propose are, but not limited to:

- Do not create another Java class, just make other methods in CreateProductFunctionalTest.java
- Make another Java class, but make the CreateProductFunctionalTest.java as it's parent (base) class
- Make another Java class as a parent class (the base) and then make CreateProductFunctionalTest.java and another Java class that I was asked to make as its child

Anyway, the whole point is to reduce redundancy by using shared and reusable code (or class) 