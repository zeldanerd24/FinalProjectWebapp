<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Factorial</title>
</head>
<body>
    <div align="center">
        <table border="0" style="table-layout:fixed; width=150px">
            <tr>
                <td colspan="2" align="center"><h2>Results</h2></td>
            </tr>
            <tr>
                <td>Factorial Requested:</td>
                <td>${results.number}</td>
            </tr>
            <tr>
                <td>Result:</td>
                <td><div style="word-wrap: break-word;">${results.result}</div></td>
            </tr>
            <tr>
                <td>IP of Host:</td>
                <td>${results.hostIp}</td>
            </tr>
            <tr>
                <td>IP of Compute:</td>
                <td>${results.computeIp}</td>
            </tr>
            <tr>
                <td>Time Taken:</td>
                <td>${results.time}</td>
            </tr>
            <tr/>
            <tr>
                <td colspan="2" align="center"><a href="factorial">Submit Another Request</a></td>
            </tr>
        </table>
    </div>
</body>
</html>