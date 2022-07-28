package record;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class rankreview extends JFrame {
    // 버퍼 생성
    BufferedReader bufferReader = null;

    // Input 스트림 생성
    InputStreamReader inputStreamReader = null;

    // File Input 스트림 생성
    FileInputStream fileInputStream = null;

    // File 경로
    File file = new File("Ranking.txt");

    // 버퍼로 읽어들일 임시 변수
    String temp = "";

    // 최종 내용 출력을 위한 변수
    String content = "";

    JButton end_Button = new JButton("end");

    JTextArea jTextArea = new JTextArea();

    public rankreview() {

        Container contain = getContentPane();


        contain.add(end_Button, BorderLayout.SOUTH);
        contain.add(jTextArea, BorderLayout.CENTER);

        try {


            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "euc-kr");
            bufferReader = new BufferedReader(inputStreamReader);


            while ((temp = bufferReader.readLine()) != null) {
                content += temp + "\n";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        jTextArea.setText(content);
        jTextArea.setFocusable(false);
        end_Button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);

    }

}
