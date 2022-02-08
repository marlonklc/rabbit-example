# Rabbit example

This project intends to show a very simple example using rabbit. My intend it's kidding with problem of large requests scale, when you delegate your http requests to async messages. 

The project was divided between endpoints(apis), producers and consumers.

The principal point of this project its on messages that apis do to process your workflows on background. It depends on your need, but sometimes you have a heavy process or when you need to process a lot of requests at same time. It avoid problems with instabilities or scale of your system.

### Requirements to run
- obviously you needed installed Java 11+
- rabbitmq server (I use a rabbitmq cloud free account - https://customer.cloudamqp.com/login)
- You'll needed set environments variables below to connect on rabbit server
```
RABBITMQ_HOST
RABBITMQ_PORT
RABBITMQ_USER
RABBITMQ_PASS
RABBITMQ_VHOST
```
Just it and you able to run on your machine 😉

### Main concept of this project
Basically project have a three endpoints. 
- one which create a new task (let's call it **create task**)
- another one update a task (let's call it **next step**), 
- last but not least, get a task (let's call it **get task**) 

The principal endpoint was on **next step** endpoint. This endpoint will produce a message on rabbitmq, but this don't change the task to next step instantly, because it's a background process that takes a while.

If you call **get task** after run **next step** endpoint, you will see that task don't went to next step, that is, **next step** will change status of current step of task, when your work is done. But if you call **get task** after a few seconds, you will see that step of task its changed, that is, it worked !

### APIs
[POST] http://localhost:3000/task \
API responsible to create a new task. It creates a new task on db with data passed on body
```
BODY REQUEST
{
    "title": "title example",
    "description": "long description for example"
}
```
```
BODY RESPONSE
{
    "id": "fe1038c6-0e80-4008-bed0-f7f457411782",
    "title": "title example",
    "description": "long description for example",
    "step": "TODO"
} 
```

[GET] http://localhost:3000/task/{id} \
API responsible to get a task by id. It makes a simple query on db to obtain task by id
```
BODY RESPONSE
{
    "id": "fe1038c6-0e80-4008-bed0-f7f457411782",
    "title": "title example",
    "description": "long description for example",
    "step": "TODO"
} 
```

[PUT] http://localhost:3000/task/{id}/next-step \
API responsible to run a next step proccess to task. It produces (rabbit producer) a message on rabbit to run a procces (rabbit consumer) on background.
```
doesn't have body to request 
```