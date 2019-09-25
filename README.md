# remote-user-valve

## Quick Start

Here is a quick recipe to get started.

1. [Download the jar](https://github.com/bjagg/remote-user-valve/releases/download/v0.1.0/remote-user-valve.jar)
2. Copy jar to Tomcat's lib/
3. Add the following line to conf/logging.properties for testing

   ```properties
   org.jasig.tomcat.valves.level = FINE
   ```
4. Add the following to conf/server.xml inside `"localhost" Host` just before other valves

    ```xml
    <Valve className="org.jasig.tomcat.valves.RemoteUserValve"
            userHeader="HTTP_USER_NAME" />
    ``` 
5. Restart Tomcat
6. Check catalina.out for logging from `RemoteUserValve`