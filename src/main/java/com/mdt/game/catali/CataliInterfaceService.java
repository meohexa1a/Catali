package com.mdt.game.catali;

import com.mdt.mindustry.menu.MenuOption;
import com.mdt.mindustry.menu.MenuService;
import com.mdt.mindustry.popup.PopupContent;
import com.mdt.mindustry.popup.PopupProvider;

import lombok.RequiredArgsConstructor;
import mindustry.gen.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliInterfaceService {
    private final MenuService menuService;

//    private

    // !------------------------------------------------------!

    public List<PopupContent> popupContents(Player player) {
        var list = new ArrayList<PopupContent>();

        return list;
    }

    // !------------------------------------------------------!


}
