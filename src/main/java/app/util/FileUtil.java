package app.util;

import com.google.common.io.Resources;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


// Do not change
public class FileUtil {

    private static final String PROJECT_DIRECTORY_1 = "src";
    private static final String PROJECT_DIRECTORY_2 = "main";
    private static final String PROJECT_DIRECTORY_3 = "resources";

    public static File getAndCreateFileFromResourcesDirectory(String fileName) throws URISyntaxException {
        Path path = getResourcesPath();
        path = path.resolve(fileName);
        return path.toFile();
    }

    private static Path getResourcesPath() throws URISyntaxException {
        URL url = Resources.getResource("");
        File auxFile = new File(url.toURI());

        Path path = Paths.get(auxFile.getAbsolutePath());
        path = path.getParent().getParent();
        path = path.resolve(PROJECT_DIRECTORY_1).resolve(PROJECT_DIRECTORY_2).resolve(PROJECT_DIRECTORY_3);
        return path;
    }
}
