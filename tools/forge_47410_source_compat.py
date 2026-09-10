#!/usr/bin/env python3
"""Apply the Forge 47.4.10 source compatibility pass.

Questlog 3.3.3 uses several anonymous AbstractButton subclasses in the editor GUI.
The current 1.20.1 Forge 47.4.10 + ModDevGradle pipeline emits duplicate synthetic
constructor parameter names for those anonymous classes. This script converts only
those affected buttons to the concrete callback-driven FunctionalButton class.

The replacements are deliberately narrow and assert their expected source anchors so
future upstream changes fail loudly rather than being rewritten silently.
"""

from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
SCREEN_DIR = ROOT / "common/src/main/java/org/infernalstudios/questlog/client/gui/screen"


def replace_region(text: str, start: str, next_start: str, replacement: str, label: str) -> str:
    pattern = re.compile(re.escape(start) + r".*?(?=\n    " + re.escape(next_start) + r")", re.S)
    text, count = pattern.subn(replacement.rstrip(), text, count=1)
    if count != 1:
        raise RuntimeError(f"{label}: expected one source region, found {count}")
    return text


def patch_questlog_screen() -> None:
    path = SCREEN_DIR / "QuestlogScreen.java"
    text = path.read_text(encoding="utf-8")

    text = replace_region(
        text,
        "    private @NotNull AbstractButton getAddChapBtn(int x, int y) {",
        "private @NotNull AbstractButton getEditChapBtn(int x, int y) {",
        '''    private @NotNull AbstractButton getAddChapBtn(int x, int y) {
        FunctionalButton addChapBtn = new FunctionalButton(
                x, y, 20, 20,
                Component.translatable("questlog.editor.add_chapter"),
                () -> {
                    if (minecraft != null) {
                        minecraft.setScreen(new ChapterEditorScreen(QuestlogScreen.this, null));
                    }
                },
                (button, ps, mouseX, mouseY, partialTicks) -> {
                    boolean hovered = button.isHoveredOrFocused();
                    ps.blit(EDITOR_CHAPTER_PLUS_TEXTURE, button.getX() + 2, button.getY() + 2, 0, 0, 16, 16, 16, 16);
                    if (hovered) {
                        ps.fill(button.getX(), button.getY(), button.getX() + 20, button.getY() + 20, 0x40FFFFFF);
                    }
                }
        );
        addChapBtn.setTooltip(Tooltip.create(Component.translatable("questlog.editor.add_chapter")));
        return addChapBtn;
    }
''',
        "QuestlogScreen#getAddChapBtn",
    )

    text = replace_region(
        text,
        "    private @NotNull AbstractButton getEditChapBtn(int x, int y) {",
        "private @NotNull AbstractButton getAddQuestBtn(int x, int y) {",
        '''    private @NotNull AbstractButton getEditChapBtn(int x, int y) {
        FunctionalButton editChapBtn = new FunctionalButton(
                x, y, 20, 20,
                Component.translatable("questlog.editor.edit_chapter"),
                () -> {
                    if (minecraft != null) {
                        minecraft.setScreen(new ChapterEditorScreen(QuestlogScreen.this, currentChapter));
                    }
                },
                (button, ps, mouseX, mouseY, partialTicks) -> {
                    boolean hovered = button.isHoveredOrFocused();
                    ps.blit(EDITOR_CHAPTER_GEAR_TEXTURE, button.getX() + 2, button.getY() + 2, 0, 0, 16, 16, 16, 16);
                    if (hovered) {
                        ps.fill(button.getX(), button.getY(), button.getX() + 20, button.getY() + 20, 0x40FFFFFF);
                    }
                }
        );
        editChapBtn.setTooltip(Tooltip.create(Component.translatable("questlog.editor.edit_chapter")));
        return editChapBtn;
    }
''',
        "QuestlogScreen#getEditChapBtn",
    )

    text = replace_region(
        text,
        "    private @NotNull AbstractButton getAddQuestBtn(int x, int y) {",
        "private void refreshQuestListOnly() {",
        '''    private @NotNull AbstractButton getAddQuestBtn(int x, int y) {
        FunctionalButton addQuestBtn = new FunctionalButton(
                x, y, 20, 20,
                Component.translatable("questlog.editor.add_quest"),
                () -> {
                    if (minecraft != null) {
                        minecraft.setScreen(new QuestEditorScreen(QuestlogScreen.this));
                    }
                },
                (button, ps, mouseX, mouseY, partialTicks) -> {
                    boolean hovered = button.isHoveredOrFocused();
                    ps.blit(EDITOR_QUEST_PLUS_TEXTURE, button.getX() + 2, button.getY() + 2, 0, 0, 16, 16, 16, 16);
                    if (hovered) {
                        ps.fill(button.getX(), button.getY(), button.getX() + 20, button.getY() + 20, 0x40FFFFFF);
                    }
                }
        );
        addQuestBtn.setTooltip(Tooltip.create(Component.translatable("questlog.editor.add_quest")));
        return addQuestBtn;
    }
''',
        "QuestlogScreen#getAddQuestBtn",
    )

    text = replace_region(
        text,
        "    private void buildSearch() {",
        "private void buildTabs() {",
        '''    private void buildSearch() {
        int listWidth = 245;
        int listHeight = 136;
        int listX = (this.width - listWidth) / 2 + 1 + Questlog.getConfig().gui.mainPanelX;
        int listY = (this.height - listHeight) / 2 + 1 + Questlog.getConfig().gui.mainPanelY;

        int searchY = listY - 32 + Questlog.getConfig().gui.searchBarY;
        int searchWidth = this.searchExpanded ? 193 : 28;
        int searchX = listX + listWidth - searchWidth + 12 + Questlog.getConfig().gui.searchBarX;

        this.addRenderableWidget(new FunctionalButton(
                searchX, searchY, searchWidth, 18, Component.empty(),
                () -> {
                    QuestlogScreen.this.searchExpanded = !QuestlogScreen.this.searchExpanded;
                    if (!QuestlogScreen.this.searchExpanded) {
                        QuestlogScreen.this.searchQuery = "";
                    }
                    QuestlogScreen.this.refreshList();
                },
                (button, ps, mouseX, mouseY, partialTicks) -> {
                    boolean hoverToggle = button.isMouseOver(mouseX, mouseY)
                            && (!QuestlogScreen.this.searchExpanded || mouseX >= button.getX() + button.getWidth() - 28);
                    if (QuestlogScreen.this.searchExpanded) {
                        (hoverToggle ? QuestlogGuiSet.DEFAULT.searchTabExpandedHovered : QuestlogGuiSet.DEFAULT.searchTabExpanded)
                                .blit(ps, button.getX() - 30, button.getY() - 19);
                    } else {
                        (hoverToggle ? QuestlogGuiSet.DEFAULT.searchTabMinimizedHovered : QuestlogGuiSet.DEFAULT.searchTabMinimized)
                                .blit(ps, button.getX() - 15, button.getY() - 19);
                    }
                },
                (button, mouseX, mouseY, mouseButton) -> {
                    if (button.isMouseOver(mouseX, mouseY) && mouseButton == GLFW.GLFW_MOUSE_BUTTON_1) {
                        if (QuestlogScreen.this.searchExpanded && mouseX < button.getX() + button.getWidth() - 28) {
                            return false;
                        }
                        button.onPress();
                        return true;
                    }
                    return false;
                }
        ));

        if (this.searchExpanded) {
            this.searchBox = new NoShadowEditBox(this.font, searchX + 86, searchY + 7, searchWidth - 36, 16, Component.translatable("itemGroup.search"));
            this.searchBox.setMaxLength(50);
            this.searchBox.setValue(this.searchQuery);
            this.searchBox.setBordered(false);
            this.searchBox.setTextColor(Questlog.getConfig().colors.searchTextColor);
            this.searchBox.setResponder(query -> {
                this.searchQuery = query;
                this.refreshQuestListOnly();
            });
            this.addRenderableWidget(this.searchBox);

            this.addRenderableWidget(new FunctionalButton(
                    searchX - 36, searchY + 2, 14, 14, Component.empty(),
                    () -> {
                        QuestlogScreen.this.hideCompleted = !QuestlogScreen.this.hideCompleted;
                        QuestlogScreen.this.refreshList();
                    },
                    (button, ps, mouseX, mouseY, partialTicks) -> {
                        boolean hovered = button.isMouseOver(mouseX, mouseY);
                        if (QuestlogScreen.this.hideCompleted) {
                            (hovered ? QuestlogGuiSet.DEFAULT.hiddenButtonHovered : QuestlogGuiSet.DEFAULT.hiddenButton)
                                    .blit(ps, button.getX() - 6, button.getY() - 6);
                        } else {
                            (hovered ? QuestlogGuiSet.DEFAULT.visibleButtonHovered : QuestlogGuiSet.DEFAULT.visibleButton)
                                    .blit(ps, button.getX() - 6, button.getY() - 6);
                        }
                    }
            ));

            this.addRenderableWidget(new FunctionalButton(
                    searchX - 18, searchY + 2, 14, 14, Component.empty(),
                    () -> {
                        QuestlogScreen.this.descriptionsCondensed = !QuestlogScreen.this.descriptionsCondensed;
                        QuestlogScreen.this.refreshList();
                    },
                    (button, ps, mouseX, mouseY, partialTicks) -> {
                        boolean hovered = button.isMouseOver(mouseX, mouseY);
                        if (QuestlogScreen.this.descriptionsCondensed) {
                            (hovered ? QuestlogGuiSet.DEFAULT.expandButtonHovered : QuestlogGuiSet.DEFAULT.expandButton)
                                    .blit(ps, button.getX() - 6, button.getY() - 6);
                        } else {
                            (hovered ? QuestlogGuiSet.DEFAULT.condenseButtonHovered : QuestlogGuiSet.DEFAULT.condenseButton)
                                    .blit(ps, button.getX() - 6, button.getY() - 6);
                        }
                    }
            ));
        } else {
            this.searchBox = null;
        }
    }
''',
        "QuestlogScreen#buildSearch",
    )

    path.write_text(text, encoding="utf-8")


