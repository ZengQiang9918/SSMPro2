
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="base.jsp"%>
<html>
<head>
    <%--以当前项目路径为相对路径参考--%>
    <base href="<%=basePath%>">
    <title>首页</title>
</head>
<body>
    <%
        //什么事也不干，请求转发到login.jsp页面
        System.out.println("index.jsp");
        request.getRequestDispatcher("login.jsp").forward(request,response);
    %>
</body>
</html>
