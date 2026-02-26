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

function shuttleKeepSelectedOrder() {
  document.querySelectorAll("tobago-select-many-shuttle").forEach((shuttle) => {
    const unselected = shuttle.querySelector(".tobago-unselected");
    const selected = shuttle.querySelector(".tobago-selected");
    const addButtons = shuttle.querySelectorAll(".tobago-controls button[id='" + shuttle.id + "::addAll'], .tobago-controls button[id='" + shuttle.id + "::add']");
    const removeButtons = shuttle.querySelectorAll(".tobago-controls button[id='" + shuttle.id + "::removeAll'], .tobago-controls button[id='" + shuttle.id + "::remove']");
    const hiddenSelect = shuttle.querySelector("select[id='" + shuttle.id + "::hidden']");

    sortSelection(shuttle);

    unselected.addEventListener("dblclick", () => {
      sortSelection(shuttle);
    });
    addButtons.forEach((button) => {
      button.addEventListener("click", () => {
        sortSelection(shuttle);
      });
    });
    removeButtons.forEach((button) => {
      button.addEventListener("click", () => {
        sortSelection(shuttle);
      });
    });
    selected.addEventListener("dblclick", () => {
      sortSelection(shuttle);
    });
  });
}

function sortSelection(shuttle) {
  const unselectedOptions = shuttle.querySelectorAll(".tobago-unselected option");
  const selectedOptions = shuttle.querySelectorAll(".tobago-selected option");
  const hiddenSelect = shuttle.querySelector("select[id='" + shuttle.id + "::hidden']");
  const hiddenSelectOptions = hiddenSelect.querySelectorAll("option");

  const order = [];
  unselectedOptions.forEach((option) => order.push(option.value));
  selectedOptions.forEach((option) => order.push(option.value));

  const options = Array.from(hiddenSelectOptions);
  options.sort((a, b) => {
    const indexA = order.indexOf(a.value);
    const indexB = order.indexOf(b.value);
    return indexA - indexB;
  });
  options.forEach((option) => hiddenSelect.appendChild(option));
}

document.addEventListener("DOMContentLoaded", function () {
  shuttleKeepSelectedOrder();
});
