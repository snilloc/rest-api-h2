# 
# REST API DEMO
#
#-------------------------


## Build a deployment jar file
Located under: dist/rest-api-demo.jar
```
mvn clean package
```

## Build a Test Report
The Total Test Report Coverage is located under: dist/jacoco/index.html
```
mnv clean test jacoco:report
```

## Verify the code coverage meets min 
```
mvn clean verify
```

## Run Program and bring up Advertiser REST Service
```
mvn clean install
```


## Bring up Ad REST API
```
mvn spring-boot:run
```

