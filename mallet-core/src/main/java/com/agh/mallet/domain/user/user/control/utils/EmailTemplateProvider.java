package com.agh.mallet.domain.user.user.control.utils;

public class EmailTemplateProvider {

    private EmailTemplateProvider() {}

    public static String getEmailConfirmationTemplate(String confirmationLink) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>MALLet - Email Confirmation</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Russo+One\">\n" +
                "      <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=REM\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: \"REM\", sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #e5f1f9;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 30px;\n" +
                "            box-sizing: border-box;\n" +
                "            border: 2px solid #2e4780;\n" +
                "            border-radius: 10px;\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "      font-family: \"Russo One\", sans-serif;\n" +
                "            color: #2e4780;\n" +
                "      font-size:60px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            color: #5493d2;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .button-container {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 20px 20px;\n" +
                "            text-align: center;\n" +
                "            text-decoration: none;\n" +
                "            color: #ffffff;\n" +
                "            background-color: #5493d2;\n" +
                "            border-radius: 5px;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .alternate-link {\n" +
                "            margin-top: 20px;\n" +
                "            color: #2e4780;\n" +
                "            text-decoration: underline;\n" +
                "            display: block;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>MALLet</h1>\n" +
                "        <p>Thank you for signing up to MALLet!</p>\n" +
                "        <p>Please click the button below to confirm your email address.</p>\n" +
                "        <div class=\"button-container\">\n" +
                "            <a href=\"" +
                confirmationLink + "\"" +
                "class=\"button\">Confirm Email</a>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static String getEmailConfirmedTemplate() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>MALLet - Email Confirmation</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Russo+One\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=REM\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: \"REM\", sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #e5f1f9;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 30px;\n" +
                "            box-sizing: border-box;\n" +
                "            border: 2px solid #2e4780;\n" +
                "            border-radius: 10px;\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            font-family: \"Russo One\", sans-serif;\n" +
                "            color: #2e4780;\n" +
                "            font-size: 60px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            color: #5493d2;\n" +
                "      font-size:20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>MALLet</h1>\n" +
                "      <p>Thank you for joining MALLet!</p>\n" +
                "      <p>You can now start learning!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
