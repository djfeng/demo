<#assign contextPath = springMacroRequestContext.getContextPath() />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type"content="text/html; charset=UTF-8">
<title>Insert titlehere</title>
</head>
<body>
<h2>${word0}${word1}</h2>

list 循环<br/>
<#list users as user>
${user.oid}<br/>


${user.birthday}<br/>
${user.birthday}<br/>
<#assign test1 = "2009-01-24 10:10:10"?datetime />
${test1}
</#list>
</body>
</html>