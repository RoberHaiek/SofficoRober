# SofficoRober

software quality

Introduction:
A freelancer has developed a program that your colleagues describe as extremely bad and error-prone. Overall, your company has had bad experiences with this software. Since this is a critical component, a refactoring is necessary, which significantly increases the quality of the software.

Task a): Analysis of the source code
Run the included source code (contained in the src folder) and debug it.
• Evaluate the source code in terms of software quality based on the following criteria:
o What function is performed by this program?
	It checks if the content of the first file is similar to the content of the second file, if they’re
not, copy all the content of the first file into the second file, otherwise do nothing.
If the files are of type text, it is file to use the BufferReader, otherwise we need to
use the FileInputStream 
o Can the program be used continuously?
	Not as is, if an exception is thrown, the program will stop. Need to handle exceptions
            and send a log message instead in order to keep it running continuously. A listener is
	preferred to be used instead of a while(true) loop so that whenever the file is changed it
	would call the listener.
o Is the program robust against errors that can occur in different situations?
	Not at all, it has no error handling whatsoever, here are some of the problems that might
occur:
File name/path incorrect
Not enough disk space to write the data
No permissions to read/write
Must close the BufferReader after reading the file data
Must lock the files when writing to avoid corrupt data and the files through the entire read and write to insure functionality
If the file is larger than 4096 bite we will miss reading some content
If a resize is needed in the thread waiting time or the buffer size, a lot of time might be wasted on searching for these values, should put them in a config file instead.
The “doIt” function can never return true because of setting the values inside the if Statement
If we receive an exception inside the helpMe function, the files wouldn’t be closed
o How easy is it to analyze and understand the program?
	Terrible, the names of the fields and methods should be informative and self-explanatory
	No logging so it will be hard to debug
	No documentation so it will be hard to find or fix bugs according to what was intended
o How much main memory does the program need? On which factor does the memory consumption essentially depend?
Memory leaks if files weren’t closed properly
The buffer can increase performance but if the size is too large it can increase memory usage during IO operations or maybe even exceed the limit of the memory of the JVM
In doIt function, we set the String value for reading lines, if the line is too large the String will be large too
o Is the program intuitive to use?
o Can the program also be used under other operating systems or with different directories?
	1- Should normalize path before using because for example linux and windows have
    different slash (\ and /)
2- BufferedReader.readLine() automatically deals with OS differences in end of line parsing
3- Using StandardCharsets.UTF_8 for cross platform encoding consistency
• Document the result of the analysis.

Task b): Refactoring of the source code

Since the freelancer is no longer available, the manager asks you to fix the deficiencies in the source code.
Improve the source code according to the deficiencies found in the analysis.
In addition, you should make sure that the maintainability of the software is significantly improved. The following information should help you:
o Meaningful method, variable and class names
o Documentation of all classes, methods using Java Dock comments
o Average method size <= 30 lines of code

Task c): Increase in efficiency

The managing director approaches you again and would like to expand the program so that large amounts of data can also be processed efficiently.
The following use case is identified as problematic: The program takes a long time to determine whether large files are different or not. This occurs especially when only small changes are made.
Improve the program so that the use case described can be executed more efficiently
Assignment 2

XML & Databases

Introduction:
As part of a customer project, you will be entrusted with the integration of material data. The material data is exported at regular intervals as an XML file by the leading ERP system. The data is to be imported into the target system, with direct access to the database being permitted here. An example of possible XML data can be found in the "material.xml" file.

Task a): Reading in the XML file
Create a program that periodically checks the contents of a folder for new XML files. If a new file is recognized, the program should carry out the following steps:
• Parse the XML file using a DOM parser ( e.g. https://howtodoinjava.com/xml/read-xml-dom-parser-example/ )
• The data contained in the XML file should be represented internally in an object-oriented manner using the following class structure. Create the associated classes and read the XML data into this structure.
 
• The "getShortText" method returns the standard description of the material. This is the short text with the language identifier "EN".
• Pay attention to the "encapsulation" principle when implementing the classes. This means that direct access to individual attributes is not permitted, but always takes place via getter/setter methods. (These are not included in the diagram above).
• Attention: The export of the XML files can take a long time. To prevent incomplete files from being read in, the following procedure was implemented on the ERP system side: If a file is exported, it is created in the export directory with the ending ".tmp". Only when the data is complete is the extension renamed to ".xml".

Task b): Saving the data
Expand the program from task "a" so that the data can be stored in a relational database. To do this, follow these steps:
• Whenever an XML file is found, it should be read and then written directly to the relational database.
• In order to optimally separate the parsing of the data from the writing of the data, a list of material objects that are to be saved is transferred to the persistence layer.
• Implement a persistence layer that can write the data directly to the database using SQL commands.
o Information about JDBC: https://www.vogella.com/tutorials/MySQLJava/article.html )
o The JDBC driver for SQL Server is located in the jdbcmssql folder
o The create scripts for the associated database tables can be found in the "material_schema.sql" file
o Database: ORCHESTRA, User: ORCHESTRA, PWD: ORCHESTRA
• Caution: The ERP system regularly provides the same data. Newly added data must be inserted, existing data should be updated. The material number can be used as a criterion for checking whether a data record exists.

 
Task c): Error handling
Your program should be used in continuous operation. For this reason, special attention must be paid to reacting correctly to a wide variety of error situations. The following error situations are conceivable:
• The connection to the database cannot be established.
• A network error occurs, for example, during the execution of an SQL command.
• Reading in the XML file aborts with an error because, for example, file access is temporarily not possible due to network errors.
Check your program whether the above error situations are handled correctly. If necessary, the program should be expanded in such a way that the situations mentioned are handled correctly.


