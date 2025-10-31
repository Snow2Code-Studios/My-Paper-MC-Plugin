package org.snow2code.util;

public class SemiChatColors {
    // !!! THIS SHIT DOESN'T WORK!! WHY? HAS I EVER?

    public static String SYMBOL = "§"; // \u00A7

    // public static String RED = SYMBOL + "#FF5555";
	public static final String BLACK = CustomColor("000000");
	public static final String DARK_BLUE = CustomColor("0000AA");
	public static final String DARK_GREEN = CustomColor("00AA00");
	public static final String DARK_AQUA = CustomColor("00AAAA");
	public static final String DARK_RED = CustomColor("AA0000");
	public static final String DARK_PURPLE = CustomColor("AA00AA");
	public static final String GOLD = CustomColor("FFAA00");
	public static final String GRAY = CustomColor("AAAAAA");
	public static final String DARK_GRAY = CustomColor("555555");
	public static final String BLUE = CustomColor("5555FF");
	public static final String GREEN = CustomColor("55FF55");
	public static final String AQUA = CustomColor("55FFFF");
//	public static final TextColor RED = TextColor.fromHexString("#FF5555");
	public static final String LIGHT_PURPLE = CustomColor("FF55FF");
	public static final String YELLOW = CustomColor("FFFF55");
	public static final String WHITE = CustomColor("FFFFFF");
	// public static final String MAGIC = "§k";
	public static final String BOLD = "§l";
	public static final String STRIKETHROUGH = "§m";
	public static final String UNDERLINE = "§n";
	public static final String ITALIC = "§o";
    public static final String RESET = "§r";
	

    /**
	 * Make a custom color
	 * @param code  the color code
	 * @return  custom chat color
	 */
    public static String CustomColor(String code) {
        return "\u00A7#" + code;
    }
}
