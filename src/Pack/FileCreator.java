package Pack;

import java.io.*;

public class FileCreator {
     void createFiles(String name, String data) throws Exception {
          File f = new File(name);
          if (!f.exists()) {
               f.createNewFile();
               FileWriter fw = new FileWriter(f);
               fw.write(data);
               fw.close();
          }
     }
}
