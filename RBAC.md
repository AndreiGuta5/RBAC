
Implement Role Based Access Control (RBAC) backend REST service.
Client applications are able to associate users with roles using REST API provided by RBAC.
One user can be associated with multiple roles.
If user is assigned with no roles, the RBAC entry will be removed for that user.
The following report will be implemented as REST API - sum of users per role

Sample data:
```
u1
  r1
  r2
  r3
u2
  r2
u3
  r3
  r4
```

Report result for sample data:
```
r1: 1
r2: 2
r3: 2
r4: 1
```

Assumptions:
* User database is prepopulated with sample data when app starts
* Role database is prepopulated with sample data when app starts
* Data is going to be stored in memory. No external DB needed.
