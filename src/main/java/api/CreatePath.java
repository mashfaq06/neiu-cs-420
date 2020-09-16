package api;

import java.io.File;
import java.net.URISyntaxException;

public class CreatePath {

    private String path;

    public CreatePath () {
    }

    public void setPath(String fileName) throws URISyntaxException {
        this.path = createPath(getFullPath(),fileName);
    }

    public String getPath() {
        return this.path;
    }

    private String createPath(String path, String fileName)
    {
        File directory = new File(path);
        if(!directory.exists())
            directory.mkdirs();
        return path + fileName;
    }
    private String getFullPath() throws URISyntaxException {
        String initialPath = ClassLoader.getSystemClassLoader().getResource("").toURI().getPath();
        initialPath = initialPath.substring(0,initialPath.indexOf("classes"));
        String finalPath = initialPath + "resources" + File.separator;
        return finalPath;
    }
}
