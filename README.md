An attempt to write custom HTTP framework

Based on Java sockets, Http Server is started on configured port. It handles each client in separate thread.  

There is a framework library inside. It parses plain HTTP request from CURL.

Features:
- Routes are based on custom ScalaRoute class
- Views are loading with passed params
- Request/Response objects are functional. We can see each one's functionality in their respective classes.
- Unit tests added for Request/Response, Routing, Templating & Controller(s)
- Db access is provided through custom QueryBuilder class
- Templating is functional along with basic filters and functions
