# 
# REST API DEMO
#
#-------------------------


## Build a dpeloyment jar file
```
mvn clean package
```

## Build a Test Report
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

