# Module 01

### Reflection 1
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code.

Jawaban:

Pada saat tulisan ini dibuat, kode pada repositori GitHub ini sudah mengimplementasikan _clean code_ dan _secure code_, walaupun masih beberapa bagiannya saja. Oleh karena itu, masih perlu dilakukan beberapa perbaikan. Rinciannya adalah sbb:
- Untuk _clean code_: Sudah mengimplementasikan _meaningful names_, _function_, serta _objects and data structure_. Masih kurang pengimplementasian _comments_ dan _error handling_.
- Untuk _secure code_: Sudah mengimplementasikan _input data validation_, tetapi masih kurang pengimplementasian _authentication_ dan _authorization_.

### Reflection 2

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

# Module 02
You have implemented a CI/CD process that automatically runs the test suites, analyzes code quality, and deploys to a PaaS. Try to answer the following questions in order to reflect on your attempt completing the tutorial and exercise.
1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

    Answer:
    
    Based on the first trial of my pmd code analysis, there is an error:
    src/test/java/id/ac/ui/cs/advprog/eshop/controller/ProductControllerTest.java:64: AvoidDuplicateLiterals:	The String literal "/product/list" appears 4 times in this file;

    So I made a private static final String named PRODUCT_LIST_URL that is equal to "/product/list" and the error disappeared
    

2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)! 

    Answer:
    
    Yes, I believe the current implementation successfully meets the definition of Continuous Integration (CI) and Continuous Deployment (CD). For the CI part, the use of ci.yml to automate builds/tests and pmd.yml for static code analysis ensures that every code change is verified and maintains high quality before being merged. The CD process is achieved through the Docker-based integration with Render, where the "After CI checks pass" trigger ensures that only verified, production-ready code is automatically pushed to the live environment without manual intervention. This end-to-end automation fulfills the core principles of CI/CD by reducing manual errors, maintaining a consistent delivery pipeline, and ensuring a fast feedback loop.

# Module 3
SOLID principles I applied to my project; it's advandatages and disadvatage(s) if not applied with examples:
1. Single Responsibility Principle (SRP):
    
    - I moved CarController class to a different file named CarController.java, so that the CarController class has only one reason to change, which is related to handling HTTP requests and responses for car-related operations. By separating it from ProductController class that may have different responsibilities (e.g., service or repository), I ensure that changes in those areas do not affect the CarController class, thus adhering to the Single Responsibility Principle.
    - Advantages if applied: If I need to change the way CarController handles HTTP requests (e.g., adding new endpoints or changing response formats), I can do so without worrying about affecting the ProductController or other parts of the codebase. This leads to better maintainability and reduces the risk of introducing bugs in unrelated areas.
    - Diadvantages if not applied: If I had kept CarController and ProductController in the same file, any change to one of them (e.g., adding a new endpoint in CarController) could potentially affect the other (e.g., ProductController), leading to a higher chance of bugs and making the code harder to maintain.

2. Open/Closed Principle (OCP):

    - I made two new repository interface named CarRepositoryInterface and ProductRepositoryInterface, so that the CarRepository and ProductRepoository class is open for extension (by implementing the CarRepositoryInterface) but closed for modification (existing code in CarRepository and ProductRepository does not need to be changed).
    - I added a new method named findCarsByName in CarServiceImpl class, so that the CarServiceImpl class is open for extension (by adding new functionality) but closed for modification (existing code does not need to be changed). 
    - This allows me to add new features and/or change implementation without altering the existing code, which helps to prevent bugs and maintain the stability of the application.
    - Advantages if applied: If I want to add a new feature to find cars by name, I can simply add the findCarsByName method in CarServiceImpl without modifying the existing methods. This allows me to extend the functionality of the CarServiceImpl class without risking breaking existing code.
    - Diadvantages if not applied: If I had not followed OCP and instead modified the existing code in CarServiceImpl to add the findCarsByName functionality, I might have accidentally introduced bugs into the existing methods (e.g., findAll, findById) that could affect the stability of the application.

3. Liskov Substitution Principle (LSP):

    - I made CarRepositoryInterface and ProductRepositoryInterface as an abstraction for CarRepository and ProductRepository (respectively), so that any instance of CarRepository and ProductRepository can be substituted with an instance of CarRepositoryInterface and ProductRepositoryInterface without affecting the correctness of the program. This means that, for example, if I have a method that expects a CarRepositoryInterface, I can pass in a CarRepository instance without any issues, as long as it implements the interface correctly. This adherence to LSP ensures that my code is more flexible and maintainable, as I can easily swap out implementations without breaking the existing codebase.
    - Advantages if applied: If I have a method that expects a CarRepositoryInterface, I can pass in a CarRepository instance without any issues, as long as it implements the interface correctly. This allows me to easily swap out implementations (e.g., using a different database or data source) without affecting the existing code that relies on the CarRepositoryInterface.
    - Diadvantages if not applied: If I had not followed LSP and instead tightly coupled my code to a specific implementation (e.g., CarRepository), I would not be able to easily swap out that implementation without modifying the existing code. This could lead to a more rigid and less maintainable codebase, as any change to the CarRepository implementation would require changes to all parts of the code that depend on it.

4. Interface Segregation Principle (ISP):

    - I segregated the CarService interface into smaller, more specific interfaces (CarServiceGET and CarServicePOST), so that clients (e.g., CarController) only need to depend on the interfaces that are relevant to them. This prevents clients from being forced to depend on methods they do not use, which can lead to a more modular and maintainable codebase. By adhering to ISP, I ensure that my code is easier to understand and modify, as each interface has a clear and focused responsibility.
    - Advantages if applied: If CarController only needs to use the methods defined in CarServiceGET, it can depend solely on that interface without being concerned about the methods in CarServicePOST. This leads to a cleaner and more maintainable codebase, as CarController is not burdened with unnecessary dependencies.
    - Diadvantages if not applied: If I had not followed ISP and instead had a single CarService interface that included all methods (both GET and POST), CarController would be forced to depend on the entire interface, even if it only uses a subset of the methods. This could lead to confusion and make the code harder to maintain, as CarController would have to be aware of all the methods in CarService, even those it does not use.

5. Dependency Inversion Principle (DSP):

    - In CarServiceImpl class, I changed the dependency from CarRepository to CarRepositoryInterface, so that the high-level module (CarServiceImpl) does not depend on the low-level module (CarRepository), but both depend on the abstraction (CarRepositoryInterface)
    - In CarController class, I changed the dependency from CarServiceImpl to CarService to CarServiceGET and CarServicePOST, so that the high-level module (CarController) does not depend on the low-level module (CarServiceImpl), but both depend on the abstraction (CarServiceGET and CarServicePOST)
    - Advantages if applied: If I want to change the implementation of CarRepository (e.g., using a different database), I can simply create a new class that implements CarRepositoryInterface and update the dependency injection in CarServiceImpl without modifying the existing code. Similarly, if I want to change the implementation of CarServiceImpl, I can create a new class that implements CarServiceGET and CarServicePOST and update the dependency injection in CarController without modifying the existing code. This leads to a more flexible and maintainable codebase, as changes to low-level modules do not affect high-level modules.
    - Diadvantages if not applied: If I had not followed DSP and instead had CarServiceImpl directly depend on CarRepository, any change to the CarRepository implementation would require changes to CarServiceImpl, which could lead to bugs and make the code harder to maintain. Similarly, if CarController directly depended on CarServiceImpl, any change to CarServiceImpl would require changes to CarController, which could also lead to bugs and maintenance issues.