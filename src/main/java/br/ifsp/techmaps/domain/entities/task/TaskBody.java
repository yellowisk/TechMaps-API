package br.ifsp.techmaps.domain.entities.task;

public enum TaskBody {
    PY1("Learn Python", "Learn basic syntax", "Familiarize yourself with Python's syntax, variables, data types, and control flow statements."),
    PY2("Learn Python", "Data Structure", "Learn about Python's built-in data structures"),
    PY3("Learn Python", "Algorithms", "Learn how to create algorithms in Python through sorting and recursions"),
    PY4("Learn Python", "Advanced Topics", "Learn different advanced topics in Python such as decorators, RegEx and generators"),
    PY5("Learn Python", "OOP", "Learn about object-oriented programming (OOP) in Python, including classes, inheritance, and encapsulation"),
    PY6("Learn Python", "Package Managers", "Learn how to use Python package managers such as Pip, PYPi and Conda"),

    JV1("Learn Java", "Learn the Fundamentals", "Familiarize yourself with Java's syntax, data types, variables, conditionals and loops, functions data structures, packages, and more."),
    JV2("Learn Java", "Getting Deeper", "Learn about memory management, collections, serialization, networking and sockets, streams, and more."),
    JV3("Learn Java", "Build Tools", "Learn how to use build tools such as Maven, Gradle and Ant to automate the build process."),
    JV4("Learn Java", "Web Frameworks", "Learn how to use Java web frameworks such as Spring, Spark, and Play."),
    JV5("Learn Java", "ORM", "Learn how to use Java Object-Relational Mapping (ORM) frameworks such as Hibernate and JPA."),
    JV6("Learn Java", "JDBC", "Learn how to use Java Database Connectivity (JDBC) to connect to databases."),
    JV7("Learn Java", "Logging Frameworks", "Learn how to use Java logging frameworks such as Log4j, SLF4J and Logback."),
    JV8("Learn Java", "Testing Frameworks", "Learn how to use Java testing frameworks such as JUnit, TestNG, and Mockito."),

    KT1("Learn Kotlin", "Install Kotlin", "Install Kotlin compiler and set up the development environment."),
    KT2("Learn Kotlin", "Learn Kotlin basics", "Understand Kotlin syntax, variables, functions, and control flow."),
    KT3("Learn Kotlin", "Develop a simple Android app", "Build a basic Android application using Kotlin, including UI elements and event handling."),

    HT1("Learn HTML", "Introduction to HTML", "Learn the basic HTML tags and their purpose in creating the structure of a web page."),
    HT2("Learn HTML", "Structuring Content with HTML", "Learn how to use HTML tags to structure the content of a web page."),
    HT3("Learn HTML", " Creating Forms with HTML", "Learn how to use HTML tags to create forms for collecting user input."),
    HT4("Learn HTML", "HTML5 Semantic Elements", "Learn about HTML5 semantic elements and how to use them to create a semantic structure of a web page."),
    HT5("Learn HTML", "HTML Best Practices and Accessibility", "Learn about HTML best practices such as naming conventions, code formatting, and organization."),

    CS1("Learn CSS", "Introduction to CSS", "Learn about CSS syntax, selectors, properties and values."),
    CS2("Learn CSS", "Box Model And Layout", "Learn about the CSS box model and how to use it to create page layouts."),
    CS3("Learn CSS", "Advanced Styling Techniques", "Learn about advanced CSS techniques such as animations, transitions, and transforms."),
    CS4("Learn CSS", "Responsive Web Design", "Learn how to create responsive web pages that work well on different screen sizes."),
    CS5("Learn CSS", "CSS Best Practices", "Learn about CSS best practices such as naming conventions, code formatting, and organization."),

    JS1("Learn JavaScript", "All About Variables", "Learn about variables and data types in JavaScript."),
    JS2("Learn JavaScript", "Type Casting", "Learn how to convert one data type to another in JavaScript."),
    JS3("Learn JavaScript", "Data Structures", "Learn about JavaScript's built-in data structures."),
    JS4("Learn JavaScript", "Equality Comparisons", "Learn about JavaScript's equality comparisons."),
    JS5("Learn JavaScript", "Loops and Iterations", "Learn how to use loops and iterations in JavaScript."),
    JS6("Learn JavaScript", "Control Flow", "Learn how to control the flow of a program through any of these control structures"),
    JS7("Learn JavaScript", "Exception Handling", "Learn how to handle exceptions in JavaScript."),
    JS8("Learn JavaScript", "Expressions and Operators", "Learn about JavaScript's expressions and operators."),
    JS9("Learn JavaScript", "Functions", "Learn how to create and use functions in JavaScript."),
    JS10("Learn JavaScript", "Using (this) keyword", "Learn how to use the (this) keyword in JavaScript."),
    JS11("Learn JavaScript", "Asynchronous JavaScript", "Learn how to use asynchronous JavaScript."),
    JS12("Learn JavaScript", "Working with APIs", "Learn how to work with APIs in JavaScript."),
    JS13("Learn JavaScript", "Classes", "Learn how to create and use classes in JavaScript."),

