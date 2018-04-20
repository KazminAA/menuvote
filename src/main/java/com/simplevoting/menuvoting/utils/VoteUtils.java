package com.simplevoting.menuvoting.utils;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.to.VoteTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoteUtils {

    private final MenuService menuService;
    private final UserService userService;

    @Autowired
    public VoteUtils(MenuService menuService, UserService userService) {
        this.menuService = menuService;
        this.userService = userService;
    }

    public Vote getFromTo(VoteTo voteTo) {
        return new Vote(voteTo.getDate(),
                userService.get(voteTo.getUserId()),
                menuService.get(voteTo.getMenu_id()));
    }

    public Vote getVote(int menu_id, int user_id) {
        Menu menu = menuService.get(menu_id);
        User user = userService.get(user_id);
        return new Vote(menu.getDate(), user, menu);
    }
}
