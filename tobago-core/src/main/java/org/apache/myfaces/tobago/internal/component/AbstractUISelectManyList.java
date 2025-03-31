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

import org.apache.myfaces.tobago.component.SupportFieldId;
import org.apache.myfaces.tobago.component.SupportsAutoSpacing;
import org.apache.myfaces.tobago.component.SupportsFilter;
import org.apache.myfaces.tobago.component.SupportsHelp;
import org.apache.myfaces.tobago.component.SupportsLabelLayout;
import org.apache.myfaces.tobago.component.Visual;
import org.apache.myfaces.tobago.internal.taglib.component.SelectManyListTagDeclaration;
import org.apache.myfaces.tobago.internal.util.SelectItemUtils;
import org.apache.myfaces.tobago.util.ComponentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * {@link SelectManyListTagDeclaration}
 */
public abstract class AbstractUISelectManyList extends AbstractUISelectManyBase
    implements SupportsAutoSpacing, Visual, SupportsLabelLayout, ClientBehaviorHolder, SupportsHelp, SupportFieldId,
    SupportsFilter {

  private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private transient boolean nextToRenderIsLabel;
  private transient Optional<UIComponent> filteredSelectItemsOptional = null; //set "null" to detect if initialized
  private transient List<SelectItem> itemList = null;

  @Override
  public Object[] getSelectedValues() {
    final Object value = getValue();
    if (value instanceof Collection) {
      return ((Collection) value).toArray();
    } else {
      return (Object[]) value;
    }
  }

  @Override
  public String getFieldId(final FacesContext facesContext) {
    return getClientId(facesContext) + ComponentUtils.SUB_SEPARATOR + "field";
  }

  public abstract Integer getTabIndex();

  public abstract boolean isDisabled();

  public abstract boolean isExpanded();

  public boolean isError() {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    return !isValid() || !facesContext.getMessageList(getClientId(facesContext)).isEmpty();
  }

  public abstract String getFilter();

  public abstract boolean isLocalMenu();

  public abstract String getFooter();

  @Override
  public boolean isNextToRenderIsLabel() {
    return nextToRenderIsLabel;
  }

  @Override
  public void setNextToRenderIsLabel(final boolean nextToRenderIsLabel) {
    this.nextToRenderIsLabel = nextToRenderIsLabel;
  }

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
        abstractUIFilteredSelectItems.updateMissingSelectedItems(facesContext, this, getSelectedValues());

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
