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

package org.apache.myfaces.tobago.internal.component;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UISelectItems;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.tobago.component.Tags;
import org.apache.myfaces.tobago.internal.taglib.component.FilteredSelectItemsTagDeclaration;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * {@link FilteredSelectItemsTagDeclaration}
 */
public abstract class AbstractUIFilteredSelectItems extends UISelectItems {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private transient String query;

  public String getQuery() {
    final ValueExpression expression = this.getValueExpression("query");
    if (expression != null) {
      try {
        return (String) expression.getValue(FacesContext.getCurrentInstance().getELContext());
      } catch (final Exception e) {
        LOG.error("", e);
        return null;
      }
    } else {
      return query;
    }
  }

  public void setQuery(final String query) {
    final ValueExpression expression = this.getValueExpression("query");
    if (expression != null) {
      try {
        expression.setValue(FacesContext.getCurrentInstance().getELContext(), query);
      } catch (final Exception e) {
        LOG.error("query='{}'", query, e);
      }
    } else {
      this.query = query;
    }
  }

  public abstract Integer getDelay();

  public abstract Integer getMinimumCharacters();

  /**
   * Selected elements must always be available. The server-side filtering reduces the available items and may filter
   * out selected items. For this reason, a UISelectItems component is added, which must contain all selected items that
   * are not available/filtered out.
   *
   * @param parent of the missing selected values UISelectItems component
   */
  public List<?> getMissingSelectedValues(FacesContext facesContext, UIComponent parent) {
    final String missingSelected = "missingSelected";

    Optional<UIComponent> missingSelectedSelectItemsOptional = parent.getChildren().stream()
        .filter(child -> child.getAttributes().get(missingSelected) != null).findFirst();
    if (missingSelectedSelectItemsOptional.isPresent()) {
      return (List<?>) ((UISelectItems) missingSelectedSelectItemsOptional.get()).getValue();
    } else {
      final UISelectItems selectedItems = (UISelectItems) ComponentUtils.createComponent(
          facesContext, Tags.selectItems.componentType(), null, null);
      selectedItems.getAttributes().put(missingSelected, true);

      copyAttribute(this, selectedItems, "binding");
      copyAttribute(this, selectedItems, "itemDisabled");
      copyAttribute(this, selectedItems, "itemLabel");
      copyAttribute(this, selectedItems, "itemValue");
      copyAttribute(this, selectedItems, "var");
      parent.getChildren().add(0, selectedItems);

      selectedItems.setValue(new ArrayList<>()); //list must be mutable
      return (List<?>) selectedItems.getValue();
    }
  }

  private void copyAttribute(UISelectItems from, UISelectItems to, String name) {
    ValueExpression fromValueExpression = from.getValueExpression(name);
    if (fromValueExpression != null) {
      to.setValueExpression(name, fromValueExpression);
    } else {
      Object value = from.getAttributes().get(name);
      if (value != null) {
        to.getAttributes().put(name, value);
      }
    }
  }

  public void updateMissingSelectedItems(FacesContext facesContext, UIComponent parent, final Object selectedValue) {
    List<Object> missingItems = (List<Object>) getMissingSelectedValues(facesContext, parent);
    missingItems.clear();
    if (selectedValue != null) {
      missingItems.add(selectedValue);
    }
  }

  public void updateMissingSelectedItems(FacesContext facesContext, UIComponent parent, final Object[] selectedValues) {
    List<Object> missingItems = (List<Object>) getMissingSelectedValues(facesContext, parent);
    missingItems.clear();
    if (selectedValues != null) {
      Collections.addAll(missingItems, selectedValues);
    }
  }
}
