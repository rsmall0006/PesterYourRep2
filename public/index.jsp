

<% @page import="java.util.Properties" %>
<% @page import="javax.mail.*" %>
<% @page import="java.mail.internet.*" %>
<% @page contentType="text/html" pageEncoding="UTF-8" %>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html"; charset=UTF-8>
<title>Email</title>
</head>

<body>
<h2>Email</h2>
    <%!
    public static class SMTPAuthenticator extends Authenticator {

        public PasswordAuthentication getPasswordAuthentication () {
             //we need to get the username and password out of what the user types into our index form
            return new PasswordAuthentication(username, password)

        }
    }
    %>
    <form name="" action="" method="GET">
        <table border="0">
            <tbody>
                <tr>
                    <td>To : </td>
                    <td><input type="text" name="to" value="" size="50" /> </td>
                </tr>
                <tr>
                    <td>Subject : </td>
                    <td><input type="text" name="subject" value="" size="50" /> </td>
                </tr>
                <tr>
                    <td>Message : </td>
                    <td><textarea name="message" rows="4" cols="50"></textarea></td>
                </tr>
            </tbody>
        </table>
        <input type="hidden" name="hidden" value="" />
        <input type="reset" value="Clear" name="clear" />
        <input type="submit" value="Send" name="send" />
    </form>

</body>

</html>

