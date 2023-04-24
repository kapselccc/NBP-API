# NBP-API

This is a simple application that returns data from NBP's API.

## How to start application
Open terminal in the directory where `Dockerfile` is. Type command below to create docker image:
```bash
> docker build -t nbp_api .
```

To run the application Run this command:
```bash
> docker run -p 7777:7777 nbp_api
```
## How to access application UI

If your server is running open your browser and go to
http://localhost:7777/swagger-ui/index.html
