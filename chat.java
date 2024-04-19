import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class chat {
   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         // Create and show the login window
         AuthenticationWindow authWindow = new AuthenticationWindow();
         authWindow.setVisible(true);
      });
   }
}

class AuthenticationWindow extends JFrame {
   private JTextField usernameField;
   private JPasswordField passwordField;
   private JButton loginButton, registerButton;
   private static final Map<String, String> userDatabase = new HashMap<>();

   public AuthenticationWindow() {
      setTitle("Login or Register");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(300, 200);
      setLocationRelativeTo(null);
      JPanel panel = new JPanel(new GridLayout(4, 2));
      usernameField = new JTextField();
      passwordField = new JPasswordField();
      loginButton = new JButton("Login");
      registerButton = new JButton("Register");
      panel.add(new JLabel("Username:"));
      panel.add(usernameField);
      panel.add(new JLabel("Password:"));
      panel.add(passwordField);
      panel.add(loginButton);
      panel.add(registerButton);
      add(panel);
      registerButton.addActionListener(e -> {
         String username = usernameField.getText();
         String password = new String(passwordField.getPassword());
         if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, password);
            JOptionPane.showMessageDialog(this, "User registered successfully!", "Registration",
                  JOptionPane.INFORMATION_MESSAGE);
         } else {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Registration Error",
                  JOptionPane.ERROR_MESSAGE);
         }
      });
      loginButton.addActionListener(e -> {
         String username = usernameField.getText();
         String password = new String(passwordField.getPassword());
         if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            ChatWindow chatWindow = new ChatWindow(username);
            chatWindow.setVisible(true);

         } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error",
                  JOptionPane.ERROR_MESSAGE);
         }
      });
   }
}

class ChatWindow extends JFrame {
   private JTextArea chatArea;
   private JTextField messageField;

   public ChatWindow(String username) {
      setTitle("Chat - " + username);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400, 300);
      setLocationRelativeTo(null);

      JPanel panel = new JPanel(new BorderLayout());

      chatArea = new JTextArea();
      chatArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(chatArea);

      messageField = new JTextField();
      messageField.addActionListener(e -> {
         String message = messageField.getText();
         sendMessage(username, message);
         messageField.setText("");
      });

      panel.add(scrollPane, BorderLayout.CENTER);
      panel.add(messageField, BorderLayout.SOUTH);

      add(panel);
   }

   private void sendMessage(String username, String message) {
      chatArea.append(username + ": " + message + "\n");
   }
}
