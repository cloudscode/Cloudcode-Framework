package com.cloudcode.framework.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import freemarker.cache.TemplateLoader;

public class ClasspathProtocolFreeMarkerTemplateLoader
  implements TemplateLoader
{
  public static final String PROTOCOL = "classpath:";
  private final Map<String, StringTemplateSource> templates;
  private boolean cache;

  public ClasspathProtocolFreeMarkerTemplateLoader()
  {
    this.templates = new HashMap();

    this.cache = false;
  }
  public boolean isCache() {
    return this.cache;
  }

  public void setCache(boolean cache) {
    this.cache = cache;
  }

  public void closeTemplateSource(Object templateSource) throws IOException {
  }

  public Object findTemplateSource(String name) throws IOException {
    StringTemplateSource tpl = (StringTemplateSource)this.templates.get(name);
    if ((tpl != null) && (this.cache)) {
      return tpl;
    }
    name = name.trim();
    if (!name.trim().startsWith("classpath:")) {
      return null;
    }
    URL url = ResourceUtils.getURL(name);
    if (url != null) {
      String content = IOUtils.toString(url.openStream(), "utf-8");
      tpl = new StringTemplateSource(name, content, new Date().getTime());
    }
    this.templates.put(name, tpl);
    return tpl;
  }

  public long getLastModified(Object templateSource) {
    StringTemplateSource tps = (StringTemplateSource)templateSource;
    if (!this.cache) {
      return tps.getLastModified();
    }
    return 9223372036854775807L;
  }

  public Reader getReader(Object templateSource, String encoding) throws IOException {
    return new StringReader(((StringTemplateSource)templateSource).source);
  }
  private static class StringTemplateSource { private final String name;
    private final String source;
    private final long lastModified;

    public long getLastModified() { return this.lastModified; }

    StringTemplateSource(String name, String source, long lastModified)
    {
      if (name == null) {
        throw new IllegalArgumentException("name == null");
      }
      if (source == null) {
        throw new IllegalArgumentException("source == null");
      }
      this.lastModified = lastModified;
      this.name = name;
      this.source = source;
    }

    public boolean equals(Object obj) {
      if ((obj instanceof StringTemplateSource)) {
        return this.name.equals(((StringTemplateSource)obj).name);
      }
      return false;
    }

    public int hashCode() {
      return this.name.hashCode();
    }
  }
}