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

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import org.apache.myfaces.tobago.component.SupportFieldId;
import org.apache.myfaces.tobago.internal.util.SelectItemUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractUISelectOneList extends AbstractUISelectOneBase implements SupportFieldId {

  private transient Optional<UIComponent> filteredSelectItemsOptional = null; //set "null" to detect if initialized
  private transient List<SelectItem> itemList = null;

  @Override
  public String getFieldId(final FacesContext facesContext) {
    return getClientId(facesContext) + ComponentUtils.SUB_SEPARATOR + "field";
  }

  public abstract String getFilter();

  public abstract boolean isExpanded();

  public abstract boolean isLocalMenu();

  public abstract String getFooter();

  public AbstractUIFilteredSelectItems getAbstractUIFilteredSelectItems() {
    if (filteredSelectItemsOptional == null) {
      filteredSelectItemsOptional = getChildren().stream()
          .filter(AbstractUIFilteredSelectItems.class::isInstance)
          .findFirst();
    }
    return (AbstractUIFilteredSelectItems) filteredSelectItemsOptional.orElse(null);
  }

  public List<SelectItem> getItemList(FacesContext facesContext) {
    final AbstractUIFilteredSelectItems abstractUIFilteredSelectItems = getAbstractUIFilteredSelectItems();
    if (abstractUIFilteredSelectItems != null) {
      if (itemList == null) {
        abstractUIFilteredSelectItems.updateMissingSelectedItems(facesContext, this, getValue());

        itemList = SelectItemUtils.getItemList(facesContext, this);

        List<?> missingSelectedValues = abstractUIFilteredSelectItems.getMissingSelectedValues(facesContext, this);
        List<SelectItem> removeSelectItems = new ArrayList<>();
        for (SelectItem selectItem : itemList.subList(missingSelectedValues.size(), itemList.size())) {
          if (missingSelectedValues.contains(selectItem.getValue())) {
            removeSelectItems.add(selectItem);
          }
        }
        for (SelectItem selectItem : removeSelectItems) {
          missingSelectedValues.remove(selectItem.getValue());
          itemList.remove(selectItem);
        }
      }

      return itemList;
    } else {
      return SelectItemUtils.getItemList(facesContext, this);
    }
  }
}
