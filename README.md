# Module-4-Project
Web Inventory 

Project Requirements

    Make a copy of your Module 3 Project (or the instructor's provided solution).
    Apply the concepts you have learned for your selected milestone topics as follows (remember to only pick TWO*):
        Error Handling
            Create a new checked exception called DataAccessException and modify DataAccessObject (and its inheriting DAO classes) so that each of the five methods can throw this exception.
            When you catch any exception inside one of your DAO methods, generate a log message and then throw a DataAccessException using the original exception as the cause.
            Modify your servlets and JSPs to catch this new exception and display appropriate error messages to the user.
        Security
            Modify UserServlet so that it securely hashes a User's password before storing it, and LoginServlet so that it hashes the submitted password before comparing it to the stored one.
                Note that you should generate and store a salt value for each user.  You can append the salt either to the front or the end of the user's hashed password and use a delimiter (such as "|" or ",") to split it back out when needed.
            Modify inventory.jsp, user.jsp, and login.jsp so that they include anti-forgery tokens (store the token as a session scoped bean) as hidden inputs in their forms.  Modify the corresponding servlets so that they check that the token form parameter matches the one in the session bean.
            Modify UserServlet and InventoryServlet so that they HTML encode all request parameters.
        Advanced Web Features
            Move the creation of your DAO beans to a ServletContextListener class so that your DAO beans are automatically created as application attributes when your application starts.
            Move the logic for your login check (checking the "currentUser" session bean) to a Filter class.
        Custom Properties
            Place the text for all of your JSP form field labels in to a messages.properties files and use a resource bundle to include them in your JSP.  Provide at least one alternative language messages file (translate as best you can!).
            Include some mechanism (button, request param, copied pages, etc.).
    Unlike the milestone, apply your changes to the same project and upload it as one WAR.
    * You can include a THIRD option for up to five possible bonus points.  You can do this even if you did not do three for the milestone.