    IN1("Learn Internet", "Internet Fundamentals", "Learn about IP addresses, DNS and TCP/IP protocols."),
    IN2("Learn Internet", "Web Development and APIs", "Learn about HTTP methods, request-response cycle, and server-side scripting."),
    IN3("Learn Internet", "Internet Security", "Learn about common security threats and how to protect against them."),
    IN4("Learn Internet", "Cloud Computing and DevOps", "Learn about cloud service models (IaaS, PaaS, SaaS) and deployment models (public, private, hybrid)."),
    IN5("Learn Internet", "Internet of Things", "Learn about IoT devices and their applications."),

    DG1("Learn Diagrams", "Learn UML basics", "Understand the different types of UML diagrams and their purpose in visualizing system design."),
    DG2("Learn Diagrams", "Create a class diagram", "Use UML notation to represent classes, relationships, and attributes in a class diagram."),
    DG3("Learn Diagrams", "Design a sequence diagram", "Illustrate the interaction between objects and the order of message exchanges in a system using UML sequence diagrams."),

    WS1("Learn WebServers", "Web Server Basics", "Learn about HTTP methods and request-response cycle"),
    WS2("Learn WebServers", "Server-Side Scripting", "Learn about server-side scripting languages, CGI and server-side APIs and Databases"),
    WS3("Learn WebServers", "Web Server Security", "Learn about common security threats and how to protect against them."),
    WS4("Learn WebServers", "Web Server Performance Optimization", "Learn about caching, load balancing, and other techniques to improve web server performance."),
    WS5("Learn WebServers", "Web Server Deployment and Hosting", "Learn about different web server deployment and hosting options."),

    CD1("Learn Cloud Systems", "Introduction to Cloud Computing", "Learn about cloud service models (IaaS, PaaS, SaaS) and deployment models (public, private, hybrid)."),
    CD2("Learn Cloud Systems", "Cloud Providers and Services", "Familiarize yourself with popular cloud services and their use cases."),
    CD3("Learn Cloud Systems", "Cloud Security and Compliance", "Learn about common security threats and how to protect against them."),
    CD4("Learn Cloud Systems", "Cloud Networking and Architecture", "Learn about cloud networking concepts and how to design a cloud architecture."),
    CD5 ("Learn Cloud Systems", "Cloud DevOps and Automation", "Learn how to automate cloud infrastructure provisioning and management."),

    GT1("Learn Git", "Install Git", "Set up Git on your computer and configure your user settings."),
    GT2("Learn Git", "Learn Git basics", "Understand Git's fundamental concepts like repositories, commits, branches, and merges."),
    GT3("Learn Git", "Collaborate with Git", "Practice cloning repositories, making changes, creating branches, and pushing your work to remote repositories."),

    GH1("Learn GitHub", "Create a GitHub account", "Sign up for a GitHub account and explore the platform's features."),
    GH2("Learn GitHub", "Manage repositories", "Create a new repository, clone it to your local machine, make changes, and push them to GitHub."),
    GH3("Learn GitHub", "Collaborate on GitHub", "Fork a repository, make contributions through pull requests, and participate in open-source projects."),

    AP1("Learn API", "Understand API fundamentals", "Learn about REST, HTTP methods, status codes, and request/response structure."),
    AP2("Learn API", "Make API requests", "Use tools like Postman to send HTTP requests and explore different endpoints and their responses."),
    AP3("Learn API", "Build a simple API client", "Develop a program that consumes an API, retrieves data, and performs basic operations using the provided endpoints."),

    RS1("Learn REST", "Grasp RESTful API concepts", "Understand resource-oriented architecture, URI design, and RESTful principles."),
    RS2("Learn REST", "Design RESTful endpoints", "Create endpoints with appropriate HTTP methods and URL structures to expose resources."),
    RS3("Learn REST", "Implement CRUD operations", "Build a RESTful API that allows creating, reading, updating, and deleting resources using HTTP methods."),

