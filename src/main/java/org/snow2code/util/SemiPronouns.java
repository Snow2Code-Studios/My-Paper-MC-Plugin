package org.snow2code.util;

import org.bukkit.entity.Player;
import org.snow2code.local.SemiSnow;

public class SemiPronouns {
    // Pronoun shit somewhat stolen from https://github.com/alexofp/bdcc (A nsfw game)
    /*
    static func genderToString(thegender):
        if(thegender == Male):
            return "male"
        if(thegender == Female):
            return "female"
        if(thegender == Androgynous):
            return "androgynous"
        if(thegender == Other):
            return "other"
        return "error?"
    */

    public static String GenderToPronouns(String thegender) {
        if ( thegender.equals("Male") ) {
            return "He/his";
        } else if ( thegender.equals("Female") ) {
            return "She/her";
        } else if ( thegender.equals("Androgynous") ) {
            return "They/their";
        } else if ( thegender.equals("Other") ) {
            return "It/its";
        }
        return "error?";
    }
        
    public static String GetGender(Player player) {
        // Hard coded genders for users cuz this plugin isn't gonna be big

        if ( SemiSnow.IsSnowy(player) ) {
            return "Female";
        }

        return "Male"; // Other
    }

    public static String GetPronounGender(Player player) {
        return GetGender(player);
    }

    public static String HeShe(Player player) {
        String gender = GetPronounGender(player);

        if ( gender.equals("Male") ) {
            return "he";
        } else if ( gender.equals("Female") ) {
            return "she";
        } else if ( gender.equals("Androgynous") ) {
            return "they";
        } else if ( gender.equals("Other") ) {
            return "it";
        }
        return "";
    }
    
    public static String HisHer(Player player) {
        String gender = GetPronounGender(player);

        if ( gender.equals("Male") ) {
            return "his";
        } else if ( gender.equals("Female") ) {
            return "her";
        } else if ( gender.equals("Androgynous") ) {
            return "their";
        } else if ( gender.equals("Other") ) {
            return "its";
        }
        return "";
    }
    
    public static String HimHer(Player player) {
        String gender = GetPronounGender(player);

        if ( gender.equals("Male") ) {
            return "him";
        } else if ( gender.equals("Female") ) {
            return "her";
        } else if ( gender.equals("Androgynous") ) {
            return "them";
        } else if ( gender.equals("Other") ) {
            return "it";
        }
        return "him";
    }
    
    public static String IsAre(Player player) {
        String gender = GetPronounGender(player);

        if ( gender.equals("Male") ) {
            return "is";
        } else if ( gender.equals("Female") ) {
            return "is";
        } else if ( gender.equals("Androgynous") ) {
            return "are";
        } else if ( gender.equals("Other") ) {
            return "is";
        }
        return "is";
    }
    
    public static String HeSHasHavehe(Player player) {
        String gender = GetPronounGender(player);

        if ( gender.equals("Male") ) {
            return "has";
        } else if ( gender.equals("Female") ) {
            return "has";
        } else if ( gender.equals("Androgynous") ) {
            return "have";
        } else if ( gender.equals("Other") ) {
            return "has";
        }

        return "has";
    }
    
    public static String HimselfHerself(Player player) {
        String gender = GetPronounGender(player);

        if ( gender.equals("Male") ) {
            return "himself";
        } else if ( gender.equals("Female") ) {
            return "herself";
        } else if ( gender.equals("Androgynous") ) {
            return "themself";
        } else if ( gender.equals("Other") ) {
            return "itself";
        }
        
        return "himself";
    }
}
