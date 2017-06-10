--------------------------------------------------

view all todolist

GET
url = /
example http://localhost:8080/james 

Parameter

-

Success 
Response  (example):
[{"id":0,"subject":"sss","detail":"2","status":"done"}]

Success 
Response  (example with nodata):
[]
---------------------------------------------------

view single todolist (select by id)

GET
url = /getById/{id}
example http://localhost:8080/james/getById/0 

Parameter
Field	Type		Description

id		Integer		id of todolist.	
-

Success 
Response  (example):
{"id":0,"subject":"ss","detail":"2","status":"done"}

Success 
Response  (example with nodata):
[]
	
---------------------------------------------------

Create new todolist

POST
url = /input
 

Parameter

Field	Type	Description

subject	String	subject of todolist (This feild is require).
detail	String 	detail of todolist.
status  String 	status of todolist(input done or pending only).

Success 
Response  :
[{"success":true},{"id":0}]

Error 
subject is empty
Response  :
[{"success":false,"error":"Invalid input subject."}]

Error 
status is invalid
Response  :
[{"success":false,"error":"Invalid input status."}]


---------------------------------------------------

update status todolist  

POST
url = /update
example http://localhost:8080/james/update/

Parameter
Field	Type		Description

id		Integer		id of todolist.	
subject	String		subject of todolist (This feild is require).
detail	String 		detail of todolist.
status  String 		status of todolist(input done or pending only).
-

Success 
Response  (example):
{"success":true}

Error  
HTTP Status 500
	 	
 ---------------------------------------------------

setStatue status todolist  

POST
url = /setstatue
example http://localhost:8080/james/setStatue/

Parameter
Field	Type		Description

id		Integer		id of todolist.	
statusInput  String 		status of todolist(input done or pending only).
-

Success 
Response  (example):
{"success":true}

Error  
HTTP Status 500

---------------------------------------------------

delete status todolist  

POST
url = /delete/"
example http://localhost:8080/delete/{idInput}"

Parameter
Field	Type		Description

idInput		Integer		id of todolist.	
 
-

Success 
Response  (example):
{"success":true}

Error  
HTTP Status 500