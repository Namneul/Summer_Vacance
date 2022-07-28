package moon_lander;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterPage extends JFrame {
    //ID 입력 필드
    private JTextField tResisterID;
    //계정 생성 버튼
    private JButton btnAddAccount;
    //레지스터 폼 패널
    private JPanel resisterPanel;
    //PW 입력 필드
    private JPasswordField tResisterPw;

    public RegisterPage(){
        setContentPane(resisterPanel);
        setTitle("resister");
        setSize(450, 300);
        setVisible(true);

        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        //화면 중앙에 띄우기
        setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);

        //resister 버튼
        btnAddAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                            .setEmail(tResisterID.getText())
                            .setEmailVerified(false)
                            .setDisplayName(String.valueOf(tResisterPw.getPassword()));

                    FirebaseAuth.getInstance().createUser(request);
                    Logger.getLogger(RegisterPage.class.getName()).log(Level.INFO, "유저 생성 성공");
                    JOptionPane.showMessageDialog(null, "유저 생성 성공");
                }
                catch (FirebaseAuthException ex){
                    Logger.getLogger(RegisterPage.class.getName()).log(Level.SEVERE, null, ex);
                }

                new LoginPage();
                dispose();
            }
        });
    }

}
