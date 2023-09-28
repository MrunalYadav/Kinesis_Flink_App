import datetime
import json
import random
import boto3
import uuid

STREAM_NAME = "CompanyTagStream"


def get_data():
    time = datetime.datetime.now().isoformat()
    return {
        "data": {
            "id": random.randint(1, 50),
            "tagUUID": str(uuid.uuid1()),
            "companyId": random.randint(1, 50),
            "tag": "company",
            "value": random.choice(['AAPL', 'AMZN', 'MSFT', 'INTC', 'TBV']),
            "dynamic": 0,
            "formula": None,
            "version": time,
            "created": "2023-09-13T21:57:32Z",
            "modified": time,
            "deleted": None,
            "filterable": 1,
            "companyTagLabelId": random.randint(1, 50),
        },
        "metadata": {
            "timestamp": time,
            "record-type": "data",
            "operation": "insert",
            "partition-key-type": "schema-table",
            "schema-name": "platform",
            "table-name": "CompanyTags",
            "transaction-id": random.randint(50000, 600000),
        }
    }


def generate(stream_name, kinesis_client):
    import time

    while True:
        data = get_data()
        print(data)
        kinesis_client.put_record(
            StreamName=stream_name,
            Data=json.dumps(data),
            PartitionKey="partitionkey"
        )
        time.sleep(1)


if __name__ == "__main__":
    generate(
        STREAM_NAME,
        boto3.client(
            "kinesis",
            region_name="us-east-1",
            endpoint_url="http://localhost:4566"
        ),
    )
