# How to use the application
In order to run the application go to `src/main/scala/Boot.scala`
and run from your Intellij Idea.

The application is exposing an endpoint which is accessible on:

>http://localhost:8080/evaluation?url=url1&url=url2

This endpoint takes variable query parameters with the same name *`url`*
it's also called repeated parameter.

### Note
<div style="color:red">
 Make sure to enable PostFix operator Notation it's been used in file [org.ps.actor.RequestProcessorActor] 
 Operator = !!
</div>

There are lots of improvements we can do in this system
right now the processing of the file and downloading is being 
happened as the part of api call which can get time out for large files.

This solution will not work efficiently for large files.
