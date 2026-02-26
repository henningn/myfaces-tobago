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

function shuttleSort() {
  document.querySelectorAll("tobago-select-many-shuttle").forEach((shuttle) => {
    const unselected = shuttle.querySelector(".tobago-unselected");
    const selected = shuttle.querySelector(".tobago-selected");
    const addButtons = shuttle.querySelectorAll(".tobago-controls button[id='" + shuttle.id + "::addAll'], .tobago-controls button[id='" + shuttle.id + "::add']");
    const removeButtons = shuttle.querySelectorAll(".tobago-controls button[id='" + shuttle.id + "::removeAll'], .tobago-controls button[id='" + shuttle.id + "::remove']");
    const reference = shuttle.querySelector("select[id='" + shuttle.id + "::hidden']");

    sortOptions(unselected, reference);
    sortOptions(selected, reference);

    unselected.addEventListener("dblclick", () => {
      sortOptions(selected, reference);
    });
    addButtons.forEach((button) => {
      button.addEventListener("click", () => {
        sortOptions(selected, reference);
      });
    });
    removeButtons.forEach((button) => {
      button.addEventListener("click", () => {
        sortOptions(unselected, reference);
      });
    });
    selected.addEventListener("dblclick", () => {
      sortOptions(unselected, reference);
    });
  });
}

function sortOptions(selectElement, referenceElement) {
  if (referenceElement) {
    const referenceOptions = Array.from(referenceElement.querySelectorAll("option"));
    const options = Array.from(selectElement.querySelectorAll("option"));
    options.sort((a, b) => {
      const indexA = referenceOptions.findIndex((o) => o.value === a.value && o.textContent === a.textContent);
      const indexB = referenceOptions.findIndex((o) => o.value === b.value && o.textContent === b.textContent);
      return indexA - indexB;
    });
    options.forEach((option) => selectElement.appendChild(option));
  }
}

document.addEventListener("DOMContentLoaded", function () {
  shuttleSort();
});