def patch_quest_editor_screen() -> None:
    path = SCREEN_DIR / "QuestEditorScreen.java"
    text = path.read_text(encoding="utf-8")

    import_anchor = "import org.infernalstudios.questlog.client.gui.components.NoShadowEditBox;"
    import_line = "import org.infernalstudios.questlog.client.gui.components.FunctionalButton;\n"
    if import_line.strip() not in text:
        if import_anchor not in text:
            raise RuntimeError("QuestEditorScreen imports: anchor missing")
        text = text.replace(import_anchor, import_line + import_anchor, 1)

    tab_pattern = re.compile(
        r"                AbstractButton tabButton = new AbstractButton\(startX \+ i \* tabBtnSpacing, panel2Y \+ 8, tabBtnSize, tabBtnSize, tabTooltip\) \{.*?\n                \};\n\n                tabButton\.setTooltip",
        re.S,
    )
    tab_replacement = '''                AbstractButton tabButton = new FunctionalButton(
                        startX + i * tabBtnSpacing, panel2Y + 8, tabBtnSize, tabBtnSize, tabTooltip,
                        () -> {
                            if (!isCurrentTab) {
                                QuestEditorScreen.this.saveTemporaryState();
                                QuestEditorScreen.this.nestingStack.clear();
                                QuestEditorScreen.this.currentNestedList = null;
                                QuestEditorScreen.this.activeTab = t;
                                QuestEditorScreen.this.listPage = 0;
                                QuestEditorScreen.this.rebuildWidgets();
                            }
                        },
                        (button, ps, mouseX, mouseY, partialTicks) -> {
                            boolean hovered = button.isHoveredOrFocused();
                            ResourceLocation drawTex;
                            if (isCurrentTab) {
                                drawTex = switch (t) {
                                    case OBJECTIVES -> TAB_OBJECTIVES_SELECTED;
                                    case PREREQUISITES -> TAB_PREREQUISITES_SELECTED;
                                    case REWARDS -> TAB_REWARDS_SELECTED;
                                    default -> TAB_SETTINGS_SELECTED;
                                };
                            } else if (hovered) {
                                drawTex = switch (t) {
                                    case OBJECTIVES -> TAB_OBJECTIVES_HIGHLIGHTED;
                                    case PREREQUISITES -> TAB_PREREQUISITES_HIGHLIGHTED;
                                    case REWARDS -> TAB_REWARDS_HIGHLIGHTED;
                                    default -> TAB_SETTINGS_HIGHLIGHTED;
                                };
                            } else {
                                drawTex = switch (t) {
                                    case OBJECTIVES -> TAB_OBJECTIVES_TEXTURE;
                                    case PREREQUISITES -> TAB_PREREQUISITES_TEXTURE;
                                    case REWARDS -> TAB_REWARDS_TEXTURE;
                                    default -> TAB_SETTINGS_TEXTURE;
                                };
                            }
                            ps.blit(drawTex, button.getX() + 2, button.getY() + 2, 0, 0, 16, 16, 16, 16);
                        }
                );

                tabButton.setTooltip'''
    text, count = tab_pattern.subn(tab_replacement, text, count=1)
    if count != 1:
        raise RuntimeError(f"QuestEditorScreen tab button: expected one block, found {count}")

    text = replace_region(
        text,
        "    private AbstractButton createImageButton(int x, int y, ResourceLocation texture, ResourceLocation highlightedTexture, Runnable onPress) {",
        "private List<String> getSuggestions(String query) {",
        '''    private AbstractButton createImageButton(int x, int y, ResourceLocation texture, ResourceLocation highlightedTexture, Runnable onPress) {
        return new FunctionalButton(
                x, y, 16, 16, Component.empty(),
                onPress,
                (button, ps, mouseX, mouseY, partialTicks) -> {
                    boolean hovered = button.isHoveredOrFocused();
                    ResourceLocation tex = hovered ? highlightedTexture : texture;
                    ps.blit(tex, button.getX(), button.getY(), 0, 0, 16, 16, 16, 16);
                }
        );
    }
''',
        "QuestEditorScreen#createImageButton",
    )

    path.write_text(text, encoding="utf-8")


