package com.mdt.game.catali;

import com.mdt.mindustry.menu.MenuService;

import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliInterfaceService {
    private final MenuService menuService;

}
