<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            function setAction (actionuri) {
                document.forms[0].action = actionuri;
            }
        </script>
        <title>Login</title>
    </head>
    <body>
        <form name="login" action="ecom-myebay/authenticate" method="POST">
            <table>
                <tr>
                    <td>
                        User Id
                    </td>
                    <td>
                        <input type="text" name="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        Password
                    </td>
                    <td>
                        <input type="password" name="userpassword">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" name="submit" value="Login">
                        <input type="submit" name="reset" value="Clear" onclick="javascript:setAction('ecom-myebay/login')">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
