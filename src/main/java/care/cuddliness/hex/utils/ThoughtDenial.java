package care.cuddliness.hex.utils;

import care.cuddliness.hex.HexCore;

import java.util.List;

public class ThoughtDenial {
    private HexCore hexCore;
    private List<String> deniedWords;

    public ThoughtDenial (HexCore hexCore){
        this.hexCore = hexCore;
        this.deniedWords = hexCore.getConfig().getStringList("thought_denial");
    }

    public String denyThoughts(String input){
        StringBuilder returnString = new StringBuilder();
        for(String word : deniedWords)
            for (int i = 0; i < word.length(); i++)
                returnString.append(String.format("|((?<=%s)%s(?=%s))",
                        word.substring(0, i), word.charAt(i), word.substring(i + 1)));
        return input.replaceAll(returnString.substring(1), "_");
    }
}
