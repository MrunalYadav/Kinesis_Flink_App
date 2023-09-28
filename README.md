## Getting Started Flink Java project - DataStream API

Skeleton project for a basic Flink Java application to run on Amazon Managed Service for Apache Flink.

* Flink version: 1.15.2
* Flink API: DataStream API
* Language: Java (11)

The project can run both on Amazon Managed Service for Apache Flink, and locally for development.

The application shows how to get runtime configuration, and sets up a Kinesis Data Stream source and a sink.

### Runtime configuration

The application reads the runtime configuration from the Runtime Properties, when running on Amazon Managed Service for 
Apache Flink, or from command line parameters, when running locally.

First Source Runtime Properties are expected in the Group ID `FirstSourceStream`. 

Second Source Runtime Properties are expected in the Group ID `SecondSourceStream`.

Sink Runtime Properties are expected in the Group ID `SinkStream`.

Please check `application_properties_file` for settings. 

To deploy the application in AWS change the `EnvName` property to `Feature`. 
In `local` mode, application will read the stream from localstack docker endpoint.

### Running in IntelliJ

* Start the localstack service in you local machine: ``localstack run -d``. Please note you will need docker installed in your machine.
* go through `ct_ut_steps.txt` file under resources folder to create streams.

* To start the Flink job in IntelliJ edit the Run/Debug configuration enabling *'Add dependencies with "provided" scope to 
the classpath'*.

### Generating companyTagData

To generate the data for the stream, you can use python application under ``python`` source folder. Please note the application is used to generate data for `UserTagInstances` and `CompanyTag` stream. 
If you want to generate data for the other stream then you need to update the code according to the stream output. 

