cd .Contents

You should have received an archive containing
    a directory called "example" containing
        this file (README) and
        two Java files:
            CalcClient.java and
            CalcServer.java and
        two Class files (compiled from the java files)
            CalcClient.class and
            CalcServer.class.


Recompiling

To recompile the java files, you will need a copy of the Apache XML-RPC Libraries.
Go to
    http://www.apache.org/dyn/closer.cgi/ws/xmlrpc/
which will suggest a download mirror - in my case it was
    http://apache.easy-webs.de/ws/xmlrpc/

From there, download "xmlrpc-current-bin.tar.gz" also known as
    http://apache.easy-webs.de/ws/xmlrpc/xmlrpc-current-bin.tar.gz

Unpack this archive in a directory of your choosing, and update your CLASSPATH
to reflect this location.  For example, I use bash, and had to type
   > export CLASSPATH=<PATH-TO-LIBS>/xmlrpc-3.1/lib/\*:$CLASSPATH
(where of course <PATH-TO-LIBS> was replaced with the actual pathname).

To check if that worked, enter
   > echo $CLASSPATH

Once this is set up, you should be able to compile both classes as usual:
    > javac CalcClient.java
    > javac CalcServer.java
    >


Operation

To run the example, start the server running:
    >java CalcServer
    The Calculator Server has been started...

This will continue to run.  Let it.

In another shell, (check $CLASSPATH, fix it if necessary, then) run the client:
    >java CalcClient
    About to get results...(params[0] = 33, params[1] = 9).
    Add Result = 42
    Sub Result = 24
    Mul Result = 297
    >

You should probably kill the server now.
(Use Ctrl-C in Linux or whatever key works in your OS. ;-)


For more information

See http://ws.apache.org/xmlrpc/index.html


Or contact the author

Prof. Ronald Moore

h_da
Hochschule Darmstadt
University of Applied Sciences

fbi
Fachbereich Informatik
http://www.fbi.h-da.de/organisation/personen/moore-ronald.html

