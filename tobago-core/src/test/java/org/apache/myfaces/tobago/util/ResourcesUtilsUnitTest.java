/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.tobago.util;

import org.apache.myfaces.test.config.ResourceBundleVarNames;
import org.apache.myfaces.tobago.context.TobagoContext;
import org.apache.myfaces.tobago.internal.config.AbstractTobagoTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.faces.context.FacesContext;
import java.util.Locale;
import java.util.MissingResourceException;

public class ResourcesUtilsUnitTest extends AbstractTobagoTestBase {

  @Test
  public void testDefault() {
    Assertions.assertEquals("First Page", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testGermany() {
    facesContext.getViewRoot().setLocale(Locale.GERMANY);
    Assertions.assertEquals("erste Seite", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testSpanish() {
    facesContext.getViewRoot().setLocale(Locale.forLanguageTag("es"));
    Assertions.assertEquals("Primera Página", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testFallback() {
    facesContext.getViewRoot().setLocale(Locale.forLanguageTag("ja"));
    Assertions.assertEquals("First Page", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testThemeBundle() {
    ResourceBundleVarNames.addVarName("testBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    Assertions.assertEquals("test", TobagoContext.getInstance(FacesContext.getCurrentInstance()).getTheme().getName());
    Assertions.assertEquals("First Page from test bundle (default)", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testThemeBundleGermany() {
    ResourceBundleVarNames.addVarName("testBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    facesContext.getViewRoot().setLocale(Locale.GERMANY);
    Assertions.assertEquals("test", TobagoContext.getInstance(FacesContext.getCurrentInstance()).getTheme().getName());
    Assertions.assertEquals("erste Seite vom Test-Bundle (DE)", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testThemeBundleSpanish() {
    ResourceBundleVarNames.addVarName("testBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    facesContext.getViewRoot().setLocale(Locale.forLanguageTag("es"));
    Assertions.assertEquals("test", TobagoContext.getInstance(FacesContext.getCurrentInstance()).getTheme().getName());
    Assertions.assertEquals("First Page from test bundle (default)", ResourceUtils.getString("sheet.first"));
  }

  @Test
  public void testThemeBundleFallback() {
    ResourceBundleVarNames.addVarName("testBundle",
        "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    Assertions.assertEquals("test", TobagoContext.getInstance(FacesContext.getCurrentInstance()).getTheme().getName());
    Assertions.assertEquals("Last Page", ResourceUtils.getString("sheet.last"));
  }

  @Test
  public void testCustomResourceBundle() {
    ResourceBundleVarNames.addVarName("customBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    Assertions.assertEquals("First Page from test bundle (default)",
        ResourceUtils.getString("customBundle", "sheet.first"));
  }

  @Test
  public void testCustomResourceBundleGermany() {
    ResourceBundleVarNames.addVarName("customBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    facesContext.getViewRoot().setLocale(Locale.GERMANY);
    Assertions.assertEquals("erste Seite vom Test-Bundle (DE)", ResourceUtils.getString("customBundle", "sheet.first"));
  }

  @Test
  public void testCustomResourceBundleSpanish() {
    ResourceBundleVarNames.addVarName("customBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    facesContext.getViewRoot().setLocale(Locale.forLanguageTag("es"));
    Assertions.assertEquals("First Page from test bundle (default)",
        ResourceUtils.getString("customBundle", "sheet.first"));
  }

  @Test
  public void testCustomResourceBundleFallback() {
    ResourceBundleVarNames.addVarName("customBundle", "org.apache.myfaces.tobago.alternate.TobagoResourceBundle");
    Assertions.assertThrows(MissingResourceException.class,
        () -> ResourceUtils.getString("customBundle", "sheet.last"));
  }
}
