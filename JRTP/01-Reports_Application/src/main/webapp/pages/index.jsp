<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
  <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container">
<h3 class="pb-3 pt-3">Report Application</h3>
<form:form action="search" modelAttribute="search" method="POST">
<table>
    <tr>
      <td>Plan Name</td>
      <td>
          <form:select path="planName" class="form-select">
             <form:option value="">-select-</form:option> 
             <form:options items="${planName}"/>	 
          </form:select> 
      </td>
      <td>Plan Status</td>
      <td>
          <form:select path="planStatus" class="form-select">
             <form:option value="">-select-</form:option>
             <form:options items="${planStatus }"/> 	 
          </form:select> 
      </td>
      <td>Gender</td>
      <td>
           <form:select path="gender" class="form-select">
             <form:option value="">-select-</form:option>
             <form:option value="Male">Male</form:option>
             <form:option value="Female">Female</form:option>   	 
          </form:select> 
      </td>
      
    </tr>
    <tr>
       <td>Start Date</td>
       <td>
          <form:input path="startDate" type="date" class="form-control"/>
       </td>
       <td>End Date</td>
       <td>
          <form:input  path="endDate" type="date" class="form-control"/>
       </td>
    </tr>
    <tr>
    <td>
      <a href="/" class="btn btn-secondary">Reset</a>
    </td>
       <td>
          <input type="submit" value="Search" class="btn btn-primary"/>      
       </td>   
    </tr>
    
</table>

</form:form>
<hr/>
  <table class="table table-stripped table-hover">
    <thead></thead>
      <tr>
         <th>Sr.no</th>
         <th>Holder Name</th>
         <th>Gender</th>
         <th>Plan Name</th>
         <th>Plan Status</th>
         <th>Start Date</th>
         <th>End Date</th>
         <th>Benefit Amount</th>
      </tr>
    <tbody>
        <c:forEach items="${plans}" var="plan" varStatus="index">
          <tr>
             <td>${index.count}</td>
             <td>${plan.citizenName}</td>
             <td>${plan.gender}</td>
             <td>${plan.planName}</td>
             <td>${plan.planStatus}</td>
             <td>${plan.planStartDate}</td>
             <td>${plan.planEndDate}</td>
             <td>${plan.benefitAmount}</td>
          </tr>
            
        </c:forEach>
        <c:if test="${empty plans}">
            <td colspan="8" class="text-center">No Records Found</td>
        </c:if>
    </tbody>
    
   
  </table>
<hr/>
Export: <a href="excel">Excel</a>  <a href="pdf">Pdf</a>
</div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    
</body>
</html>