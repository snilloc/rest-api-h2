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


## Bring up Applcation through Maven and Spring
```
mvn spring-boot:run
```

## Bring up Application through jar
java -jar dist/rest-api-demo.jar 

