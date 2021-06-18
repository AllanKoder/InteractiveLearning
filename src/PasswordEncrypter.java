public class PasswordEncrypter {
    public String BasicEncryption(String text, int key)
    {
        String encryptedText = "";
        for (char letter : text.toCharArray()) {
            letter += key;
            encryptedText += letter;
        }
        return encryptedText;
    }
    //using rot 13 with chars on the computer, Password123 is converted
}
