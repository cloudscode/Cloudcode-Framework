package com.cloudcode.framework.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;

public class FreeMarkerConfigurers extends FreeMarkerConfigurer
{
  private final Log log = LogFactory.getLog(FreeMarkerConfigurers.class);
  private String filesystemRoot;
  private List<String> templateDirs;

  public void setTemplateDirs(List<String> templateDirs)
  {
    this.templateDirs = templateDirs;
  }

  public void setFilesystemRoot(String filesystemRoot) {
    this.filesystemRoot = filesystemRoot;
  }

  public void afterPropertiesSet()
    throws IOException, TemplateException
  {
    List tpls = new ArrayList();
    if (this.templateDirs != null) {
      for (String str : this.templateDirs) {
        TemplateLoader loader = parseTemplateDir(str);
        if (loader != null) {
          tpls.add(loader);
        }
      }
    }
    setPostTemplateLoaders((TemplateLoader[])tpls.toArray(new TemplateLoader[0]));
    super.afterPropertiesSet();
    getConfiguration().setLocalizedLookup(false);
    getConfiguration().setClassicCompatible(true);
  }

  private TemplateLoader parseTemplateDir(String dirPath) throws IOException {
    if (!StringUtils.isNotBlank(dirPath))
      return null;
    if (dirPath.startsWith("classpath:"))
      return new ClassTemplateLoader(getClass(), dirPath.substring(9));
    if (StringUtils.isNotBlank(dirPath)) {
      if (StringUtils.isNotBlank(this.filesystemRoot)) {
        dirPath = StringUtils.replaceOnce(dirPath, "%{filesystem-root}", this.filesystemRoot);
      }
      File dirFile = new File(dirPath);
      if (!dirFile.exists()) {
        dirFile.mkdirs();
      }
      this.log.info("add a new FileTemplateLoader from directory:" + dirPath);
      return new FileTemplateLoader(dirFile);
    }
    return null;
  }
}