    SP1("Learn SOAP", "Understand SOAP basics", "Learn about the Simple Object Access Protocol (SOAP) and its XML-based messaging structure."),
    SP2("Learn SOAP", "Create a SOAP client", "Use a programming language (e.g., Java, Python) to build a SOAP client that interacts with a SOAP web service."),
    SP3("Learn SOAP", "Implement SOAP server", "Develop a SOAP web service that exposes functionalities and responds to client requests."),

    AG1("Learn Agile Methods", "Explore Agile methodologies", "Learn about Scrum, Kanban, and Agile principles such as iterative development and continuous improvement."),
    AG2("Learn Agile Methods", "Participate in a Scrum team", "Act as a member of a Scrum team, attending daily stand-ups, sprint planning, and sprint reviews."),
    AG3("Learn Agile Methods", "Apply Agile practices", "Practice user story estimation, backlog grooming, and prioritization in an Agile project."),

    DV1("Learn DevOps", "Learn DevOps principles", "Understand the integration of development and operations, continuous integration/continuous delivery (CI/CD), and automation."),
    DV2("Learn DevOps", "Set up a CI/CD pipeline", "Configure a CI/CD pipeline using tools like Jenkins or GitLab CI to automate the build, test, and deployment processes."),
    DV3("Learn DevOps", "Deploy an application with infrastructure as code", "Use tools like Ansible or Terraform to provision and deploy an application infrastructure on a cloud platform."),

    OP1("Learn Java OOP", "Understand Classes and Objects", "Learn all about Classes and Objects."),
    OP2("Learn Java OOP", "Inheritance", "Learn about inheritance and how to use it in your code."),
    OP3("Learn Java OOP", "Abstraction", "Learn about interfaces and how to use them in your code."),
    OP4("Learn Java OOP", "Encapsulation", "Learn about polymorphism and how to use it in your code."),
    OP5("Learn Java OOP", "Polymorphism", "Learn about polymorphism and how to use it in your code."),

    SD1("Learn SOLID", "Study SOLID principles", "Understand the SOLID acronym and its principles (Single Responsibility, Open-Closed, Liskov Substitution, Interface Segregation, Dependency Inversion)."),
    SD2("Learn SOLID", "Apply SOLID to code", "Refactor existing code or write new code that adheres to SOLID principles, promoting maintainability and extensibility."),
    SD3("Learn SOLID", "Discuss SOLID examples", "Analyze code examples, identify violations or improvements, and discuss the benefits of following SOLID principles."),

    CC1("Learn Clean Code", "Learn clean code principles", "Understand the importance of readable, maintainable, and well-structured code."),
    CC2("Learn Clean Code", "Apply code formatting standards", "Follow established coding conventions (e.g., PEP 8 for Python) to improve code readability."),
    CC3("Learn Clean Code", "Refactor code for clarity", "Identify code smells and refactor them to improve the code's structure, readability, and maintainability."),

    TD1("Learn TDD", "Understand Test-Driven Development (TDD)", "Learn the TDD cycle (red-green-refactor) and its benefits in software development."),
    TD2("Learn TDD", "Write unit tests", "Practice writing unit tests before implementing new functionality and ensuring test coverage."),
    TD3("Learn TDD", "Apply TDD to a project", "Develop a small project using TDD principles, writing tests first and then implementing the required code."),

    CA1("Learn Clean Architecture", "Study clean architecture principles", "Learn about the separation of concerns, dependency inversion, and architectural boundaries."),
    CA2("Learn Clean Architecture", "Design clean architecture", "Apply clean architecture principles to design a system with clear boundaries between layers."),
    CA3("Learn Clean Architecture", "Implement clean architecture", "Develop a project following the clean architecture principles, separating business logic from infrastructure and ensuring testability."),

    VC1("Learn Visual Studio Code", "Introduction to Visual Studio Code", "Learn about VS Code's features, extensions, and customization options."),
    VC2("Learn Visual Studio Code", "Programming and Debugging", "Use VS Code to write code, run programs, and debug applications."),
    VC3("Learn Visual Studio Code", "Advanced Features and Productivity", "Explore VS Code's advanced features, such automations and external tools and services"),

    II1("Learn IntelliJ IDEA", "Getting Started with IntelliJ IDEA", "Install IntelliJ IDEA and set up the development environment."),
    II2("Learn IntelliJ IDEA", "Java Development in IntelliJ IDEA", "Learn how to use IntelliJ IDEA to develop Java applications."),
    II3("Learn IntelliJ IDEA", " Advanced IntelliJ IDEA Features", "Explore IntelliJ IDEA's advanced features, such as database management and code analysis."),

