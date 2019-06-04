package view;


import com.sun.deploy.Environment;

import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map map=System.getenv();
        System.out.println(map);
        System.out.println(map.get("CLASSPATH"));
    }
}
