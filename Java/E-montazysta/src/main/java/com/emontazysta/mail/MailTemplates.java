package com.emontazysta.mail;

public class MailTemplates {

    public static String companyCreate(String comapnyName, String userName, String password) {
        String content = "<p><strong>Witamy <em>" + comapnyName + "</em> na pokładzie <em>E-Montażysty!</em></strong></p>" +
                "<p>Dziękujemy za zaufanie nam i skorzystanie z naszych usług.<br />" +
                "Dołożymy wszelkich starań, aby planowanie, realizacja i archiwizacja waszych zleceń była jeszcze bardziej efektywne!</p>" +
                "<p>Poniżej przesyłamy dane logowania na konto administratora firmy:<br />" +
                "Nazwa użytkownika: <em>" + userName + "</em><br />" +
                "Hasło: <em>" + password + "</em><br />" +
                "<a href=\"https://dev.emontazysta.pl/login\">Logowanie</a></p>" +
                "<p>W razie niejasności, bądź problemów zapraszamy do kontaktu pod adresem email: " +
                "<span style=\"text-decoration: underline;\">support@emontazysta.pl</span>,<br />" +
                "bądź dzwoniąc na infolinię supportu w godzinach 8:00 - 18:00.</p>" +
                "<p>Liczymy na owocną współpracę<br />" +
                "Zespół <em>E-Montażysta</em></p>";
        return content;
    }

    public static String employeeCreate(String userName, String password, String firstName, String lastName) {
        String content = "<p><strong>Witaj <em>" + firstName + " " + lastName + "</em> na pokładzie serwisiu E-Montażysta!</strong></p>" +
                "<p>Zostałeś dodany jako jeden z pracowników.</p>" +
                "<p>Poniżej przesyłamy Twoje dane logowania<br />" +
                "Nazwa użytkownika: <em>" + userName + "</em><br />" +
                "Hasło: <em>" + password + "</em><br />" +
                "<span style=\"text-decoration: underline;\">support@emontazysta.pl</span>,<br />" +
                "<p>W razie niejasności, bądź problemów zapraszamy do kontaktu pod adresem email: " +
                "<span style=\"text-decoration: underline;\">support@emontazysta.pl</span>,<br />" +
                "bądź dzwoniąc na infolinię supportu w godzinach 8:00 - 18:00.</p>" +
                "<p>Do zobaczenia w systemie<br />Zesp&oacute;ł <em>E-Montażysta</em></p>";
        return content;
    }

    public static String resetPassword(String url) {
        return "<p>Aby zresetować hasło kliknij poniższy link:</p>" +
                "<p><a href=\"" + url + "\">Resetowanie hasła</a></p>";
    }
}