def patch_chapter_editor_screen() -> None:
    path = SCREEN_DIR / "ChapterEditorScreen.java"
    text = path.read_text(encoding="utf-8")

    import_anchor = "import org.infernalstudios.questlog.client.gui.components.NoShadowEditBox;"
    import_line = "import org.infernalstudios.questlog.client.gui.components.FunctionalButton;\n"
    if import_line.strip() not in text:
        if import_anchor not in text:
            raise RuntimeError("ChapterEditorScreen imports: anchor missing")
        text = text.replace(import_anchor, import_line + import_anchor, 1)

    pattern = re.compile(
        r"            AbstractButton actionButton = new AbstractButton\(panel2X \+ 125, rowY \+ 1, 16, 16, Component\.empty\(\)\) \{.*?\n            \};\n\n            actionButton\.setTooltip",
        re.S,
    )
    replacement = '''            AbstractButton actionButton = new FunctionalButton(
                    panel2X + 125, rowY + 1, 16, 16, Component.empty(),
                    () -> {
                        ChapterEditorScreen.this.saveTemporaryState();
                        if (inThisChapter) {
                            qJson.addProperty("chapter", currentChapPath.equals("main") ? "" : "main");
                        } else {
                            if (!currentChapPath.isEmpty()) {
                                qJson.addProperty("chapter", currentChapPath);
                            } else {
                                String futureId = ChapterEditorScreen.this.idBox.getValue().trim();
                                try {
                                    ResourceLocation futureRl = futureId.contains(":") ?
                                            ResourceLocation.tryParse(futureId) :
                                            new ResourceLocation(Questlog.MODID, futureId);
                                    if (futureRl != null) {
                                        qJson.addProperty("chapter", futureRl.getPath());
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                        DefinitionUtil.putCachedQuest(qKey, qJson);
                        Services.PLATFORM.sendPacketToServer(new QuestEditSavePacket(qKey, qJson.toString()));
                        ChapterEditorScreen.this.rebuildWidgets();
                    },
                    (button, ps, mouseX, mouseY, partialTicks) -> {
                        boolean hovered = button.isHoveredOrFocused();
                        ResourceLocation tex = hovered ? iconHighlight : icon;
                        ps.blit(tex, button.getX(), button.getY(), 0, 0, 16, 16, 16, 16);
                    }
            );

            actionButton.setTooltip'''
    text, count = pattern.subn(replacement, text, count=1)
    if count != 1:
        raise RuntimeError(f"ChapterEditorScreen action button: expected one block, found {count}")

    path.write_text(text, encoding="utf-8")


def main() -> None:
    patch_questlog_screen()
    patch_quest_editor_screen()
    patch_chapter_editor_screen()
    print("Forge 47.4.10 AbstractButton compatibility pass applied successfully.")


if __name__ == "__main__":
    main()
