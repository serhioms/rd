package ca.mss.rd.starter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

final public class AppClasspath {

	final private List<File> _elements = new ArrayList<File>();

    public AppClasspath() {}

    public AppClasspath(String initial) {
        addClasspath(initial);
    }

    final public boolean addComponent(String component) {
        if ((component != null) && (component.length() > 0)) {
            try {
                File f = new File(component);
                if (f.exists()) {
                    File key = f.getCanonicalFile();
                    if (!_elements.contains(key)) {
                        _elements.add(key);
                        return true;
                    }
                }
            } catch (IOException e) {}
        }
        return false;
    }

    final public boolean addComponent(File component) {
        if (component != null) {
            try {
                if (component.exists()) {
                    File key = component.getCanonicalFile();
                    if (!_elements.contains(key)) {
                        _elements.add(key);
                        return true;
                    }
                }
            } catch (IOException e) {}
        }
        return false;
    }

    final public boolean addClasspath(String s) {
        boolean added = false;
        if (s != null) {
            StringTokenizer t = new StringTokenizer(s, File.pathSeparator);
            while (t.hasMoreTokens()) {
                added |= addComponent(t.nextToken());
            }
        }
        return added;
    }

    final public String toString() {
        StringBuffer cp = new StringBuffer(1024);
        int cnt = _elements.size();
        if (cnt >= 1) {
            cp.append(((File) (_elements.get(0))).getPath());
        }
        for (int i = 1; i < cnt; i++) {
            cp.append(File.pathSeparatorChar);
            cp.append(((File) (_elements.get(i))).getPath());
        }
        return cp.toString();
    }

    @SuppressWarnings("deprecation")
	final public URL[] getUrls() {
        int cnt = _elements.size();
        URL[] urls = new URL[cnt];
        for (int i = 0; i < cnt; i++) {
            try {
                urls[i] = ((File) (_elements.get(i))).toURL();
            } catch (MalformedURLException e) {}
        }
        return urls;
    }

    final public ClassLoader getClassLoader() {
        URL[] urls = getUrls();

        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null) {
            parent = AppClasspath.class.getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return new URLClassLoader(urls, null);
    }

    final public List<File> getElements() {
        return _elements;
    }
}