    AD1("Learn Android Studio", "Set up Android development environment", "Install Android Studio and necessary SDKs for Android app development."),
    AD2("Learn Android Studio", "Learn Android app structure", "Understand the components (activities, services, layouts) and lifecycle of an Android app."),
    AD3("Learn Android Studio", "Develop a basic Android app", "Create a simple Android application with multiple activities, layouts, and basic functionality."),

    ND1("Learn Node.js", "Install Node.js", "Download and set up Node.js, a JavaScript runtime environment."),
    ND2("Learn Node.js", "Learn Node.js basics", "Understand Node.js modules, asynchronous programming, and the Node.js event loop."),
    ND3("Learn Node.js", "Build a Node.js application", "Develop a Node.js application that interacts with a database and exposes RESTful endpoints."),

    AN1("Learn Angular", "Install Angular CLI", "Set up Angular development environment using the Angular CLI."),
    AN2("Learn Angular", "Learn Angular basics", "Understand components, modules, data binding, and routing in the Angular framework."),
    AN3("Learn Angular", "Build a simple Angular app", "Develop an Angular application that fetches data from an API and displays it using components and routing."),

    RE1("Learn React", "Set up React development environment", "Install Node.js and create a new React project using create-react-app."),
    RE2("Learn React", "Learn React fundamentals", "Understand React components, JSX syntax, state management, and lifecycle methods."),
    RE3("Learn React", "Develop a React application", "Build a basic React application with multiple components, state management, and interactivity."),

    SPG1("Learn Spring", "Understand Spring framework", "Learn about Spring Boot, dependency injection, and inversion of control (IoC)."),
    SPG2("Learn Spring", "Develop a Spring application", "Build a simple Spring Boot application with RESTful endpoints and database integration."),
    SPG3("Learn Spring", "Explore Spring features", "Dive deeper into Spring's capabilities, such as security, caching, and integration with other frameworks."),

    DJ1("Learn Django", "Set up Django environment", "Install Django and set up a virtual environment for your Django project."),
    DJ2("Learn Django", "Create a basic Django app", "Build a simple Django application with a few models and views."),
    DJ3("Learn Django", "Implement user authentication", "Integrate Django's built-in authentication system to handle user registration and login."),

    BT1("Learn Bootstrap", "Install Bootstrap", "Download Bootstrap and set up a project with Bootstrap's CSS and JavaScript files."),
    BT2("Learn Bootstrap", "Learn Bootstrap basics", "Understand Bootstrap's grid system, components, and utilities."),
    BT3("Learn Bootstrap", "Build a simple website", "Develop a basic website using Bootstrap's grid system and components."),

//    SQ1("Learn SQL", "Install SQL", "Set up MySQL database server on your machine."),
//    SQ2("Learn SQL", "Learn SQL basics", "Understand SQL syntax, data manipulation (SELECT, INSERT, UPDATE, DELETE), and table creation."),
//    SQ3("Learn SQL", "Build a database-driven application", "Develop a simple application that interacts with a MySQL database, performing CRUD operations and querying data."),

    PS1("Learn PostgreSQL", "Install PostgreSQL", "Set up PostgreSQL database server on your machine."),
    PS2("Learn PostgreSQL", "Explore PostgreSQL features", "Learn about advanced data types, indexing, transactions, and querying capabilities."),
    PS3("Learn PostgreSQL", "Develop a database-driven application", "Build an application that utilizes a PostgreSQL database, focusing on efficient data manipulation and retrieval."),

    DC1("Learn Docker", "Install Docker", "Set up Docker on your machine and learn the basics of containerization."),
    DC2("Learn Docker", "Create Docker containers", "Build Docker images, define container configurations using Dockerfiles, and run containers locally."),
    DC3("Learn Docker", "Deploy containerized application", "Utilize Docker to package and deploy an application in a containerized environment."),

    FB1("Learn Firebase", "Set up Firebase project", "Create a Firebase project and configure Firebase SDK in your web or mobile application."),
    FB2("Learn Firebase", "Use Firebase Authentication", "Implement user authentication using Firebase Authentication services."),
    FB3("Learn Firebase", "Integrate Firebase Database", "Utilize Firebase Realtime Database or Firestore to store and retrieve data in your application.");
    private String topic;
    private String title;
    private String description;

    TaskBody(String topic, String title, String description) {
        this.topic = topic;
        this.title = title;
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String stage) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
