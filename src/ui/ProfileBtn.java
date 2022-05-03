/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import models.Profile;

/**
 *
 * @author a21samuelnc
 */
public class ProfileBtn extends Button {
    private Profile profile;
    
    public ProfileBtn(Profile profile) {
        super(profile.name);
        
        this.profile = profile;
    }
    
    public Profile getProfile() {
        return profile;
    }
}
