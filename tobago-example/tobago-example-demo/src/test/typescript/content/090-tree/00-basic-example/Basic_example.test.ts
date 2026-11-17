/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {expect, test} from "@playwright/test";

test.describe("090-tree/00-basic-example/Basic_example.xhtml", () => {

  test.beforeEach(async ({page}) => {
    await page.goto("/content/090-tree/00-basic-example/Basic_example.xhtml");
  });

  test("simpleTree", async ({page}) => {
    const tree = page.locator("tobago-tree[id='page:mainForm:simpleTree']");
    const musicToggle = tree.locator("tobago-tree-node").filter({hasText: "Music"}).locator(".tobago-toggle");
    const classicLabel = tree.locator("tobago-tree-node").filter({hasText: "Classic"});
    const popLabel = tree.locator("tobago-tree-node").filter({hasText: "Pop"});
    const worldToggle = tree.locator("tobago-tree-node").filter({hasText: "World"}).locator(".tobago-toggle");
    const caribLabel = tree.locator("tobago-tree-node").filter({hasText: "Carib"});
    const africaLabel = tree.locator("tobago-tree-node").filter({hasText: "Africa"});
    const scienceToggle = tree.locator("tobago-tree-node").filter({hasText: "Science"}).locator(".tobago-toggle");
    const mathematicsToggle = tree.locator("tobago-tree-node").filter({hasText: "Mathematics"}).locator(".tobago-toggle");
    const astronomyToggle = tree.locator("tobago-tree-node").filter({hasText: "Astronomy"}).locator(".tobago-toggle");

    await musicToggle.focus();
    await expect(musicToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(astronomyToggle).toBeFocused();

    await page.keyboard.down("Shift");
    await page.keyboard.press("Tab");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(musicToggle).toBeFocused();
    await page.keyboard.up("Shift");

    await page.keyboard.press("ArrowDown");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(astronomyToggle).toBeFocused();

    await page.keyboard.press("ArrowUp");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(musicToggle).toBeFocused();

    await page.keyboard.press("ArrowRight");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(caribLabel).not.toBeVisible();
    await expect(africaLabel).not.toBeVisible();
    await page.keyboard.press("ArrowRight");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(0);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(1);
    await expect(caribLabel).toBeVisible();
    await expect(africaLabel).toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(caribLabel).not.toBeVisible();
    await expect(africaLabel).not.toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(musicToggle).toBeFocused();
    await expect(musicToggle.locator("> .bi-plus-square")).toHaveCount(0);
    await expect(musicToggle.locator("> .bi-dash-square")).toHaveCount(1);
    await expect(classicLabel).toBeVisible();
    await expect(popLabel).toBeVisible();
    await expect(worldToggle).toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(musicToggle).toBeFocused();
    await expect(musicToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(musicToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(classicLabel).not.toBeVisible();
    await expect(popLabel).not.toBeVisible();
    await expect(worldToggle).not.toBeVisible();

    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(musicToggle).toBeFocused();
  });

  test("treeIcon with treeIndent", async ({page}) => {
    const tree = page.locator("tobago-tree[id='page:mainForm:treeIconWithIndent']");
    const musicToggle = tree.locator("tobago-tree-node").filter({hasText: "Music"}).locator(".tobago-toggle").first();
    const classicLabel = tree.locator("tobago-tree-node").filter({hasText: "Classic"});
    const popLabel = tree.locator("tobago-tree-node").filter({hasText: "Pop"});
    const worldToggle = tree.locator("tobago-tree-node").filter({hasText: "World"}).locator(".tobago-toggle").first();
    const caribLabel = tree.locator("tobago-tree-node").filter({hasText: "Carib"});
    const africaLabel = tree.locator("tobago-tree-node").filter({hasText: "Africa"});
    const scienceToggle = tree.locator("tobago-tree-node").filter({hasText: "Science"}).locator(".tobago-toggle").first();
    const mathematicsToggle = tree.locator("tobago-tree-node").filter({hasText: "Mathematics"}).locator(".tobago-toggle").first();
    const astronomyToggle = tree.locator("tobago-tree-node").filter({hasText: "Astronomy"}).locator(".tobago-toggle").first();

    await musicToggle.focus();
    await expect(musicToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(astronomyToggle).toBeFocused();

    await page.keyboard.down("Shift");
    await page.keyboard.press("Tab");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(musicToggle).toBeFocused();
    await page.keyboard.up("Shift");

    await page.keyboard.press("ArrowDown");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(astronomyToggle).toBeFocused();

    await page.keyboard.press("ArrowUp");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(musicToggle).toBeFocused();

    await page.keyboard.press("ArrowRight");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(caribLabel).not.toBeVisible();
    await expect(africaLabel).not.toBeVisible();
    await page.keyboard.press("ArrowRight");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(0);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(1);
    await expect(caribLabel).toBeVisible();
    await expect(africaLabel).toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(caribLabel).not.toBeVisible();
    await expect(africaLabel).not.toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(musicToggle).toBeFocused();
    await expect(musicToggle.locator("> .bi-plus-square")).toHaveCount(0);
    await expect(musicToggle.locator("> .bi-dash-square")).toHaveCount(1);
    await expect(classicLabel).toBeVisible();
    await expect(popLabel).toBeVisible();
    await expect(worldToggle).toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(musicToggle).toBeFocused();
    await expect(musicToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(musicToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(classicLabel).not.toBeVisible();
    await expect(popLabel).not.toBeVisible();
    await expect(worldToggle).not.toBeVisible();

    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(musicToggle).toBeFocused();
  });

  test("treeIcon without treeIndent", async ({page}) => {
    const tree = page.locator("tobago-tree[id='page:mainForm:treeIconWithoutIndent']");
    const musicToggle = tree.locator("tobago-tree-node").filter({hasText: "Music"}).locator(".tobago-toggle");
    const classicLabel = tree.locator("tobago-tree-node").filter({hasText: "Classic"});
    const popLabel = tree.locator("tobago-tree-node").filter({hasText: "Pop"});
    const worldToggle = tree.locator("tobago-tree-node").filter({hasText: "World"}).locator(".tobago-toggle");
    const caribLabel = tree.locator("tobago-tree-node").filter({hasText: "Carib"});
    const africaLabel = tree.locator("tobago-tree-node").filter({hasText: "Africa"});
    const scienceToggle = tree.locator("tobago-tree-node").filter({hasText: "Science"}).locator(".tobago-toggle");
    const mathematicsToggle = tree.locator("tobago-tree-node").filter({hasText: "Mathematics"}).locator(".tobago-toggle");
    const astronomyToggle = tree.locator("tobago-tree-node").filter({hasText: "Astronomy"}).locator(".tobago-toggle");

    await musicToggle.focus();
    await expect(musicToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(astronomyToggle).toBeFocused();

    await page.keyboard.down("Shift");
    await page.keyboard.press("Tab");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("Tab");
    await expect(musicToggle).toBeFocused();
    await page.keyboard.up("Shift");

    await page.keyboard.press("ArrowDown");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("ArrowDown");
    await expect(astronomyToggle).toBeFocused();

    await page.keyboard.press("ArrowUp");
    await expect(mathematicsToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(worldToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(musicToggle).toBeFocused();

    await page.keyboard.press("ArrowRight");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(caribLabel).not.toBeVisible();
    await expect(africaLabel).not.toBeVisible();
    await page.keyboard.press("ArrowRight");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(0);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(1);
    await expect(caribLabel).toBeVisible();
    await expect(africaLabel).toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(worldToggle).toBeFocused();
    await expect(worldToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(worldToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(caribLabel).not.toBeVisible();
    await expect(africaLabel).not.toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(musicToggle).toBeFocused();
    await expect(musicToggle.locator("> .bi-plus-square")).toHaveCount(0);
    await expect(musicToggle.locator("> .bi-dash-square")).toHaveCount(1);
    await expect(classicLabel).toBeVisible();
    await expect(popLabel).toBeVisible();
    await expect(worldToggle).toBeVisible();

    await page.keyboard.press("ArrowLeft");
    await expect(musicToggle).toBeFocused();
    await expect(musicToggle.locator("> .bi-plus-square")).toHaveCount(1);
    await expect(musicToggle.locator("> .bi-dash-square")).toHaveCount(0);
    await expect(classicLabel).not.toBeVisible();
    await expect(popLabel).not.toBeVisible();
    await expect(worldToggle).not.toBeVisible();

    await page.keyboard.press("Tab");
    await expect(scienceToggle).toBeFocused();
    await page.keyboard.press("ArrowUp");
    await expect(musicToggle).toBeFocused();
  });
});
