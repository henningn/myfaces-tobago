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

import org.apache.myfaces.tobago.context.Theme;
import org.apache.myfaces.tobago.context.TobagoContext;

import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import java.util.ResourceBundle;

public class ResourceUtils {

  public static final String TOBAGO_RESOURCE_BUNDLE = "tobagoResourceBundle";

  public static String getString(final String key) {
    return getString(TOBAGO_RESOURCE_BUNDLE, key);
  }

  public static String getString(final FacesContext facesContext, final String key) {
    return getString(facesContext, TOBAGO_RESOURCE_BUNDLE, key);
  }

  public static String getString(final FacesContext facesContext, final Application application, final String key) {
    return getString(facesContext, application, TOBAGO_RESOURCE_BUNDLE, key);
  }

  public static String getString(final String bundle, final String key) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    return getString(facesContext, bundle, key);
  }

  public static String getString(final FacesContext facesContext, final String bundle, final String key) {
    return getString(facesContext, facesContext.getApplication(), bundle, key);
  }

  public static String getString(
      final FacesContext facesContext, final Application application, final String bundle, final String key) {

    if (TOBAGO_RESOURCE_BUNDLE.equals(bundle)) {
      Theme theme = TobagoContext.getInstance(facesContext).getTheme();
      final ResourceBundle themeBundle = application.getResourceBundle(facesContext, theme.getName() + "Bundle");

      if (themeBundle != null && themeBundle.containsKey(key)) {
        return themeBundle.getString(key);
      } else {
        final ResourceBundle tobagoResourceBundle = application.getResourceBundle(facesContext, TOBAGO_RESOURCE_BUNDLE);
        return tobagoResourceBundle.getString(key);
      }
    } else {
      final ResourceBundle resourceBundle = application.getResourceBundle(facesContext, bundle);
      return resourceBundle.getString(key);
    }
  }
}
