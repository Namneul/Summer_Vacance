package firebase;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MFirebaseTool {
    private static MFirebaseTool mFirebaseTool = null;

    private MFirebaseTool() {
        try {
            FileInputStream serviceAccount = new FileInputStream(this.getClass().getResource("key.json").getPath());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://moonlander1-c4ddb-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println(FirebaseApp.getInstance().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MFirebaseTool getInstance() {
        if (mFirebaseTool == null) {
            mFirebaseTool = new MFirebaseTool();
        }

        return mFirebaseTool;
    }
}