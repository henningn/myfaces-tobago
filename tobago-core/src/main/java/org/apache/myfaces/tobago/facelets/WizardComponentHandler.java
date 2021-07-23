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

package org.apache.myfaces.tobago.facelets;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

public class WizardComponentHandler extends TobagoComponentHandler {

  private TagAttribute outcomeAttribute;

  public WizardComponentHandler(final ComponentConfig componentConfig) {
    super(componentConfig);
    outcomeAttribute = getAttribute("outcome");
  }

  public void onComponentCreated(
      final FaceletContext faceletContext, final UIComponent wizard, final UIComponent parent) {

    if (outcomeAttribute != null) {
      if (outcomeAttribute.isLiteral()) {
        wizard.getAttributes().put("outcome", outcomeAttribute.getValue(faceletContext));
      } else {
        final ValueExpression expression = outcomeAttribute.getValueExpression(faceletContext, String.class);
        wizard.setValueExpression("outcome", expression);
      }
    }

    super.onComponentCreated(faceletContext, wizard, parent);
  }

}