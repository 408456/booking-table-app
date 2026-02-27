package goltsman.bookingtableapp.common;

public class ValidationPatternConstant {
    public static final String TITLE_PATTERN =
            "^(?!\\s)(?!.*\\s$)[A-Za-zА-Яа-яЁё0-9\\s.,]+$";

    public static final String TITLE_PATTERN_MESSAGE_ERROR =
            "Передана некорректная строка. " +
                    "Разрешены: латиница, кириллица, цифры, точки и запятые" +
                    "Пробелы разрешены только между словами. " +
                    "Запрещены пробелы в начале и конце строки, иероглифы, смайлики, любые спецсимволы";

    public static final String PHONE_PATTERN =
            "^\\+[0-9]{11}$";

    public static final String PHONE_PATTERN_MESSAGE_ERROR =
            "Телефон должен начинаться с '+' и содержать от 11 цифр";

    public static final String EMAIL_PATTERN =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static final String EMAIL_PATTERN_MESSAGE_ERROR =
            "Неверный формат электронной почты";

    public static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    public static final String PASSWORD_PATTERN_MESSAGE_ERROR =
            "Пароль должен содержать минимум 8 символов, включая цифру, строчную и заглавную латинские буквы, и хотя бы один спецсимвол";
}
