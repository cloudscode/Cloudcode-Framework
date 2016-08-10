package com.cloudcode.framework.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.TemplateLoader;

public class StringProtocolFreeMarkerTemplateLoader
  implements TemplateLoader
{
  public static final String PROTOCOL = "data:tpl/freemarker;";
  private final Map<String, StringTemplateSource> templates;

  public StringProtocolFreeMarkerTemplateLoader()
  {
    this.templates = new HashMap();
  }

  public void closeTemplateSource(Object templateSource) throws IOException {
  }

  public Object findTemplateSource(String name) throws IOException {
    StringTemplateSource tpl = (StringTemplateSource)this.templates.get(name);
    if (tpl == null) {
      String content = name.trim();
      if (!content.startsWith("data:tpl/freemarker;")) {
        return null;
      }
      content = content.substring("data:tpl/freemarker;".length());
      tpl = new StringTemplateSource(name, content);
      this.templates.put(name, tpl);
    }
    return tpl;
  }

  public long getLastModified(Object templateSource) {
    return 9223372036854775807L;
  }

  public Reader getReader(Object templateSource, String encoding) throws IOException {
    return new StringReader(((StringTemplateSource)templateSource).source);
  }
  private static class StringTemplateSource {
    private final String name;
    private final String source;

    StringTemplateSource(String name, String source) { if (name == null) {
        throw new IllegalArgumentException("name == null");
      }
      if (source == null) {
        throw new IllegalArgumentException("source == null");
      }
      this.name = name;
      this.source = source; }

    public boolean equals(Object obj)
    {
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